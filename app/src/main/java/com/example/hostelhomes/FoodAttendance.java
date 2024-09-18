package com.example.hostelhomes;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FoodAttendance extends AppCompatActivity {

    private CalendarView calendarView;
    private TextView attendanceDetails;
    private DatabaseReference attendanceRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_attendance);

        calendarView = findViewById(R.id.calendarView);
        attendanceDetails = findViewById(R.id.attendanceDetails);


        // Fetch the student ID passed via Intent
        String studentID = getIntent().getStringExtra("ID");

        // If no student ID is passed, handle it (for safety, fallback to a default value or show an error)
        if (studentID == null) {
            studentID = "defaultUser";  // Or handle with an error message
        }

        // Reference to the student's attendance data in Firebase
        attendanceRef = FirebaseDatabase.getInstance().getReference("attendance").child(studentID);

        // Set an OnDateChangeListener to fetch attendance for the selected date
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            fetchAttendanceDetails(year, month, dayOfMonth);
        });

        // Fetch attendance details for the current date initially
        Calendar today = Calendar.getInstance();
        fetchAttendanceDetails(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH));
    }

    private void fetchAttendanceDetails(int year, int month, int dayOfMonth) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        String formattedDate = dateFormat.format(calendar.getTime());

        attendanceRef.child(formattedDate).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Fetch the attendance details and check for null values
                    String breakfast = snapshot.hasChild("Breakfast") ? snapshot.child("Breakfast").getValue(String.class) : "Absent";
                    String lunch = snapshot.hasChild("Lunch") ? snapshot.child("Lunch").getValue(String.class) : "Absent";
                    String dinner = snapshot.hasChild("Dinner") ? snapshot.child("Dinner").getValue(String.class) : "Absent";

                    breakfast = breakfast == null ? "Absent" : breakfast;
                    lunch = lunch == null ? "Absent" : lunch;
                    dinner = dinner == null ? "Absent" : dinner;

                    // Display attendance info in the TextView
                    String attendanceInfo = "Breakfast: " + breakfast + "\nLunch: " + lunch + "\nDinner: " + dinner;
                    attendanceDetails.setText(attendanceInfo);
                } else {
                    attendanceDetails.setText("No attendance data available for this day");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle Firebase errors here
            }
        });
    }
}
