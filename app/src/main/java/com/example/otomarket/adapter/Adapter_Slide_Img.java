package com.example.otomarket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.otomarket.R;
import com.example.otomarket.model.SlideImg;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter_Slide_Img extends RecyclerView.Adapter<Adapter_Slide_Img.VH_SlideImg> {
    ArrayList<SlideImg> slideImgArrayList;
    Context mContext;

    public Adapter_Slide_Img(ArrayList<SlideImg> slideImgArrayList, Context mContext) {
        this.slideImgArrayList = slideImgArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public VH_SlideImg onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slide_img, parent, false);
        return new VH_SlideImg(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH_SlideImg holder, int position) {
        SlideImg slideImg = slideImgArrayList.get(position);
        if (slideImg == null) {
            return;
        }
        Picasso.get().load(slideImg.getUrl()).into(holder.mImg);
    }

    @Override
    public int getItemCount() {
        if (slideImgArrayList != null) {
            return slideImgArrayList.size();
        }
        return 0;
    }

    public class VH_SlideImg extends RecyclerView.ViewHolder {
        ImageView mImg;

        public VH_SlideImg(@NonNull View itemView) {
            super(itemView);
            mImg = itemView.findViewById(R.id.slide_img);
        }
    }
}

