package com.example.otomarket.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.otomarket.R;
import com.example.otomarket.model.Item;
import com.example.otomarket.server.SERVER;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter_ChiTietHD extends RecyclerView.Adapter<Adapter_ChiTietHD.VHchiTietHD> {
    List<Item> itemList;


    public Adapter_ChiTietHD(List<Item> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public Adapter_ChiTietHD.VHchiTietHD onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donhang_chitiet, parent, false);
        return new VHchiTietHD(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_ChiTietHD.VHchiTietHD holder, int position) {
        Item itemm = itemList.get(position);
        holder.tvTen.setText(itemm.getTenXe());
        Picasso.get().load(SERVER.imgXepath + itemm.getAnhXe()).into(holder.img);
        holder.tvSoLuong.setText("Số lượng: " + itemm.getSoLuong());

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class VHchiTietHD extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tvTen, tvSoLuong;

        public VHchiTietHD(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.anhXe_donhangCT);
            tvTen = itemView.findViewById(R.id.tenXe_donhangCT);
            tvSoLuong = itemView.findViewById(R.id.soluong_donhang);
        }
    }
}
