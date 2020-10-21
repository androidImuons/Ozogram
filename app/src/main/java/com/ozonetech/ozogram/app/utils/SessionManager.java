package com.ozonetech.ozogram.app.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.ozonetech.ozogram.view.activity.LoginActivity;
import com.ozonetech.ozogram.view.activity.OzogramHomeActivity;

import java.util.HashMap;

public class SessionManager {
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "OzogramPref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_USERNAME = "username";
    public static final String KEY_ACCESS_TOKEN="accessToken";
    public static final String KEY_ID ="id";
    public static final String KEY_USERID = "userId";
    public static final String KEY_FULL_NAME="fullname";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_MOBILE="mobile";
    public static final String KEY_BIO = "bio";
    public static final String KEY_PROFILE_PICTURE="profilePicture";
    public static final String KEY_JOINING_DATE = "joiningDate";
    public static final String KEY_GENDER="gender";
    public static final String KEY_WEBSITE = "website";
    public static final String KEY_POST_COUNT="postCount";
    public static final String KEY_FOLLOWERS_COUNT="followersCount";
    public static final String KEY_FOLLOWINGS_COUNT="followingCount";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    /**
     * Create login session
     * */
    public void createLoginSession(String username,String accessToken){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_ACCESS_TOKEN,accessToken);

        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // Staring Login Activity
            _context.startActivity(i);
        }else{
            goToOzogramHomeActivity();
        }

    }

    public void goToOzogramHomeActivity() {
        Intent intent = new Intent(_context, OzogramHomeActivity.class);
        // Closing all the Activities
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Add new Flag to start new Activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Staring Login Activity
        _context.startActivity(intent);
    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_ID,pref.getString(KEY_ID,"0"));
        user.put(KEY_USERID,pref.getString(KEY_USERID,null));
        user.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null));
        user.put(KEY_ACCESS_TOKEN,pref.getString(KEY_ACCESS_TOKEN,null));
        user.put(KEY_FULL_NAME,pref.getString(KEY_FULL_NAME,null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_MOBILE,pref.getString(KEY_MOBILE,null));
        user.put(KEY_BIO, pref.getString(KEY_BIO, null));
        user.put(KEY_PROFILE_PICTURE,pref.getString(KEY_PROFILE_PICTURE,null));
        user.put(KEY_WEBSITE,pref.getString(KEY_WEBSITE,null));
        user.put(KEY_GENDER,pref.getString(KEY_GENDER,null));
        user.put(KEY_JOINING_DATE,pref.getString(KEY_JOINING_DATE,null));
        user.put(KEY_POST_COUNT,pref.getString(KEY_POST_COUNT,"0"));
        user.put(KEY_FOLLOWERS_COUNT,pref.getString(KEY_FOLLOWERS_COUNT,"0"));
        user.put(KEY_FOLLOWINGS_COUNT,pref.getString(KEY_FOLLOWINGS_COUNT,"0"));
        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void saveUserProfileData(int id,String userId, String fullname, String email, String mobile, String bio,
                                    String profilePicture,String website,String gender, String joiningDate,
                                    int postCount,int followersCount,int followingCount) {
        // Storing name in pref
        editor.putString(KEY_ID,String.valueOf(id));
        editor.putString(KEY_USERID, userId);
        editor.putString(KEY_FULL_NAME,fullname);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_MOBILE,mobile);
        editor.putString(KEY_BIO, bio);
        editor.putString(KEY_PROFILE_PICTURE,profilePicture);
        editor.putString(KEY_WEBSITE,website);
        editor.putString(KEY_GENDER,gender);
        editor.putString(KEY_JOINING_DATE, joiningDate);
        editor.putString(KEY_POST_COUNT, String.valueOf(postCount));
        editor.putString(KEY_FOLLOWERS_COUNT, String.valueOf(followersCount));
        editor.putString(KEY_FOLLOWINGS_COUNT, String.valueOf(followingCount));
        // commit changes
        editor.commit();
    }

    public void setEditProfileData(String bio, String website,String gender) {
        // Storing name in pref

        editor.putString(KEY_BIO, bio);
        //editor.putString(KEY_PROFILE_PICTURE,profilePicture);
        editor.putString(KEY_WEBSITE, website);
        editor.putString(KEY_GENDER,gender);
        // commit changes
        editor.commit();
    }

    public void setProfileBio(String bio) {
        editor.putString(KEY_BIO, bio);
        // commit changes
        editor.commit();
    }
}