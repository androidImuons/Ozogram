package com.example.ozogram.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.ozogram.R;
import com.example.ozogram.view.activity.GalleryActivity;


import java.util.ArrayList;

public class GirdViewAdapter extends RecyclerView.Adapter<GirdViewAdapter.GridView> {
    private boolean[] thumbnailsselection;
    private LayoutInflater inflater;
    Bitmap[] listofImage;
    Context context;
    ArrayList<String> arrayList;
    ClickEvent clickEvent;

    public GirdViewAdapter(Context applicationContext, ArrayList<String> al_images, GalleryActivity galleryActivity) {
        context = applicationContext;
        arrayList = al_images;
        clickEvent=(ClickEvent)galleryActivity;
    }

    @NonNull
    @Override
    public GridView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_list_view, parent, false);
        return new GridView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GridView binding, final int position) {
        Log.d("grid adpter", "----");
        //   binding.imageview.setImageBitmap(listofImage[position]);

        final String file = "file://"+arrayList.get(position);
        Glide.with(context).load(file)
                .into(binding.imageview);
        binding.id = position;
        if(position==0){
            clickEvent.imageClickEvent(position,file);
        }

        binding.imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickEvent.imageClickEvent(position,file);
            }
        });
        binding.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickEvent.checkBokClickEvent(position,file);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public void update(Bitmap[] thumbnails, boolean[] thumbnailsselection) {
        this.thumbnailsselection = thumbnailsselection;
        this.listofImage = thumbnails;
        notifyDataSetChanged();
    }

    protected class GridView extends RecyclerView.ViewHolder {

        public ImageView imageview;
        public CheckBox checkbox;
        public int id;

        public GridView(@NonNull View itemView) {
            super(itemView);
            imageview = itemView.findViewById(R.id.iv_show_image);
            checkbox=itemView.findViewById(R.id.ch_image_check);
        }
    }

    public interface ClickEvent{
        public void imageClickEvent(int position,String url);
        public void checkBokClickEvent(int position,String url);
    }
}
