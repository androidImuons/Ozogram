package com.example.ozogram.view.listeners;

import androidx.lifecycle.LiveData;

import com.example.ozogram.model.LoginResponseModel;

public interface LoginListener {

    void onStarted();
    void onLoginSuccess(LiveData<LoginResponseModel> loginResponse);
    void onLoginFailure(String message);


}
