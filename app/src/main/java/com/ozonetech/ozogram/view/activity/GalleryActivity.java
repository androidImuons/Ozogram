package com.ozonetech.ozogram.view.activity;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;


import com.google.android.material.tabs.TabLayout;
import com.ozonetech.ozogram.R;
import com.ozonetech.ozogram.app.utils.Gallery;
import com.ozonetech.ozogram.databinding.ActivityGalleryBinding;
import com.ozonetech.ozogram.model.ImageModel;
import com.ozonetech.ozogram.view.adapter.GirdViewAdapter;
import com.ozonetech.ozogram.view.adapter.SpinnerBaseAdapter;
import com.ozonetech.ozogram.view.fragment.PhotoFragment;
import com.ozonetech.ozogram.view.fragment.PostGalleryFragment;
import com.ozonetech.ozogram.view.fragment.VideoFragment;
import com.ozonetech.ozogram.viewmodel.GalleryViewModel;


import java.util.ArrayList;
import java.util.List;

import static com.ozonetech.ozogram.app.utils.Contrants.REQUEST_READ_EXTERNAL_STORAGE_PERMISSION;
//https://deepshikhapuri.wordpress.com/2017/03/29/get-all-videos-from-gallery-in-android/
public class GalleryActivity extends BaseActivity  {
    ActivityGalleryBinding galleryBinding;
    GalleryViewModel galleryViewModel;
    private String tag = "GalleryActivity";
    private boolean boolean_folder;
    private GirdViewAdapter obj_adapter;
    private boolean is_crop;
    private boolean is_check_open;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PostGalleryFragment postGalleryFrament;
    private ArrayList<ImageModel> al_images;

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

        setupViewPager();
        checkPermission();

        setListner();

    }

    private void setListner() {
        galleryBinding.inculdeGalleryToolBar.spinnerShow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                postGalleryFrament.setList(al_images.get(i).getAl_imagepath(),al_images.get(i).getType());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        galleryBinding.inculdeGalleryToolBar.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        galleryBinding.inculdeGalleryToolBar.txtNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),FilterActivity.class);
                startActivity(intent);
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
        Gallery gallery = new Gallery();
        gallery.getImages(getApplicationContext());

         al_images = Gallery.al_images;

        SpinnerBaseAdapter spinnerBaseAdapter = new SpinnerBaseAdapter(getApplicationContext(), al_images);
        galleryBinding.inculdeGalleryToolBar.spinnerShow.setAdapter(spinnerBaseAdapter);
        // galleryBinding.gridView.setHasFixedSize(true)
        if(al_images.get(0).getAl_imagepath()!=null){
            postGalleryFrament.setList(al_images.get(0).getAl_imagepath(), al_images.get(0).getType());
        }

    }

    ArrayList<String> selected_image = new ArrayList<>();
    int selected_position;
    String selected_file_url;


    private void setupViewPager() {


//        galleryBinding.gallery.getTabAt(1);
//        galleryBinding.gallery.getTabAt(2);
        postGalleryFrament=new PostGalleryFragment();
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(postGalleryFrament, "GALLERY");
        adapter.addFragment(new PhotoFragment(), "PHOTO");
        adapter.addFragment(new VideoFragment(), "VIDEO");

        galleryBinding.galleryViewPager.setAdapter(adapter);
        galleryBinding.gallery.setupWithViewPager( galleryBinding.galleryViewPager);


    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            //  return mFragmentTitleList.get(position);
            // return null to display only the icon
            return mFragmentTitleList.get(position);
        }
    }

//    @Override
//    public void imageClickEvent(int position, String url) {
//        postGalleryFrament.imageClick(position,url);
//    }
//
//    @Override
//    public void checkBokClickEvent(int position, String url) {
//     postGalleryFrament.checkClick(position,url);
//    }

}