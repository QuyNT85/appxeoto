package com.example.otomarket.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.otomarket.R;
import com.example.otomarket.activity.ListActivity;
import com.example.otomarket.model.HangXe;
import com.example.otomarket.server.SERVER;
import com.example.otomarket.model.HangXe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter_Brand_Search extends RecyclerView.Adapter<Adapter_Brand_Search.VH_HangXe> {
    Context context;
    ArrayList<HangXe> hangXeArrayList;


    public Adapter_Brand_Search(Context context, ArrayList<HangXe> hangXeArrayList) {
        this.context = context;
        this.hangXeArrayList = hangXeArrayList;
    }


    @NonNull
    @Override
    public VH_HangXe onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_brand_search, parent, false);
        return new VH_HangXe(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH_HangXe holder, int position) {
        HangXe hx = hangXeArrayList.get(position);
        if (hx == null) {
            return;
        }
        holder.ten.setText(hx.getTenHangXe());
        Picasso.get().load(SERVER.imgHangpath + hx.getHinhHangXe()).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ListActivity.class);
                intent.putExtra("mahang", hx.getMaHangXe());
                intent.putExtra("tenhang", hx.getTenHangXe());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        if (hangXeArrayList != null) {
            return hangXeArrayList.size();
        }
        return 0;
    }

    class VH_HangXe extends RecyclerView.ViewHolder {
        TextView ten;
        ImageView imageView;

        public VH_HangXe(@NonNull View itemView) {
            super(itemView);
            ten = itemView.findViewById(R.id.tenBrand_Search);
            imageView = itemView.findViewById(R.id.hinhBrandSearch);
        }
    }
}
