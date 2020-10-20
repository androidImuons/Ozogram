package com.ozonetech.ozogram.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ozonetech.ozogram.app.utils.SessionManager;
import com.ozonetech.ozogram.model.UserData;
import com.ozonetech.ozogram.repository.ProfileRepository;
import com.ozonetech.ozogram.view.listeners.UnFollowUserListener;
import com.ozonetech.ozogram.view.listeners.UserProfileListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnfollowUsersResponseModel extends ViewModel {
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
    private List<UnFollowerData> data = null;

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

    public List<UnFollowerData> getData() {
        return data;
    }

    public void setData(List<UnFollowerData> data) {
        this.data = data;
    }

    private LiveData<UnfollowUsersResponseModel> unfollowUsersResponse;
    public UnFollowUserListener unFollowUserListener;

    public void fetchUnFollowUsersList(Context context, UnFollowUserListener unFollowUserListener) {
        if (unfollowUsersResponse == null) {
            unfollowUsersResponse = new MutableLiveData<UnfollowUsersResponseModel>();
            //we will load it asynchronously from server in this method
            unfollowUsersResponse = new ProfileRepository().fetchUnFollowUsers(context);
            unFollowUserListener.onGetUnFollowUserSuccess(unfollowUsersResponse);
        }else{
            unfollowUsersResponse = new ProfileRepository().fetchUnFollowUsers(context);
            unFollowUserListener.onGetUnFollowUserSuccess(unfollowUsersResponse);
        }
    }

}
