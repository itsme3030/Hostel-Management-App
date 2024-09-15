package com.example.hostelhomes;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class FoodstaffFoodSchedule extends AppCompatActivity {

    private LinearLayout layoutContainer;
    private Button btnSave;
    private HashMap<String, HashMap<String, EditText>> scheduleInputs = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodstaff_food_schedule);

        layoutContainer = findViewById(R.id.layoutContainer);
        btnSave = findViewById(R.id.btnSave);

        // Set up UI with all days and times
        setupScheduleViews();

        // Handle save button click
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSchedule();
            }
        });
    }

    private void setupScheduleViews() {
        // Days of the week and times
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        String[] times = {"Morning", "Afternoon", "Evening"};

        for (String day : days) {
            // Inflate the day layout
            View dayView = getLayoutInflater().inflate(R.layout.day_schedule, layoutContainer, false);
            TextView dayLabel = dayView.findViewById(R.id.dayLabel);
            dayLabel.setText(day);
            LinearLayout dayLayout = dayView.findViewById(R.id.dayLayout);

            // Store meal inputs for the day
            HashMap<String, EditText> daySchedule = new HashMap<>();

            for (String time : times) {
                // Inflate the time layout for each time of day
                View timeView = getLayoutInflater().inflate(R.layout.time_schedule, dayLayout, false);
                TextView timeLabel = timeView.findViewById(R.id.timeLabel);
                timeLabel.setText(time);

                // Get the EditText input for meals
                EditText mealInput = timeView.findViewById(R.id.mealInput);

                // Add to the schedule map for the day
                daySchedule.put(time, mealInput);

                // Add the time view to the day layout
                dayLayout.addView(timeView);
            }

            // Add the day schedule inputs to the main map
            scheduleInputs.put(day, daySchedule);

            // Add the day view to the main container
            layoutContainer.addView(dayView);
        }
    }

    // Save the updated schedule to Firebase
    private void saveSchedule() {
        boolean isSaved = false;  // Flag to track if any meals were saved

        for (Map.Entry<String, HashMap<String, EditText>> dayEntry : scheduleInputs.entrySet()) {
            String day = dayEntry.getKey();
            HashMap<String, EditText> times = dayEntry.getValue();

            for (Map.Entry<String, EditText> timeEntry : times.entrySet()) {
                String time = timeEntry.getKey();
                String meal = timeEntry.getValue().getText().toString().trim();

                if (!meal.isEmpty()) {
                    updateFirebase(day, time, meal);
                    isSaved = true;
                }
            }
        }

        // Show a toast if any schedule was saved
        if (isSaved) {
            Toast.makeText(FoodstaffFoodSchedule.this, "Schedule saved successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(FoodstaffFoodSchedule.this, "No meals to save.", Toast.LENGTH_SHORT).show();
        }
    }

    // Update Firebase Realtime Database
    private void updateFirebase(String day, String time, String meal) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("foodSchedule/" + day + "/" + time);
        ref.setValue(meal);
    }
}
