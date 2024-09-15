package com.example.hostelhomes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import org.json.JSONException;
import org.json.JSONObject;

public class FoodstaffAttendanceScanner extends AppCompatActivity {

    private DatabaseReference attendanceRef;
    private String currentMealTime;

    // Use the new ActivityResultLauncher
    private final ActivityResultLauncher<Intent> qrCodeScannerLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    IntentResult intentResult = IntentIntegrator.parseActivityResult(result.getResultCode(), result.getData());
                    if (intentResult != null && intentResult.getContents() != null) {
                        processQRCode(intentResult.getContents());
                    } else {
                        Toast.makeText(FoodstaffAttendanceScanner.this, "Cancelled", Toast.LENGTH_LONG).show();
                    }
                }
                // If the result is not OK or data is null, simply finish the activity
                else {
                    finish(); // Finish the activity when scanning is cancelled
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodstaff_attendance_scanner);

        attendanceRef = FirebaseDatabase.getInstance().getReference("attendance");

        // Get the current meal time from the intent
        currentMealTime = getIntent().getStringExtra("currentMealTime");
        if (currentMealTime == null) {
            currentMealTime = "Breakfast";  // Default to breakfast if no meal time was passed
        }

        // Start QR code scanning
        startScanner();
    }

    private void startScanner() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setPrompt("Scan student QR code");
        integrator.setBeepEnabled(true);
        integrator.setOrientationLocked(true);
        integrator.setCaptureActivity(FoodstaffCaptureActivityPortrait.class);  // Custom QR scanner layout

        // Instead of initiating scan directly, use ActivityResultLauncher
        qrCodeScannerLauncher.launch(integrator.createScanIntent());
    }

    private void processQRCode(String qrData) {
        try {
            JSONObject qrJson = new JSONObject(qrData);
            String studentID = qrJson.getString("ID");
            String studentName = qrJson.getString("username");

            // Get current date for marking attendance
            String currentDate = java.text.DateFormat.getDateInstance().format(new java.util.Date());

            // Update attendance in Firebase
            updateAttendance(studentID, studentName, currentDate);

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Invalid QR code format", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateAttendance(String studentID, String studentName, String currentDate) {
        DatabaseReference studentRef = attendanceRef.child(studentID).child(currentDate).child(currentMealTime);
        studentRef.setValue("Present").addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(FoodstaffAttendanceScanner.this, "Attendance marked for " + studentName, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(FoodstaffAttendanceScanner.this, "Error updating attendance", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Override onBackPressed to finish the activity when the back button is pressed
    @Override
    public void onBackPressed() {
        // Finish the activity when the back button is pressed
        super.onBackPressed();
        finish();
    }
}
