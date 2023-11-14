package com.example.otomarket.activity;

import static com.example.otomarket.server.SERVER.khachhangpath;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.otomarket.R;
import com.example.otomarket.adapter.Adapter_HoaDon;
import com.example.otomarket.adapter.Adapter_Xe;
import com.example.otomarket.model.HoaDon;
import com.example.otomarket.model.Item;
import com.example.otomarket.model.Xe;
import com.example.otomarket.server.SERVER;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HistoryOrderActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView rcHistory, rcChiTiet;
    ArrayList<HoaDon> hoaDonList = new ArrayList<>();

    ArrayList<Item> itemList = new ArrayList<>();
    Adapter_HoaDon adapter_hoaDon;

    public static String emailnhan, passnhan, makh;


    int a = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_order);

        AnhXa();
        setToolbar();
        checkLogin();


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

    void AnhXa() {
        toolbar = findViewById(R.id.toolbarHis);
        rcHistory = findViewById(R.id.rc_His_donHang);
    }

    void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(null);
    }

    void checkLogin() {
        emailnhan = MainActivity.email;
        passnhan = MainActivity.pass;


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = khachhangpath;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        if (emailnhan.equals(jsonObject.getString("Email")) &&
                                passnhan.equals(jsonObject.getString("Password"))) {
                            makh = jsonObject.getString("MaKH");
                            getDataHoDon();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    public void getDataHoDon() {
        RequestQueue requestQueue = Volley.newRequestQueue(HistoryOrderActivity.this);
        Response.Listener<JSONArray> thanhcong = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int maHD = Integer.parseInt(jsonObject.getString("MaHD"));
                        int maKH = Integer.parseInt(jsonObject.getString("MaKH"));
                        String diaChi = jsonObject.getString("DiaChi");
                        String sdt = jsonObject.getString("SDT");
                        String tongTien = jsonObject.getString("TongTien");

                        //get api item
                        List<Item> itemList1 = new ArrayList<>();
                        JSONArray itemArray = jsonObject.getJSONArray("item");
                        for (int j = 0; j < itemArray.length(); j++) {
                            JSONObject itemObject = itemArray.getJSONObject(j);
                            itemList1.add(new Item(
                                    itemObject.getString("MaOTO"),
                                    itemObject.getString("TenOTO"),
                                    itemObject.getString("AnhOTO"),
                                    itemObject.getString("SoLuong")
                            ));
                        }
                        hoaDonList.add(new HoaDon(a, maHD, maKH, diaChi, sdt, tongTien, itemList1));

                    } catch (JSONException e) {
                        Toast.makeText(HistoryOrderActivity.this, "loi", Toast.LENGTH_SHORT).show();
                    }
                }
                adapter_hoaDon.notifyDataSetChanged();
            }
        };

        Response.ErrorListener thatbai = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HistoryOrderActivity.this, "That Bai", Toast.LENGTH_SHORT).show();
            }
        };
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(SERVER.historyPath + makh, thanhcong, thatbai);
        requestQueue.add(jsonArrayRequest);

        LinearLayoutManager layoutManager = new LinearLayoutManager(HistoryOrderActivity.this);
        rcHistory.setLayoutManager(layoutManager);
        adapter_hoaDon = new Adapter_HoaDon(getApplicationContext(), hoaDonList);
        rcHistory.setAdapter(adapter_hoaDon);
    }
}