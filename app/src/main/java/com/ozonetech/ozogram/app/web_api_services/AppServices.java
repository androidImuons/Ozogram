package com.ozonetech.ozogram.app.web_api_services;

import com.ozonetech.ozogram.model.GetPostResponseModel;
import com.ozonetech.ozogram.model.LoginResponseModel;
import com.ozonetech.ozogram.model.UserProfileResponseModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AppServices {
    @FormUrlEncoded
    @POST("login")
    Call<LoginResponseModel> userLogin(@FieldMap Map<String, String> loginMap);

    @FormUrlEncoded
    @POST("user_profile")
    Call<UserProfileResponseModel> userProfile(@FieldMap Map<String, String> userProfileMap);

    @POST("get_posts")
    Call<GetPostResponseModel> getPostS();
}
