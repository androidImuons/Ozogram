package com.ozonetech.ozogram.viewmodel;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ozonetech.ozogram.app.utils.SessionManager;
import com.ozonetech.ozogram.model.CommonResponse;
import com.ozonetech.ozogram.model.Follower;
import com.ozonetech.ozogram.repository.ProfileRepository;
import com.ozonetech.ozogram.view.listeners.FollowerListener;
import com.ozonetech.ozogram.view.listeners.UnFollowUserListener;
import com.ozonetech.ozogram.view.listeners.UserProfileListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FollowerResponseModel extends ViewModel {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<Follower> data = null;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Follower> getData() {
        return data;
    }

    public void setData(List<Follower> data) {
        this.data = data;
    }

    private LiveData<FollowerResponseModel> followerResponse;
    public FollowerListener followerListener;

    private LiveData<CommonResponse> commonResponse;

    public void fetchFollowers(Context context, FollowerListener followerListener,Map<String, String> followerMap) {

        if (followerResponse == null) {
            followerResponse = new MutableLiveData<FollowerResponseModel>();
            //we will load it asynchronously from server in this method
            followerResponse = new ProfileRepository().getFollowers(followerMap,context);
            followerListener.onGetFollowersSuccess(followerResponse);
        }else{
            followerResponse = new ProfileRepository().getFollowers(followerMap,context);
            followerListener.onGetFollowersSuccess(followerResponse);        }
    }

    public void fetchFollowings(Context context, FollowerListener followerListener,Map<String, String> followerMap) {

        if (followerResponse == null) {
            followerResponse = new MutableLiveData<FollowerResponseModel>();
            //we will load it asynchronously from server in this method
            followerResponse = new ProfileRepository().getFollowings(followerMap,context);
            followerListener.onGetFollowingsSuccess(followerResponse);
        }else{
            followerResponse = new ProfileRepository().getFollowings(followerMap,context);
            followerListener.onGetFollowingsSuccess(followerResponse);        }
    }


    public void sendfollowUnFollow(Context context, Map<String, String> followDataMap, FollowerListener followerListener){
        if (commonResponse == null) {
            commonResponse = new MutableLiveData<CommonResponse>();
            //we will load it asynchronously from server in this method
            commonResponse = new ProfileRepository().followUnFollow(followDataMap,context);
            followerListener.onGetFollowUnFollowUserResponse(commonResponse);
        }else{
            commonResponse = new ProfileRepository().followUnFollow(followDataMap,context);
            followerListener.onGetFollowUnFollowUserResponse(commonResponse);        }
    }
}
