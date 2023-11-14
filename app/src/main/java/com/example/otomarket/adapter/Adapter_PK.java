package com.example.otomarket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.otomarket.R;
import com.example.otomarket.model.HangXe;
import com.example.otomarket.model.PhanKhuc;
import com.example.otomarket.server.SERVER;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter_PK extends RecyclerView.Adapter<Adapter_PK.VH_PK> {
    Context context;
    ArrayList<PhanKhuc> dataPK;
    itf_ClickItemListenerPK mitf_clickItemListenerPK;

    public interface itf_ClickItemListenerPK {
        void clickPK(PhanKhuc phanKhuc);
    }

    public Adapter_PK(Context context, ArrayList<PhanKhuc> dataPK, itf_ClickItemListenerPK listenerPK) {
        this.context = context;
        this.dataPK = dataPK;
        this.mitf_clickItemListenerPK = listenerPK;
    }

    @NonNull
    @Override
    public VH_PK onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pk, parent, false);
        return new VH_PK(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH_PK holder, int position) {
        PhanKhuc pk = dataPK.get(position);
        if (pk == null) {
            return;
        }
        holder.tenPK.setText(pk.TenPK);
        Picasso.get().load(SERVER.imgPKpath + pk.HinhPK).into(holder.hinhPK);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mitf_clickItemListenerPK.clickPK(pk);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (dataPK != null) {
            return dataPK.size();
        }
        return 0;
    }

    class VH_PK extends RecyclerView.ViewHolder {
        ImageView hinhPK;
        TextView tenPK;

        public VH_PK(@NonNull View itemView) {
            super(itemView);
            hinhPK = itemView.findViewById(R.id.hinh_pk);
            tenPK = itemView.findViewById(R.id.tenpk);
        }
    }
}
