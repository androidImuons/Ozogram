package com.ozonetech.ozogram.view.listeners;

import androidx.lifecycle.LiveData;

import com.ozonetech.ozogram.model.GetPostResponseModel;

public interface GetPostDataListener {
    void onStarted();
    void onSuccess(LiveData<GetPostResponseModel> postResponse);
    void onFailure(String message);
}
