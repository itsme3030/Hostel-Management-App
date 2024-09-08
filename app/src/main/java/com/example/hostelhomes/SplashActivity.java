package com.example.hostelhomes;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private ImageView logo;
    private Paint paint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logo = findViewById(R.id.logo);

        // Scale animation for the logo
        Animation scaleAnimation = new ScaleAnimation(
                1f, 1.5f,   // Start and end scale for X-axis
                1f, 1.5f,   // Start and end scale for Y-axis
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        scaleAnimation.setDuration(1500);  // Animation duration in milliseconds
        scaleAnimation.setFillAfter(true);  // Keep the animation scale after it ends
        logo.startAnimation(scaleAnimation);

//        // Custom View with Canvas to draw text
//        final View canvasView = new View(this) {
//            @Override
//            protected void onDraw(Canvas canvas) {
//                super.onDraw(canvas);
//
//                // Paint to draw "Hostel Homes"
//                paint = new Paint();
//                paint.setColor(Color.BLACK);
//                paint.setTextSize(60);
//                paint.setTextAlign(Paint.Align.CENTER);
//                paint.setAntiAlias(true);
//
//                // Drawing the text in the middle of the canvas
//                canvas.drawText("Hostel Homes", getWidth() / 2, getHeight() - 200, paint);
//            }
//        };

        // Adding the custom view to the activity
//        addContentView(canvasView, new RelativeLayout.LayoutParams(
//                RelativeLayout.LayoutParams.MATCH_PARENT,
//                RelativeLayout.LayoutParams.MATCH_PARENT
//        ));

        // Move to next activity after the splash screen
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 3000);
    }
}
