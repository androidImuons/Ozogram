package com.ozonetech.ozogram.view.listeners;

import androidx.lifecycle.LiveData;

import com.ozonetech.ozogram.viewmodel.UnfollowUsersResponseModel;

public interface UnFollowUserListener {
    public void onGetUnFollowUserSuccess(LiveData<UnfollowUsersResponseModel> unfollowUsersResponse);
}
