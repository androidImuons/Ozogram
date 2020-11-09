package com.ozonetech.ozogram.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ozonetech.ozogram.R;
import com.ozonetech.ozogram.app.utils.SessionManager;

public class SplashActivity extends AppCompatActivity {
    private static final long SPLASH_TIME_MS = 2000;
    private Handler handler = new Handler();
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }
            session = new SessionManager(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, SPLASH_TIME_MS);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(session.isLoggedIn()){
                session.goToOzogramHomeActivity();
            }else{

                goToLoginActivity();
            }

        }
    };
    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    private void goToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        //supportFinishAfterTransition();
    }
}