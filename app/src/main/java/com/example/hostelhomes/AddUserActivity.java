package com.example.hostelhomes;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddUserActivity extends AppCompatActivity {
    private EditText etUserID, etUsername, etPassword, etRole;
    private Button btnAddUser;
    private DatabaseReference databaseReference;
    private long nextUserIndex = 1; // Start counting users from 1

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        etUserID = findViewById(R.id.etUserID);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etRole = findViewById(R.id.etRole);
        btnAddUser = findViewById(R.id.btnAddUser);

        // Initialize Firebase reference to "users" section
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        // Get the current count of users to generate the next user ID
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nextUserIndex = snapshot.getChildrenCount() + 1;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddUserActivity.this, "Error fetching user data", Toast.LENGTH_SHORT).show();
            }
        });

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });
    }

    private void addUser() {
        final String userID = etUserID.getText().toString().trim();
        final String username = etUsername.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();
        final String role = etRole.getText().toString().trim();

        if (TextUtils.isEmpty(userID) || TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(role)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if userID already exists in the database
        databaseReference.orderByChild("id").equalTo(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Toast.makeText(AddUserActivity.this, "User ID already exists. Please try again.", Toast.LENGTH_SHORT).show();
                } else {
                    // Create the next user key like user4, user5, etc.
                    String nextUserKey = "user" + nextUserIndex;

                    // Create a new user object
                    User newUser = new User(userID, username, password, role);

                    // Save the user under the next user key
                    databaseReference.child(nextUserKey).setValue(newUser)
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(AddUserActivity.this, "User added successfully", Toast.LENGTH_SHORT).show();
                                nextUserIndex++; // Increment for the next user
                                clearFields();
                            })
                            .addOnFailureListener(e -> Toast.makeText(AddUserActivity.this, "Failed to add user", Toast.LENGTH_SHORT).show());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddUserActivity.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearFields() {
        etUserID.setText("");
        etUsername.setText("");
        etPassword.setText("");
        etRole.setText("");
    }

    public static class User {
        public String id;
        public String username;
        public String password;
        public String role;

        public User() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public User(String id, String username, String password, String role) {
            this.id = id;
            this.username = username;
            this.password = password;
            this.role = role;
        }
    }
}
