package com.example.hostelhomes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    Button logoutbtn;
    TextView tvUserName;
    SharedPreferences sharedPreferences;
    private static final String TAG = "HomeActivity";
    private static final String PREFS_NAME = "LoginPrefs"; // Name for SharedPreferences

    private LinearLayout rulesSection, foodScheduleSection, attendanceSection, requestsSection, complaintSection, qrcodeSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Define sharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Get the username and ID from the intent
        Intent intent = getIntent();
        String uname = intent.getStringExtra("uname");
        String ID = intent.getStringExtra("ID");

        Log.d(TAG, "Received in HomeActivity - Username: " + uname + ", ID: " + ID);  // Debugging log

        tvUserName = findViewById(R.id.tvUserName);

        // Set the UserName and ID in the UI
        tvUserName.setText("Welcome " + uname);

        // Initialize UI elements
        rulesSection = findViewById(R.id.rulesSection);
        foodScheduleSection = findViewById(R.id.foodScheduleSection);
        attendanceSection = findViewById(R.id.foodAttendanceSection);
        requestsSection = findViewById(R.id.requestSection);
        complaintSection = findViewById(R.id.complaintSection);
        qrcodeSection = findViewById(R.id.qrcodeSection);
        // Set click listeners
        rulesSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(HomeActivity.this, RulesActivity.class);
                startActivity(i1);
            }
        });

        foodScheduleSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(HomeActivity.this, FoodSchedule.class);
                startActivity(i1);
            }
        });



        attendanceSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, FoodAttendance.class);
                intent.putExtra("ID", ID);
                startActivity(intent);
            }
        });

        requestsSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(HomeActivity.this, LeaveRequestActivity.class);
                startActivity(i1);
            }
        });

        complaintSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(HomeActivity.this, Complaint.class);
                i1.putExtra("ID", ID);
                startActivity(i1);
            }
        });

        qrcodeSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(HomeActivity.this, QrGenerate.class);
                i1.putExtra("ID", ID);
                startActivity(i1);
            }
        });

        // Set up logout button
        logoutbtn = findViewById(R.id.logoutbtn);
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "You are logged out", Toast.LENGTH_SHORT).show();

                // Clear login status in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("loggedIn", false);
                editor.apply();

                // Redirect to MainActivity
                Intent hometomain = new Intent(HomeActivity.this, MainActivity.class);
                hometomain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(hometomain);
                finish();
            }
        });


    }
}
