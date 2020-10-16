package com.ozonetech.ozogram.app.web_api_services;

import com.ozonetech.ozogram.model.CommonResponse;
import com.ozonetech.ozogram.model.GetPostResponseModel;
import com.ozonetech.ozogram.model.LoginResponseModel;
import com.ozonetech.ozogram.viewmodel.UserProfileResponseModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface AppServices {
    @FormUrlEncoded
    @POST("login")
    Call<LoginResponseModel> userLogin(@FieldMap Map<String, String> loginMap);

    @FormUrlEncoded
    @POST("user_profile")
    Call<UserProfileResponseModel> userProfile(@FieldMap Map<String, String> userProfileMap);

    @POST("get_posts")
    Call<GetPostResponseModel> getPostS();

    @Multipart
    @POST("upload_post")
    Call<CommonResponse> postUpload(@Header("Authorization") String authHeader, @PartMap() HashMap<String, RequestBody> param,
                                    @Part List<MultipartBody.Part> file);
    @FormUrlEncoded
    @POST("like_post")
    Call<CommonResponse> postLike(@FieldMap Map<String, String> param);

    @FormUrlEncoded
    @POST("comment_on_post")
    Call<CommonResponse> postComment(@FieldMap Map<String, String> param);
}
