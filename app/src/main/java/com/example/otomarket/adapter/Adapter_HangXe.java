package com.example.otomarket.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.otomarket.R;
import com.example.otomarket.activity.ListActivity;
import com.example.otomarket.model.HangXe;
import com.example.otomarket.server.SERVER;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter_HangXe extends RecyclerView.Adapter<Adapter_HangXe.VH_HangXe> {
    Context context;
    ArrayList<HangXe> hangXeArrayList;
    itf_ClickItemListener mItf_clickItemListener;


    public interface itf_ClickItemListener {
        void clickBand(HangXe hangXe);
    }

    public Adapter_HangXe(Context context, ArrayList<HangXe> hangXeArrayList, itf_ClickItemListener listener) {
        this.context = context;
        this.hangXeArrayList = hangXeArrayList;
        this.mItf_clickItemListener = listener;
    }

    @NonNull
    @Override
    public VH_HangXe onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_brand_xe, parent, false);
        return new VH_HangXe(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH_HangXe holder, int position) {
        HangXe hx = hangXeArrayList.get(position);
        if (hx == null) {
            return;
        }
        holder.ten.setText(hx.getTenHangXe());
        Picasso.get().load(SERVER.imgHangpath + hx.getHinhHangXe()).into(holder.hinh);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItf_clickItemListener.clickBand(hx);
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
        ImageView hinh;
        TextView ten;

        public VH_HangXe(@NonNull View itemView) {
            super(itemView);
            hinh = itemView.findViewById(R.id.hinhBrand);
            ten = itemView.findViewById(R.id.tenBrand);
        }
    }
}
