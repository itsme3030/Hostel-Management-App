package com.example.hostelhomes;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

    public class MaintenanceComplaint extends AppCompatActivity {

    private EditText editTextComplaint;
    private Button btnSendComplaint,btnViewComplaint;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_complaint);

        // Get the username from the intent
        Intent intent = getIntent();
        String ID = intent.getStringExtra("ID");
//maintenance
        // Initialize views
        editTextComplaint = findViewById(R.id.editText_maintenance_complaint);
        btnSendComplaint = findViewById(R.id.btn_send_maintenance_complaint);
        btnViewComplaint = findViewById(R.id.btn_view_maintenance_complaint);

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
                    String studentId = ID;  // Replace with actual student ID
                    System.out.println("ID");
//                    System.out.println(ID);
                    String staffId = "ms1";      // Replace with actual maintenance staff ID
                    long timestamp = System.currentTimeMillis(); // Current timestamp

                    // Generate a unique complaint ID
                    String maintenanceComplaintId = databaseReference.push().getKey();

                    // Create a MaintenanceComplaint object with necessary data
                    Model complaintModel = new Model(studentId, staffId, "maintenance", complaintText, "pending", timestamp);

                    // Store the complaint in Firebase under "maintenance_complaints" node
                    databaseReference.child(maintenanceComplaintId).setValue(complaintModel);

                    // Show success message and clear the input
                    Toast.makeText(MaintenanceComplaint.this, "Complaint Sent", Toast.LENGTH_SHORT).show();
                    editTextComplaint.setText("");  // Clear the input field
                } else {
                    // Show an error message if the complaint text is empty
                    Toast.makeText(MaintenanceComplaint.this, "Please enter your complaint", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnViewComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MaintenanceComplaint.this, ViewMaintenanceComplaint.class);
                intent.putExtra("ID", ID);
                startActivity(intent);
            }
        });
    }
}
