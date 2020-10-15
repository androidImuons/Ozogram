package com.ozonetech.ozogram.model;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ozonetech.ozogram.R;

import java.util.List;

public class User extends ViewModel {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("bio")
    @Expose
    private String bio;
    @SerializedName("profile_picture")
    @Expose
    private String profilePicture;
    @SerializedName("joining_date")
    @Expose
    private String joiningDate;
    @SerializedName("posts")
    @Expose
    private List<Object> posts = null;
    @SerializedName("topup_status")
    @Expose
    private Integer topupStatus;

    // profile meta fields are ObservableField, will update the UI
    // whenever a new value is set
    public ObservableField<Long> numberOfFollowers = new ObservableField<>();
    public ObservableField<Long> numberOfPosts = new ObservableField<>();
    public ObservableField<Long> numberOfFollowing = new ObservableField<>();

    public User() {
    }

   // @Bindable
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
      ///  notifyPropertyChanged(BR.userId);
    }

    //@Bindable
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
       // notifyPropertyChanged(BR.fullname);
    }

   // @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
      //  notifyPropertyChanged(BR.email);
    }

   // @Bindable
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
       // notifyPropertyChanged(BR.mobile);
    }

   // @Bindable
    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
       // notifyPropertyChanged(BR.bio);
    }


    @BindingAdapter({"profilePicture"})
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext())
                .load(imageUrl)
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.profile_icon)
                .into(view);

        // If you consider Picasso, follow the below
        // Picasso.with(view.getContext()).load(imageUrl).placeholder(R.drawable.placeholder).into(view);
    }

  //  @Bindable
    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
       // notifyPropertyChanged(BR.profilePicture);
    }

   // @Bindable
    public String getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
       // notifyPropertyChanged(BR.joiningDate);
    }

    public List<Object> getPosts() {
        return posts;
    }

    public void setPosts(List<Object> posts) {
        this.posts = posts;
    }

   // @Bindable
    public Integer getTopupStatus() {
        return topupStatus;
    }

    public void setTopupStatus(Integer topupStatus) {
        this.topupStatus = topupStatus;
      //  notifyPropertyChanged(BR.topupStatus);
    }

    public ObservableField<Long> getNumberOfFollowers() {
        return numberOfFollowers;
    }

    public ObservableField<Long> getNumberOfPosts() {
        return numberOfPosts;
    }

    public ObservableField<Long> getNumberOfFollowing() {
        return numberOfFollowing;
    }

}
