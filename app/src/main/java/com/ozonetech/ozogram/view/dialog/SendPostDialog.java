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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;
import com.ozonetech.ozogram.R;
import com.ozonetech.ozogram.app.utils.SessionManager;
import com.ozonetech.ozogram.base.AppBaseDialog;
import com.ozonetech.ozogram.model.CommonResponse;
import com.ozonetech.ozogram.view.activity.BaseActivity;
import com.ozonetech.ozogram.view.activity.GalleryActivity;
import com.ozonetech.ozogram.view.activity.OzogramHomeActivity;
import com.ozonetech.ozogram.view.adapter.UpLoadImageVideoPagerAdapter;
import com.ozonetech.ozogram.view.fragment.BaseFragment;
import com.ozonetech.ozogram.view.listeners.CommonResponseInterface;
import com.ozonetech.ozogram.viewmodel.CommonViewModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;

public class SendPostDialog extends AppBaseDialog implements CommonResponseInterface {

    private String tag = "SendPostDialog";


    @Override
    public int getLayoutResourceId() {
        return R.layout.dialog_send_post;
    }


    public static SendPostDialog getInstance(Bundle bundle, OzogramHomeActivity galleryActivity) {
        SendPostDialog dialog = new SendPostDialog();
        dialog.setArguments(bundle);
        return dialog;
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

    @Override
    public void initializeComponent() {
        super.initializeComponent();
        bottom_sheet = getView().findViewById(R.id.bottom_sheet);
        initializeBottomSheet();
        cv_data = getView().findViewById(R.id.cv_data);
        iv_profile_image = getView().findViewById(R.id.iv_profile_image);
        sessionManager = new SessionManager(getContext());
        edt_message = getView().findViewById(R.id.edt_message);
        edt_search=getView().findViewById(R.id.edt_search);
        recycle_follower_list=getView().findViewById(R.id.recycle_follower_list);

        Glide.with(getContext())
                .load(sessionManager.getUserDetails().get(SessionManager.KEY_PROFILE_PICTURE))
                .apply(RequestOptions.circleCropTransform())
                .placeholder(R.drawable.profile_icon)
                .into(iv_profile_image);


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

                } catch (Exception e) {
                } finally {
                    dismiss();
                }
            }
        });
    }

    @Override
    public void onCommonFailure(String message) {

    }

    public void showSnackbar(View view, String message, int duration) {
        Snackbar snackbar = Snackbar.make(view, message, duration);
        snackbar.setActionTextColor(Color.WHITE);
        snackbar.setBackgroundTint(getResources().getColor(R.color.colorPrimaryDark));
        snackbar.show();
    }
}
