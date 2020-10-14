package com.ozonetech.ozogram.view.fragment;

import androidx.lifecycle.ViewModelProviders;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ozonetech.ozogram.R;
import com.ozonetech.ozogram.model.Post;
import com.ozonetech.ozogram.view.adapter.PostsAdapter;

import java.util.ArrayList;

public class StoryFragment extends Fragment implements PostsAdapter.PostsAdapterListener {

    private StoryViewModel mViewModel;
    RecyclerView rv_profile_story_gallery;
    private PostsAdapter mAdapter;

    public static StoryFragment newInstance() {
        return new StoryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.story_fragment, container, false);
        rv_profile_story_gallery=(RecyclerView)view.findViewById(R.id.rv_profile_story_gallery);
        rv_profile_story_gallery.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mAdapter = new PostsAdapter(getPosts(), this);
        rv_profile_story_gallery.setAdapter(mAdapter);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(StoryViewModel.class);
        // TODO: Use the ViewModel
    }

    private ArrayList<Post> getPosts() {
        ArrayList<Post> posts = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            Post post = new Post();
            post.setImageUrl("https://api.androidhive.info/images/nature/" + i + ".jpg");
            posts.add(post);
        }

        return posts;
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public void onPostClicked(Post post) {
        Toast.makeText(getActivity(), "Post clicked! " + post.getImageUrl(), Toast.LENGTH_SHORT).show();
    }
}