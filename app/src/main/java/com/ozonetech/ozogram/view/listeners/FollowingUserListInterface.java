package com.ozonetech.ozogram.view.listeners;

import androidx.lifecycle.LiveData;

import com.ozonetech.ozogram.model.FollowingUserResponse;
import com.ozonetech.ozogram.model.GetPostResponseModel;

public interface FollowingUserListInterface {
    void onSuccessFollowingUser(LiveData<FollowingUserResponse> postResponse);
}

