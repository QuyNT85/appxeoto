package com.example.otomarket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.otomarket.R;
import com.example.otomarket.model.HoaDon;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Adapter_HoaDon extends RecyclerView.Adapter<Adapter_HoaDon.ViewHolderDH> {
    RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    Context context;
    ArrayList<HoaDon> hoaDonList;

    public Adapter_HoaDon(Context context, ArrayList<HoaDon> hoaDonList) {
        this.context = context;
        this.hoaDonList = hoaDonList;
    }

    @NonNull
    @Override
    public Adapter_HoaDon.ViewHolderDH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donhang, parent, false);
        return new ViewHolderDH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_HoaDon.ViewHolderDH holder, int position) {
        HoaDon hd = hoaDonList.get(position);
        holder.txtID_HD.setText("Mã hóa đơn:" + hd.getId());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txt_TT.setText("Tổng tiền: " + decimalFormat.format(Integer.parseInt(hd.getTongtien())) + " $");

        holder.tv_sttHD.setText("Hóa đơn #" + (position + 1));


        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.rcCTHD.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(hd.getItem().size());

        //adpter chi tiết hóa đơn
        Adapter_ChiTietHD adapter_chiTietHD = new Adapter_ChiTietHD(hd.getItem());
        holder.rcCTHD.setLayoutManager(layoutManager);
        holder.rcCTHD.setAdapter(adapter_chiTietHD);
        holder.rcCTHD.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        if (hoaDonList != null) {
            return hoaDonList.size();
        }
        return 0;
    }

    public class ViewHolderDH extends RecyclerView.ViewHolder {
        TextView txtID_HD, txt_TT, tv_sttHD;
        RecyclerView rcCTHD;

        public ViewHolderDH(@NonNull View itemView) {
            super(itemView);
            txtID_HD = itemView.findViewById(R.id.idDonHang);
            txt_TT = itemView.findViewById(R.id.tongtienDonHang);
            tv_sttHD = itemView.findViewById(R.id.stt_HD);
            rcCTHD = itemView.findViewById(R.id.rc_CT_DonHang);
        }
    }
}
