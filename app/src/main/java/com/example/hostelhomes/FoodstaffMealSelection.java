package com.example.hostelhomes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class FoodstaffMealSelection extends AppCompatActivity {

    private String currentMealTime = "Breakfast"; // Default meal time

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodstaff_food_schedule);

        Button btnBreakfast = findViewById(R.id.btnBreakfast);
        Button btnLunch = findViewById(R.id.btnLunch);
        Button btnDinner = findViewById(R.id.btnDinner);

        // Set breakfast button click listener
        btnBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentMealTime = "Breakfast";
                sendMealTime();
            }
        });

        // Set lunch button click listener
        btnLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentMealTime = "Lunch";
                sendMealTime();
            }
        });

        // Set dinner button click listener
        btnDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentMealTime = "Dinner";
                sendMealTime();
            }
        });
    }

    private void sendMealTime() {
        // Send the selected meal time back to the scanner activity
        Intent intent = new Intent(FoodstaffMealSelection.this, FoodAttendanceScanner.class);
        intent.putExtra("currentMealTime", currentMealTime);
        startActivity(intent);

        // Show a confirmation message
        Toast.makeText(FoodstaffMealSelection.this, "Meal time set to " + currentMealTime, Toast.LENGTH_SHORT).show();
    }
}
