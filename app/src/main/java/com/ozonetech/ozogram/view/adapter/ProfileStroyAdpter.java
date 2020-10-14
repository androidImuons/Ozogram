package com.ozonetech.ozogram.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.ozonetech.ozogram.R;
import com.ozonetech.ozogram.databinding.ProfileStoryListItemBinding;
import com.ozonetech.ozogram.model.Post;
import java.util.List;

public class ProfileStroyAdpter extends RecyclerView.Adapter<ProfileStroyAdpter.MyViewHolder> {

    private List<Post> postList;
    private LayoutInflater layoutInflater;
    private ProfileStroyAdpterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final ProfileStoryListItemBinding binding;

        public MyViewHolder(final ProfileStoryListItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }

    public ProfileStroyAdpter(List<Post> postList, ProfileStroyAdpterListener listener) {
        this.postList = postList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ProfileStoryListItemBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.profile_story_list_item, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.binding.setPost(postList.get(position));
        holder.binding.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onPostClicked(postList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public interface ProfileStroyAdpterListener {
        void onPostClicked(Post post);
    }
}
