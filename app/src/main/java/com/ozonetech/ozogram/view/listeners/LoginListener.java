package com.ozonetech.ozogram.view.listeners;

import androidx.lifecycle.LiveData;

import com.ozonetech.ozogram.model.LoginResponseModel;

public interface LoginListener {

    void onStarted();
    void onLoginSuccess(LiveData<LoginResponseModel> loginResponse);
    void onLoginFailure(String message);


}
