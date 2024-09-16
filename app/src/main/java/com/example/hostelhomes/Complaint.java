package com.example.hostelhomes;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class Complaint extends AppCompatActivity {

    private Button btnMaintenance, btnFood, btnConfidential;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        btnMaintenance = findViewById(R.id.btn_maintenance);
        btnFood = findViewById(R.id.btn_food);
        btnConfidential = findViewById(R.id.btn_confidential);

        Intent i1 = getIntent();
        String ID = i1.getStringExtra("ID");

        btnMaintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Complaint.this, MaintenanceComplaint.class);
                intent.putExtra("ID",ID);
                startActivity(intent);
            }
        });

        btnFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Complaint.this, FoodComplaint.class);
                intent.putExtra("ID",ID);
                startActivity(intent);
            }
        });

        btnConfidential.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Complaint.this, ConfidentialComplaint.class);
                intent.putExtra("ID",ID);
                startActivity(intent);
            }
        });

        Button backbutton = findViewById(R.id.backButton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
