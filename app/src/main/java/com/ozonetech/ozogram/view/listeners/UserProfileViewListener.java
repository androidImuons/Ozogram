package com.ozonetech.ozogram.view.listeners;

import androidx.lifecycle.LiveData;

import com.ozonetech.ozogram.viewmodel.UserProfileResponseModel;
import com.ozonetech.ozogram.viewmodel.UserProfileViewResponseModel;

public interface UserProfileViewListener {

    void onStarted();
    void onUserProfileSuccess(LiveData<UserProfileViewResponseModel> userProfileResponse);

    void onFailure(String message);

}
