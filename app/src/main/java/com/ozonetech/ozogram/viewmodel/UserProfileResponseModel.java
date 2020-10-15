package com.ozonetech.ozogram.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ozonetech.ozogram.app.utils.SessionManager;
import com.ozonetech.ozogram.model.User;
import com.ozonetech.ozogram.repository.ProfileRepository;
import com.ozonetech.ozogram.view.listeners.UserProfileListener;

import java.util.HashMap;
import java.util.Map;

public class UserProfileResponseModel extends ViewModel {
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
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private LiveData<UserProfileResponseModel> userProfileResponse;
    public UserProfileListener userProfileListener;

    public void fetchUserProfileData(Context context, UserProfileListener userProfileListener) {
        SessionManager session = new SessionManager(context);
        Map<String, String> userProfileMap = new HashMap<>();
        userProfileMap.put("user_id", session.getUserDetails().get(SessionManager.KEY_USERNAME));

        if (userProfileResponse == null) {
            userProfileResponse = new MutableLiveData<UserProfileResponseModel>();
            //we will load it asynchronously from server in this method
            userProfileResponse = new ProfileRepository().fetchUserProfileData(userProfileMap,context);
            userProfileListener.onUserProfileSuccess(userProfileResponse);
        }else{
            userProfileResponse = new ProfileRepository().fetchUserProfileData(userProfileMap,context);
            userProfileListener.onUserProfileSuccess(userProfileResponse);
        }
    }


}
