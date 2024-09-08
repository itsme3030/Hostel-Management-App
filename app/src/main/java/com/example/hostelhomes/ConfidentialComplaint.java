package com.example.hostelhomes;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfidentialComplaint extends AppCompatActivity {

    private EditText editTextComplaint;
    private Button btnSendComplaint;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confidential_complaint);

        editTextComplaint = findViewById(R.id.editText_confidential_complaint);
        btnSendComplaint = findViewById(R.id.btn_send_confidential_complaint);

        databaseReference = FirebaseDatabase.getInstance().getReference("complaints");

        btnSendComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String complaint = editTextComplaint.getText().toString().trim();

                if (!TextUtils.isEmpty(complaint)) {
                    sendComplaintToFirebase("superwiser", complaint);
                    Toast.makeText(ConfidentialComplaint.this, "Confidential Report Sent", Toast.LENGTH_SHORT).show();
                    editTextComplaint.setText("");
                } else {
                    Toast.makeText(ConfidentialComplaint.this, "Please enter your report", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendComplaintToFirebase(String department, String complaint) {
        DatabaseReference ref = databaseReference.child(department);
        ref.push().setValue(complaint);
    }
}
