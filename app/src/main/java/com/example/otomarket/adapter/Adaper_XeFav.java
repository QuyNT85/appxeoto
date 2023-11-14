package com.example.otomarket.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.otomarket.R;
import com.example.otomarket.activity.DetailActivity;
import com.example.otomarket.activity.MainActivity;

import com.example.otomarket.model.Xe_Fav;
import com.example.otomarket.server.SERVER;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Adaper_XeFav extends RecyclerView.Adapter<Adaper_XeFav.VH_XeFav> {
    Context context;
    ArrayList<Xe_Fav> xe_favArrayList;

    public Adaper_XeFav(Context context, ArrayList<Xe_Fav> xe_favArrayList) {
        this.context = context;
        this.xe_favArrayList = xe_favArrayList;
    }

    @NonNull
    @Override
    public VH_XeFav onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_xe_fav, parent, false);
        return new Adaper_XeFav.VH_XeFav(view);
    }


    @Override
    public void onBindViewHolder(@NonNull VH_XeFav holder, int position) {
        Xe_Fav x = xe_favArrayList.get(position);

        if (x == null) {
            return;
        }
        Picasso.get().load(SERVER.imgXepath + x.getAnhXeFav()).into(holder.mhinhXe);
        holder.mtenXe.setText(x.getTenXeFav());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.mGia.setText(decimalFormat.format(x.getGiaXeFav()) + " $");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context.getApplicationContext(), "Go to Detail", Toast.LENGTH_SHORT).show();
            }
        });

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < MainActivity.xeFavArrayList.size(); i++) {
                    if (MainActivity.xeFavArrayList.get(i).getIdXeFav() == x.getIdXeFav()) {
                        MainActivity.xeFavArrayList.remove(i);
                        notifyDataSetChanged();
                        Snackbar snackbar = Snackbar.make(holder.button, x.getTenXeFav() + " đã xóa khỏi danh sách yêu thích", Toast.LENGTH_SHORT);
                        snackbar.setBackgroundTint(Color.parseColor("#8B0000"));
                        snackbar.setTextColor(Color.parseColor("#ffffff"))
                                .show();
                        break;
                    }

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (xe_favArrayList != null) {
            return xe_favArrayList.size();
        }
        return 0;
    }

    public class VH_XeFav extends RecyclerView.ViewHolder {
        ImageView mhinhXe;
        TextView mtenXe, mGia;
        ImageButton button;

        public VH_XeFav(@NonNull View itemView) {
            super(itemView);
            mhinhXe = itemView.findViewById(R.id.anhXe_cus);
            mtenXe = itemView.findViewById(R.id.tenXe_cus);
            mGia = itemView.findViewById(R.id.giaXe_cus);
            button = itemView.findViewById(R.id.btnXoaFAV);
        }
    }
}
