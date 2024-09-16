package com.example.hostelhomes;

import android.os.Bundle;
import android.util.Log;
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

public class ViewFoodComplaint extends AppCompatActivity {

    private ListView listView;
    private FoodComplaintAdapter adapter;
    private List<FoodComplaintModel> complaintList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_food_complaint);

        // Initialize ListView and list
        listView = findViewById(R.id.listView_food_complaints);
        complaintList = new ArrayList<>();

        // Initialize Firebase reference
        databaseReference = FirebaseDatabase.getInstance().getReference("complaints/food_complaint");
        Log.d("dbref", "db ref");

        // Fetch complaints from Firebase
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d("dataSnapshot is exist", "onDataChange: ");
                    complaintList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        FoodComplaintModel complaint = snapshot.getValue(FoodComplaintModel.class);
                        complaintList.add(complaint);
                    }
                    // Set adapter to ListView
                    adapter = new FoodComplaintAdapter(ViewFoodComplaint.this, complaintList);
                    listView.setAdapter(adapter);
                } else {
                    Toast.makeText(ViewFoodComplaint.this, "No complaints found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewFoodComplaint.this, "Failed to load complaints", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
