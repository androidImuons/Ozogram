package com.example.ozogram.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ozogram.R;

public class StoryUserRecycleViewAdapter extends RecyclerView.Adapter<StoryUserRecycleViewAdapter.StoryUserListView>  {
    public StoryUserRecycleViewAdapter(Context applicationContext) {

    }

    @NonNull
    @Override
    public StoryUserListView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_home_story_list, parent, false);
        return new StoryUserListView(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryUserListView holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class StoryUserListView extends RecyclerView.ViewHolder{

        public StoryUserListView(View itemView) {
            super(itemView);
        }

    }
}
