package com.example.ozogram.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.ozogram.R;

public class SplashActivity extends AppCompatActivity {
    private static final long SPLASH_TIME_MS = 2000;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, SPLASH_TIME_MS);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            goToDashboardActivity();
        }
    };
    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    private void goToDashboardActivity() {
        Intent intent = new Intent(this, OzogramHomeActivity.class);
        startActivity(intent);
        supportFinishAfterTransition();
    }
}