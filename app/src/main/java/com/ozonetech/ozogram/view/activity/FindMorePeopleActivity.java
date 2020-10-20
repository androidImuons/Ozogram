package com.ozonetech.ozogram.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.ozonetech.ozogram.R;
import com.ozonetech.ozogram.databinding.ActivityFindMorePeopleBinding;
import com.ozonetech.ozogram.model.PostData;
import com.ozonetech.ozogram.model.PostGalleryPath;
import com.ozonetech.ozogram.view.adapter.UnFollowUserAdapter;
import com.ozonetech.ozogram.view.listeners.UnFollowUserListener;
import com.ozonetech.ozogram.viewmodel.UnFollowerData;
import com.ozonetech.ozogram.viewmodel.UnfollowUsersResponseModel;

import java.util.ArrayList;
import java.util.List;

public class FindMorePeopleActivity extends BaseActivity implements UnFollowUserListener,UnFollowUserAdapter.UnFollowUserAdapterListener {

    ActivityFindMorePeopleBinding activityFindMorePeopleBinding;
    UnfollowUsersResponseModel unfollowUsersResponseModel;
    UnFollowUserAdapter unFollowUserAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityFindMorePeopleBinding= DataBindingUtil.setContentView(FindMorePeopleActivity.this,R.layout.activity_find_more_people);
        unfollowUsersResponseModel= ViewModelProviders.of(FindMorePeopleActivity.this).get(UnfollowUsersResponseModel.class);
        activityFindMorePeopleBinding.setUnfollowUserModel(unfollowUsersResponseModel);
        activityFindMorePeopleBinding.executePendingBindings();
        activityFindMorePeopleBinding.setLifecycleOwner(FindMorePeopleActivity.this);
        init();
    }

    private void init() {
        activityFindMorePeopleBinding.rvNewSuggestions.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        activityFindMorePeopleBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToProfileActivity();
            }
        });
        renderFindMorePeople();

    }

    private void renderFindMorePeople() {
        showProgressDialog("Please wait...");
        unfollowUsersResponseModel.fetchUnFollowUsersList(FindMorePeopleActivity.this, unfollowUsersResponseModel.unFollowUserListener=this);
    }

    @Override
    public void onGetUnFollowUserSuccess(LiveData<UnfollowUsersResponseModel> unfollowUsersResponse) {
        unfollowUsersResponse.observe(FindMorePeopleActivity.this, new Observer<UnfollowUsersResponseModel>() {
            @Override
            public void onChanged(UnfollowUsersResponseModel unfollowUsersResponseModel) {
                //save access token
                hideProgressDialog();
                try {
                    if (unfollowUsersResponseModel.getCode() == 200 && unfollowUsersResponseModel.getStatus().equalsIgnoreCase("OK")) {
                        //showSnackbar(activityFindMorePeopleBinding.llFindFollowers, unfollowUsersResponseModel.getMessage(), Snackbar.LENGTH_SHORT);
                        Log.d("FindMorePeopleActivity", "Response : Code" + unfollowUsersResponseModel.getCode() + "\n Status : " + unfollowUsersResponseModel.getStatus() + "\n Message : " + unfollowUsersResponseModel.getMessage());


                        List<UnFollowerData> unFollowUserArrayList=new ArrayList<>();
                        unFollowUserArrayList=unfollowUsersResponseModel.getData();
                        setRecyclerView(unFollowUserArrayList);


                    } else {
                        showSnackbar(activityFindMorePeopleBinding.llFindFollowers, unfollowUsersResponseModel.getMessage(), Snackbar.LENGTH_SHORT);
                    }
                } catch (Exception e) {
                } finally {
                    hideProgressDialog();
                }
            }
        });
    }

    private void setRecyclerView(List<UnFollowerData> unFollowUserArrayList) {
        unFollowUserAdapter=new UnFollowUserAdapter(unFollowUserArrayList,this);
        activityFindMorePeopleBinding.rvNewSuggestions.setAdapter(unFollowUserAdapter);

    }

    @Override
    public void onFollowRequestClicked(UnFollowerData unFollowerData) {
        Toast.makeText(getApplicationContext(), "Follow clicked! " + unFollowerData.getFullname(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRemoveUserClicked(UnFollowerData unFollowerData) {
        Toast.makeText(getApplicationContext(), "Remove clicked! " + unFollowerData.getFullname(), Toast.LENGTH_SHORT).show();

    }

    private void goToProfileActivity() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goToProfileActivity();
    }
}