package com.ozonetech.ozogram.view.listeners;

import androidx.lifecycle.LiveData;

import com.ozonetech.ozogram.model.UpdateDataResponseModel;

public interface EditProfileListener {

    void onUpdateProfileDataSuccess(LiveData<UpdateDataResponseModel> updateDataResponse);

}
