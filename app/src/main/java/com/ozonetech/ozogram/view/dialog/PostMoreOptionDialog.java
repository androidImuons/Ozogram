package com.ozonetech.ozogram.view.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;

import com.ozonetech.ozogram.R;
import com.ozonetech.ozogram.app.utils.DeviceScreenUtil;
import com.ozonetech.ozogram.base.AppBaseFragment;
import com.ozonetech.ozogram.view.activity.OzogramHomeActivity;
import com.ozonetech.ozogram.view.fragment.BaseFragment;

public class PostMoreOptionDialog extends AppBaseFragment {
    public static PostMoreOptionDialog getInstance(Bundle bundle, OzogramHomeActivity ozogramHomeActivity) {
        PostMoreOptionDialog messageDialog = new PostMoreOptionDialog();
        messageDialog.setArguments(bundle);
        return messageDialog;
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.dialog_more_option;
    }

    @Override
    public int getFragmentContainerResourceId(BaseFragment baseFragment) {
        return 0;
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        WindowManager.LayoutParams wlmp = dialog.getWindow().getAttributes();
        wlmp.gravity = Gravity.CENTER;
        wlmp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wlmp.width = DeviceScreenUtil.getInstance().getWidth(0.90f);
        wlmp.dimAmount = 0.8f;
    }


    @Override
    public void initializeComponent() {
        super.initializeComponent();
    }
}
