package com.example.ontap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends AppCompatActivity {
    private static final long SPLASH_DELAY = 2000; // Thời gian hiển thị màn hình Splash (2 giây)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash.this, activity_register.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_DELAY);
    }
}

