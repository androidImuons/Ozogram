package com.ozonetech.ozogram.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ozonetech.ozogram.app.utils.SessionManager;
import com.ozonetech.ozogram.model.UserData;
import com.ozonetech.ozogram.model.UserViewData;
import com.ozonetech.ozogram.repository.ProfileRepository;
import com.ozonetech.ozogram.view.listeners.UserProfileListener;

import java.util.HashMap;
import java.util.Map;

public class UserProfileViewResponseModel extends ViewModel {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private UserViewData user;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserViewData getUser() {
        return user;
    }

    public void setUser(UserViewData user) {
        this.user = user;
    }


}
