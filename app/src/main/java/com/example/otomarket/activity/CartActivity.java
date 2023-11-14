
package com.example.otomarket.activity;

import static com.example.otomarket.adapter.Adapter_Cart.TongTien;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.otomarket.R;
import com.example.otomarket.adapter.Adapter_Cart;
import com.example.otomarket.model.Xe;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    Toolbar mToolbarCart;

    RecyclerView recygiohang;
    TextView tvthongbao;
    public static TextView tvtongtien;
    Adapter_Cart gioHangAdapter;
    Button btn_thanhtoan,btn_tieptuc_muahang;


    static ArrayList<Xe> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        AnhXa();
        setToolbar();
        CheckData();
        ThanhToan();
        TiepTucMuaHang();

    }

    private void TiepTucMuaHang() {
        btn_tieptuc_muahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void AnhXa() {
        recygiohang = findViewById(R.id.recy_giohang);
        tvthongbao = findViewById(R.id.tv_thongbao_giohang);
        tvtongtien = findViewById(R.id.tvtongtien_giohang);


        gioHangAdapter = new Adapter_Cart(CartActivity.this, MainActivity.manggiohang);
        recygiohang.setAdapter(gioHangAdapter);
        recygiohang.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mToolbarCart = findViewById(R.id.toolbarCart);
        btn_thanhtoan = findViewById(R.id.button_thanhtoan_giohang);
        btn_tieptuc_muahang = findViewById(R.id.button_tieptucmuahang_giohang);
    }


    private void CheckData() {
        if (MainActivity.manggiohang.size() <= 0) {
            gioHangAdapter.notifyDataSetChanged();
            tvthongbao.setVisibility(View.VISIBLE);
            recygiohang.setVisibility(View.INVISIBLE);
        } else {
            gioHangAdapter.notifyDataSetChanged();
            tvthongbao.setVisibility(View.INVISIBLE);
            recygiohang.setVisibility(View.VISIBLE);
        }
    }

    private void setToolbar() {
        setSupportActionBar(mToolbarCart);
        getSupportActionBar().setTitle("Carts");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void ThanhToan() {
        btn_thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.manggiohang.size()!=0) {
                    Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
                    intent.putExtra("tt", TongTien());
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(CartActivity.this, "Chưa có sản phẩm trong giỏ hàng", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    public void slxe(){
        
    }
}

