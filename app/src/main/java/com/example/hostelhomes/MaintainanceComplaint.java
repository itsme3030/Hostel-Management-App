package com.example.hostelhomes;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

    public class MaintainanceComplaint extends AppCompatActivity {

    private EditText editTextComplaint;
    private Button btnSendComplaint;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintainance_complaint);

        // Initialize views
        editTextComplaint = findViewById(R.id.editText_maintenance_complaint);
        btnSendComplaint = findViewById(R.id.btn_send_maintenance_complaint);

        // Initialize Firebase reference (complaints node)
        databaseReference = FirebaseDatabase.getInstance().getReference("complaints/maintainance_complaint");

        // Set up send button click listener
        btnSendComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String complaintText = editTextComplaint.getText().toString().trim();

                // Check if the complaint text is empty
                if (!TextUtils.isEmpty(complaintText)) {
                    // Example student and staff IDs (replace with dynamic values)
                    String studentId = "user1";  // Replace with actual student ID
                    String staffId = "ms1";      // Replace with actual maintenance staff ID
                    long timestamp = System.currentTimeMillis(); // Current timestamp

                    // Generate a unique complaint ID
                    String maintenanceComplaintId = databaseReference.push().getKey();

                    // Create a MaintenanceComplaint object with necessary data
                    MaintenanceComplaintModel complaintModel = new MaintenanceComplaintModel(studentId, staffId, "maintenance", complaintText, "pending", timestamp);

                    // Store the complaint in Firebase under "maintenance_complaints" node
                    databaseReference.child(maintenanceComplaintId).setValue(complaintModel);

                    // Show success message and clear the input
                    Toast.makeText(MaintainanceComplaint.this, "Complaint Sent", Toast.LENGTH_SHORT).show();
                    editTextComplaint.setText("");  // Clear the input field
                } else {
                    // Show an error message if the complaint text is empty
                    Toast.makeText(MaintainanceComplaint.this, "Please enter your complaint", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
