package com.ozonetech.ozogram.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.ozonetech.ozogram.R;
import com.ozonetech.ozogram.app.utils.SessionManager;
import com.ozonetech.ozogram.databinding.ActivityProfileBinding;
import com.ozonetech.ozogram.model.Post;
import com.ozonetech.ozogram.model.User;
import com.ozonetech.ozogram.view.adapter.ProfileStroyAdpter;
import com.ozonetech.ozogram.view.fragment.GalleryFragment;
import com.ozonetech.ozogram.view.fragment.StoryFragment;
import com.google.android.material.tabs.TabLayout;
import com.ozonetech.ozogram.view.listeners.UserProfileListener;
import com.ozonetech.ozogram.viewmodel.UserProfileResponseModel;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends BaseActivity implements ProfileStroyAdpter.ProfileStroyAdpterListener, UserProfileListener {

    ActivityProfileBinding activityProfileBinding;
    UserProfileResponseModel userProfileResponseModel;
    SessionManager session;
    RecyclerView rv_profile_story;
    private MyClickHandlers handlers;
    ProfileStroyAdpter profileStroyAdpter;


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.story_icon,
            R.drawable.photo_icon
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityProfileBinding = DataBindingUtil.setContentView(ProfileActivity.this, R.layout.activity_profile);
        userProfileResponseModel = ViewModelProviders.of(ProfileActivity.this).get(UserProfileResponseModel.class);
        activityProfileBinding.setUserprofiel(userProfileResponseModel);
        activityProfileBinding.executePendingBindings();
        activityProfileBinding.setLifecycleOwner(this);
        session = new SessionManager(getApplicationContext());
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
        profileStroyAdpter = new ProfileStroyAdpter(getPosts(), this);
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

        showProgressDialog("Please wait...");
        userProfileResponseModel.fetchUserProfileData(ProfileActivity.this, userProfileResponseModel.userProfileListener=this);
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

    @Override
    public void onStarted() {
        showProgressDialog("Please wait...");
    }

    @Override
    public void onUserProfileSuccess(final LiveData<UserProfileResponseModel> userProfileResponse) {
        userProfileResponse.observe(ProfileActivity.this, new Observer<UserProfileResponseModel>() {
            @Override
            public void onChanged(UserProfileResponseModel userProfileResponseModel) {

                //save access token
                hideProgressDialog();
                try {
                    if (userProfileResponse.getValue().getCode() == 200 && userProfileResponse.getValue().getStatus().equalsIgnoreCase("OK")) {
                        showSnackbar(activityProfileBinding.llUserProfile, userProfileResponse.getValue().getMessage(), Snackbar.LENGTH_SHORT);
                        Log.d("ProfileActivity", "Response : Code" + userProfileResponse.getValue().getCode() + "\n Status : " + userProfileResponse.getValue().getStatus() + "\n Message : " + userProfileResponse.getValue().getMessage());
                        Log.d("ProfileActivity", "User Data" + userProfileResponse.getValue().getUser().getFullname());

                        session.saveUserProfileData(userProfileResponse.getValue().getUser().getUserId(),
                                userProfileResponse.getValue().getUser().getFullname(),
                                userProfileResponse.getValue().getUser().getEmail(),
                                userProfileResponse.getValue().getUser().getMobile(),
                                userProfileResponse.getValue().getUser().getBio(),
                                userProfileResponse.getValue().getUser().getProfilePicture(),
                                userProfileResponse.getValue().getUser().getJoiningDate());

                        // display user
                        activityProfileBinding.setUser(userProfileResponse.getValue().getUser());
                        // assign click handlers
                        activityProfileBinding.setHandlers(handlers);

                    } else {
                        showSnackbar(activityProfileBinding.llUserProfile, userProfileResponse.getValue().getMessage(), Snackbar.LENGTH_SHORT);
                    }
                } catch (Exception e) {
                } finally {
                    hideProgressDialog();
                }
            }
        });
    }



    @Override
    public void onFailure(String message) {
        showSnackbar(activityProfileBinding.llUserProfile, message, Snackbar.LENGTH_SHORT);
    }

    public void showSnackbar(View view, String message, int duration) {
        Snackbar snackbar = Snackbar.make(view, message, duration);
        snackbar.setActionTextColor(Color.WHITE);
        snackbar.setBackgroundTint(getResources().getColor(R.color.colorPrimaryDark));
        snackbar.show();
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

        public void onEditProfileClick(View view){
            goToEditProfileActivity();
        }

        public void onProfileFabClicked(View view) {
            Toast.makeText(getApplicationContext(), "Profile image pressed!", Toast.LENGTH_LONG).show();
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

    private void goToEditProfileActivity() {
        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivity(intent);
    }

}