package com.example.hostelhomes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    Button loginbtn;
    EditText etuname, etid, etpassword;
    DatabaseReference databaseReference;
    private static final String TAG = "MainActivity";
    private static final String PREFS_NAME = "LoginPrefs"; // Name for SharedPreferences

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if the user is already logged in
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        if (preferences.getBoolean("loggedIn", false)) {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            intent.putExtra("uname", preferences.getString("username", ""));
            intent.putExtra("ID", preferences.getString("ID", ""));
            startActivity(intent);
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        loginbtn = findViewById(R.id.loginbtn);
        etuname = findViewById(R.id.etuname);
        etid = findViewById(R.id.etid);
        etpassword = findViewById(R.id.etpassword);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        Log.d(TAG, "Firebase Database reference initialized");

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = etuname.getText().toString();
                String id = etid.getText().toString();
                String pass = etpassword.getText().toString();

                Log.d(TAG, "Login button clicked, username: " + uname);
                if (!uname.isEmpty() && !pass.isEmpty() && !id.isEmpty()) {
                    Log.d(TAG, "Authentication process started");
                    authenticateUser(uname, pass);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter Username, ID, and Password", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void authenticateUser(String inputUsername, String inputPassword) {
        Log.d(TAG, "Inside auth");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean userFound = false;

                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    String username = userSnapshot.child("username").getValue(String.class);
                    String password = userSnapshot.child("password").getValue(String.class);
                    String ID = userSnapshot.child("ID").getValue(String.class);  // Retrieve ID from Firebase

                    Log.d(TAG, "Retrieved from Firebase - Username: " + username + ", ID: " + ID);  // Debugging log

                    if (username != null && username.equals(inputUsername)) {
                        userFound = true;

                        if (password != null && password.equals(inputPassword)) {
                            // Save login status, username, and ID in SharedPreferences
                            SharedPreferences preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putBoolean("loggedIn", true);
                            editor.putString("username", username);
                            editor.putString("ID", ID);  // Store ID
                            editor.apply();

                            // Pass username and ID to HomeActivity
                            Intent maintohome = new Intent(MainActivity.this, HomeActivity.class);
                            maintohome.putExtra("uname", username);
                            maintohome.putExtra("ID", ID);  // Pass ID
                            startActivity(maintohome);
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, "Invalid password", Toast.LENGTH_LONG).show();
                        }
                        break;
                    }
                }

                if (!userFound) {
                    Toast.makeText(MainActivity.this, "User does not exist", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
