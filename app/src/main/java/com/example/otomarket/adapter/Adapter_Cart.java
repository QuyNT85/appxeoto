package com.example.otomarket.adapter;


import static com.example.otomarket.activity.CartActivity.tvtongtien;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.otomarket.R;
import com.example.otomarket.activity.MainActivity;
import com.example.otomarket.model.GioHang;
import com.example.otomarket.model.Xe;
import com.example.otomarket.server.SERVER;
import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Adapter_Cart extends RecyclerView.Adapter<Adapter_Cart.ViewHolder> {
    Context context;
    public static ArrayList<GioHang> arraygiohang;

    public Adapter_Cart(Context context, ArrayList<GioHang> arraygiohang) {
        this.context = context;
        this.arraygiohang = arraygiohang;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sp_trong_giohang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        GioHang sp = arraygiohang.get(position);
        holder.tengiohang.setText(sp.getTenxe());
        holder.giagiohang.setText(String.valueOf(sp.getGiaxe() * sp.getSoluongxe()));
        Glide.with(context).load(SERVER.imgXepath + sp.getHinhxe()).into(holder.imagegiohang);
        holder.tvSoLuong.setText(String.valueOf(sp.getSoluongxe()));
        holder.btndelete.setVisibility(View.VISIBLE);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvtongtien.setText(decimalFormat.format(TongTien()) + " $");


        int sl = Integer.parseInt(holder.tvSoLuong.getText().toString());
        if (sl >= 20) {
            holder.btnremove.setVisibility(View.VISIBLE);
            holder.btnadd.setVisibility(View.INVISIBLE);
        } else if (sl <= 1) {
            holder.btnremove.setVisibility(View.INVISIBLE);
        } else if (sl >= 2) {
            holder.btnremove.setVisibility(View.VISIBLE);
            holder.btnadd.setVisibility(View.VISIBLE);

        }

        holder.btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slcu = Integer.parseInt(holder.tvSoLuong.getText().toString());
                int slmoinhat = Integer.parseInt(holder.tvSoLuong.getText().toString()) + 1;
                if (slmoinhat >= 1 && slmoinhat <= 20) {
                    holder.tvSoLuong.setText(String.valueOf(slmoinhat));
                    int giacu = Integer.parseInt(holder.giagiohang.getText().toString()) * slmoinhat;
                    long giamoinhat = (giacu) / slcu;
                    holder.giagiohang.setText(String.valueOf(giamoinhat));
                    sp.setSoluongxe(slmoinhat);
                    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                    tvtongtien.setText(decimalFormat.format(TongTien()) + " $");

                }


                if (slmoinhat > 19) {
                    holder.btnadd.setVisibility(View.INVISIBLE);
                    holder.btnremove.setVisibility(View.VISIBLE);
                } else {
                    holder.btnadd.setVisibility(View.VISIBLE);
                    holder.btnremove.setVisibility(View.VISIBLE);
                }

            }
        });

        holder.btnremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slht = Integer.parseInt(holder.tvSoLuong.getText().toString());
                int slmoinhat = Integer.parseInt(holder.tvSoLuong.getText().toString()) - 1;
                if (slmoinhat >= 1 && slmoinhat <= 20) {
                    holder.tvSoLuong.setText(String.valueOf(slmoinhat));
                    int giaht = Integer.parseInt(holder.giagiohang.getText().toString()) * slmoinhat;
                    long giamoinhat = (giaht) / slht;
                    holder.giagiohang.setText(String.valueOf(giamoinhat));
                    sp.setSoluongxe(slmoinhat);
                    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                    tvtongtien.setText(decimalFormat.format(TongTien()) + " $");
                }
                if (slmoinhat > 1) {
                    holder.btnadd.setVisibility(View.VISIBLE);
                    holder.btnremove.setVisibility(View.VISIBLE);
                } else if (slmoinhat == 1) {
                    holder.btnremove.setVisibility(View.INVISIBLE);
                }
            }
        });

        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arraygiohang.size() > 0) {
                    arraygiohang.remove(position);
                    sp.setSoluongxe(position);
                    tvtongtien.setText(String.valueOf(TongTien()));
                    notifyDataSetChanged();
                    Snackbar snackbar = Snackbar.make(holder.btndelete, "Đã xóa " + sp.getTenxe(), Toast.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.parseColor("#8B0000"));
                    snackbar.setTextColor(Color.parseColor("#ffffff"))
                            .show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if (arraygiohang != null) return arraygiohang.size();
        return 0;
    }

    public static double TongTien() {
        double tt = 0;
        if (arraygiohang != null) {
            for (GioHang i : arraygiohang) {
                tt += i.tinhTongTien();
            }
        }
        return tt;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagegiohang;
        TextView tengiohang, giagiohang, tvSoLuong;
        ImageButton btnadd, btnremove, btndelete;


        public ViewHolder(@NonNull View view) {
            super(view);
            imagegiohang = view.findViewById(R.id.imageview_dong_giohang);
            tengiohang = view.findViewById(R.id.tensanpham_dong_giohang);
            giagiohang = view.findViewById(R.id.giasanpham_dong_giohang);
            tvSoLuong = view.findViewById(R.id.tv_soluong_dong_giohang);
            btnremove = view.findViewById(R.id.button_remove_dong_giohang);
            btnadd = view.findViewById(R.id.button_add_dong_giohang);
            btndelete = view.findViewById(R.id.button_xoa_dong_giohang);

        }
    }
    public static int TongSoluongXe()
    {
        int sum=0;
        if (arraygiohang != null)
        {
            for (GioHang i: arraygiohang
            ) {
                sum +=i.getSoluongxe();
            }
        }
        return sum;
    }

}
