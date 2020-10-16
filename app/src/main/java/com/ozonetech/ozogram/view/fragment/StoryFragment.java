package com.ozonetech.ozogram.view.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
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
import com.ozonetech.ozogram.databinding.StoryFragmentBinding;
import com.ozonetech.ozogram.model.PostGalleryPath;
import com.ozonetech.ozogram.view.activity.AddProfileActivity;
import com.ozonetech.ozogram.view.activity.EditProfileActivity;
import com.ozonetech.ozogram.view.activity.ProfileActivity;
import com.ozonetech.ozogram.view.adapter.PostsAdapter;

import java.util.ArrayList;

public class StoryFragment extends Fragment implements PostsAdapter.PostsAdapterListener {

    public StoryFragmentBinding storyFragmentBinding;
    private StoryViewModel mViewModel;
    RecyclerView rv_profile_story_gallery;
    private MyClickHandlers handlers;
    private PostsAdapter mAdapter;

    public static StoryFragment newInstance() {
        return new StoryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        storyFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.story_fragment, container, false);
        View view = storyFragmentBinding.getRoot();
        storyFragmentBinding.setLifecycleOwner(this);
        handlers = new MyClickHandlers(getActivity());


        renderStroryFragment(view);
        return view;

    }

    private void renderStroryFragment(View view) {
        storyFragmentBinding.rvProfileStoryGallery.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mAdapter = new PostsAdapter(getPosts(), this);
        storyFragmentBinding.rvProfileStoryGallery.setAdapter(mAdapter);
        storyFragmentBinding.setHandlers(handlers);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(StoryViewModel.class);
        // TODO: Use the ViewModel
    }

    private ArrayList<PostGalleryPath> getPosts() {
        ArrayList<PostGalleryPath> postGalleryPaths = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            PostGalleryPath postGalleryPath = new PostGalleryPath();
            postGalleryPath.setImageUrl("https://api.androidhive.info/images/nature/" + i + ".jpg");
            postGalleryPaths.add(postGalleryPath);
        }

        return postGalleryPaths;
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public void onPostClicked(PostGalleryPath postGalleryPath) {
        Toast.makeText(getActivity(), "Post clicked! " + postGalleryPath.getImageUrl(), Toast.LENGTH_SHORT).show();
    }

    public class MyClickHandlers {

        Context context;

        public MyClickHandlers(Context context) {
            this.context = context;
        }

        public void onAddBioClick(View view){
            goToAddProfileActivity("addBio");
        }

        public void onEditNameClick(View view) {
            goToAddProfileActivity("editName");
        }

        public void onFindMoreClick(View view){
            Toast.makeText(getActivity(), "Find More Firends clicked! ", Toast.LENGTH_SHORT).show();

        }

    }

    private void goToAddProfileActivity(String type) {
        Intent intent = new Intent(getActivity(), AddProfileActivity.class);
        intent.putExtra("type",type);
        getActivity().startActivity(intent);
    }
}