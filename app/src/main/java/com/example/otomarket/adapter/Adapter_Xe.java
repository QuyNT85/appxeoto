package com.example.otomarket.adapter;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.otomarket.R;
import com.example.otomarket.activity.DetailActivity;
import com.example.otomarket.activity.ListActivity;
import com.example.otomarket.activity.MainActivity;
import com.example.otomarket.fragment.ListFragment;
import com.example.otomarket.model.GioHang;
import com.example.otomarket.model.Xe;
import com.example.otomarket.model.Xe_Fav;
import com.example.otomarket.server.SERVER;
import com.example.otomarket.util.CircleAnimationUtil;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Adapter_Xe extends RecyclerView.Adapter<Adapter_Xe.VH_Xe> implements Filterable {
    Context context;
    ArrayList<Xe> dataXe;
    ArrayList<Xe> dataXeOld;


    public Adapter_Xe(Context context, ArrayList<Xe> dataXe) {
        this.context = context;
        this.dataXe = dataXe;
        this.dataXeOld = dataXe;
    }


    @NonNull
    @Override
    public VH_Xe onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_xe, parent, false);
        return new VH_Xe(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH_Xe holder, int position) {
        Xe x = dataXe.get(position);

        if (x == null) {
            return;
        }
        Picasso.get().load(SERVER.imgXepath + x.getAnhXe()).into(holder.mhinhXe);
        Picasso.get().load(SERVER.imgXepath + x.getAnhXe()).into(holder.hinhXeCoppy);
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
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        holder.buttonAddFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof MainActivity) {
                    new CircleAnimationUtil().attachActivity((Activity) context).setTargetView(holder.hinhXeCoppy)
                            .setMoveDuration(1000)
                            .setDestView(ListFragment.frameLayoutFav)
                            .setAnimationListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(@NonNull Animator animation) {

                                }

                                @Override
                                public void onAnimationEnd(@NonNull Animator animation) {
                                    if (MainActivity.xeFavArrayList.size() > 0) {
                                        boolean isExisted = false;
                                        for (int i = 0; i < MainActivity.xeFavArrayList.size(); i++) {
                                            if (MainActivity.xeFavArrayList.get(i).getIdXeFav().equals(x.getMaXe())) {
                                                Snackbar snackbar = Snackbar.make(holder.buttonAddFav, x.getTenXe() + " đã tồn tại danh sách yêu thích", Toast.LENGTH_SHORT);
                                                snackbar.setBackgroundTint(Color.parseColor("#8B0000"));
                                                View snackbarView = snackbar.getView();
                                                TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                                                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                                                snackbar.setTextColor(Color.parseColor("#ffffff"))
                                                        .show();
                                                isExisted = true;
                                            }
                                        }
                                        if (!isExisted) {
                                            Xe_Fav xe_fav = new Xe_Fav();
                                            xe_fav.setAnhXeFav(x.getAnhXe());
                                            xe_fav.setTenXeFav(x.getTenXe());
                                            xe_fav.setGiaXeFav(x.getGiaXe());
                                            xe_fav.setIdXeFav(x.getMaXe()); // gán id cho xe_fav
                                            MainActivity.xeFavArrayList.add(xe_fav);
                                            Snackbar snackbar = Snackbar.make(holder.buttonAddFav, x.getTenXe() + " đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT);
                                            snackbar.setBackgroundTint(Color.parseColor("#008000"));
                                            View snackbarView = snackbar.getView();
                                            TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                                            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                                            snackbar.setTextColor(Color.parseColor("#ffffff"))
                                                    .show();
                                        }
                                    } else {
                                        Xe_Fav xe_fav = new Xe_Fav();
                                        xe_fav.setAnhXeFav(x.getAnhXe());
                                        xe_fav.setTenXeFav(x.getTenXe());
                                        xe_fav.setGiaXeFav(x.getGiaXe());
                                        xe_fav.setIdXeFav(x.getMaXe()); // gán id cho xe_fav
                                        MainActivity.xeFavArrayList.add(xe_fav);
                                        //snackbar
                                        Snackbar snackbar = Snackbar.make(holder.buttonAddFav, x.getTenXe().toUpperCase() + " đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT);
                                        snackbar.setBackgroundTint(Color.parseColor("#008000"));
                                        View snackbarView = snackbar.getView();
                                        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                                        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                                        snackbar.setTextColor(Color.parseColor("#ffffff"))
                                                .show();
                                    }
                                    ListFragment.bage.setText(String.valueOf(MainActivity.xeFavArrayList.size()));
                                }

                                @Override
                                public void onAnimationCancel(@NonNull Animator animation) {

                                }


                                @Override
                                public void onAnimationRepeat(@NonNull Animator animation) {

                                }
                            }).startAnimation();
                } else if (context instanceof ListActivity) {

                    new CircleAnimationUtil().attachActivity((Activity) context).setTargetView(holder.hinhXeCoppy)
                            .setMoveDuration(1000)
                            .setDestView(ListActivity.frameLayoutFavALi)
                            .setAnimationListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(@NonNull Animator animation) {

                                }

                                @Override
                                public void onAnimationEnd(@NonNull Animator animation) {
                                    if (MainActivity.xeFavArrayList.size() > 0) {
                                        boolean isExisted = false;
                                        for (int i = 0; i < MainActivity.xeFavArrayList.size(); i++) {
                                            if (MainActivity.xeFavArrayList.get(i).getIdXeFav().equals(x.getMaXe())) {
                                                Snackbar snackbar = Snackbar.make(holder.buttonAddFav, x.getTenXe() + " đã tồn tại danh sách yêu thích", Toast.LENGTH_SHORT);
                                                snackbar.setBackgroundTint(Color.parseColor("#8B0000"));
                                                View snackbarView = snackbar.getView();
                                                TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                                                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                                                snackbar.setTextColor(Color.parseColor("#ffffff"))
                                                        .show();
                                                isExisted = true;
                                            }
                                        }
                                        if (!isExisted) {
                                            Xe_Fav xe_fav = new Xe_Fav();
                                            xe_fav.setAnhXeFav(x.getAnhXe());
                                            xe_fav.setTenXeFav(x.getTenXe());
                                            xe_fav.setGiaXeFav(x.getGiaXe());
                                            xe_fav.setIdXeFav(x.getMaXe()); // gán id cho xe_fav
                                            MainActivity.xeFavArrayList.add(xe_fav);
                                            Snackbar snackbar = Snackbar.make(holder.buttonAddFav, x.getTenXe() + " đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT);
                                            snackbar.setBackgroundTint(Color.parseColor("#008000"));
                                            View snackbarView = snackbar.getView();
                                            TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                                            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                                            snackbar.setTextColor(Color.parseColor("#ffffff"))
                                                    .show();
                                        }
                                    } else {
                                        Xe_Fav xe_fav = new Xe_Fav();
                                        xe_fav.setAnhXeFav(x.getAnhXe());
                                        xe_fav.setTenXeFav(x.getTenXe());
                                        xe_fav.setGiaXeFav(x.getGiaXe());
                                        xe_fav.setIdXeFav(x.getMaXe()); // gán id cho xe_fav
                                        MainActivity.xeFavArrayList.add(xe_fav);
                                        //snackbar
                                        Snackbar snackbar = Snackbar.make(holder.buttonAddFav, x.getTenXe().toUpperCase() + " đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT);
                                        snackbar.setBackgroundTint(Color.parseColor("#008000"));
                                        View snackbarView = snackbar.getView();
                                        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                                        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                                        snackbar.setTextColor(Color.parseColor("#ffffff"))
                                                .show();
                                    }
                                    ListActivity.bageFavALi.setText(String.valueOf(MainActivity.xeFavArrayList.size()));
                                }

                                @Override
                                public void onAnimationCancel(@NonNull Animator animation) {

                                }


                                @Override
                                public void onAnimationRepeat(@NonNull Animator animation) {

                                }
                            }).startAnimation();
                }
            }
        });


        holder.btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof MainActivity) {
                    new CircleAnimationUtil().attachActivity((Activity) context).setTargetView(holder.hinhXeCoppy)
                            .setMoveDuration(1000)
                            .setDestView(ListFragment.frameLayoutCart)
                            .setAnimationListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(@NonNull Animator animation) {
                                }

                                @Override
                                public void onAnimationEnd(@NonNull Animator animation) {
                                    boolean exists = false;
                                    if (MainActivity.manggiohang.size() > 0) {
                                        for (int i = 0; i < MainActivity.manggiohang.size(); i++) {
                                            if (MainActivity.manggiohang.get(i).getMaxe() == x.getMaXe()) {
                                                if (MainActivity.manggiohang.get(i).getSoluongxe() >= 20) {
                                                    MainActivity.manggiohang.get(i).setSoluongxe(20);
                                                }
                                                Snackbar snackbar = Snackbar.make(holder.btnAddCart, x.getTenXe() + " đã tồn tại trong giỏ hàng", Toast.LENGTH_SHORT);
                                                snackbar.setBackgroundTint(Color.parseColor("#8B0000"));
                                                View snackbarView = snackbar.getView();
                                                TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                                                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                                                snackbar.setTextColor(Color.parseColor("#ffffff"))
                                                        .show();
                                                exists = true;
                                            }
                                        }
                                        if (!exists) {
                                            int soluong = 1;
                                            double giamoi = soluong * x.getGiaXe();
                                            GioHang cart = new GioHang();
                                            cart.setGiaxe((int) giamoi);
                                            cart.setSoluongxe(soluong);
                                            cart.setMaxe(x.getMaXe());
                                            cart.setTenxe(x.getTenXe());
                                            cart.setHinhxe(x.getAnhXe());
                                            MainActivity.manggiohang.add(cart);
                                            Snackbar snackbar = Snackbar.make(holder.btnAddCart, x.getTenXe() + " đã thêm vào giỏ hàng", Toast.LENGTH_SHORT);
                                            snackbar.setBackgroundTint(Color.parseColor("#008000"));
                                            View snackbarView = snackbar.getView();
                                            TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                                            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                                            snackbar.setTextColor(Color.parseColor("#ffffff"))
                                                    .show();
                                        }

                                    } else {
                                        int soluong = 1;
                                        double giamoi = soluong * x.getGiaXe();
                                        GioHang cart = new GioHang();
                                        cart.setGiaxe((int) giamoi);
                                        cart.setSoluongxe(soluong);
                                        cart.setMaxe(x.getMaXe());
                                        cart.setTenxe(x.getTenXe());
                                        cart.setHinhxe(x.getAnhXe());
                                        MainActivity.manggiohang.add(cart);
                                        Snackbar snackbar = Snackbar.make(holder.btnAddCart, x.getTenXe() + " đã thêm vào giỏ hàng", Toast.LENGTH_SHORT);
                                        snackbar.setBackgroundTint(Color.parseColor("#008000"));
                                        View snackbarView = snackbar.getView();
                                        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                                        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                                        snackbar.setTextColor(Color.parseColor("#ffffff"))
                                                .show();
                                    }
                                    ListFragment.bageCart.setText(String.valueOf(MainActivity.manggiohang.size()));
                                }

                                @Override
                                public void onAnimationCancel(@NonNull Animator animation) {

                                }

                                @Override
                                public void onAnimationRepeat(@NonNull Animator animation) {

                                }
                            }).startAnimation();
                } else if (context instanceof ListActivity) {
                    new CircleAnimationUtil().attachActivity((Activity) context).setTargetView(holder.hinhXeCoppy)
                            .setMoveDuration(1000)
                            .setDestView(ListActivity.frameLayoutCartAli)
                            .setAnimationListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(@NonNull Animator animation) {
                                }

                                @Override
                                public void onAnimationEnd(@NonNull Animator animation) {
                                    boolean exists = false;
                                    if (MainActivity.manggiohang.size() > 0) {
                                        for (int i = 0; i < MainActivity.manggiohang.size(); i++) {
                                            if (MainActivity.manggiohang.get(i).getMaxe() == x.getMaXe()) {
                                                if (MainActivity.manggiohang.get(i).getSoluongxe() >= 20) {
                                                    MainActivity.manggiohang.get(i).setSoluongxe(20);
                                                }
                                                Snackbar snackbar = Snackbar.make(holder.btnAddCart, x.getTenXe() + " đã tồn tại trong giỏ hàng", Toast.LENGTH_SHORT);
                                                snackbar.setBackgroundTint(Color.parseColor("#8B0000"));
                                                View snackbarView = snackbar.getView();
                                                TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                                                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                                                snackbar.setTextColor(Color.parseColor("#ffffff"))
                                                        .show();
                                                exists = true;
                                            }
                                        }
                                        if (!exists) {
                                            int soluong = 1;
                                            double giamoi = soluong * x.getGiaXe();
                                            GioHang cart = new GioHang();
                                            cart.setGiaxe((int) giamoi);
                                            cart.setSoluongxe(soluong);
                                            cart.setMaxe(x.getMaXe());
                                            cart.setTenxe(x.getTenXe());
                                            cart.setHinhxe(x.getAnhXe());
                                            MainActivity.manggiohang.add(cart);
                                            Snackbar snackbar = Snackbar.make(holder.btnAddCart, x.getTenXe() + " đã thêm vào giỏ hàng", Toast.LENGTH_SHORT);
                                            snackbar.setBackgroundTint(Color.parseColor("#008000"));
                                            View snackbarView = snackbar.getView();
                                            TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                                            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                                            snackbar.setTextColor(Color.parseColor("#ffffff"))
                                                    .show();
                                        }

                                    } else {
                                        int soluong = 1;
                                        double giamoi = soluong * x.getGiaXe();
                                        GioHang cart = new GioHang();
                                        cart.setGiaxe((int) giamoi);
                                        cart.setSoluongxe(soluong);
                                        cart.setMaxe(x.getMaXe());
                                        cart.setTenxe(x.getTenXe());
                                        cart.setHinhxe(x.getAnhXe());
                                        MainActivity.manggiohang.add(cart);
                                        Snackbar snackbar = Snackbar.make(holder.btnAddCart, x.getTenXe() + " đã thêm vào giỏ hàng", Toast.LENGTH_SHORT);
                                        snackbar.setBackgroundTint(Color.parseColor("#008000"));
                                        View snackbarView = snackbar.getView();
                                        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                                        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                                        snackbar.setTextColor(Color.parseColor("#ffffff"))
                                                .show();
                                    }
                                    ListActivity.bageCartALi.setText(String.valueOf(MainActivity.manggiohang.size()));
                                }

                                @Override
                                public void onAnimationCancel(@NonNull Animator animation) {

                                }

                                @Override
                                public void onAnimationRepeat(@NonNull Animator animation) {

                                }
                            }).startAnimation();
                }

            }
        });
    }


    @Override
    public int getItemCount() {
        if (dataXe != null) {
            return dataXe.size();
        }
        return 0;
    }


    public void clear() {
        dataXeOld.clear();
    }


    public class VH_Xe extends RecyclerView.ViewHolder {
        FrameLayout frameLayoutFav;
        ImageView mhinhXe, hinhXeCoppy;
        TextView mtenXe, mGia;
        LinearLayout linearLayout;
        Button btnAddCart, buttonAddFav;

        public VH_Xe(@NonNull View itemView) {
            super(itemView);
            mhinhXe = itemView.findViewById(R.id.anhXe_cus);
            mtenXe = itemView.findViewById(R.id.tenXe_cus);
            mGia = itemView.findViewById(R.id.giaXe_cus);
            btnAddCart = itemView.findViewById(R.id.btn_Ad_Cart);
            buttonAddFav = itemView.findViewById(R.id.btn_Ad_Fav);
            linearLayout = itemView.findViewById(R.id.lnListFrag);

            hinhXeCoppy = itemView.findViewById(R.id.anhXe_cus_coppy);
            frameLayoutFav = itemView.findViewById(R.id.frame_Fav_LF);
        }

    }

    //tim kiem trong list xe
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String stringSearch = constraint.toString();
                if (stringSearch.isEmpty()) {
                    dataXe = dataXeOld;
                } else {
                    ArrayList<Xe> dsXe = new ArrayList<>();
                    for (Xe xe : dataXeOld) {
                        if (xe.getTenXe().toLowerCase().contains(stringSearch.toLowerCase())) {
                            dsXe.add(xe);
                        }
                    }
                    dataXe = dsXe;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = dataXe;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                dataXe = (ArrayList<Xe>) results.values;
                notifyDataSetChanged();
            }
        };
    }


}


