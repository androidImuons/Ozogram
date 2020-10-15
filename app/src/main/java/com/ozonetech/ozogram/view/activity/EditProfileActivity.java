package com.ozonetech.ozogram.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ozonetech.ozogram.R;
import com.ozonetech.ozogram.app.utils.SessionManager;
import com.ozonetech.ozogram.databinding.ActivityEditProfileBinding;
import com.ozonetech.ozogram.viewmodel.EditProfileModel;

public class EditProfileActivity extends AppCompatActivity {

    ActivityEditProfileBinding activityEditProfileBinding;
    EditProfileModel editProfileModel;
    MyClickHandlers handlers;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityEditProfileBinding= DataBindingUtil.setContentView(EditProfileActivity.this,R.layout.activity_edit_profile);
        sessionManager=new SessionManager(EditProfileActivity.this);
        handlers = new MyClickHandlers(this);
        renderEditProfile();
    }

    private void renderEditProfile() {

        editProfileModel=new EditProfileModel();
        editProfileModel.setProfilePicture(sessionManager.getUserDetails().get(SessionManager.KEY_PROFILE_PICTURE));
        editProfileModel.setFullName(sessionManager.getUserDetails().get(SessionManager.KEY_FULL_NAME));
        editProfileModel.setUserName(sessionManager.getUserDetails().get(SessionManager.KEY_USERNAME));
        editProfileModel.setBio(sessionManager.getUserDetails().get(SessionManager.KEY_BIO));
        editProfileModel.setEmail(sessionManager.getUserDetails().get(SessionManager.KEY_EMAIL));
        editProfileModel.setMobileNumber(sessionManager.getUserDetails().get(SessionManager.KEY_MOBILE));
        editProfileModel.setGender(sessionManager.getUserDetails().get(SessionManager.KEY_GENDER));
        editProfileModel.setWebsite(sessionManager.getUserDetails().get(SessionManager.KEY_WEBSITE));

        activityEditProfileBinding.setEditProfile(editProfileModel);
        // assign click handlers
        activityEditProfileBinding.setHandlers(handlers);

    }

    public class MyClickHandlers {

        Context context;

        public MyClickHandlers(Context context) {
            this.context = context;
        }

        public void onAcceptClicked(View view){

            Toast.makeText(getApplicationContext(), "Edit Profile Accept pressed!", Toast.LENGTH_LONG).show();

            editProfileModel.setProfilePicture(editProfileModel.getProfilePicture());
            editProfileModel.setFullName(editProfileModel.getFullName());
            editProfileModel.setUserName(editProfileModel.getUserName());
            editProfileModel.setBio(editProfileModel.getBio());
            editProfileModel.setEmail(editProfileModel.getEmail());
            editProfileModel.setMobileNumber(editProfileModel.getMobileNumber());
            editProfileModel.setGender(editProfileModel.getGender());
            editProfileModel.setWebsite(editProfileModel.getWebsite());

            activityEditProfileBinding.setEditProfile(editProfileModel);
            activityEditProfileBinding.setHandlers(handlers);

            sessionManager.setEditProfileData(editProfileModel.getProfilePicture(),editProfileModel.getFullName(),editProfileModel.getUserName(),
                    editProfileModel.getBio(),editProfileModel.getEmail() ,editProfileModel.getMobileNumber(),editProfileModel.getGender(),editProfileModel.getWebsite());
        }

        public void onCancelClicked(View view){
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}