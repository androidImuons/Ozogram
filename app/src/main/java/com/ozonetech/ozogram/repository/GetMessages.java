package com.ozonetech.ozogram.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.ozonetech.ozogram.app.utils.SessionManager;
import com.ozonetech.ozogram.app.web_api_services.AppServices;
import com.ozonetech.ozogram.app.web_api_services.ServiceGenerator;
import com.ozonetech.ozogram.model.GetMessageResponse;
import com.ozonetech.ozogram.model.GetPostResponseModel;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetMessages {
    private MutableLiveData<GetMessageResponse> mutableLiveData;
    private GetMessageResponse getMessageResponse;

    public MutableLiveData<GetMessageResponse> getMessages(Context context,String user_id){
        mutableLiveData=new MutableLiveData<>();
        SessionManager session = new SessionManager(context);
        AppServices apiService = ServiceGenerator.createService(AppServices.class, session.getUserDetails().get(session.KEY_ACCESS_TOKEN));
        Map<String, String> param=new HashMap<>();
        param.put("user_id",user_id);
        apiService.get_message(param).enqueue(new Callback<GetMessageResponse>() {
            @Override
            public void onResponse(Call<GetMessageResponse> call, Response<GetMessageResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("response 200", "--2-"+new Gson().toJson(response.body()));
                    getMessageResponse = response.body();
                    mutableLiveData.setValue(getMessageResponse);
                } else {
                    Log.d("response not 200", "--3-"+response.message());
                    getMessageResponse = response.body();
                    mutableLiveData.setValue(getMessageResponse);
                }
            }

            @Override
            public void onFailure(Call<GetMessageResponse> call, Throwable t) {
                //Toast.makeText(UserRepository.this.getClass(), "Please check your internet", Toast.LENGTH_SHORT).show();
                Log.d("response fail", "--on fail-"+t.getMessage());
            }
        });
        return mutableLiveData;
    }
}
