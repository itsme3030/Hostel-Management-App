package com.example.hostelhomes;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONException;
import org.json.JSONObject;

public class QrGenerate extends AppCompatActivity {

    private ImageView qrCodeImage;
    private DatabaseReference studentRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_generate);

        qrCodeImage = findViewById(R.id.qrCodeImage);
        String studentID = getIntent().getStringExtra("ID");

        generateQRCode(studentID);
    }

    private void generateQRCode(String studentID) {
        try {
            JSONObject qrData = new JSONObject();
            qrData.put("ID", studentID);
            qrData.put("username", "Student Name");  // Fill with actual student data

            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(qrData.toString(), BarcodeFormat.QR_CODE, 400, 400);

            qrCodeImage.setImageBitmap(bitmap);

        } catch (WriterException | JSONException e) {
            e.printStackTrace();
        }
    }
}
