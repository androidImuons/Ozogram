package com.ozonetech.ozogram.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ozonetech.ozogram.model.GetPostResponseModel;
import com.ozonetech.ozogram.repository.GetPosts;
import com.ozonetech.ozogram.view.activity.GalleryActivity;
import com.ozonetech.ozogram.view.listeners.GetPostDataListener;

public class HomeViewModel extends ViewModel {
    public GetPostDataListener postDataListener;
    LiveData<GetPostResponseModel> getPosts;

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

    public void onClickHome(View view){

    }

    public void onClicVideo(View view){

    }
    public void onClickGallery(View view){
        Intent intent=new Intent(view.getContext(), GalleryActivity.class);
        view.getContext().startActivity(intent);
    }
    public void onClickFav(View view){

    }
    public void onClickProfile(View view){

    }
}
