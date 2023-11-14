package com.example.otomarket.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.otomarket.R;
import com.example.otomarket.activity.DetailActivity;
import com.example.otomarket.model.Xe;
import com.example.otomarket.server.SERVER;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Adapter_BestCar extends RecyclerView.Adapter<Adapter_BestCar.VH_BestCar> {
    Context context;
    ArrayList<Xe> xeArrayList;
//    itf_ClickItemBC itemBC;
//
//    public interface itf_ClickItemBC {
//        void clickBestCar(Xe xe);
//    }

    public Adapter_BestCar(Context context, ArrayList<Xe> xeArrayList) {
        this.context = context;
        this.xeArrayList = xeArrayList;
    }



    @NonNull
    @Override
    public VH_BestCar onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_best_car, parent, false);
        return new VH_BestCar(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH_BestCar holder, int position) {
        Xe x = xeArrayList.get(position);

        if (x == null) {
            return;
        }
        Picasso.get().load(SERVER.imgXepath + x.getAnhXe()).into(holder.mhinhXe);
        holder.mtenXe.setText(x.getTenXe());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.mGia.setText(decimalFormat.format(x.getGiaXe()) + " $");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("ct", x);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (xeArrayList != null) {
            return xeArrayList.size();
        }
        return 0;
    }

    class VH_BestCar extends RecyclerView.ViewHolder {
        ImageView mhinhXe;
        TextView mtenXe, mGia;

        public VH_BestCar(@NonNull View itemView) {
            super(itemView);
            mhinhXe = itemView.findViewById(R.id.anh_BC);
            mtenXe = itemView.findViewById(R.id.tenXe_BC);
            mGia = itemView.findViewById(R.id.giaXe_BC);
        }
    }
}