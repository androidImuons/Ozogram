package com.ozonetech.ozogram.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ozonetech.ozogram.model.CommonResponse;
import com.ozonetech.ozogram.repository.LikePost;
import com.ozonetech.ozogram.repository.PostUpload;
import com.ozonetech.ozogram.view.listeners.CommonResponseInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class CommonViewModel extends ViewModel {
    public CommonResponseInterface commonResponseInterface;
    private LiveData<CommonResponse> commonResponseLiveData;


    public void uploadPost(Context context, String post, ArrayList<String> arrayList) {


        RequestBody message = RequestBody.create(MediaType.parse("text/plain"), post);
        HashMap<String, okhttp3.RequestBody> map = new HashMap<>();
        map.put("caption", message);

        commonResponseInterface.onCommoStarted();
        //if the list is null
        if (commonResponseLiveData == null) {
            commonResponseLiveData = new MutableLiveData<CommonResponse>();
            commonResponseLiveData = new PostUpload().postUpload(map,arrayList,context);
            commonResponseInterface.onCommonSuccess(commonResponseLiveData);
        }else {
            commonResponseLiveData = new PostUpload().postUpload(map,arrayList,context);
            commonResponseInterface.onCommonSuccess(commonResponseLiveData);
        }
    }
}
