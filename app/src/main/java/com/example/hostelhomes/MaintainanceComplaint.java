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

public class MaintainanceComplaint extends AppCompatActivity {

    private EditText editTextComplaint;
    private Button btnSendComplaint;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintainance_complaint);

        editTextComplaint = findViewById(R.id.editText_maintenance_complaint);
        btnSendComplaint = findViewById(R.id.btn_send_maintenance_complaint);

        databaseReference = FirebaseDatabase.getInstance().getReference("complaints");

        btnSendComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String complaint = editTextComplaint.getText().toString().trim();

                if (!TextUtils.isEmpty(complaint)) {
                    sendComplaintToFirebase("maintenance department", complaint);
                    sendComplaintToFirebase("superwiser", complaint);
                    Toast.makeText(MaintainanceComplaint.this, "Complaint Sent", Toast.LENGTH_SHORT).show();
                    editTextComplaint.setText("");
                } else {
                    Toast.makeText(MaintainanceComplaint.this, "Please enter your complaint", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendComplaintToFirebase(String department, String complaint) {
        DatabaseReference ref = databaseReference.child(department);
        ref.push().setValue(complaint);
    }
}
