package com.ozonetech.ozogram.view.activity;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ozonetech.ozogram.R;
import com.ozonetech.ozogram.app.utils.SessionManager;
import com.ozonetech.ozogram.databinding.ActivityLoginBinding;
import com.ozonetech.ozogram.model.LoginResponseModel;
import com.ozonetech.ozogram.view.listeners.LoginListener;
import com.ozonetech.ozogram.viewmodel.LoginViewModel;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends BaseActivity implements LoginListener {

    ActivityLoginBinding loginBinding;
    LoginViewModel loginViewModel;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginBinding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(LoginActivity.this).get(LoginViewModel.class);
        loginBinding.setLogin(loginViewModel);
        loginViewModel.loginListener = this;
        loginBinding.executePendingBindings();
        loginBinding.setLifecycleOwner(LoginActivity.this);

        session = new SessionManager(getApplicationContext());

    }

    @Override
    public void onStarted() {
        showProgressDialog("Please wait...");
    }

    @Override
    public void onLoginSuccess(final LiveData<LoginResponseModel> loginResponse) {

        loginResponse.observe(LoginActivity.this, new Observer<LoginResponseModel>() {
            @Override
            public void onChanged(LoginResponseModel loginResponseModel) {
                //save access token
                hideProgressDialog();
                try {
                    if (loginResponse.getValue().getCode() == 200 && loginResponse.getValue().getStatus().equalsIgnoreCase("OK")) {
                        showSnackbar(loginBinding.llLogin, loginResponse.getValue().getMessage(), Snackbar.LENGTH_SHORT);
                        session.createLoginSession(loginViewModel.username,loginResponse.getValue().getData().getAccessToken());
                        goToOzogramHomeActivity();
                        Log.d("LoginActivity", "Response : Code" + loginResponse.getValue().getCode() + "\n Status : " + loginResponse.getValue().getStatus() + "\n Message : " + loginResponse.getValue().getMessage());

                    } else {
                        showSnackbar(loginBinding.llLogin, loginResponse.getValue().getMessage(), Snackbar.LENGTH_SHORT);
                    }
                } catch (Exception e) {
                } finally {
                    hideProgressDialog();
                }
            }
        });
    }

    @Override
    public void onLoginFailure(String message) {
        showSnackbar(loginBinding.llLogin, message, Snackbar.LENGTH_SHORT);
    }

    public void showSnackbar(View view, String message, int duration) {
        Snackbar snackbar = Snackbar.make(view, message, duration);
        snackbar.setActionTextColor(Color.WHITE);
        snackbar.setBackgroundTint(getResources().getColor(R.color.colorPrimaryDark));
        snackbar.show();
    }

    private void goToOzogramHomeActivity() {
        Intent intent = new Intent(LoginActivity.this,OzogramHomeActivity.class);
        // Closing all the Activities
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Add new Flag to start new Activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
        //supportFinishAfterTransition();
    }
}