package com.ozonetech.ozogram.view.listeners;

import androidx.lifecycle.LiveData;

import com.ozonetech.ozogram.model.FollowingUserResponse;
import com.ozonetech.ozogram.model.GetMessageResponse;

public interface OnChatGetMessage {
    void onSuccessGetMessage(LiveData<GetMessageResponse> postResponse);
}
