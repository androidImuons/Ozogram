package com.example.ozogram.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.ozogram.R;
import com.example.ozogram.app.utils.SessionManager;
import com.example.ozogram.view.adapter.PostRecycleViewAdapter;
import com.example.ozogram.view.adapter.StoryUserRecycleViewAdapter;
import com.example.ozogram.app.utils.DeviceScreenUtil;
import com.google.android.material.appbar.AppBarLayout;


public class OzogramHomeActivity extends AppCompatActivity {
    // Session Manager Class
    SessionManager session;
    //@BindView(R.id.recycle_story_user)
    RecyclerView recycle_story_user;
    //@BindView(R.id.recycle_post_list)
    RecyclerView recycle_post_list;
    CoordinatorLayout coordinatorLayout;
    AppBarLayout app_bar_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ozogram_home);

        // Session class instance
        session = new SessionManager(getApplicationContext());

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
      recycle_story_user=findViewById(R.id.recycle_story_user);
        recycle_post_list=findViewById(R.id.recycle_post_list);
        coordinatorLayout =findViewById(R.id.coordinatorLayout);
        app_bar_layout = findViewById(R.id.app_bar_layout);


        recycle_story_user.setHasFixedSize(true);
        recycle_story_user.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        StoryUserRecycleViewAdapter storyUserRecycleViewAdapter=new StoryUserRecycleViewAdapter(getApplicationContext());
        recycle_story_user.setAdapter(storyUserRecycleViewAdapter);


        recycle_post_list.setHasFixedSize(true);
        recycle_post_list.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        PostRecycleViewAdapter postRecycleViewAdapter=new PostRecycleViewAdapter(getApplicationContext());
        recycle_post_list.setAdapter(postRecycleViewAdapter);

        setupUi();

    }

    private void setupUi() {
        coordinatorLayout.post(new Runnable() {
            @Override
            public void run() {
                int width = app_bar_layout.getWidth();
                CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) app_bar_layout.getLayoutParams();
                layoutParams.height = Math.round(width * 0.30f) + DeviceScreenUtil.getInstance()
                        .convertDpToPixel(20.0f);
                app_bar_layout.setLayoutParams(layoutParams);
            }
        });

    }


    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}