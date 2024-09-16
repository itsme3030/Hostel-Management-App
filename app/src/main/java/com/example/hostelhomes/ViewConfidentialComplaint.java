package com.example.hostelhomes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class ViewConfidentialComplaint extends AppCompatActivity {

    private ListView listView;
    private ComplaintAdapter adapter;
    private List<Model> complaintList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_confidential_complaint);

        // Initialize ListView and list
        listView = findViewById(R.id.listView_confidential_complaint);
        complaintList = new ArrayList<>();

        // Initialize Firebase reference
        databaseReference = FirebaseDatabase.getInstance().getReference("complaints/confidential_complaint");

        // Fetch complaints from Firebase
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    complaintList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Model complaint = snapshot.getValue(Model.class);
                        complaintList.add(complaint);
                    }
                    // Set adapter to ListView
                    adapter = new ComplaintAdapter(ViewConfidentialComplaint.this, complaintList);
                    listView.setAdapter(adapter);
                } else {
                    Toast.makeText(ViewConfidentialComplaint.this, "No complaints found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewConfidentialComplaint.this, "Failed to load complaints", Toast.LENGTH_SHORT).show();
            }
        });

        // Set item click listener for ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected complaint
                Model selectedComplaint = complaintList.get(position);

                // Open the dialog box
                showComplaintDialog(selectedComplaint);
            }
        });
    }

    // Function to show dialog box
    private void showComplaintDialog(Model complaint) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewConfidentialComplaint.this);
        builder.setTitle("Complaint Details");
        builder.setMessage("Complaint: " + complaint.getComplaintText() + "\nSolve the complaint?");

        // Yes Button (Mark as Done)
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Update complaint status to Done by searching for the matching timestamp
                updateComplaintStatus(complaint.getTimestamp(), "Done");
            }
        });

        // No Button (Mark as Pending)
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Update complaint status to Pending by searching for the matching timestamp
                updateComplaintStatus(complaint.getTimestamp(), "Pending");
            }
        });

        // Show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Function to update complaint status in Firebase by looping through children
    private void updateComplaintStatus(long timestamp, String newStatus) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean complaintFound = false;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Model complaint = snapshot.getValue(Model.class);

                    // Check if this is the complaint we need to update
                    if (complaint != null && complaint.getTimestamp() == timestamp) {
                        // Update the status field for this complaint
                        snapshot.getRef().child("status").setValue(newStatus)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(ViewConfidentialComplaint.this, "Complaint status updated to " + newStatus, Toast.LENGTH_SHORT).show();
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(ViewConfidentialComplaint.this, "Failed to update status", Toast.LENGTH_SHORT).show();
                                });
                        complaintFound = true;
                        break;
                    }
                }

                if (!complaintFound) {
                    Toast.makeText(ViewConfidentialComplaint.this, "Complaint not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewConfidentialComplaint.this, "Failed to update complaint", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
