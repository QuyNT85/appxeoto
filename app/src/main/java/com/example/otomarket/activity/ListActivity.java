package com.example.otomarket.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.otomarket.R;
import com.example.otomarket.adapter.Adapter_Xe;
import com.example.otomarket.model.Xe;
import com.example.otomarket.server.SERVER;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ListActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    RecyclerView rcXeList;
    Adapter_Xe adapter_xe;
    ArrayList<Xe> xeArrayList = new ArrayList<>();

    Intent intent;
    String mahang, tenHang, mapk, tenpk, anhHang = "";

    Toolbar mToolbarList;

    SearchView searchView;
    SearchManager searchManager;
    String titleDsPK = "";
    String titlePKXe = "";

    public static TextView bageFavALi, bageCartALi;
    public static FrameLayout frameLayoutFavALi, frameLayoutCartAli;
    ImageView imgFav1, imgCart1;


    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        AnhXa();
        setmToolbarList();
        LoadDsXeBrand();
        LoadDsXePK();
        setSearchView();
        setSpinner();

        if (titleDsPK == null) {
            getSupportActionBar().setTitle(titlePKXe);
        } else {
            getSupportActionBar().setTitle(titleDsPK);
        }


        imgCart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(ListActivity.this, CartActivity.class);
                startActivity(intent2);
            }
        });

        imgFav1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ListActivity.this, FavActivity.class);
                startActivity(intent1);
            }
        });
    }

    public void AnhXa() {
        rcXeList = findViewById(R.id.rc_List);
        mToolbarList = findViewById(R.id.toolbarList);
        searchView = findViewById(R.id.search_List);
        bageCartALi = findViewById(R.id.badge_Cart_ActL);
        bageFavALi = findViewById(R.id.badge_fav_ActL);
        imgCart1 = findViewById(R.id.imgCartActList);
        imgFav1 = findViewById(R.id.imgFavActList);
        frameLayoutCartAli = findViewById(R.id.frame_Cart_ActL);
        frameLayoutFavALi = findViewById(R.id.frame_Fav_ActLi);
    }

    public void setmToolbarList() {
        setSupportActionBar(mToolbarList);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void setSpinner() {
        spinner = findViewById(R.id.spinner1);
        adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.spinner_array, R.layout.spinner_text);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
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


    public void setSearchView() {
        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter_xe.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter_xe.getFilter().filter(newText);
                return false;
            }
        });
    }

    public void LoadDsXeBrand() {
        intent = getIntent();
        mahang = intent.getStringExtra("mahang");
        tenHang = intent.getStringExtra("tenhang");

        titleDsPK = tenHang;

        RequestQueue requestQueue = Volley.newRequestQueue(ListActivity.this);
        Response.Listener<JSONArray> thanhcong = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        xeArrayList.add(new Xe(
                                jsonObject.getString("maxe"),
                                jsonObject.getString("tenxe"),
                                jsonObject.getString("anhxe"),
                                jsonObject.getString("mahang"),
                                jsonObject.getString("mapk"),
                                jsonObject.getInt("dongia"),
                                jsonObject.getInt("soluongxe")));
                    } catch (JSONException e) {
                        Toast.makeText(ListActivity.this, "loi", Toast.LENGTH_SHORT).show();

                    }
                }
                adapter_xe.notifyDataSetChanged();
            }
        };

        Response.ErrorListener thatbai = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ListActivity.this, "That Bai", Toast.LENGTH_SHORT).show();
            }
        };

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(SERVER.xeByBrand + mahang + "'",
                thanhcong, thatbai);
        requestQueue.add(jsonArrayRequest);
        adapter_xe = new Adapter_Xe(ListActivity.this, xeArrayList);
        rcXeList.setAdapter(adapter_xe);
        rcXeList.setLayoutManager(new LinearLayoutManager(ListActivity.this, LinearLayoutManager.VERTICAL, false));
    }

    public void LoadDsXePK() {
        intent = getIntent();
        mapk = intent.getStringExtra("mapk");
        tenpk = intent.getStringExtra("tenpk");

        titlePKXe = tenpk;
        RequestQueue requestQueue = Volley.newRequestQueue(ListActivity.this);
        Response.Listener<JSONArray> thanhcong = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        xeArrayList.add(new Xe(
                                jsonObject.getString("maxe"),
                                jsonObject.getString("tenxe"),
                                jsonObject.getString("anhxe"),
                                jsonObject.getString("mahang"),
                                jsonObject.getString("mapk"),
                                jsonObject.getInt("dongia"),
                                jsonObject.getInt("soluongxe")));
                    } catch (JSONException e) {
                        Toast.makeText(ListActivity.this, "loi", Toast.LENGTH_SHORT).show();

                    }
                }
                adapter_xe.notifyDataSetChanged();
            }
        };

        Response.ErrorListener thatbai = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ListActivity.this, "That Bai", Toast.LENGTH_SHORT).show();
            }
        };

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                SERVER.xeByPK + mapk + "'",
                thanhcong, thatbai);
        requestQueue.add(jsonArrayRequest);
        adapter_xe = new Adapter_Xe(ListActivity.this, xeArrayList);
        rcXeList.setAdapter(adapter_xe);
        rcXeList.setLayoutManager(new LinearLayoutManager(ListActivity.this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int idPos = position;
        switch (idPos) {
            case 0:
                sortAZ();
                break;
            case 1:
                sortZA();
                break;
            case 2:
                sortPriceAsc();
                break;
            case 3:
                sortPriceDesc();
                break;
        }
    }

    private void sortAZ() {
        Collections.sort(xeArrayList, new Comparator<Xe>() {
            @Override
            public int compare(Xe o1, Xe o2) {
                return o1.getTenXe().compareToIgnoreCase(o2.getTenXe());
            }
        });
        adapter_xe.notifyDataSetChanged();
    }


    private void sortZA() {
        Collections.sort(xeArrayList, new Comparator<Xe>() {
            @Override
            public int compare(Xe o1, Xe o2) {
                return o2.getTenXe().compareToIgnoreCase(o1.getTenXe());
            }
        });
        adapter_xe.notifyDataSetChanged();
    }

    private void sortPriceAsc() {
        Collections.sort(xeArrayList, new Comparator<Xe>() {
            @Override
            public int compare(Xe o1, Xe o2) {
                if (o1.getGiaXe() > o2.getGiaXe()) {
                    return 1;
                } else if (o1.getGiaXe() < o2.getGiaXe()) {
                    return -1;
                }
                return 0;
            }
        });
        adapter_xe.notifyDataSetChanged();
    }

    private void sortPriceDesc() {
        Collections.sort(xeArrayList, new Comparator<Xe>() {
            @Override
            public int compare(Xe o1, Xe o2) {
                if (o2.getGiaXe() > o1.getGiaXe()) {
                    return 1;
                } else if (o2.getGiaXe() < o1.getGiaXe()) {
                    return -1;
                }
                return 0;
            }
        });
        adapter_xe.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MainActivity.xeFavArrayList != null) {
            bageFavALi.setText(String.valueOf(MainActivity.xeFavArrayList.size()));
        }
        if (MainActivity.manggiohang != null) {
            long tongSoLuong = 0;
            for (int i = 0; i < MainActivity.manggiohang.size(); i++) {
                tongSoLuong = tongSoLuong + MainActivity.manggiohang.get(i).getSoluongxe();
            }
            bageCartALi.setText(String.valueOf(tongSoLuong));
        }
    }
}