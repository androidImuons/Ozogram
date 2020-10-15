package com.ozonetech.ozogram.application;


import android.Manifest;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.ozonetech.ozogram.app.utils.Gallery;

import static com.ozonetech.ozogram.app.utils.Contrants.REQUEST_READ_EXTERNAL_STORAGE_PERMISSION;

public class OZogramApplication extends AppBaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
    }



}
