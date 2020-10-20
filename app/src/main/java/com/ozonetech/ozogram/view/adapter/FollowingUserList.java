package com.ozonetech.ozogram.view.adapter;

import android.view.View;
import android.widget.TextView;

import com.ozonetech.ozogram.R;
import com.ozonetech.ozogram.base.AppBaseRecycleAdapter;

import de.hdodenhof.circleimageview.CircleImageView;

public class FollowingUserList extends AppBaseRecycleAdapter {
    @Override
    public BaseViewHolder getViewHolder() {
        return new ViewHolder(inflateLayout(R.layout.view_follower_list));
    }

    @Override
    public int getDataCount() {
        return 10;
    }

    public class ViewHolder extends AppBaseRecycleAdapter.BaseViewHolder {
        CircleImageView iv_profile;
        TextView txt_name;
        TextView txt_send;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_profile = itemView.findViewById(R.id.iv_profile);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_send = itemView.findViewById(R.id.txt_send);

        }

        @Override
        public void setData(int position) {
            sendPost(this,position);
        }
    }

    private void sendPost(ViewHolder viewHolder, int position) {

    }
}
