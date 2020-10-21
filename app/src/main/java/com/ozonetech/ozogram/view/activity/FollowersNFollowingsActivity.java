package com.ozonetech.ozogram.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.ozonetech.ozogram.R;
import com.ozonetech.ozogram.app.utils.SessionManager;
import com.ozonetech.ozogram.databinding.ActivityFollowersNFollowingsBinding;
import com.ozonetech.ozogram.view.fragment.FollowersFragment;
import com.ozonetech.ozogram.view.fragment.FollowingsFragment;
import com.ozonetech.ozogram.view.fragment.GalleryFragment;
import com.ozonetech.ozogram.view.fragment.StoryFragment;

import java.util.ArrayList;
import java.util.List;

public class FollowersNFollowingsActivity extends AppCompatActivity {

    ActivityFollowersNFollowingsBinding databinding;
    SessionManager sessionManager;
    private ViewPager viewPager;
    String type,followers,followings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databinding= DataBindingUtil.setContentView(FollowersNFollowingsActivity.this,R.layout.activity_followers_n_followings);
        databinding.executePendingBindings();
        databinding.setLifecycleOwner(FollowersNFollowingsActivity.this);

        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(databinding.viewpager);
        databinding.tabs.setSelectedTabIndicatorColor(Color.parseColor("#CD7F0C"));
        databinding.tabs.setSelectedTabIndicatorHeight((int) (2 * getResources().getDisplayMetrics().density));
        databinding.tabs.setTabTextColors(Color.parseColor("#ffffff"), Color.parseColor("#CD7F0C"));
        databinding.tabs.setupWithViewPager(viewPager);
        sessionManager=new SessionManager(FollowersNFollowingsActivity.this);
        /*followers=sessionManager.getUserDetails().get(SessionManager.KEY_FOLLOWERS_COUNT);
        followings=sessionManager.getUserDetails().get(SessionManager.KEY_FOLLOWINGS_COUNT);*/
        databinding.tvUserName.setText(sessionManager.getUserDetails().get(SessionManager.KEY_FULL_NAME));
        databinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToProfileActivity();
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FollowersFragment(), "FOLLOWERS");
        adapter.addFragment(new FollowingsFragment(), "FOLLOWINGS");
        viewPager.setAdapter(adapter);
        switchToTab(type);
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
            return mFragmentTitleList.get(position);
            // return null to display only the icon
            //return null;
        }
    }

    public void switchToTab(String type) {
        if (type.equals("followers")) {
            viewPager.setCurrentItem(0);
        } else if (type.equals("followings")) {
            viewPager.setCurrentItem(1);
        } else {
            viewPager.setCurrentItem(0);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goToProfileActivity();
    }

    private void goToProfileActivity() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }
}