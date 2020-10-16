package com.ozonetech.ozogram.view.listeners;

import androidx.lifecycle.LiveData;

import com.ozonetech.ozogram.model.CommonResponse;
import com.ozonetech.ozogram.viewmodel.UserProfileResponseModel;

public interface CommonResponseInterface {
    void onCommoStarted();
    void onCommonSuccess(LiveData<CommonResponse> userProfileResponse);
    void onCommonFailure(String message);
}
