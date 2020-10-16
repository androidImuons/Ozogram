package com.ozonetech.ozogram.view.adapter;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ozonetech.ozogram.R;
import com.ozonetech.ozogram.model.PostGalleryPathModel;
import com.ozonetech.ozogram.view.activity.BaseActivity;
import com.ozonetech.ozogram.view.activity.OzogramHomeActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;

public class PostPagerAdapter extends PagerAdapter {


    private Context context;
    List<PostGalleryPathModel> postGalleryPathModelList;
    PostRecycleViewAdapter postRecycleViewAdapter;
OzogramHomeActivity activity;
    public PostPagerAdapter(Context context, List<PostGalleryPathModel> postGalleryPath, PostRecycleViewAdapter postRecycleViewAdapter, OzogramHomeActivity activity) {
        this.context = context;
        this.postGalleryPathModelList = postGalleryPath;
        this.postRecycleViewAdapter = postRecycleViewAdapter;
        this.activity=activity;
    }

    @Override
    public int getCount() {
        return postGalleryPathModelList.size();
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        View view = null;
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.fragment_post_view, null);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
            container.addView(view);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        setData(viewHolder, postGalleryPathModelList.get(position), position);

        return view;
    }

    private void setData(ViewHolder viewHolder, PostGalleryPathModel postGalleryPathModel, int position) {
        if (postGalleryPathModel.getType().equals("image")) {
            viewHolder.iv_post_image.setVisibility(View.VISIBLE);
            viewHolder.vv_video.setVisibility(View.GONE);
           activity.loadImage(context, viewHolder.iv_post_image, viewHolder.pb_bar, postGalleryPathModel.getPath(), R.drawable.ic_profile);
        }else{
            viewHolder.iv_post_image.setVisibility(View.GONE);
            viewHolder.vv_video.setVisibility(View.VISIBLE);
            MediaController mediaController= new MediaController(context);
            mediaController.setAnchorView(viewHolder.vv_video);

            //specify the location of media file
            Uri uri=Uri.parse(postGalleryPathModel.getPath());

            //Setting MediaController and URI, then starting the videoView
            viewHolder.vv_video.setMediaController(mediaController);
            viewHolder.vv_video.setVideoURI(uri);
            viewHolder.vv_video.requestFocus();
            viewHolder.vv_video.start();
        }
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        container.removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return object == view;
    }


    public class ViewHolder {
        ImageView iv_post_image;
        VideoView vv_video;
        ProgressBar pb_bar;

        public ViewHolder(View itemView) {
            vv_video = itemView.findViewById(R.id.vv_video);
            iv_post_image = itemView.findViewById(R.id.iv_post_image);
            pb_bar = itemView.findViewById(R.id.pb_bar);
        }
    }
}
