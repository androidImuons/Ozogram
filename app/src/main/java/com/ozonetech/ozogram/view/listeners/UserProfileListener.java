package com.ozonetech.ozogram.view.listeners;

import androidx.lifecycle.LiveData;

import com.ozonetech.ozogram.viewmodel.UserProfileResponseModel;

public interface UserProfileListener {

    void onStarted();
    void onUserProfileSuccess(LiveData<UserProfileResponseModel> userProfileResponse);
    void onFailure(String message);

}
