package com.ozonetech.ozogram.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ozonetech.ozogram.app.utils.SessionManager;
import com.ozonetech.ozogram.model.CommonResponse;
import com.ozonetech.ozogram.model.GetPostResponseModel;
import com.ozonetech.ozogram.model.GetUnfollowUserResponse;
import com.ozonetech.ozogram.model.LoginResponseModel;
import com.ozonetech.ozogram.repository.FollowUnFollow;
import com.ozonetech.ozogram.repository.GetPosts;
import com.ozonetech.ozogram.repository.GetUser;
import com.ozonetech.ozogram.repository.LikePost;
import com.ozonetech.ozogram.repository.LoginRepository;
import com.ozonetech.ozogram.repository.ProfileRepository;
import com.ozonetech.ozogram.repository.SharePost;
import com.ozonetech.ozogram.view.activity.GalleryActivity;
import com.ozonetech.ozogram.view.activity.ProfileActivity;
import com.ozonetech.ozogram.view.listeners.CommonResponseInterface;
import com.ozonetech.ozogram.view.listeners.GetPostDataListener;
import com.ozonetech.ozogram.view.listeners.GetUserInterface;
import com.ozonetech.ozogram.view.listeners.UserProfileListener;

import java.util.HashMap;
import java.util.Map;

public class HomeViewModel extends ViewModel {
    public GetPostDataListener postDataListener;
    LiveData<GetPostResponseModel> getPosts;
    public CommonResponseInterface commonResponseInterface;
    public GetUserInterface getUserInterface;

    public void getPost(Context context) {
        postDataListener.onStarted();
        Log.d("HomeViewModel", "--1-");
        if (getPosts == null) {
            Log.d("HomeViewModel", "--2-");
//            getPosts = new MutableLiveData<GetPostResponseModel>();
            getPosts = new GetPosts().GetPost(context);
            postDataListener.onSuccess(getPosts);
        } else {
            Log.d("HomeViewModel", "--3-");
            postDataListener.onSuccess(getPosts = new GetPosts().GetPost(context));
        }
    }

    private LiveData<CommonResponse> commonResponseLiveData;

    public void postLike(Context context, String post_id, String action) {


        Map<String, String> likeparam = new HashMap<>();
        likeparam.put("post_id", post_id);
        likeparam.put("action", action);

        //if the list is null
        if (commonResponseLiveData == null) {
            commonResponseLiveData = new MutableLiveData<CommonResponse>();
            commonResponseLiveData = new LikePost().postLike(likeparam, context);
            commonResponseInterface.onCommonSuccess(commonResponseLiveData);
        } else {
            commonResponseLiveData = new LikePost().postLike(likeparam, context);
            commonResponseInterface.onCommonSuccess(commonResponseLiveData);
        }
    }

    public void postComment(Context context, String post_id, String action) {

        Map<String, String> likeparam = new HashMap<>();
        likeparam.put("post_id", post_id);
        likeparam.put("comment", action);

        //if the list is null
        if (commonResponseLiveData == null) {
            commonResponseLiveData = new MutableLiveData<CommonResponse>();
            commonResponseLiveData = new LikePost().postComment(likeparam, context);
            commonResponseInterface.onCommonSuccess(commonResponseLiveData);
        } else {
            commonResponseLiveData = new LikePost().postComment(likeparam, context);
            commonResponseInterface.onCommonSuccess(commonResponseLiveData);
        }
    }

    public void followUnfollow(Context context, String user_id, String action) {

        Map<String, String> likeparam = new HashMap<>();
        likeparam.put("user_id", user_id);
        likeparam.put("action", action);

        //if the list is null
        if (commonResponseLiveData == null) {
            commonResponseLiveData = new MutableLiveData<CommonResponse>();
            commonResponseLiveData = new FollowUnFollow().followUnFollow(likeparam, context);
            commonResponseInterface.onCommonSuccess(commonResponseLiveData);
        } else {
            commonResponseLiveData = new FollowUnFollow().followUnFollow(likeparam, context);
            commonResponseInterface.onCommonSuccess(commonResponseLiveData);
        }
    }


    private LiveData<GetUnfollowUserResponse> userResponseLiveData;

    public void getUser(Context context, String search) {

        Map<String, String> likeparam = new HashMap<>();
        //likeparam.put("search", search);
        commonResponseInterface.onCommoStarted();
        //if the list is null
        if (userResponseLiveData == null) {
            userResponseLiveData = new MutableLiveData<GetUnfollowUserResponse>();
            userResponseLiveData = new GetUser().getUser(likeparam, context);
            getUserInterface.onUserSuccess(userResponseLiveData);
        } else {
            userResponseLiveData = new GetUser().getUser(likeparam, context);
            getUserInterface.onUserSuccess(userResponseLiveData);
        }
    }



    public void onClickHome(View view) {

    }

    public void onClicVideo(View view) {

    }

    public void onClickGallery(View view) {
        Intent intent = new Intent(view.getContext(), GalleryActivity.class);
        view.getContext().startActivity(intent);
    }

    public void onClickFav(View view) {

    }

    public void onClickProfile(View view) {
        Intent intent = new Intent(view.getContext(), ProfileActivity.class);
        view.getContext().startActivity(intent);
    }
}
