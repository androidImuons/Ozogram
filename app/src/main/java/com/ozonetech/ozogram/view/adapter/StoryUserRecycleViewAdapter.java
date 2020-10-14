package com.ozonetech.ozogram.view.adapter;

import android.content.Context;
import android.view.View;

import com.ozonetech.ozogram.R;
import com.ozonetech.ozogram.base.AppBaseRecycleAdapter;
import com.ozonetech.ozogram.model.GetPostDataModel;

import java.util.List;

public class StoryUserRecycleViewAdapter extends AppBaseRecycleAdapter {
    Context context;
    List<GetPostDataModel> postDataModelList;

    public StoryUserRecycleViewAdapter(Context applicationContext, List<GetPostDataModel> post) {
        context = applicationContext;
        postDataModelList = post;
    }

    public int getViewHeight() {
        return 0;
    }

    @Override
    public BaseViewHolder getViewHolder() {
        return new StoryUserListView(inflateLayout(R.layout.view_home_story_list));
    }

    @Override
    public int getDataCount() {
         return postDataModelList == null ? 10 : postDataModelList.size();
    }

    public void updateList(List<GetPostDataModel> post) {
        postDataModelList=post;
        notifyDataSetChanged();
    }

    public class StoryUserListView extends BaseViewHolder {

        public StoryUserListView(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(int position) {

        }

    }
}
