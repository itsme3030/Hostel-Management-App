package com.example.hostelhomes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Maintenancestaffhomeactivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "LoginPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_maintenancestaffhomeactivity);

        // Set up logout button
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        Button logoutbtn = findViewById(R.id.logoutbtn);
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Maintenancestaffhomeactivity.this, "You are logged out", Toast.LENGTH_SHORT).show();

                // Clear login status in SharedPreferences

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("loggedIn", false);
                editor.apply();

                // Redirect to MainActivity
                Intent hometomain = new Intent(Maintenancestaffhomeactivity.this, MainActivity.class);
                hometomain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(hometomain);
                finish();
            }
        });

    }
}