package com.ozonetech.ozogram.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.ozonetech.ozogram.R;
import com.ozonetech.ozogram.app.utils.SessionManager;
import com.ozonetech.ozogram.databinding.ActivityAddProfileBinding;
import com.ozonetech.ozogram.model.UpdateDataResponseModel;
import com.ozonetech.ozogram.view.listeners.EditProfileListener;
import com.ozonetech.ozogram.viewmodel.EditProfileViewModel;

public class AddProfileActivity extends BaseActivity implements EditProfileListener {

    ActivityAddProfileBinding addProfileBinding;
    public EditProfileViewModel editProfileViewModel;
    private MyClickHandlers handlers;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addProfileBinding = DataBindingUtil.setContentView(AddProfileActivity.this, R.layout.activity_add_profile);
        editProfileViewModel = ViewModelProviders.of(AddProfileActivity.this).get(EditProfileViewModel.class);
        editProfileViewModel.editProfileListener = this;
        addProfileBinding.setEditProfile(editProfileViewModel);
        handlers = new MyClickHandlers(this);
        addProfileBinding.setHandlers(handlers);
        addProfileBinding.executePendingBindings();
        addProfileBinding.setLifecycleOwner(this);
        sessionManager = new SessionManager(getApplicationContext());

        Intent i = getIntent();
        String type = i.getStringExtra("type");
        if(type.equals("addBio")){
            addProfileBinding.llAddBio.setVisibility(View.VISIBLE);
            addProfileBinding.llEditName.setVisibility(View.GONE);
        }else if(type.equals("editName")){
            addProfileBinding.llAddBio.setVisibility(View.GONE);
            addProfileBinding.llEditName.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onUpdateProfileDataSuccess(LiveData<UpdateDataResponseModel> updateDataResponse) {
        updateDataResponse.observe(AddProfileActivity.this, new Observer<UpdateDataResponseModel>() {
            @Override
            public void onChanged(UpdateDataResponseModel updateDataResponseModel) {
                //save access token
                hideProgressDialog();
                try {
                    if (updateDataResponse.getValue().getCode() == 200 && updateDataResponse.getValue().getStatus().equalsIgnoreCase("OK")) {
                        showSnackbar(addProfileBinding.llAddProfile, updateDataResponse.getValue().getMessage(), Snackbar.LENGTH_SHORT);
                        sessionManager.setProfileBio(editProfileViewModel.bio);
                        Log.d("AddProfileActivity", "Response : Code" + updateDataResponse.getValue().getCode() + "\n Status : " + updateDataResponse.getValue().getStatus() + "\n Message : " + updateDataResponse.getValue().getMessage());

                    } else {
                        showSnackbar(addProfileBinding.llAddProfile, updateDataResponse.getValue().getMessage(), Snackbar.LENGTH_SHORT);
                    }
                } catch (Exception e) {
                } finally {
                    hideProgressDialog();
                }
            }
        });

    }

    public class MyClickHandlers {

        Context context;

        public MyClickHandlers(Context context) {
            this.context = context;
        }

        public void onCancelClick(View view) {
            goToProfileActivity();
        }

        public void onAcceptAddBioClick(View view){
            updateBio();
            showSnackbar(addProfileBinding.llAddProfile,addProfileBinding.inputAddBio.getText().toString(), Snackbar.LENGTH_SHORT);
        }

        public void onAcceptEditNameClick(View view){
            showSnackbar(addProfileBinding.llAddProfile,addProfileBinding.inputEditName.getText().toString(), Snackbar.LENGTH_SHORT);
        }
    }

    private void updateBio() {
        showProgressDialog("Please wait...");
        editProfileViewModel.onUpdateBio(AddProfileActivity.this, editProfileViewModel.editProfileListener = this);
    }

    public void showSnackbar(View view, String message, int duration) {
        Snackbar snackbar = Snackbar.make(view, message, duration);
        snackbar.setActionTextColor(Color.WHITE);
        snackbar.setBackgroundTint(getResources().getColor(R.color.colorPrimaryDark));
        snackbar.show();
    }

    private void goToProfileActivity() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goToProfileActivity();
    }
}