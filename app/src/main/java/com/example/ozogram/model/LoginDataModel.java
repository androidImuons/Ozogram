package com.example.ozogram.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginDataModel {
    @SerializedName("otpmode")
    @Expose
    private String otpmode;
    @SerializedName("mobileverification")
    @Expose
    private String mobileverification;
    @SerializedName("mailotp")
    @Expose
    private String mailotp;
    @SerializedName("google2faauth")
    @Expose
    private String google2faauth;
    @SerializedName("mailverification")
    @Expose
    private String mailverification;
    @SerializedName("access_token")
    @Expose
    private String accessToken;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("transaction_mode")
    @Expose
    private String transactionMode;

    public String getOtpmode() {
        return otpmode;
    }

    public void setOtpmode(String otpmode) {
        this.otpmode = otpmode;
    }

    public String getMobileverification() {
        return mobileverification;
    }

    public void setMobileverification(String mobileverification) {
        this.mobileverification = mobileverification;
    }

    public String getMailotp() {
        return mailotp;
    }

    public void setMailotp(String mailotp) {
        this.mailotp = mailotp;
    }

    public String getGoogle2faauth() {
        return google2faauth;
    }

    public void setGoogle2faauth(String google2faauth) {
        this.google2faauth = google2faauth;
    }

    public String getMailverification() {
        return mailverification;
    }

    public void setMailverification(String mailverification) {
        this.mailverification = mailverification;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTransactionMode() {
        return transactionMode;
    }

    public void setTransactionMode(String transactionMode) {
        this.transactionMode = transactionMode;
    }

}