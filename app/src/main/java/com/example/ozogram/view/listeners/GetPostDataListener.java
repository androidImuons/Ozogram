package com.example.ozogram.view.listeners;

import androidx.lifecycle.LiveData;

import com.example.ozogram.model.GetPostResponseModel;
import com.example.ozogram.model.LoginResponseModel;

public interface GetPostDataListener {
    void onStarted();
    void onSuccess(LiveData<GetPostResponseModel> postResponse);
    void onFailure(String message);
}
