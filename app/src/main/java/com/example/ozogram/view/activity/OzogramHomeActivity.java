package com.example.ozogram.view.activity;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.ozogram.R;
import com.example.ozogram.app.utils.ItemClickSupport;
import com.example.ozogram.app.utils.SessionManager;
import com.example.ozogram.databinding.ActivityOzogramHomeBinding;
import com.example.ozogram.model.GetPostDataModel;
import com.example.ozogram.model.GetPostResponseModel;
import com.example.ozogram.view.adapter.PostRecycleViewAdapter;
import com.example.ozogram.view.adapter.StoryUserRecycleViewAdapter;
import com.example.ozogram.view.listeners.GetPostDataListener;
import com.example.ozogram.viewmodel.HomeViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;


public class OzogramHomeActivity extends BaseActivity implements GetPostDataListener {
    // Session Manager Class
    SessionManager session;
    ActivityOzogramHomeBinding activityOzogramHomeBinding;
    HomeViewModel homeViewModel;
  //  BottomTabViewModel bottomTabViewModel;
    private List<GetPostDataModel> post=new ArrayList<>();
    private StoryUserRecycleViewAdapter storyUserRecycleViewAdapter;
    private PostRecycleViewAdapter postRecycelAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOzogramHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_ozogram_home);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        homeViewModel = ViewModelProviders.of(OzogramHomeActivity.this).get(HomeViewModel.class);
        activityOzogramHomeBinding.setHome(homeViewModel);

        homeViewModel.postDataListener = OzogramHomeActivity.this;

        // Session class instance
        session = new SessionManager(getApplicationContext());
        activityOzogramHomeBinding.executePendingBindings();
        activityOzogramHomeBinding.setLifecycleOwner(OzogramHomeActivity.this);
        setupSwipLayout();
        init();

    }


    private void init() {

        storyUserRecycleViewAdapter = new StoryUserRecycleViewAdapter(getApplicationContext(), post);
        activityOzogramHomeBinding.recycleStoryUser.setHasFixedSize(true);
        activityOzogramHomeBinding.recycleStoryUser.setNestedScrollingEnabled(true);
        activityOzogramHomeBinding.recycleStoryUser.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        activityOzogramHomeBinding.recycleStoryUser.setAdapter(storyUserRecycleViewAdapter);
        ItemClickSupport.addTo(activityOzogramHomeBinding.recycleStoryUser).
                getmOnItemClickListener(new ItemClickSupport.OnItemClickListener(){
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

            }
        });





        postRecycelAdapter = new PostRecycleViewAdapter(getApplicationContext(), post);
        activityOzogramHomeBinding.recyclePostList.setHasFixedSize(true);
        activityOzogramHomeBinding.recyclePostList.setNestedScrollingEnabled(true);
        activityOzogramHomeBinding.recyclePostList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        activityOzogramHomeBinding.recyclePostList.setAdapter(postRecycelAdapter);
        ItemClickSupport.addTo(activityOzogramHomeBinding.recyclePostList).
                getmOnItemClickListener(new ItemClickSupport.OnItemClickListener(){
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                    }
                });


    }

    private void setupSwipLayout() {
        homeViewModel.getPost(getApplicationContext());
        activityOzogramHomeBinding.swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                activityOzogramHomeBinding.swipeLayout.setRefreshing(true);
                homeViewModel.getPost(getApplicationContext());
            }
        });
    }


    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    @Override
    public void onStarted() {
        Log.d("Home", "On start");
    }

    @Override
    public void onSuccess(LiveData<GetPostResponseModel> postResponse) {
        postResponse.observe(OzogramHomeActivity.this, new Observer<GetPostResponseModel>() {
            @Override
            public void onChanged(GetPostResponseModel responseModel) {
                //save access token
                hideProgressDialog();
                try {
                    if (responseModel.getCode() == 200 && responseModel.getStatus().equalsIgnoreCase("OK")) {
                        Log.d("Home", "Response : Code" + responseModel.getCode());
                        setPost(responseModel.getData());
                    } else {
                        activityOzogramHomeBinding.swipeLayout.setRefreshing(false);
                        Log.d("Home", "Response fail: Code" + responseModel.getCode());
                        showSnackbar(activityOzogramHomeBinding.nestedList, responseModel.getMessage(), Snackbar.LENGTH_SHORT);
                    }
                } catch (Exception e) {
                } finally {
                    activityOzogramHomeBinding.swipeLayout.setRefreshing(false);
                    hideProgressDialog();
                }
            }
        });
    }

    @Override
    public void onFailure(String message) {
        activityOzogramHomeBinding.swipeLayout.setRefreshing(false);
    }

    public void showSnackbar(View view, String message, int duration) {
        Snackbar snackbar = Snackbar.make(view, message, duration);
        snackbar.setActionTextColor(Color.WHITE);
        snackbar.setBackgroundTint(getResources().getColor(R.color.colorPrimaryDark));
        snackbar.show();
    }

    public void setPost(List<GetPostDataModel> post) {
        this.post = post;
        activityOzogramHomeBinding.swipeLayout.setRefreshing(false);
        storyUserRecycleViewAdapter.updateList(post);
        postRecycelAdapter.updateList(post);

    }

    public List<GetPostDataModel> getPost() {
        return post;
    }
}