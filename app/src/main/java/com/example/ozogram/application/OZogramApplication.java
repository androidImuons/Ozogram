package com.example.ozogram.application;

import com.example.ozogram.app.utils.Gallery;

public class OZogramApplication extends AppBaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        Gallery gallery=new Gallery();
        gallery.getImages(getApplicationContext());
    }
}
