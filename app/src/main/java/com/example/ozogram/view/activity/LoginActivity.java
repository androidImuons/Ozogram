package com.example.ozogram.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ozogram.R;
import com.example.ozogram.databinding.ActivityLoginBinding;
import com.example.ozogram.model.LoginResponseModel;
import com.example.ozogram.view.listeners.LoginListener;
import com.example.ozogram.viewmodel.LoginViewModel;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends BaseActivity implements LoginListener {

    ActivityLoginBinding loginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginBinding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_login);
        LoginViewModel loginViewModel = ViewModelProviders.of(LoginActivity.this).get(LoginViewModel.class);
        loginBinding.setLogin(loginViewModel);
        loginViewModel.loginListener = this;
        loginBinding.executePendingBindings();
        loginBinding.setLifecycleOwner(LoginActivity.this);

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
                        Log.d("ChatFragment", "Response : Code" + loginResponse.getValue().getCode() + "\n Status : " + loginResponse.getValue().getStatus() + "\n Message : " + loginResponse.getValue().getMessage());

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

    }

    public void showSnackbar(View view, String message, int duration) {
        Snackbar snackbar = Snackbar.make(view, message, duration);
        snackbar.setActionTextColor(Color.WHITE);
        snackbar.setBackgroundTint(getResources().getColor(R.color.colorPrimaryDark));
        snackbar.show();
    }
}