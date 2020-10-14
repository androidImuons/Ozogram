package com.ozonetech.ozogram.app.web_api_services;

import com.ozonetech.ozogram.model.GetPostResponseModel;
import com.ozonetech.ozogram.model.LoginResponseModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AppServices {
    @FormUrlEncoded
    @POST("ozogram/login")
    Call<LoginResponseModel> userLogin(@FieldMap Map<String, String> loginMap);


    @POST("ozogram/get_posts")
    Call<GetPostResponseModel> getPostS();
}
