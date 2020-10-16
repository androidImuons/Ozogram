package com.ozonetech.ozogram.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ozonetech.ozogram.app.utils.SessionManager;
import com.ozonetech.ozogram.app.web_api_services.AppServices;
import com.ozonetech.ozogram.app.web_api_services.ServiceGenerator;
import com.ozonetech.ozogram.model.UpdateDataResponseModel;
import com.ozonetech.ozogram.viewmodel.UserProfileResponseModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileRepository {

    private MutableLiveData<UserProfileResponseModel> userProfileResponse;
    UserProfileResponseModel userProfileResponseModel;

    private MutableLiveData<UpdateDataResponseModel> updateDataResponse;
    UpdateDataResponseModel updateDataResponseModel;


    public LiveData<UserProfileResponseModel> fetchUserProfileData(Map<String, String> userProfileMap,Context context) {

        userProfileResponse = new MutableLiveData<>();
        SessionManager session = new SessionManager(context);
        AppServices apiService = ServiceGenerator.createService(AppServices.class,session.getUserDetails().get(SessionManager.KEY_ACCESS_TOKEN));
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

    public LiveData<UpdateDataResponseModel> updateUserProfile(Map<String, String> updateProfileMap,Context context) {

        updateDataResponse = new MutableLiveData<>();
        SessionManager session = new SessionManager(context);
        AppServices apiService = ServiceGenerator.createService(AppServices.class,session.getUserDetails().get(SessionManager.KEY_ACCESS_TOKEN));
        apiService.updateProfile(updateProfileMap).enqueue(new Callback<UpdateDataResponseModel>() {
            @Override
            public void onResponse(Call<UpdateDataResponseModel> call, Response<UpdateDataResponseModel> response) {
                if (response.isSuccessful()) {
                    updateDataResponseModel = response.body();
                    updateDataResponse.setValue(updateDataResponseModel);
                } else {
                    updateDataResponseModel = response.body();
                    updateDataResponse.setValue(updateDataResponseModel);
                }
            }

            @Override
            public void onFailure(Call<UpdateDataResponseModel> call, Throwable t) {
                Log.d("get post", "--on fail-"+t.getMessage());
                //Toast.makeText(UserRepository.this.getClass(), "Please check your internet", Toast.LENGTH_SHORT).show();
            }
        });
        return updateDataResponse;
    }
}
