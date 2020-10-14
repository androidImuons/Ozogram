package com.ozonetech.ozogram.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ozonetech.ozogram.R;
import com.ozonetech.ozogram.databinding.ActivityProfileBinding;
import com.ozonetech.ozogram.model.Post;
import com.ozonetech.ozogram.model.User;
import com.ozonetech.ozogram.view.adapter.ProfileStroyAdpter;
import com.ozonetech.ozogram.view.fragment.GalleryFragment;
import com.ozonetech.ozogram.view.fragment.StoryFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements ProfileStroyAdpter.ProfileStroyAdpterListener {

    ActivityProfileBinding activityProfileBinding;
    RecyclerView rv_profile_story;
    private MyClickHandlers handlers;
    ProfileStroyAdpter profileStroyAdpter;
    private User user;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.story_icon,
            R.drawable.photo_icon
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProfileBinding= DataBindingUtil.setContentView(ProfileActivity.this,R.layout.activity_profile);
        handlers = new MyClickHandlers(this);
        renderProfile();
        initRecyclerView();


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

    }

    private void initRecyclerView() {
        rv_profile_story = activityProfileBinding.rvProfileStory;
        rv_profile_story.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        profileStroyAdpter=new ProfileStroyAdpter(getPosts(),this);
        rv_profile_story.setAdapter(profileStroyAdpter);

        viewPager = activityProfileBinding.viewpager;
        setupViewPager(viewPager);

        tabLayout = activityProfileBinding.tabs;
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new StoryFragment(), "STORY");
        adapter.addFragment(new GalleryFragment(), "GALLERY");
        viewPager.setAdapter(adapter);
    }

    private void renderProfile() {
        user = new User();
        user.setName("David Attenborough");
        user.setEmail("david@natgeo.com");
        user.setProfileImage("https://api.androidhive.info/images/nature/david.jpg");
        user.setAbout("Naturalist");

        // ObservableField doesn't have setter method, instead will
        // be called using set() method
        user.numberOfPosts.set(3400L);
        user.numberOfFollowers.set(3050890L);
        user.numberOfFollowing.set(150L);


        // display user
        activityProfileBinding.setUser(user);

        // assign click handlers
        activityProfileBinding.setHandlers(handlers);
    }

    private ArrayList<Post> getPosts() {
        ArrayList<Post> posts = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            Post post = new Post();
            post.setImageUrl("https://api.androidhive.info/images/nature/" + i + ".jpg");
            posts.add(post);
        }

        return posts;
    }


    @Override
    public void onPostClicked(Post post) {
        Toast.makeText(getApplicationContext(), "Post clicked! " + post.getImageUrl(), Toast.LENGTH_SHORT).show();
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
            return null;
        }
    }

    public class MyClickHandlers {

        Context context;

        public MyClickHandlers(Context context) {
            this.context = context;
        }

        /**
         * Demonstrating updating bind data
         * Profile name, number of posts and profile image
         * will be updated on Fab click
         */
        public void onProfileFabClicked(View view) {
            user.setName("Sir David Attenborough");
            user.setProfileImage("https://api.androidhive.info/images/nature/david1.jpg");

            // updating ObservableField
            user.numberOfPosts.set(5500L);
            user.numberOfFollowers.set(5050890L);
            user.numberOfFollowing.set(180L);
        }

        public boolean onProfileImageLongPressed(View view) {
            Toast.makeText(getApplicationContext(), "Profile image long pressed!", Toast.LENGTH_LONG).show();
            return false;
        }


        public void onFollowersClicked(View view) {
            Toast.makeText(context, "Followers is clicked!", Toast.LENGTH_SHORT).show();
        }

        public void onFollowingClicked(View view) {
            Toast.makeText(context, "Following is clicked!", Toast.LENGTH_SHORT).show();
        }

        public void onPostsClicked(View view) {
            Toast.makeText(context, "Posts is clicked!", Toast.LENGTH_SHORT).show();
        }
    }

}