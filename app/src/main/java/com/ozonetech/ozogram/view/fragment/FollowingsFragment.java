package com.ozonetech.ozogram.view.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.ozonetech.ozogram.R;
import com.ozonetech.ozogram.databinding.FragmentFollowingsBinding;
import com.ozonetech.ozogram.model.CommonResponse;
import com.ozonetech.ozogram.model.Follower;
import com.ozonetech.ozogram.view.adapter.FollowingAdapter;
import com.ozonetech.ozogram.view.listeners.FollowerListener;
import com.ozonetech.ozogram.viewmodel.FollowerResponseModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FollowingsFragment extends BaseFragment implements FollowerListener,FollowingAdapter.FollowingAdapterListener {

    FragmentFollowingsBinding dataBinding;
    FollowerResponseModel followerResponseModel;
    FollowingAdapter followingAdapter;

    public static FollowingsFragment newInstance() {
        return new FollowingsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        followerResponseModel= ViewModelProviders.of(getActivity()).get(FollowerResponseModel.class);
        dataBinding=  DataBindingUtil.inflate(inflater, R.layout.fragment_followings, container, false);
        dataBinding.setFollowingsModel(followerResponseModel);
        View view = dataBinding.getRoot();
        dataBinding.setLifecycleOwner(this);
        renderFollowings();
        return view;
    }

    private void renderFollowings() {
        dataBinding.rvFollowingList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        Map<String, String> followingMap = new HashMap<>();
        followingMap.put("search", "");

        showProgressDialog("Please wait...");
        followerResponseModel.fetchFollowings(getActivity(),followerResponseModel.followerListener=this,followingMap);
    }

    @Override
    public void onGetFollowersSuccess(LiveData<FollowerResponseModel> followerResponse) {

    }

    @Override
    public void onGetFollowingsSuccess(LiveData<FollowerResponseModel> followerResponse) {

        followerResponse.observe(getViewLifecycleOwner(), new Observer<FollowerResponseModel>() {
            @Override
            public void onChanged(FollowerResponseModel followerResponseModel) {
                hideProgressDialog();
                try {
                    if (followerResponseModel.getCode() == 200 && followerResponseModel.getStatus().equalsIgnoreCase("OK")) {
                        //  showSnackbar(storyFragmentBinding.flStoryFragment, userProfileResponse.getValue().getMessage(), Snackbar.LENGTH_SHORT);
                        Log.d("followerResponseModel", "Response : Code" + followerResponseModel.getCode() + "\n Status : " + followerResponseModel.getStatus() + "\n Message : " + followerResponseModel.getMessage());

                        if(followerResponseModel.getData()!=null){
                            List<Follower> followingList=new ArrayList<>();
                            followingList=followerResponseModel.getData();
                            setRecyclerView(followingList);
                        }else{
                            showSnackbar(dataBinding.flFollowingFragment, "Your not following anyone.", Snackbar.LENGTH_SHORT);
                        }

                    } else {
                        showSnackbar(dataBinding.flFollowingFragment, followerResponseModel.getMessage(), Snackbar.LENGTH_SHORT);
                    }
                } catch (Exception e) {
                } finally {
                    hideProgressDialog();
                }
            }
        });


    }

    @Override
    public void onGetFollowUnFollowUserResponse(LiveData<CommonResponse> commonResponse) {
        commonResponse.observe(getViewLifecycleOwner(), new Observer<CommonResponse>() {
            @Override
            public void onChanged(CommonResponse commonResponse) {
                //save access token
                hideProgressDialog();
                try {
                    if (commonResponse.getCode() == 200 && commonResponse.getStatus().equalsIgnoreCase("OK")) {
                        showSnackbar(dataBinding.flFollowingFragment, commonResponse.getMessage(), Snackbar.LENGTH_SHORT);
                        Log.d("FollowersFragment", "Response : Code" + commonResponse.getCode() + "\n Status : " + commonResponse.getStatus() + "\n Message : " + commonResponse.getMessage());
                        renderFollowings();
                    } else {
                        showSnackbar(dataBinding.flFollowingFragment, commonResponse.getMessage(), Snackbar.LENGTH_SHORT);
                    }
                } catch (Exception e) {
                } finally {
                    hideProgressDialog();
                }
            }
        });

    }

    private void setRecyclerView(List<Follower> followingList) {
        followingAdapter=new FollowingAdapter(this,followingList);
        dataBinding.rvFollowingList.setAdapter(followingAdapter);
    }

    @Override
    public void onFollowingClick(Follower follower) {
        showProgressDialog("Please wait...");

        Map<String, String> followUnfollowMap = new HashMap<>();
        followUnfollowMap.put("user_id", follower.getUserId());
        followUnfollowMap.put("action", "unfollow");            //follow , unfollow
        followerResponseModel.sendfollowUnFollow(getActivity(), followUnfollowMap,followerResponseModel.followerListener=this);

       // showSnackbar(dataBinding.flFollowingFragment,"Clicked on UnFollow"+ follower.getFullname(), Snackbar.LENGTH_SHORT);
    }
}