package com.ozonetech.ozogram.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ozonetech.ozogram.app.utils.Contrants;

import java.util.List;

public class CommentModel {
    @SerializedName("user_id")
    @Expose
    private String userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("comment")
    @Expose
    private String comment;


    @SerializedName("time")
    @Expose
    private String time;

    @SerializedName("comment_likes_count")
    @Expose
    private Integer comment_likes_count;

    public List<LikeUserModel> getComment_like_users() {
        return comment_like_users;
    }

    public void setComment_like_users(List<LikeUserModel> comment_like_users) {
        this.comment_like_users = comment_like_users;
    }

    @SerializedName("comment_like_users")
    @Expose
    private List<LikeUserModel> comment_like_users;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getComment_likes_count() {
        return comment_likes_count;
    }

    public void setComment_likes_count(Integer comment_likes_count) {
        this.comment_likes_count = comment_likes_count;
    }

    private boolean readMore=true;

    public String getProfile_picture() {
        return Contrants.getValidString(profile_picture);
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    @SerializedName("profile_picture")
    @Expose
    private String profile_picture;

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

    public boolean isReadMore() {
        return readMore;
    }

    public void setReadMore(boolean readMore) {
        this.readMore = readMore;
    }

}
