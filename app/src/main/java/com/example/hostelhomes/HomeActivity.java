package com.example.hostelhomes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    Button logoutbtn;
    TextView tvwelcometxt,tvUserName;
    SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "LoginPrefs"; // Name for SharedPreferences

    private LinearLayout attendanceSection, requestsSection, foodScheduleSection, complaintSection, eventSection, rulesSection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Define sharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Get the username from the intent
        Intent intent = getIntent();
        String uname = intent.getStringExtra("uname");

        logoutbtn = findViewById(R.id.logoutbtn);
        tvwelcometxt = findViewById(R.id.tvwelcometxt);
        tvUserName = findViewById(R.id.tvUserName);

        // Initialize UI elements
        attendanceSection = findViewById(R.id.attendanceSection);
        requestsSection = findViewById(R.id.requestsSection);
        foodScheduleSection = findViewById(R.id.foodScheduleSection);
        complaintSection = findViewById(R.id.complaintSection);
        eventSection = findViewById(R.id.eventSection);
        rulesSection = findViewById(R.id.rulesSection);

        //set UserName
        tvUserName.setText("Welcome " + uname);

        // Set click listeners
        attendanceSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this, AttendanceActivity.class);
//                startActivity(intent);
            }
        });

        requestsSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(HomeActivity.this, RequestsActivity.class);
//                startActivity(intent);
            }
        });

//        foodScheduleSection.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Home.this, FoodScheduleActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        complaintSection.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Home.this, ComplaintActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        eventSection.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Home.this, EventActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        rulesSection.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Home.this, RulesActivity.class);
//                startActivity(intent);
//            }
//        });



        // Set the welcome message with the username
        tvwelcometxt.setText("Welcome " + uname);

        // Set the logout button functionality
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "You are logged out", Toast.LENGTH_SHORT).show();

                // Clear the login status and username in SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("loggedIn", false);
                editor.putString("username", null);
                editor.apply(); // Apply the changes

                // Redirect to MainActivity
                Intent hometomain = new Intent(HomeActivity.this, MainActivity.class);
                hometomain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(hometomain);
                finish();
            }
        });
    }
}
