package com.example.otomarket.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.otomarket.R;
import com.example.otomarket.fragment.ListFragment;
import com.example.otomarket.fragment.NotifyFragment;
import com.example.otomarket.model.GioHang;
import com.example.otomarket.model.Xe;
import com.example.otomarket.model.Xe_Fav;
import com.example.otomarket.server.SERVER;
import com.example.otomarket.util.CircleAnimationUtil;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.security.PublicKey;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    ImageView imgDT, imgFav, imgCart, imgDTcoppy;
    TextView tenDT, giaTD, bage, bageCart;
    Button btnAddCart, btnAddFav;
    Toolbar toolbar;
    public static Spinner spinner_ctsp;
    String maxe, hinhxe, tenxe;


    FrameLayout frameFAV, frameCart;
    Xe xe1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        AnhXa();
        setToolbar();
        getBundle();
        setBtnAddFav();
        setSpiner();
        setBtnAddCart();


    }


    public void AnhXa() {
        imgDT = findViewById(R.id.anh_detail);
        tenDT = findViewById(R.id.ten_Detail);
        giaTD = findViewById(R.id.gia_Detail);
        btnAddFav = findViewById(R.id.add_fav);
        btnAddCart = findViewById(R.id.add_Cart);
        toolbar = findViewById(R.id.toolbarDetail);
        imgFav = findViewById(R.id.imgFavDT);
        spinner_ctsp = findViewById(R.id.spinner_chitietsanpham);
        bage = findViewById(R.id.badge_DT);
        bageCart = findViewById(R.id.badge_Cart_DT);
        imgCart = findViewById(R.id.imgCartDT);
        frameCart = findViewById(R.id.frame_Cart_DT);
        frameFAV = findViewById(R.id.frame_Fav_DT);
        imgDTcoppy = findViewById(R.id.imgDetalCoppy);

        if (MainActivity.xeFavArrayList != null) {
            bage.setText(String.valueOf(MainActivity.xeFavArrayList.size()));
        }

        if (MainActivity.manggiohang == null) {
            MainActivity.manggiohang = new ArrayList<>();
        } else {
            int tongSoLuong = 0;
            for (int i = 0; i < MainActivity.manggiohang.size(); i++) {
                tongSoLuong = tongSoLuong + MainActivity.manggiohang.get(i).getSoluongxe();
            }
            bageCart.setText(String.valueOf(tongSoLuong));
        }

    }

    public void getBundle() {
        Bundle bundle = getIntent().getExtras();
        Xe xe1 = (Xe) bundle.get("ct");
        Picasso.get().load(SERVER.imgXepath + xe1.getAnhXe()).into(imgDT);
//        Picasso.get().load(SERVER.imgXepath + xe1.getAnhXe()).into(imgDT);
        tenDT.setText(xe1.getTenXe());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        giaTD.setText("Price: " + decimalFormat.format(xe1.getGiaXe()) + " $");
        getSupportActionBar().setTitle(xe1.getTenXe());

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setSpiner() {
        Integer[] soluong = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_spinner_dropdown_item, soluong);
        spinner_ctsp.setAdapter(arrayAdapter);

    }

    private void setBtnAddCart() {
        Bundle bundle = getIntent().getExtras();
        Xe xe1 = (Xe) bundle.get("ct");
        maxe = xe1.getMaXe();
        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean exists = false;
                if (MainActivity.manggiohang.size() > 0) {
                    int sl = Integer.parseInt(spinner_ctsp.getSelectedItem().toString());
                    for (int i = 0; i < MainActivity.manggiohang.size(); i++) {
                        if (MainActivity.manggiohang.get(i).getMaxe() == maxe) {
                            MainActivity.manggiohang.get(i).setSoluongxe(MainActivity.manggiohang.get(i).getSoluongxe() + sl);
                            if (MainActivity.manggiohang.get(i).getSoluongxe() >= 20) {
                                MainActivity.manggiohang.get(i).setSoluongxe(20);
                            }
                            exists = true;
                        }
                    }
                    if (!exists) {
                        int soluong = Integer.parseInt(spinner_ctsp.getSelectedItem().toString());
                        double giamoi = soluong * xe1.getGiaXe();
                        GioHang cart = new GioHang();
                        cart.setGiaxe((int) giamoi);
                        cart.setSoluongxe(soluong);
                        cart.setMaxe(xe1.getMaXe());
                        cart.setTenxe(xe1.getTenXe());
                        cart.setHinhxe(xe1.getAnhXe());
                        MainActivity.manggiohang.add(cart);
                        Snackbar snackbar = Snackbar.make(btnAddCart, xe1.getTenXe() + " đã thêm vào giỏ hàng", Toast.LENGTH_SHORT);
                        snackbar.setBackgroundTint(Color.parseColor("#008000"));
                        View snackbarView = snackbar.getView();
                        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                        snackbar.setTextColor(Color.parseColor("#ffffff"))
                                .show();
                    }

                } else {
                    int soluong = Integer.parseInt(spinner_ctsp.getSelectedItem().toString());
                    double giamoi = soluong * xe1.getGiaXe();
                    GioHang cart = new GioHang();
                    cart.setGiaxe((int) giamoi);
                    cart.setSoluongxe(soluong);
                    cart.setMaxe(xe1.getMaXe());
                    cart.setTenxe(xe1.getTenXe());
                    cart.setHinhxe(xe1.getAnhXe());
                    MainActivity.manggiohang.add(cart);
                    Snackbar snackbar = Snackbar.make(btnAddCart, xe1.getTenXe() + " đã thêm vào giỏ hàng", Toast.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.parseColor("#008000"));
                    View snackbarView = snackbar.getView();
                    TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    snackbar.setTextColor(Color.parseColor("#ffffff"))
                            .show();
                }
                xe1.setSoluongxe(Integer.parseInt(spinner_ctsp.getSelectedItem().toString()));
                int tongSoLuong = 0;
                for (int i = 0; i < MainActivity.manggiohang.size(); i++) {
                    tongSoLuong = tongSoLuong + MainActivity.manggiohang.get(i).getSoluongxe();
                }
                bageCart.setText(String.valueOf(tongSoLuong));


//                xe1.setSoluongxe(Integer.parseInt(spinner_ctsp.getSelectedItem().toString()));
//                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
//                intent.putExtra("thongtinsanpham", xe1);
//                startActivity(intent);
            }
        });

        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent1);
            }
        });
    }

    public void setBtnAddFav() {
        Bundle bundle = getIntent().getExtras();
        Xe xe1 = (Xe) bundle.get("ct");
        btnAddFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.xeFavArrayList.size() > 0) {
                    boolean isExisted = false;
                    for (int i = 0; i < MainActivity.xeFavArrayList.size(); i++) {
                        if (MainActivity.xeFavArrayList.get(i).getIdXeFav().equals(xe1.getMaXe())) {
                            Snackbar snackbar = Snackbar.make(btnAddFav, xe1.getTenXe() + " đã tồn tại trong danh sách yêu thích", Toast.LENGTH_SHORT);
                            snackbar.setBackgroundTint(Color.parseColor("#8B0000"));
                            View snackbarView = snackbar.getView();
                            TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                            snackbar.setTextColor(Color.parseColor("#ffffff"))
                                    .show();
                            isExisted = true;
                        }
                    }
                    if (!isExisted) {
                        Xe_Fav xe_fav = new Xe_Fav();
                        xe_fav.setAnhXeFav(xe1.getAnhXe());
                        xe_fav.setTenXeFav(xe1.getTenXe());
                        xe_fav.setGiaXeFav(xe1.getGiaXe());
                        xe_fav.setIdXeFav(xe1.getMaXe()); // gán id cho xe_fav
                        MainActivity.xeFavArrayList.add(xe_fav);
                        bage.setText(String.valueOf(MainActivity.xeFavArrayList.size()));
                        Snackbar snackbar = Snackbar.make(btnAddFav, xe1.getTenXe() + " đã thêm vào yêu thích", Toast.LENGTH_SHORT);
                        snackbar.setBackgroundTint(Color.parseColor("#008000"));
                        View snackbarView = snackbar.getView();
                        TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                        snackbar.setTextColor(Color.parseColor("#ffffff"))
                                .show();
                    }

                } else {
                    Xe_Fav xe_fav = new Xe_Fav();
                    xe_fav.setAnhXeFav(xe1.getAnhXe());
                    xe_fav.setTenXeFav(xe1.getTenXe());
                    xe_fav.setGiaXeFav(xe1.getGiaXe());
                    xe_fav.setIdXeFav(xe1.getMaXe()); // gán id cho xe_fav
                    MainActivity.xeFavArrayList.add(xe_fav);

                    //snackbar
                    Snackbar snackbar = Snackbar.make(btnAddFav, xe1.getTenXe() + " đã thêm vào yêu thích", Toast.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.parseColor("#008000"));
                    View snackbarView = snackbar.getView();
                    TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    snackbar.setTextColor(Color.parseColor("#ffffff"))
                            .show();
                }
                bage.setText(String.valueOf(MainActivity.xeFavArrayList.size()));
            }
        });
        imgFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), FavActivity.class);
                startActivity(intent1);
            }
        });

    }


    //cập nhập lại trạng thái lực resume lại
    @Override
    protected void onResume() {
        super.onResume();
        if (MainActivity.xeFavArrayList != null) {
            bage.setText(String.valueOf(MainActivity.xeFavArrayList.size()));
        }

        if (MainActivity.manggiohang != null) {
            int tongSoLuong = 0;
            for (int i = 0; i < MainActivity.manggiohang.size(); i++) {
                tongSoLuong = tongSoLuong + MainActivity.manggiohang.get(i).getSoluongxe();
            }
            bageCart.setText(String.valueOf(tongSoLuong));
        }

    }

}