package com.example.hostelhomes;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class LeaveRequestActivity extends AppCompatActivity {

    private TextView fromDate, toDate;
    private EditText reasonText;
    private Button submitLeaveRequest;
    private Calendar fromCalendar, toCalendar;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_request);

        // Initialize views
        fromDate = findViewById(R.id.fromDate);
        toDate = findViewById(R.id.toDate);
        reasonText = findViewById(R.id.ReasonText);
        submitLeaveRequest = findViewById(R.id.submitLeaveRequest);

        // Initialize calendars
        fromCalendar = Calendar.getInstance();
        toCalendar = Calendar.getInstance();

        // Set click listeners to open date picker dialogs
        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(fromDate, fromCalendar);
            }
        });

        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(toDate, toCalendar);
            }
        });

        // Initialize Firebase reference
//        databaseReference = FirebaseDatabase.getInstance().getReference("leaveRequests");

        // Submit button logic
        submitLeaveRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fromDateString = fromDate.getText().toString();
                String toDateString = toDate.getText().toString();
                String reason = reasonText.getText().toString().trim();

                if (!fromDateString.equals("Select From Date") && !toDateString.equals("Select To Date") && !TextUtils.isEmpty(reason)) {

                    // Save the leave request
//                    sendLeaveRequestToFirebase(fromDateString, toDateString, reason);

                    Toast.makeText(LeaveRequestActivity.this, "Leave request submitted", Toast.LENGTH_LONG).show();
                    clearFields();
                } else {
                    Toast.makeText(LeaveRequestActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Method to send data to Firebase
//    private void sendLeaveRequestToFirebase(String fromDate, String toDate, String reason) {
//        String userId = "someUserId"; // Replace with actual user ID
//
//        // Create a map to store the request
//        HashMap<String, String> leaveRequest = new HashMap<>();
//        leaveRequest.put("fromDate", fromDate);
//        leaveRequest.put("toDate", toDate);
//        leaveRequest.put("reason", reason);
//
//        // Store in Firebase under the userId
//        databaseReference.child(userId).setValue(leaveRequest);
//    }

    // Method to clear input fields
    private void clearFields() {
        fromDate.setText("Select From Date");
        toDate.setText("Select To Date");
        reasonText.setText("");
    }

    // Method to show date picker dialog
    private void showDatePickerDialog(final TextView dateTextView, final Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(LeaveRequestActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        dateTextView.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

}