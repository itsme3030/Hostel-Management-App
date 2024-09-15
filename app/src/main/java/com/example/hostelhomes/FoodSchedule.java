package com.example.hostelhomes;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FoodSchedule extends AppCompatActivity {

    private TextView mondayMorningView, mondayAfternoonView, mondayEveningView;
    private TextView tuesdayMorningView, tuesdayAfternoonView, tuesdayEveningView;
    private TextView wednesdayMorningView, wednesdayAfternoonView, wednesdayEveningView;
    private TextView thursdayMorningView, thursdayAfternoonView, thursdayEveningView;
    private TextView fridayMorningView, fridayAfternoonView, fridayEveningView;
    private TextView saturdayMorningView, saturdayAfternoonView, saturdayEveningView;
    private TextView sundayMorningView, sundayAfternoonView, sundayEveningView;
    private Button backbutton;
    private DatabaseReference foodScheduleRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_schedule);

        // Initialize Firebase reference
        foodScheduleRef = FirebaseDatabase.getInstance().getReference("foodSchedule");
        if (foodScheduleRef == null) {
            Toast.makeText(getApplicationContext(), "null", Toast.LENGTH_SHORT).show();
            return;
        }

        backbutton = findViewById(R.id.backButton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Initialize views for Monday
        mondayMorningView = findViewById(R.id.mondayMorningView);
        mondayAfternoonView = findViewById(R.id.mondayAfternoonView);
        mondayEveningView = findViewById(R.id.mondayEveningView);

        // Initialize views for Tuesday
        tuesdayMorningView = findViewById(R.id.tuesdayMorningView);
        tuesdayAfternoonView = findViewById(R.id.tuesdayAfternoonView);
        tuesdayEveningView = findViewById(R.id.tuesdayEveningView);

        // Initialize views for Wednesday
        wednesdayMorningView = findViewById(R.id.wednesdayMorningView);
        wednesdayAfternoonView = findViewById(R.id.wednesdayAfternoonView);
        wednesdayEveningView = findViewById(R.id.wednesdayEveningView);

        // Initialize views for Thursday
        thursdayMorningView = findViewById(R.id.thursdayMorningView);
        thursdayAfternoonView = findViewById(R.id.thursdayAfternoonView);
        thursdayEveningView = findViewById(R.id.thursdayEveningView);

        // Initialize views for Friday
        fridayMorningView = findViewById(R.id.fridayMorningView);
        fridayAfternoonView = findViewById(R.id.fridayAfternoonView);
        fridayEveningView = findViewById(R.id.fridayEveningView);

        // Initialize views for Saturday
        saturdayMorningView = findViewById(R.id.saturdayMorningView);
        saturdayAfternoonView = findViewById(R.id.saturdayAfternoonView);
        saturdayEveningView = findViewById(R.id.saturdayEveningView);

        // Initialize views for Sunday
        sundayMorningView = findViewById(R.id.sundayMorningView);
        sundayAfternoonView = findViewById(R.id.sundayAfternoonView);
        sundayEveningView = findViewById(R.id.sundayEveningView);

        // Fetch schedule from Firebase for each day
        loadFoodSchedule();
    }

    private void loadFoodSchedule() {
        // Fetch Monday schedule
        foodScheduleRef.child("Monday").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String morning = dataSnapshot.child("Morning").getValue(String.class);
                    String afternoon = dataSnapshot.child("Afternoon").getValue(String.class);
                    String evening = dataSnapshot.child("Evening").getValue(String.class);

                    mondayMorningView.setText("Morning: " + morning);
                    mondayAfternoonView.setText("Afternoon: " + afternoon);
                    mondayEveningView.setText("Evening: " + evening);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Fetch Tuesday schedule
        foodScheduleRef.child("Tuesday").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String morning = dataSnapshot.child("Morning").getValue(String.class);
                    String afternoon = dataSnapshot.child("Afternoon").getValue(String.class);
                    String evening = dataSnapshot.child("Evening").getValue(String.class);

                    tuesdayMorningView.setText("Morning: " + morning);
                    tuesdayAfternoonView.setText("Afternoon: " + afternoon);
                    tuesdayEveningView.setText("Evening: " + evening);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Fetch Wednesday schedule
        foodScheduleRef.child("Wednesday").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String morning = dataSnapshot.child("Morning").getValue(String.class);
                    String afternoon = dataSnapshot.child("Afternoon").getValue(String.class);
                    String evening = dataSnapshot.child("Evening").getValue(String.class);

                    wednesdayMorningView.setText("Morning: " + morning);
                    wednesdayAfternoonView.setText("Afternoon: " + afternoon);
                    wednesdayEveningView.setText("Evening: " + evening);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Fetch Thursday schedule
        foodScheduleRef.child("Thursday").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String morning = dataSnapshot.child("Morning").getValue(String.class);
                    String afternoon = dataSnapshot.child("Afternoon").getValue(String.class);
                    String evening = dataSnapshot.child("Evening").getValue(String.class);

                    thursdayMorningView.setText("Morning: " + morning);
                    thursdayAfternoonView.setText("Afternoon: " + afternoon);
                    thursdayEveningView.setText("Evening: " + evening);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Fetch Friday schedule
        foodScheduleRef.child("Friday").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String morning = dataSnapshot.child("Morning").getValue(String.class);
                    String afternoon = dataSnapshot.child("Afternoon").getValue(String.class);
                    String evening = dataSnapshot.child("Evening").getValue(String.class);

                    fridayMorningView.setText("Morning: " + morning);
                    fridayAfternoonView.setText("Afternoon: " + afternoon);
                    fridayEveningView.setText("Evening: " + evening);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Fetch Saturday schedule
        foodScheduleRef.child("Saturday").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String morning = dataSnapshot.child("Morning").getValue(String.class);
                    String afternoon = dataSnapshot.child("Afternoon").getValue(String.class);
                    String evening = dataSnapshot.child("Evening").getValue(String.class);

                    saturdayMorningView.setText("Morning: " + morning);
                    saturdayAfternoonView.setText("Afternoon: " + afternoon);
                    saturdayEveningView.setText("Evening: " + evening);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Fetch Sunday schedule
        foodScheduleRef.child("Sunday").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String morning = dataSnapshot.child("Morning").getValue(String.class);
                    String afternoon = dataSnapshot.child("Afternoon").getValue(String.class);
                    String evening = dataSnapshot.child("Evening").getValue(String.class);

                    sundayMorningView.setText("Morning: " + morning);
                    sundayAfternoonView.setText("Afternoon: " + afternoon);
                    sundayEveningView.setText("Evening: " + evening);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
