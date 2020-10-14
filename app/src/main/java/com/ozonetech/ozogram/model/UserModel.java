package com.ozonetech.ozogram.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserModel {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("topup_status")
    @Expose
    private Integer topupStatus;
    private final static long serialVersionUID = -4599323318968435094L;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Integer getTopupStatus() {
        return topupStatus;
    }

    public void setTopupStatus(Integer topupStatus) {
        this.topupStatus = topupStatus;
    }

}
