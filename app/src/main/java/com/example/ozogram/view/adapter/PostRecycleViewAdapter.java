package com.example.ozogram.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ozogram.R;
import com.example.ozogram.base.AppBaseRecycleAdapter;
import com.example.ozogram.base.BaseRecycleAdapter;
import com.example.ozogram.model.GetPostDataModel;

import java.util.List;

public class PostRecycleViewAdapter extends AppBaseRecycleAdapter {
    Context context;
    List<GetPostDataModel> postDataModelList;

    public PostRecycleViewAdapter(Context applicationContext, List<GetPostDataModel> post) {
        context = applicationContext;
        postDataModelList = post;
    }

    public int getViewHeight() {
        return 0;
    }

    @Override
    public BaseRecycleAdapter.BaseViewHolder getViewHolder() {
        return new StoryUserListView(inflateLayout(R.layout.view_post_view_list));
    }

    @Override
    public int getDataCount() {
        return postDataModelList == null ? 10 :postDataModelList.size();
    }



    public void updateList(List<GetPostDataModel> post) {
        postDataModelList=post;
        notifyDataSetChanged();
    }

    public class StoryUserListView extends BaseRecycleAdapter.BaseViewHolder {

        public StoryUserListView(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(int position) {

        }

    }
}
