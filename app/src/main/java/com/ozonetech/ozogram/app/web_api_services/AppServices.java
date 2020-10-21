package com.ozonetech.ozogram.app.web_api_services;

import com.ozonetech.ozogram.model.CommonResponse;
import com.ozonetech.ozogram.model.FollowingUserResponse;
import com.ozonetech.ozogram.model.GetPostResponseModel;
import com.ozonetech.ozogram.model.GetUnfollowUserResponse;
import com.ozonetech.ozogram.model.LoginResponseModel;
import com.ozonetech.ozogram.model.UpdateDataResponseModel;
import com.ozonetech.ozogram.viewmodel.FollowerResponseModel;
import com.ozonetech.ozogram.view.adapter.FollowingUserList;
import com.ozonetech.ozogram.viewmodel.UnfollowUsersResponseModel;
import com.ozonetech.ozogram.viewmodel.UserProfileResponseModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
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

    @POST("update_profile")
    Call<UpdateDataResponseModel> updateProfile(@Body RequestBody post);

    @POST("update_profile")
    Call<UpdateDataResponseModel> updateProfilePic(@Body RequestBody post);

    @POST("get_users")
    Call<UnfollowUsersResponseModel> getUnFollowers();

    @POST("get_posts")
    Call<GetPostResponseModel> getPostS();

    /*Parameters :
1) search -> optional (it can be user_id or name)*/
    @FormUrlEncoded
    @POST("get_followers")
    Call<FollowerResponseModel> getFollowers(@FieldMap Map<String , String> followersMap);

    @FormUrlEncoded
    @POST("get_followings")
    Call<FollowerResponseModel> getFollowings(@FieldMap Map<String , String> followingsMap);

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

    //    user_id -> required (Follower user id whom you want to follow)
// action -> required (follow , unfollow)
    @FormUrlEncoded
    @POST("follow_user")
    Call<CommonResponse> followUnfollow(@FieldMap Map<String, String> param);

    //search
    @FormUrlEncoded
    @POST("get_users")
    Call<GetUnfollowUserResponse> getUser(@FieldMap Map<String, String> param);

    //user_id ->required
//post_id ->required
//message ->optional
    @FormUrlEncoded
    @POST("share_post")
    Call<CommonResponse> postShare(@FieldMap Map<String, String> param);

    //search
    @FormUrlEncoded
    @POST("get_followings")
    Call<FollowingUserResponse> getFollowing(@FieldMap Map<String, String> param);
}



