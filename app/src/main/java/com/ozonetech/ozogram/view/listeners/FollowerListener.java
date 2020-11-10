package com.ozonetech.ozogram.view.listeners;

import androidx.lifecycle.LiveData;

import com.ozonetech.ozogram.model.CommonResponse;
import com.ozonetech.ozogram.viewmodel.FollowerResponseModel;
import com.ozonetech.ozogram.viewmodel.UserProfileResponseModel;

public interface FollowerListener {
    void onGetFollowersSuccess(LiveData<FollowerResponseModel> followerResponse);

    void onGetFollowingsSuccess(LiveData<FollowerResponseModel> followerResponse);

    void onGetFollowUnFollowUserResponse(LiveData<CommonResponse> commonResponse);

    void onFollowerProfileSuccess(LiveData<UserProfileResponseModel> userProfileResponse);
}
