package com.ozonetech.ozogram.view.activity;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.ozonetech.ozogram.R;
import com.ozonetech.ozogram.app.utils.ItemClickSupport;
import com.ozonetech.ozogram.app.utils.SessionManager;
import com.ozonetech.ozogram.appupdate.AppUpdateChecker;
import com.ozonetech.ozogram.appupdate.AppVersionModel;
import com.ozonetech.ozogram.databinding.ActivityOzogramHomeBinding;
import com.ozonetech.ozogram.model.CommonResponse;
import com.ozonetech.ozogram.model.GetPostRecordModel;
import com.ozonetech.ozogram.model.GetPostResponseModel;
import com.ozonetech.ozogram.model.GetUnfollowUserResponse;
import com.ozonetech.ozogram.model.UnfollowUserRecordDataModel;
import com.ozonetech.ozogram.repository.PostUpload;
import com.ozonetech.ozogram.view.adapter.PostRecycleViewAdapter;
import com.ozonetech.ozogram.view.adapter.StoryUserRecycleViewAdapter;
import com.ozonetech.ozogram.view.adapter.UnFollowUserListAdapter;
import com.ozonetech.ozogram.view.dialog.DialogAppUpdater;
import com.ozonetech.ozogram.view.dialog.EditCommentDialog;
import com.ozonetech.ozogram.view.dialog.PostMoreOptionDialog;
import com.ozonetech.ozogram.view.dialog.SendPostDialog;
import com.ozonetech.ozogram.view.listeners.CommonResponseInterface;
import com.ozonetech.ozogram.view.listeners.GetPostDataListener;
import com.ozonetech.ozogram.view.listeners.GetUserInterface;
import com.ozonetech.ozogram.viewmodel.HomeViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;


public class OzogramHomeActivity extends BaseActivity implements GetPostDataListener,
        CommonResponseInterface, PostRecycleViewAdapter.PostViewInterface, EditCommentDialog.SendCallBack, GetUserInterface {
    // Session Manager Class
    SessionManager session;
    ActivityOzogramHomeBinding activityOzogramHomeBinding;
    public HomeViewModel homeViewModel;
    //  BottomTabViewModel bottomTabViewModel;
    private List<GetPostRecordModel> post = new ArrayList<>();
    private StoryUserRecycleViewAdapter storyUserRecycleViewAdapter;
    private PostRecycleViewAdapter postRecycelAdapter;
    private String tag = "OzogramHomeActivity";
    private List<UnfollowUserRecordDataModel> unFollowUserList = new ArrayList<>();
    private UnFollowUserListAdapter unFollowUserAdapter;
    private int action_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOzogramHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_ozogram_home);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        homeViewModel = ViewModelProviders.of(OzogramHomeActivity.this).get(HomeViewModel.class);
        activityOzogramHomeBinding.setHome(homeViewModel);

        homeViewModel.postDataListener = OzogramHomeActivity.this;
        homeViewModel.commonResponseInterface = OzogramHomeActivity.this;
        homeViewModel.getUserInterface = OzogramHomeActivity.this;
        // Session class instance
        session = new SessionManager(getApplicationContext());
        activityOzogramHomeBinding.executePendingBindings();
        activityOzogramHomeBinding.setLifecycleOwner(OzogramHomeActivity.this);
        setupSwipLayout();
        init();
        checkUpdate();
//

    }

    DialogAppUpdater dialogAppUpdater;
    AppVersionModel appVersionModel;

    private void checkUpdate() {
        new AppUpdateChecker(this, new AppUpdateChecker.AppUpdateAvailableListener() {
            @Override
            public void appUpdateAvailable(LiveData<AppVersionModel> appUpdatemodal) {

                appUpdatemodal.observe(OzogramHomeActivity.this, new Observer<AppVersionModel>() {
                    @Override
                    public void onChanged(AppVersionModel appVersionModel) {
                        if (appVersionModel != null) {
                            OzogramHomeActivity.this.appVersionModel = appVersionModel;
                            if (appVersionModel.getCode() == 200) {
                                if (isFinishing()) return;
                                callupdatedialog();
                            }

                        }
                    }
                });


            }
        }).checkForUpdate();
    }

    private void callupdatedialog() {
        try {
            if (dialogAppUpdater != null && dialogAppUpdater.isShowing()) {
                dialogAppUpdater.dismiss();
            }
            dialogAppUpdater = new DialogAppUpdater(OzogramHomeActivity.this, appVersionModel);
            dialogAppUpdater.show();
        } catch (Exception e) {
            Log.d(tag, "--exception-" + e.getMessage());
        }
    }

    private void init() {

        storyUserRecycleViewAdapter = new StoryUserRecycleViewAdapter(getApplicationContext(), post);
        activityOzogramHomeBinding.recycleStoryUser.setHasFixedSize(true);
        activityOzogramHomeBinding.recycleStoryUser.setNestedScrollingEnabled(true);
        activityOzogramHomeBinding.recycleStoryUser.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        activityOzogramHomeBinding.recycleStoryUser.setAdapter(storyUserRecycleViewAdapter);


        postRecycelAdapter = new PostRecycleViewAdapter(getApplicationContext(), post, OzogramHomeActivity.this);
        activityOzogramHomeBinding.recyclePostList.setHasFixedSize(true);
        activityOzogramHomeBinding.recyclePostList.setNestedScrollingEnabled(true);
        activityOzogramHomeBinding.recyclePostList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        activityOzogramHomeBinding.recyclePostList.setAdapter(postRecycelAdapter);


    }

    private void setupSwipLayout() {

        activityOzogramHomeBinding.swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                activityOzogramHomeBinding.swipeLayout.setRefreshing(true);
                homeViewModel.getPost(getApplicationContext());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityOzogramHomeBinding.swipeLayout.setRefreshing(true);
        homeViewModel.getPost(getApplicationContext());
        if (appVersionModel != null) {
            if (appVersionModel.getCode() == 200) {
                if (isFinishing()) return;
                callupdatedialog();
            }

        }
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    @Override
    public void onStarted() {
        Log.d("Home", "On start");
    }

    @Override
    public void onUserSuccess(LiveData<GetUnfollowUserResponse> getUnfollowUserResponseLiveData) {
        getUnfollowUserResponseLiveData.observe(OzogramHomeActivity.this, new Observer<GetUnfollowUserResponse>() {
            @Override
            public void onChanged(GetUnfollowUserResponse responseModel) {
                try {
                    if (responseModel.getCode() == 200 && responseModel.getStatus().equalsIgnoreCase("OK")) {
                        Log.d("Home", "Response : Code" + responseModel.getCode());
                        setUser(responseModel.getData());
                    } else {
                        activityOzogramHomeBinding.swipeLayout.setRefreshing(false);
                        Log.d("Home", "Response fail: Code" + responseModel.getCode());
                        showSnackbar(activityOzogramHomeBinding.nestedList, responseModel.getMessage(), Snackbar.LENGTH_SHORT);
                    }
                } catch (Exception e) {
                } finally {
                    activityOzogramHomeBinding.swipeLayout.setRefreshing(false);
                    hideProgressDialog();
                }
            }
        });
    }

    private void setUser(List<UnfollowUserRecordDataModel> data) {
        unFollowUserList = data;
        unFollowUserAdapter = new UnFollowUserListAdapter(getApplicationContext(), unFollowUserList, OzogramHomeActivity.this);
        activityOzogramHomeBinding.viewPagerForUser.setNestedScrollingEnabled(true);
        activityOzogramHomeBinding.viewPagerForUser.setAdapter(unFollowUserAdapter);
    }


    @Override
    public void onSuccess(LiveData<GetPostResponseModel> postResponse) {
        postResponse.observe(OzogramHomeActivity.this, new Observer<GetPostResponseModel>() {
            @Override
            public void onChanged(GetPostResponseModel responseModel) {
                //save access token
                hideProgressDialog();
                try {
                    if (responseModel.getCode() == 200 && responseModel.getStatus().equalsIgnoreCase("OK")) {
                        Log.d("Home", "Response : Code" + responseModel.getCode());
                        setPost(responseModel.getData());
                    } else {
                        activityOzogramHomeBinding.swipeLayout.setRefreshing(false);
                        Log.d("Home", "Response fail: Code" + responseModel.getCode());
                        showSnackbar(activityOzogramHomeBinding.nestedList, responseModel.getMessage(), Snackbar.LENGTH_SHORT);
                    }
                } catch (Exception e) {
                } finally {
                    activityOzogramHomeBinding.swipeLayout.setRefreshing(false);
                    hideProgressDialog();
                }
            }
        });
    }

    @Override
    public void onFailure(String message) {
        activityOzogramHomeBinding.swipeLayout.setRefreshing(false);
    }

    public void showSnackbar(View view, String message, int duration) {
        Snackbar snackbar = Snackbar.make(view, message, duration);
        snackbar.setActionTextColor(Color.WHITE);
        snackbar.setBackgroundTint(getResources().getColor(R.color.colorPrimaryDark));
        snackbar.show();
    }

    public void setPost(List<GetPostRecordModel> post) {
        this.post = post;
        if (post.size() > 0) {
            activityOzogramHomeBinding.recyclePostList.setVisibility(View.VISIBLE);
            activityOzogramHomeBinding.llFollowLayerj.setVisibility(View.GONE);
            activityOzogramHomeBinding.swipeLayout.setRefreshing(false);
            storyUserRecycleViewAdapter.updateList(post);
            postRecycelAdapter.updateList(post);
            // postRecycelAdapter.insert(action_position, post.get(action_position));
        } else {
            activityOzogramHomeBinding.recyclePostList.setVisibility(View.GONE);
            activityOzogramHomeBinding.llFollowLayerj.setVisibility(View.VISIBLE);
            homeViewModel.getUser(getApplicationContext(), null);
        }
    }


    public List<GetPostRecordModel> getPost() {
        return post;
    }

    @Override
    public void onCommoStarted() {
        showProgressDialog("Please Wait");
    }

    @Override
    public void onCommonSuccess(LiveData<CommonResponse> commonResponseLiveData) {
        commonResponseLiveData.observe(OzogramHomeActivity.this, new Observer<CommonResponse>() {
            @Override
            public void onChanged(CommonResponse responseModel) {
                hideProgressDialog();
                try {
                    if (responseModel.getCode() == 200 && responseModel.getStatus().equalsIgnoreCase("OK")) {
                        Log.d(tag, "like Response : Code" + responseModel.getCode());
                        homeViewModel.getPost(getApplicationContext());
                    } else {
                        activityOzogramHomeBinding.swipeLayout.setRefreshing(false);
                        Log.d(tag, "like Response fail: Code" + responseModel.getCode());
                        showSnackbar(activityOzogramHomeBinding.nestedList, responseModel.getMessage(), Snackbar.LENGTH_SHORT);
                    }
                } catch (Exception e) {
                } finally {
                    activityOzogramHomeBinding.swipeLayout.setRefreshing(false);
                    hideProgressDialog();
                }
            }
        });
    }

    @Override
    public void onCommonFailure(String message) {
        Log.d(tag, "----like post fail--");
        hideProgressDialog();
    }

    @Override
    public void clickLike(int pos, String action) {

        action_position = pos;
        Log.d(tag, "----click like pos--" + pos);
        homeViewModel.postLike(getApplicationContext(), String.valueOf(post.get(pos).getId()), action);

    }

    @Override
    public void clickComment(int pos) {
        action_position = pos;
        Bundle bundle = new Bundle();
        bundle.putString("id", String.valueOf(post.get(pos).getId()));
        EditCommentDialog instance = EditCommentDialog.getInstance(bundle);
        instance.show(getSupportFragmentManager(), instance.getClass().getSimpleName());
        instance.sendCallBack = (EditCommentDialog.SendCallBack) OzogramHomeActivity.this;

    }

    @Override
    public void sendPost(int pos) {
        action_position = pos;
        Bundle bundle = new Bundle();
        bundle.putString("id", String.valueOf(post.get(pos).getId()));
        SendPostDialog instance = SendPostDialog.getInstance(bundle);
        instance.show(getSupportFragmentManager(), instance.getClass().getSimpleName());
    }

    @Override
    public void savePOST(int pos) {
        homeViewModel.savePost(getApplicationContext(), post.get(pos).getId().toString());
    }

    @Override
    public void sendMessage(String msg, String id) {
        homeViewModel.postComment(getApplicationContext(), id, msg);
    }

    @Override
    public void sendError(String err) {
        showSnackbar(activityOzogramHomeBinding.nestedList, err, Snackbar.LENGTH_SHORT);
    }


    public void moreaction(GetPostRecordModel getPostRecordModel) {
        Bundle bundle = new Bundle();
        bundle.putString("id", String.valueOf(getPostRecordModel.getId()));
        bundle.putString("u_id", getPostRecordModel.getUser().getUserId());
        PostMoreOptionDialog instance = PostMoreOptionDialog.getInstance(bundle);
        // instance.unfollowUser=(PostMoreOptionDialog.UnfollowUser)OzogramHomeActivity.this;
        instance.show(getSupportFragmentManager(), instance.getClass().getSimpleName());
    }
}

