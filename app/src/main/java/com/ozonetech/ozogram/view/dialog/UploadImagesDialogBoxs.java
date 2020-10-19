package com.ozonetech.ozogram.view.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;
import com.ozonetech.ozogram.R;
import com.ozonetech.ozogram.base.AppBaseDialog;
import com.ozonetech.ozogram.model.CommonResponse;
import com.ozonetech.ozogram.repository.PostUpload;
import com.ozonetech.ozogram.view.activity.BaseActivity;
import com.ozonetech.ozogram.view.activity.GalleryActivity;
import com.ozonetech.ozogram.view.adapter.PostPagerAdapter;
import com.ozonetech.ozogram.view.adapter.PostRecycleViewAdapter;
import com.ozonetech.ozogram.view.adapter.UpLoadImageVideoPagerAdapter;
import com.ozonetech.ozogram.view.fragment.BaseFragment;
import com.ozonetech.ozogram.view.listeners.CommonResponseInterface;
import com.ozonetech.ozogram.viewmodel.CommonViewModel;

import okhttp3.MediaType;
import okhttp3.RequestBody;

import java.util.ArrayList;
import java.util.HashMap;

import me.relex.circleindicator.CircleIndicator;

public class UploadImagesDialogBoxs extends AppBaseDialog implements CommonResponseInterface {
    private ViewPager view_pager;
    private CircleIndicator circle;
    private String tag="UploadImagesDialogBoxs";


    @Override
    public int getLayoutResourceId() {
        return R.layout.dialog_upload_image_video;
    }

    static GalleryActivity activity;

    public static UploadImagesDialogBoxs getInstance(Bundle bundle, GalleryActivity galleryActivity) {
        activity = galleryActivity;
        UploadImagesDialogBoxs dialog = new UploadImagesDialogBoxs();
        dialog.setArguments(bundle);
        return dialog;
    }

    private ArrayList<String> getArraList() {
        Bundle extras = getArguments();
        return (extras == null ? null : extras.getStringArrayList("list"));
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
    public ImageView iv_profile;
    public EditText et_comment;
    public ImageView iv_send;

    @Override
    public void initializeComponent() {
        super.initializeComponent();
        bottom_sheet = getView().findViewById(R.id.bottom_sheet);
        initializeBottomSheet();
        cv_data = getView().findViewById(R.id.cv_data);
        iv_profile = getView().findViewById(R.id.iv_profile_image);
        et_comment = getView().findViewById(R.id.input_name);
        iv_send = getView().findViewById(R.id.iv_send);
        view_pager = getView().findViewById(R.id.view_pager_upload_images);
        circle = getView().findViewById(R.id.circle);
        iv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String caption = "";
                if (!et_comment.getText().toString().isEmpty()) {
                    caption = et_comment.getText().toString();
                }
                CommonViewModel commonViewModel = new CommonViewModel();
                commonViewModel.commonResponseInterface = (CommonResponseInterface) UploadImagesDialogBoxs.this;
                commonViewModel.uploadPost(getContext(), caption, getArraList());
            }
        });

        setData();
    }

    private void setData() {

        UpLoadImageVideoPagerAdapter postPagerAdapter = new UpLoadImageVideoPagerAdapter((BaseActivity) getActivity(), getArraList(),
                activity, getActivity());
        view_pager.setAdapter(postPagerAdapter);
        circle.setViewPager(view_pager);
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
        commonResponseLiveData.observe(UploadImagesDialogBoxs.this, new Observer<CommonResponse>() {
            @Override
            public void onChanged(CommonResponse responseModel) {
                try {
                    if (responseModel.getCode() == 200 && responseModel.getStatus().equalsIgnoreCase("OK")) {
                        Log.d(tag, "like Response : Code" + responseModel.getCode());
                        showSnackbar(view_pager, responseModel.getMessage(), Snackbar.LENGTH_SHORT);
                        dismiss();
                        activity.finish();
                    } else {

                        Log.d(tag, "like Response fail: Code" + responseModel.getCode());
                        showSnackbar(view_pager, responseModel.getMessage(), Snackbar.LENGTH_SHORT);
                        dismiss();
                        activity.finish();
                    }
                } catch (Exception e) {
                } finally {
                   dismiss();
                }
            }
        });
    }

    @Override
    public void onCommonFailure(String message) {
        showSnackbar(view_pager, message, Snackbar.LENGTH_SHORT);
    }

    public void showSnackbar(View view, String message, int duration) {
        Snackbar snackbar = Snackbar.make(view, message, duration);
        snackbar.setActionTextColor(Color.WHITE);
        snackbar.setBackgroundTint(getResources().getColor(R.color.colorPrimaryDark));
        snackbar.show();
    }
}
