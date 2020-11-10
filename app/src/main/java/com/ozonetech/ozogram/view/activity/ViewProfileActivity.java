package com.ozonetech.ozogram.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.ozonetech.ozogram.R;
import com.ozonetech.ozogram.view.fragment.FollowersFragment;
import com.ozonetech.ozogram.view.fragment.FollowingsFragment;
import com.ozonetech.ozogram.viewmodel.UserProfileResponseModel;

public class ViewProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        UserProfileResponseModel userProfileResponseModel= (UserProfileResponseModel) getIntent().getSerializableExtra("data");
        Log.i("Response::", "---ViewProfileActivity --"+new Gson().toJson(userProfileResponseModel));

    }



    /*@Override
    public void onFollowersProfileView(UserProfileResponseModel userProfileResponseModel) {
        Log.d("ProfileActivity", "--onFollowersProfileView : " + userProfileResponseModel.getUser().getFullname());

        if (userProfileResponseModel.getCode() == 200 && userProfileResponseModel.getStatus().equalsIgnoreCase("OK")) {
            //  showSnackbar(activityProfileBinding.llUserProfile, userProfileResponseModel.getMessage(), Snackbar.LENGTH_SHORT);
            Log.d("ProfileActivity", "--Followers Response : Code" + userProfileResponseModel.getCode() + "\n Status : " + userProfileResponseModel.getStatus() + "\n Message : " + userProfileResponseModel.getMessage());
            Log.d("ProfileActivity", "--Followers User Data" + userProfileResponseModel.getUser().getFullname());

            // display user
            activityProfileBinding.setUser(userProfileResponseModel.getUser());

            // assign click handlers
            activityProfileBinding.setHandlers(handlers);


            List<PostData> postDataArrayList=new ArrayList<>();
            postDataArrayList=userProfileResponseModel.getUser().getPostDat();
            if(postDataArrayList.size() != 0 ){
                List<PostGalleryPath> postGalleryPathsArraylist=new ArrayList<>();
                for(int i=0;i<postDataArrayList.size();i++){
                    if(postDataArrayList.get(i).getPostGalleryPath().size() != 0){

                        for (int j=0;j<postDataArrayList.get(i).getPostGalleryPath().size();j++){
                            postGalleryPathsArraylist.add(postDataArrayList.get(i).getPostGalleryPath().get(j));
                        }

                    }
                }

                setRecyclerView(postGalleryPathsArraylist);

            }else{
                showSnackbar(activityProfileBinding.llUserProfile, "No Posts yet !", Snackbar.LENGTH_SHORT);
            }

        } else {
            showSnackbar(activityProfileBinding.llUserProfile, userProfileResponseModel.getMessage(), Snackbar.LENGTH_SHORT);
        }


    }

    @Override
    public void onFollowingProfileView(UserProfileResponseModel userProfileResponseModel) {

        Log.d("ProfileActivity", "--onFollowingProfileView : " + userProfileResponseModel.getUser().getFullname());

        if (userProfileResponseModel.getCode() == 200 && userProfileResponseModel.getStatus().equalsIgnoreCase("OK")) {
            //  showSnackbar(activityProfileBinding.llUserProfile, userProfileResponseModel.getMessage(), Snackbar.LENGTH_SHORT);
            Log.d("ProfileActivity", "--onFollowingProfileView: Code" + userProfileResponseModel.getCode() + "\n Status : " + userProfileResponseModel.getStatus() + "\n Message : " + userProfileResponseModel.getMessage());
            Log.d("ProfileActivity", "--onFollowingProfileViewUser Data " + userProfileResponseModel.getUser().getFullname());

            // display user
            activityProfileBinding.setUser(userProfileResponseModel.getUser());

            // assign click handlers
            activityProfileBinding.setHandlers(handlers);


            List<PostData> postDataArrayList=new ArrayList<>();
            postDataArrayList=userProfileResponseModel.getUser().getPostDat();
            if(postDataArrayList.size() != 0 ){
                List<PostGalleryPath> postGalleryPathsArraylist=new ArrayList<>();
                for(int i=0;i<postDataArrayList.size();i++){
                    if(postDataArrayList.get(i).getPostGalleryPath().size() != 0){

                        for (int j=0;j<postDataArrayList.get(i).getPostGalleryPath().size();j++){
                            postGalleryPathsArraylist.add(postDataArrayList.get(i).getPostGalleryPath().get(j));
                        }

                    }
                }

                setRecyclerView(postGalleryPathsArraylist);

            }else{
                showSnackbar(activityProfileBinding.llUserProfile, "No Posts yet !", Snackbar.LENGTH_SHORT);
            }

        } else {
            showSnackbar(activityProfileBinding.llUserProfile, userProfileResponseModel.getMessage(), Snackbar.LENGTH_SHORT);
        }



    }
*/
}