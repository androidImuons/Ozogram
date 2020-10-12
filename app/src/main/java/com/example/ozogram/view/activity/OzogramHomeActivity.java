package com.example.ozogram.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.example.ozogram.R;

public class OzogramHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ozogram_home);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}