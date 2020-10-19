package com.ozonetech.ozogram.app.web_api_services;

import com.ozonetech.ozogram.model.GetPostResponseModel;
import com.ozonetech.ozogram.model.LoginResponseModel;
import com.ozonetech.ozogram.model.UpdateDataResponseModel;
import com.ozonetech.ozogram.viewmodel.UserProfileResponseModel;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
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

    @POST("update_profile")
    Call<UpdateDataResponseModel> updateProfile(@Body RequestBody post);

    @POST("update_profile")
    Call<UpdateDataResponseModel> updateProfilePic(@Body RequestBody post);

    @POST("get_posts")
    Call<GetPostResponseModel> getPostS();
}
