package com.ozonetech.ozogram.view.fragment;

import android.net.Uri;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.bumptech.glide.Glide;
import com.ozonetech.ozogram.R;
import com.ozonetech.ozogram.databinding.FragmentPostGalleryBinding;
import com.ozonetech.ozogram.view.activity.GalleryActivity;
import com.ozonetech.ozogram.view.adapter.GirdViewAdapter;
import com.ozonetech.ozogram.viewmodel.PostGalleryViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostGalleryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostGalleryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private boolean is_crop;
    private boolean is_check_open;
    ArrayList<String> selected_image = new ArrayList<>();
    int selected_position;
    String selected_file_url;
    private GirdViewAdapter obj_adapter;
    private String tag = "PostGalleryFragment";

    public PostGalleryFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostGalleryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PostGalleryFragment newInstance(String param1, String param2) {
        PostGalleryFragment fragment = new PostGalleryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    FragmentPostGalleryBinding fragmentPostGalleryBinding;
    PostGalleryViewModel postGalleryViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentPostGalleryBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_post_gallery, container, false);
        postGalleryViewModel = ViewModelProviders.of(getActivity()).get(PostGalleryViewModel.class);
        fragmentPostGalleryBinding.setLifecycleOwner(getActivity());
        fragmentPostGalleryBinding.setPostone(postGalleryViewModel);
        view = fragmentPostGalleryBinding.getRoot();
        initView();
        return view;
    }

    private void initView() {
        setListner();
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        fragmentPostGalleryBinding.recycleGallery.setLayoutManager(sglm);
        obj_adapter = new GirdViewAdapter(getContext(), arrayList, PostGalleryFragment.this);
        fragmentPostGalleryBinding.recycleGallery.setAdapter(obj_adapter);
        fragmentPostGalleryBinding.nestedListGallery.scrollTo(0, 0);
    }

    private void setListner() {
        fragmentPostGalleryBinding.ivCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!is_crop) {
                    is_crop = true;
                    fragmentPostGalleryBinding.ivImage.setVisibility(View.GONE);
                    fragmentPostGalleryBinding.cropImageView.setVisibility(View.VISIBLE);
                    fragmentPostGalleryBinding.cropImageView.setImageUriAsync(Uri.parse(selected_file_url));
                } else {
                    is_crop = false;
                    fragmentPostGalleryBinding.ivImage.setVisibility(View.VISIBLE);
                    fragmentPostGalleryBinding.cropImageView.setVisibility(View.GONE);
                }

            }
        });

        fragmentPostGalleryBinding.ivSelectMultipleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!is_check_open) {
                    is_check_open = true;
                    obj_adapter.update(is_check_open);
                } else {
                    is_check_open = false;
                    obj_adapter.update(is_check_open);
                }
            }
        });
    }

    ArrayList<String> arrayList = new ArrayList<>();

    public void setList(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
        if (obj_adapter != null) {
            obj_adapter.update(arrayList);
        } else {
            Log.d(tag, "--post obj null-");
        }

    }

    public void imageClick(int position, String url) {
        Glide.with(getActivity()).load(url)
                .into(fragmentPostGalleryBinding.ivImage);
        selected_file_url = url;
        if (selected_position != position) {
            selected_position = position;
            selected_image.add(url);
        }
        fragmentPostGalleryBinding.nestedListGallery.scrollTo(0, 0);
    }

    public void checkClick(int position, String url) {
        Glide.with(getActivity()).load(url)
                .into(fragmentPostGalleryBinding.ivImage);
    }




}