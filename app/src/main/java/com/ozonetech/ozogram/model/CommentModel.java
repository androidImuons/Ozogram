package com.ozonetech.ozogram.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ozonetech.ozogram.app.utils.Contrants;

public class CommentModel {
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("comment")
    @Expose
    private String comment;
    private final static long serialVersionUID = 6945037347844154059L;

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

    public String getComment() {
        return Contrants.getValidString(comment);
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
