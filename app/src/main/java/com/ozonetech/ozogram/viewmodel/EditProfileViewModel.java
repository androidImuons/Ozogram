package com.ozonetech.ozogram.viewmodel;

import android.content.Context;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ozonetech.ozogram.R;
import com.ozonetech.ozogram.app.utils.SessionManager;
import com.ozonetech.ozogram.model.UpdateDataResponseModel;
import com.ozonetech.ozogram.repository.ProfileRepository;
import com.ozonetech.ozogram.view.activity.AddProfileActivity;
import com.ozonetech.ozogram.view.listeners.EditProfileListener;

import java.util.HashMap;
import java.util.Map;

public class EditProfileViewModel extends ViewModel {

    public String profilePicture;
    public String fullName;
    public String userName;
    public String website;
    public String bio;
    public String email;
    public String mobileNumber;
    public String gender;

    @BindingAdapter({"profilePicture"})
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.profile_icon)
                .into(view);
    }


    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public EditProfileListener editProfileListener;
    public LiveData<UpdateDataResponseModel> updateDataResponseModel;

    public void onUpdateProfile(Context context, EditProfileListener editProfileListener) {

        SessionManager sessionManager = new SessionManager(context);
        Map<String, String> updateProfielMap = new HashMap<>();
        String userid = sessionManager.getUserDetails().get(SessionManager.KEY_USERNAME);
        updateProfielMap.put("user_id", userid);
        updateProfielMap.put("bio", bio);
        updateProfielMap.put("website", website);


        //if the list is null
        if (updateDataResponseModel == null) {
            updateDataResponseModel = new MutableLiveData<UpdateDataResponseModel>();
            updateDataResponseModel = new ProfileRepository().updateUserProfile(updateProfielMap, context);
            editProfileListener.onUpdateProfileDataSuccess(updateDataResponseModel);
        } else {
            updateDataResponseModel = new ProfileRepository().updateUserProfile(updateProfielMap, context);
            editProfileListener.onUpdateProfileDataSuccess(updateDataResponseModel);
        }
    }

    public void onUpdateBio(Context context, EditProfileListener editProfileListener) {
        SessionManager sessionManager = new SessionManager(context);
        Map<String, String> updateProfielMap = new HashMap<>();
        String userid = sessionManager.getUserDetails().get(SessionManager.KEY_USERNAME);
        updateProfielMap.put("user_id", userid);
        updateProfielMap.put("bio", bio);

        //if the list is null
        if (updateDataResponseModel == null) {
            updateDataResponseModel = new MutableLiveData<UpdateDataResponseModel>();
            updateDataResponseModel = new ProfileRepository().updateUserProfile(updateProfielMap, context);
            editProfileListener.onUpdateProfileDataSuccess(updateDataResponseModel);
        } else {
            updateDataResponseModel = new ProfileRepository().updateUserProfile(updateProfielMap, context);
            editProfileListener.onUpdateProfileDataSuccess(updateDataResponseModel);
        }
    }
}
