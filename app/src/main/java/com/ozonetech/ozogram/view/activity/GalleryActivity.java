package com.example.ozogram.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.bumptech.glide.Glide;
import com.example.ozogram.R;
import com.example.ozogram.app.utils.Contrants;
import com.example.ozogram.app.utils.Gallery;
import com.example.ozogram.databinding.ActivityGalleryBinding;
import com.example.ozogram.model.ImageModel;
import com.example.ozogram.view.adapter.GirdViewAdapter;
import com.example.ozogram.view.adapter.SpinnerBaseAdapter;
import com.example.ozogram.viewmodel.GalleryViewModel;
import com.example.ozogram.viewmodel.HomeViewModel;

import java.io.File;
import java.util.ArrayList;

import static com.example.ozogram.app.utils.Contrants.REQUEST_READ_EXTERNAL_STORAGE_PERMISSION;

public class GalleryActivity extends BaseActivity implements GirdViewAdapter.ClickEvent {
    ActivityGalleryBinding galleryBinding;
    GalleryViewModel galleryViewModel;
    private String tag = "GalleryActivity";
    private boolean boolean_folder;
    private GirdViewAdapter obj_adapter;
    private boolean is_crop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        galleryBinding = DataBindingUtil.setContentView(this, R.layout.activity_gallery);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        galleryViewModel = ViewModelProviders.of(GalleryActivity.this).get(GalleryViewModel.class);
        galleryBinding.setGallery(galleryViewModel);
        galleryBinding.executePendingBindings();
        galleryBinding.setLifecycleOwner(GalleryActivity.this);


        checkPermission();

        setListner();

    }

    private void setListner() {
        galleryBinding.inculdeGalleryToolBar.spinnerShow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        galleryBinding.ivCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!is_crop) {
                    is_crop = true;
                    galleryBinding.ivImage.setVisibility(View.GONE);
                    galleryBinding.cropImageView.setVisibility(View.VISIBLE);
                    galleryBinding.cropImageView.setImageUriAsync(Uri.parse(selected_file_url));
                } else {
                    is_crop = false;
                    galleryBinding.ivImage.setVisibility(View.VISIBLE);
                    galleryBinding.cropImageView.setVisibility(View.GONE);
                }

            }
        });

        galleryBinding.ivSelectMultipleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private void checkPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE_PERMISSION);
        } else {
            getImages();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_READ_EXTERNAL_STORAGE_PERMISSION) {
            if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getImages();
            }
        }
    }


    public void getImages() {
        ArrayList<ImageModel> al_images = Gallery.al_images;
        SpinnerBaseAdapter spinnerBaseAdapter = new SpinnerBaseAdapter(getApplicationContext(), al_images);
        galleryBinding.inculdeGalleryToolBar.spinnerShow.setAdapter(spinnerBaseAdapter);
        // galleryBinding.gridView.setHasFixedSize(true);
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        galleryBinding.gridView.setLayoutManager(sglm);
        obj_adapter = new GirdViewAdapter(getApplicationContext(), al_images.get(0).getAl_imagepath(), GalleryActivity.this);
        galleryBinding.gridView.setAdapter(obj_adapter);
    }

    ArrayList<String> selected_image = new ArrayList<>();
    int selected_position;
    String selected_file_url;

    @Override
    public void imageClickEvent(int position, String url) {
        Glide.with(getApplicationContext()).load(url)
                .into(galleryBinding.ivImage);
        selected_file_url = url;
        if (selected_position != position) {
            selected_position = position;
            selected_image.add(url);
        }

    }

    @Override
    public void checkBokClickEvent(int position, String url) {
        Glide.with(getApplicationContext()).load(url)
                .into(galleryBinding.ivImage);
    }
}