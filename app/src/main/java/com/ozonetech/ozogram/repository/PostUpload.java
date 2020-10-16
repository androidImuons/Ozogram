package com.ozonetech.ozogram.repository;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.ozonetech.ozogram.app.utils.SessionManager;
import com.ozonetech.ozogram.app.web_api_services.AppServices;
import com.ozonetech.ozogram.app.web_api_services.ServiceGenerator;
import com.ozonetech.ozogram.model.CommonResponse;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;

import static android.widget.Toast.LENGTH_SHORT;

public class PostUpload {

    private String tag="PostUpload";

    public void postUpload(HashMap<String, RequestBody> param, ArrayList<String> filepath1, Context context) {

        ArrayList<String> filepath = new ArrayList<>();
        filepath.add("/storage/emulated/0/DCIM/Camera/20200911_181635.jpg");
        filepath.add("/storage/emulated/0/DCIM/Camera/20200911_181618.jpg");
        filepath.add("/storage/emulated/0/DCIM/Camera/20200903_153252.jpg");
//        MultipartBody.Builder builder = new MultipartBody.Builder();
//        builder.setType(MultipartBody.FORM);
//        builder.addFormDataPart("caption", "Robert");


        MultipartBody.Part body1 = null;
        List<MultipartBody.Part> parts = new ArrayList<>();
        for (int i = 0; i < filepath.size(); i++) {
            parts.add(prepareFilePart(filepath.get(i)));
        }

//        try {
//            File file = new File(m_selectedPath);
//            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//            body = MultipartBody.Part.createFormData("post_gallery_arr", file.getName().replace(" ", "_"), requestFile);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        SessionManager session = new SessionManager(context);
        AppServices apiService = ServiceGenerator.getApiService();
        Call<CommonResponse> call = apiService.postUpload("Bearer " +session.getUserDetails().get(SessionManager.KEY_ACCESS_TOKEN),
                param, parts); //, body


        call.enqueue(new Callback<CommonResponse>() {

            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {

                if (response.isSuccessful()) {
                    Log.d(tag, "--------------" + new Gson().toJson(response.body()));
                    CommonResponse responseState = response.body();

                }
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Log.d(tag, "--------------" + t.getMessage());
            }
        });
    }

    private MultipartBody.Part prepareFilePart(String fileUri){

        File file = new File(fileUri);
        MultipartBody.Part body = null;
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
         body = MultipartBody.Part.createFormData("post_gallery_arr[]", file.getName().replace(" ", "_"), requestFile);
        return body;
    }

}
