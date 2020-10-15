package com.ozonetech.ozogram.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ozonetech.ozogram.app.utils.SessionManager;
import com.ozonetech.ozogram.app.web_api_services.AppServices;
import com.ozonetech.ozogram.app.web_api_services.ServiceGenerator;
import com.ozonetech.ozogram.model.UserProfileResponseModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileRepository {

    private MutableLiveData<UserProfileResponseModel> userProfileResponse;
    UserProfileResponseModel userProfileResponseModel;


    public LiveData<UserProfileResponseModel> checkUserProfileData(Map<String, String> userProfileMap,Context context) {

        userProfileResponse = new MutableLiveData<>();
        SessionManager session = new SessionManager(context);
        AppServices apiService = ServiceGenerator.createService(AppServices.class,session.getUserDetails().get(session.KEY_ACCESS_TOKEN));
        apiService.userProfile(userProfileMap).enqueue(new Callback<UserProfileResponseModel>() {
            @Override
            public void onResponse(Call<UserProfileResponseModel> call, Response<UserProfileResponseModel> response) {
                if (response.isSuccessful()) {
                    userProfileResponseModel = response.body();
                    userProfileResponse.setValue(userProfileResponseModel);
                } else {
                    userProfileResponseModel = response.body();
                    userProfileResponse.setValue(userProfileResponseModel);
                }
            }

            @Override
            public void onFailure(Call<UserProfileResponseModel> call, Throwable t) {
                //Toast.makeText(UserRepository.this.getClass(), "Please check your internet", Toast.LENGTH_SHORT).show();
            }
        });
        return userProfileResponse;
    }
}
