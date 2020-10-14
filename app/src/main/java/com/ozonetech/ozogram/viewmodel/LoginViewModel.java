package com.ozonetech.ozogram.viewmodel;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ozonetech.ozogram.model.LoginResponseModel;
import com.ozonetech.ozogram.repository.LoginRepository;
import com.ozonetech.ozogram.view.listeners.LoginListener;

import java.util.HashMap;
import java.util.Map;

public class LoginViewModel extends ViewModel {

    public String username;
    public String password;
    private String errorMessage = "Email or Password not valid";

    public LoginListener loginListener;
    private LiveData<LoginResponseModel> loginResponseModel;

    public void onLoginButtonClick(View view) {
        loginListener.onStarted();
        if(isInputDataValid()){
            loginListener.onLoginFailure(errorMessage);
            return;
        }

        Map<String, String> loginMap = new HashMap<>();
        loginMap.put("user_id", username);
        loginMap.put("password", password);

        //if the list is null
        if (loginResponseModel == null) {
            loginResponseModel = new MutableLiveData<LoginResponseModel>();
            loginResponseModel = new LoginRepository().checkUserLogin(loginMap);
            loginListener.onLoginSuccess(loginResponseModel);
        }else {
            loginResponseModel = new LoginRepository().checkUserLogin(loginMap);
            loginListener.onLoginSuccess(loginResponseModel);
        }
    }

    public boolean isInputDataValid() {
        return !TextUtils.isEmpty(username) && Patterns.EMAIL_ADDRESS.matcher(username).matches() && password.length() > 5;
    }
}
