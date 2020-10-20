package com.ozonetech.ozogram.view.listeners;

import androidx.lifecycle.LiveData;

import com.ozonetech.ozogram.model.GetUnfollowUserResponse;
import com.ozonetech.ozogram.viewmodel.UserProfileResponseModel;

public interface GetUserInterface {

    void onUserSuccess(LiveData<GetUnfollowUserResponse> getUnfollowUserResponseLiveData);
}
