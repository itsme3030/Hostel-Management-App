package com.example.hostelhomes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfidentialComplaint extends AppCompatActivity {

    private EditText editTextComplaint;
    private Button btnSendComplaint;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confidential_complaint);

        // Get the username from the intent
        Intent intent = getIntent();
        String ID = intent.getStringExtra("ID");

        // Initialize views
        editTextComplaint = findViewById(R.id.editText_confidential_complaint);
        btnSendComplaint = findViewById(R.id.btn_send_confidential_complaint);

        // Initialize Firebase reference for confidential complaints
        databaseReference = FirebaseDatabase.getInstance().getReference("complaints/confidential_complaint");

        // Set up send button click listener
        btnSendComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String complaintText = editTextComplaint.getText().toString().trim();

                // Check if the complaint text is empty
                if (!TextUtils.isEmpty(complaintText)) {
                    // Example student and supervisor IDs (replace with dynamic values)
                    String studentId = ID;  // Replace with actual student ID
                    String staffId = "supervisor";  // Replace with supervisor staff ID
                    long timestamp = System.currentTimeMillis(); // Current timestamp

                    // Generate a unique complaint ID
                    String confidentialComplaintId = databaseReference.push().getKey();

                    // Create a ConfidentialComplaintModel object with necessary data
                    ConfidentialComplaintModel complaintModel = new ConfidentialComplaintModel(studentId, staffId, "supervisor", complaintText, "pending", timestamp);

                    // Store the complaint in Firebase under "confidential_complaints" node
                    databaseReference.child(confidentialComplaintId).setValue(complaintModel);

                    // Show success message and clear the input
                    Toast.makeText(ConfidentialComplaint.this, "Confidential Report Sent", Toast.LENGTH_SHORT).show();
                    editTextComplaint.setText("");  // Clear the input field
                } else {
                    // Show an error message if the complaint text is empty
                    Toast.makeText(ConfidentialComplaint.this, "Please enter your report", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
