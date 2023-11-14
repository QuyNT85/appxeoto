package com.example.otomarket.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.otomarket.R;
import com.example.otomarket.adapter.Adapter_Brand_Search;
import com.example.otomarket.adapter.Adapter_Xe;
import com.example.otomarket.model.HangXe;
import com.example.otomarket.model.Xe;
import com.example.otomarket.server.SERVER;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    Toolbar toolbar;

    RecyclerView rc_search_hang, rc_Search;

    Adapter_Xe adapter_xe;
    //    Adapter_Search_Xe adapter_search_xe;
    Adapter_Brand_Search adapter_brand_search;

    ArrayList<Xe> xeArrayList = new ArrayList<>();
    ArrayList<HangXe> hangXeArrayList = new ArrayList<>();
    EditText edtSearch;

    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        toolbar = findViewById(R.id.toolbarSearch);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        rc_Search = findViewById(R.id.rc_Search);
        rc_search_hang = findViewById(R.id.rc_Search_Brand);
        edtSearch = findViewById(R.id.searchEditText);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edtSearch.getText().length() > 0) {
                    query = s.toString();
                    loadDataSearch(query);
                } else {
                    xeArrayList.clear();
                }
                adapter_xe.notifyDataSetChanged();
            }
        });
        LoadBrand();
    }

    private void loadDataSearch(String searchQuery) {
        RequestQueue requestQueue = Volley.newRequestQueue(SearchActivity.this);
        Response.Listener<JSONArray> thanhcong = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                xeArrayList.clear();
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
                        Toast.makeText(SearchActivity.this, "loi", Toast.LENGTH_SHORT).show();

                    }
                }
                adapter_xe.notifyDataSetChanged();
            }
        };

        Response.ErrorListener thatbai = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SearchActivity.this, "That Bai", Toast.LENGTH_SHORT).show();
            }
        };

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, SERVER.searchPath + query, null, thanhcong, thatbai);
        requestQueue.add(jsonArrayRequest);
        rc_Search.setLayoutManager(new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.VERTICAL, false));
        adapter_xe = new Adapter_Xe(SearchActivity.this, xeArrayList);
        rc_Search.setAdapter(adapter_xe);
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

    public void LoadBrand() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Response.Listener<JSONArray> thanhcong = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        hangXeArrayList.add(new HangXe(
                                jsonObject.getString("mahang"),
                                jsonObject.getString("tenhang"),
                                jsonObject.getString("hinhanh")));
                        adapter_brand_search.notifyDataSetChanged();
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), "loi", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        };

        Response.ErrorListener thatbai = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "tHATbai", Toast.LENGTH_SHORT).show();
            }
        };

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(SERVER.hangpath, thanhcong, thatbai);
        requestQueue.add(jsonArrayRequest);
        adapter_brand_search = new Adapter_Brand_Search(getApplicationContext(), hangXeArrayList);
        rc_search_hang.setAdapter(adapter_brand_search);
        rc_search_hang.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        rc_search_hang.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.HORIZONTAL));
        rc_search_hang.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));

    }
}