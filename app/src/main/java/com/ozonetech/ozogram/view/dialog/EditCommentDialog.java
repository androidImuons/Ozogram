package com.ozonetech.ozogram.view.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.snackbar.Snackbar;
import com.ozonetech.ozogram.R;
import com.ozonetech.ozogram.base.AppBaseDialog;
import com.ozonetech.ozogram.model.UserModel;
import com.ozonetech.ozogram.view.fragment.BaseFragment;

public class EditCommentDialog extends AppBaseDialog {
    public ImageView iv_send;
    public SendCallBack sendCallBack;

    public interface SendCallBack {
        public void sendMessage(String msg,String pos);
        public void sendError(String err);
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.dialog_edit_comment;
    }

    public static EditCommentDialog getInstance(Bundle bundle) {
        EditCommentDialog dialog = new EditCommentDialog();
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
    public ImageView iv_profile;
    public EditText et_comment;

    @Override
    public void initializeComponent() {
        super.initializeComponent();
        bottom_sheet = getView().findViewById(R.id.bottom_sheet);
        initializeBottomSheet();
        cv_data = getView().findViewById(R.id.cv_data);
        iv_profile = getView().findViewById(R.id.iv_profile_image);
        et_comment = getView().findViewById(R.id.input_name);
        iv_send = getView().findViewById(R.id.iv_send);
        updateBottomMessage();

        iv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!et_comment.getText().toString().isEmpty()) {

                    sendCallBack.sendMessage(et_comment.getText().toString(),getPostId());
                    dismiss();
                } else {
                    sendCallBack.sendError("Enter Comment...!");
                }
            }
        });


    }
    private String getPostId() {
        Bundle extras = getArguments();
        return (extras == null ? "0" : extras.getString("id", "0"));
    }

    private void updateBottomMessage() {

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
}
