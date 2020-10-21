package com.ozonetech.ozogram.view.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;
import com.ozonetech.ozogram.R;
import com.ozonetech.ozogram.app.utils.SessionManager;
import com.ozonetech.ozogram.base.AppBaseDialog;
import com.ozonetech.ozogram.databinding.DialogSendPostBinding;
import com.ozonetech.ozogram.model.CommonResponse;
import com.ozonetech.ozogram.model.FolloweDataModel;
import com.ozonetech.ozogram.model.FollowingUserResponse;
import com.ozonetech.ozogram.view.activity.BaseActivity;
import com.ozonetech.ozogram.view.activity.GalleryActivity;
import com.ozonetech.ozogram.view.activity.OzogramHomeActivity;
import com.ozonetech.ozogram.view.adapter.FollowingUserList;
import com.ozonetech.ozogram.view.adapter.PostRecycleViewAdapter;
import com.ozonetech.ozogram.view.adapter.UpLoadImageVideoPagerAdapter;
import com.ozonetech.ozogram.view.fragment.BaseFragment;
import com.ozonetech.ozogram.view.listeners.CommonResponseInterface;
import com.ozonetech.ozogram.view.listeners.FollowingUserListInterface;
import com.ozonetech.ozogram.viewmodel.CommonViewModel;
import com.ozonetech.ozogram.viewmodel.ShareUserListViewModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;

public class SendPostDialog extends AppBaseDialog implements CommonResponseInterface, FollowingUserListInterface {

    private String tag = "SendPostDialog";
    private ShareUserListViewModel shareUserListViewMode;
    private List<FolloweDataModel> followingList=new ArrayList<>();
    private FollowingUserList followingListAdapter;


    @Override
    public int getLayoutResourceId() {
        return R.layout.dialog_send_post;
    }


    public static SendPostDialog getInstance(Bundle bundle, OzogramHomeActivity galleryActivity) {
        SendPostDialog dialog = new SendPostDialog();
        dialog.setArguments(bundle);
        return dialog;
    }

    private String getPostId() {
        Bundle extras = getArguments();
        return (extras == null ? "0" : extras.getString("id", "0"));
    }
    @Override
    public int getFragmentContainerResourceId(BaseFragment baseFragment) {
        return 0;
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        WindowManager.LayoutParams wlmp = dialog.getWindow().getAttributes();
        wlmp.gravity = Gravity.BOTTOM;
        wlmp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wlmp.width = WindowManager.LayoutParams.MATCH_PARENT;
        wlmp.dimAmount = 0.8f;

        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
    }

    private BottomSheetBehavior bottomSheetBehavior;
    private RelativeLayout bottom_sheet;
    CardView cv_data;
    CircleImageView iv_profile_image;
    EditText edt_message;
    EditText edt_search;
    RecyclerView recycle_follower_list;
    private SessionManager sessionManager;

    DialogSendPostBinding sendPostBinding;

    @Override
    public void initializeComponent() {
        super.initializeComponent();
        sendPostBinding = DataBindingUtil.bind(getView());
         shareUserListViewMode = ViewModelProviders.of(this).get(ShareUserListViewModel.class);
        shareUserListViewMode.followingUserListInterface=this;
        shareUserListViewMode.commonResponseInterface=this;
        sendPostBinding.setSendpost(shareUserListViewMode);
        bottom_sheet = getView().findViewById(R.id.bottom_sheet);
        initializeBottomSheet();
        cv_data = getView().findViewById(R.id.cv_data);
        iv_profile_image = getView().findViewById(R.id.iv_profile_image);
        sessionManager = new SessionManager(getContext());
        edt_message = getView().findViewById(R.id.edt_message);
        edt_search = getView().findViewById(R.id.edt_search);
        recycle_follower_list = getView().findViewById(R.id.recycle_follower_list);

        Glide.with(getContext())
                .load(sessionManager.getUserDetails().get(SessionManager.KEY_PROFILE_PICTURE))
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.profile_icon)
                .into(iv_profile_image);
        shareUserListViewMode.getFollowing(getContext(),null);


        edt_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    shareUserListViewMode.getFollowing(getContext(),edt_search.getText().toString());

                    return true;
                }
                return false;
            }
        });

        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(edt_search.getText().length()==0){
                    shareUserListViewMode.getFollowing(getContext(),null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        followingListAdapter = new FollowingUserList(getContext(), followingList,this);
        sendPostBinding.recycleFollowerList.setHasFixedSize(true);
        sendPostBinding.recycleFollowerList.setNestedScrollingEnabled(true);
        sendPostBinding.recycleFollowerList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        sendPostBinding.recycleFollowerList.setAdapter(followingListAdapter);

    }


    private void initializeBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    dismiss();
                }

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

    @Override
    public void onCommoStarted() {

    }

    @Override
    public void onCommonSuccess(LiveData<CommonResponse> commonResponseLiveData) {
        commonResponseLiveData.observe(SendPostDialog.this, new Observer<CommonResponse>() {
            @Override
            public void onChanged(CommonResponse responseModel) {
                try {
                    showSnackbar(sendPostBinding.recycleFollowerList, responseModel.getMessage(), Snackbar.LENGTH_SHORT);

                } catch (Exception e) {
                }
            }
        });
    }

    @Override
    public void onCommonFailure(String message) {
        showSnackbar(sendPostBinding.recycleFollowerList, message, Snackbar.LENGTH_SHORT);

    }

    public void showSnackbar(View view, String message, int duration) {
        Snackbar snackbar = Snackbar.make(view, message, duration);
        snackbar.setActionTextColor(Color.WHITE);
        snackbar.setBackgroundTint(getResources().getColor(R.color.colorPrimaryDark));
        snackbar.show();
    }

    @Override
    public void onSuccessFollowingUser(LiveData<FollowingUserResponse> postResponse) {
        postResponse.observe(this, new Observer<FollowingUserResponse>() {
            @Override
            public void onChanged(FollowingUserResponse responseModel) {
                try {
                    if (responseModel.getCode() == 200 && responseModel.getStatus().equalsIgnoreCase("OK")) {
                        Log.d("Home", "Response : Code" + responseModel.getCode());
                        setPost(responseModel.getData());
                    } else {
                        Log.d("Home", "Response fail: Code" + responseModel.getCode());
                        showSnackbar(sendPostBinding.recycleFollowerList, responseModel.getMessage(), Snackbar.LENGTH_SHORT);
                    }
                    if(responseModel.getCode()==404){
                        onCommonFailure("No Following Available...");
                    }

                } catch (Exception e) {
                } finally {

                }
            }
        });
    }

    private void setPost(List<FolloweDataModel> data) {
        followingList=data;
        followingListAdapter.updateList(data);
    }

    public void sendPost(int position) {
        FolloweDataModel dataModel=followingList.get(position);
        shareUserListViewMode.sharePost(getContext(),dataModel.getUser_id(),getPostId(),edt_message.getText().toString());
    }
}
