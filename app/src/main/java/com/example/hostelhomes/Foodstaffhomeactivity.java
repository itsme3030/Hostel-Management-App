package com.example.hostelhomes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Foodstaffhomeactivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "LoginPrefs";
    LinearLayout foodScheduleSection, complaintSection, foodAttendanceSection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_foodstaffhomeactivity);

        foodScheduleSection = findViewById(R.id.foodScheduleSection);
        complaintSection = findViewById(R.id.complaintSection);
        foodAttendanceSection = findViewById(R.id.foodAttendanceSection);

        foodScheduleSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(Foodstaffhomeactivity.this, FoodstaffFoodSchedule.class);
                startActivity(i1);
            }
        });

        foodAttendanceSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(Foodstaffhomeactivity.this, FoodstaffMealSelection.class);
                startActivity(i1);
            }
        });





        // Set up logout button
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        Button logoutbtn = findViewById(R.id.logoutbtn);
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Foodstaffhomeactivity.this, "You are logged out", Toast.LENGTH_SHORT).show();

                // Clear login status in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("loggedIn", false);
                editor.apply();

                // Redirect to MainActivity
                Intent hometomain = new Intent(Foodstaffhomeactivity.this, MainActivity.class);
                hometomain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(hometomain);
                finish();
            }
        });

    }
}