package com.example.ozogram.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ozogram.R;

public class PostRecycleViewAdapter extends RecyclerView.Adapter<PostRecycleViewAdapter.UserPostListView>  {
    public PostRecycleViewAdapter(Context applicationContext) {

    }

    @NonNull
    @Override
    public UserPostListView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_post_view_list, parent, false);
        return new UserPostListView(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserPostListView holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class UserPostListView extends RecyclerView.ViewHolder{

        public UserPostListView(View itemView) {
            super(itemView);
        }

    }
}
