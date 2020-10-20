package com.ozonetech.ozogram.view.adapter;

import android.view.View;

import com.ozonetech.ozogram.R;
import com.ozonetech.ozogram.base.AppBaseRecycleAdapter;
import com.ozonetech.ozogram.base.BaseRecycleAdapter;

public class AllFollowUserList extends AppBaseRecycleAdapter {
    @Override
    public BaseViewHolder getViewHolder() {
        return new ViewHolder(inflateLayout(R.layout.view_follow_list));
    }

    @Override
    public int getDataCount() {
        return 10;
    }
    public class ViewHolder extends BaseRecycleAdapter.BaseViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(int position) {

        }
    }
}
