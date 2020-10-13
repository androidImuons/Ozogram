package com.example.ozogram.view.fragment;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ozogram.R;
import com.example.ozogram.view.adapter.ProfileGalleryAdpter;

public class StoryFragment extends Fragment {

    private StoryViewModel mViewModel;
    RecyclerView rv_profile_gallery;
    ProfileGalleryAdpter profileGalleryAdpter;

    public static StoryFragment newInstance() {
        return new StoryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.story_fragment, container, false);
        rv_profile_gallery=(RecyclerView)view.findViewById(R.id.rv_profile_gallery);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rv_profile_gallery.setLayoutManager(layoutManager);
        profileGalleryAdpter=new ProfileGalleryAdpter(getActivity());
        rv_profile_gallery.setAdapter(profileGalleryAdpter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(StoryViewModel.class);
        // TODO: Use the ViewModel
    }

}