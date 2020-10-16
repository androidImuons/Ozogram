package com.ozonetech.ozogram.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.ozonetech.ozogram.R;
import com.ozonetech.ozogram.app.utils.SessionManager;
import com.ozonetech.ozogram.databinding.ActivityEditProfileBinding;
import com.ozonetech.ozogram.model.UpdateDataResponseModel;
import com.ozonetech.ozogram.view.listeners.EditProfileListener;
import com.ozonetech.ozogram.viewmodel.EditProfileViewModel;

public class EditProfileActivity extends BaseActivity implements EditProfileListener, View.OnClickListener {

    ActivityEditProfileBinding activityEditProfileBinding;
    public EditProfileViewModel editProfileViewModel;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editProfileViewModel = ViewModelProviders.of(EditProfileActivity.this).get(EditProfileViewModel.class);
        activityEditProfileBinding = DataBindingUtil.setContentView(EditProfileActivity.this, R.layout.activity_edit_profile);
        editProfileViewModel.editProfileListener = this;
        activityEditProfileBinding.executePendingBindings();
        activityEditProfileBinding.setLifecycleOwner(this);
        activityEditProfileBinding.setEditProfile(editProfileViewModel);
        sessionManager = new SessionManager(getApplicationContext());
        renderEditProfile();

    }

    private void renderEditProfile() {
        editProfileViewModel.setProfilePicture(sessionManager.getUserDetails().get(SessionManager.KEY_PROFILE_PICTURE));
        editProfileViewModel.setFullName(sessionManager.getUserDetails().get(SessionManager.KEY_FULL_NAME));
        editProfileViewModel.setUserName(sessionManager.getUserDetails().get(SessionManager.KEY_USERNAME));
        editProfileViewModel.setBio(sessionManager.getUserDetails().get(SessionManager.KEY_BIO));
        editProfileViewModel.setEmail(sessionManager.getUserDetails().get(SessionManager.KEY_EMAIL));
        editProfileViewModel.setMobileNumber(sessionManager.getUserDetails().get(SessionManager.KEY_MOBILE));
        editProfileViewModel.setGender(sessionManager.getUserDetails().get(SessionManager.KEY_GENDER));
        editProfileViewModel.setWebsite(sessionManager.getUserDetails().get(SessionManager.KEY_WEBSITE));
        activityEditProfileBinding.setEditProfile(editProfileViewModel);

        activityEditProfileBinding.ivAccept.setOnClickListener(this);
        activityEditProfileBinding.ivCancel.setOnClickListener(this);

    }

    private void updateProfiel() {
        showProgressDialog("Please wait...");
        editProfileViewModel.onUpdateProfile(EditProfileActivity.this, editProfileViewModel.editProfileListener = this);
    }


    @Override
    public void onUpdateProfileDataSuccess(LiveData<UpdateDataResponseModel> updateDataResponse) {

        updateDataResponse.observe(EditProfileActivity.this, new Observer<UpdateDataResponseModel>() {
            @Override
            public void onChanged(UpdateDataResponseModel updateDataResponseModel) {
                //save access token
                hideProgressDialog();
                try {
                    if (updateDataResponse.getValue().getCode() == 200 && updateDataResponse.getValue().getStatus().equalsIgnoreCase("OK")) {
                        showSnackbar(activityEditProfileBinding.llEditProfileData, updateDataResponse.getValue().getMessage(), Snackbar.LENGTH_SHORT);
                        sessionManager.setEditProfileData(editProfileViewModel.bio, editProfileViewModel.website, editProfileViewModel.gender);
                        Log.d("EditProfileActivity", "Response : Code" + updateDataResponse.getValue().getCode() + "\n Status : " + updateDataResponse.getValue().getStatus() + "\n Message : " + updateDataResponse.getValue().getMessage());

                    } else {
                        showSnackbar(activityEditProfileBinding.llEditProfileData, updateDataResponse.getValue().getMessage(), Snackbar.LENGTH_SHORT);
                    }
                } catch (Exception e) {
                } finally {
                    hideProgressDialog();
                }
            }
        });

    }

    public void showSnackbar(View view, String message, int duration) {
        Snackbar snackbar = Snackbar.make(view, message, duration);
        snackbar.setActionTextColor(Color.WHITE);
        snackbar.setBackgroundTint(getResources().getColor(R.color.colorPrimaryDark));
        snackbar.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_accept) {
            updateProfiel();
        }else if(view.getId() == R.id.iv_cancel){
           finish();
        }
    }


}