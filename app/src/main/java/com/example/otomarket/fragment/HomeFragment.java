package com.example.otomarket.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.otomarket.activity.CartActivity;
import com.example.otomarket.activity.DetailActivity;
import com.example.otomarket.activity.FavActivity;
import com.example.otomarket.activity.ListActivity;
import com.example.otomarket.activity.MainActivity;
import com.example.otomarket.R;
import com.example.otomarket.activity.SearchActivity;
import com.example.otomarket.activity.SplashActivity;
import com.example.otomarket.adapter.Adapter_BestCar;
import com.example.otomarket.adapter.Adapter_HangXe;
import com.example.otomarket.adapter.Adapter_PK;
import com.example.otomarket.adapter.Adapter_Slide_Img;
import com.example.otomarket.adapter.Adapter_Xe;
import com.example.otomarket.model.HangXe;
import com.example.otomarket.model.PhanKhuc;
import com.example.otomarket.model.SlideImg;
import com.example.otomarket.model.Xe;
import com.example.otomarket.server.SERVER;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    View mViewHF;

    ImageView imgSearch, imgFav, imgCart;
    TextView bage, bageCart;


    Adapter_Slide_Img adapter_slide_img;
    Adapter_HangXe adapter_hangXe;
    Adapter_PK adapter_pk;
    Adapter_BestCar adapter_bestCar;


    ArrayList<SlideImg> imgArrayList = new ArrayList<>();
    ArrayList<HangXe> hangXeArrayList = new ArrayList<>();
    ArrayList<PhanKhuc> dataPk = new ArrayList<>();
    ArrayList<Xe> xeArrayList = new ArrayList<>();

    RecyclerView rc_HX, rc_PK, rc_BestCar;

    ViewPager2 vp2_SlideImg;
    Handler handler = new Handler(Looper.getMainLooper());
    Runnable runnable;

    AppCompatActivity appCompatActivity;
    Toolbar mToolbar;
    MainActivity mainActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewHF = inflater.inflate(R.layout.fragment_home, container, false);

        AnhXa();
        setToolbarHome();
        Slide_Img();
        LoadBrand();
        LoadPK();
        LoadBestCar();

//        bage.setText(String.valueOf(MainActivity.xeFavArrayList.size()));
//        bageCart.setText(String.valueOf(MainActivity.manggiohang.size()));
//

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });

        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getActivity(), CartActivity.class);
                startActivity(intent2);
            }
        });

        imgFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), FavActivity.class);
                startActivity(intent1);
            }
        });

        return mViewHF;
    }

    public void AnhXa() {
        vp2_SlideImg = mViewHF.findViewById(R.id.vp2_slide);
        rc_HX = mViewHF.findViewById(R.id.rc_Brand_xe);
        rc_PK = mViewHF.findViewById(R.id.rc_PK);
        rc_BestCar = mViewHF.findViewById(R.id.rc_best_car);
        mToolbar = mViewHF.findViewById(R.id.toolbarHF);

        imgSearch = mViewHF.findViewById(R.id.imgSeachFragHome);
        imgCart = mViewHF.findViewById(R.id.imgCartFragHome);

        imgFav = mViewHF.findViewById(R.id.imgFavFragHome);
        bage = mViewHF.findViewById(R.id.badge_FH);
        bageCart = mViewHF.findViewById(R.id.badge_Cart_FH);

    }

    public void setToolbarHome() {
        mainActivity = (MainActivity) getActivity();
        appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(mToolbar);
        appCompatActivity.getSupportActionBar().setTitle("Autocar");
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        appCompatActivity.getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public ArrayList<SlideImg> getListImage() {
        imgArrayList.add(new SlideImg("https://d1csarkz8obe9u.cloudfront.net/posterpreviews/car-sale-promotion-facebook-cover-banner-design-template-904fb96ddd27f584e4da597e920ef22d_screen.jpg?ts=1630013522", "link"));
        imgArrayList.add(new SlideImg("https://www.rolls-roycemotorcarswashington.com/static/dealer-14434/WF51311_VASTEROL_MLPBannerUpdates_Phantom.jpg", "link"));
        imgArrayList.add(new SlideImg("https://img.freepik.com/premium-vector/luxury-car-sale-banner-social-media-post-template_416835-122.jpg?w=740", "link"));
        imgArrayList.add(new SlideImg("https://i.pinimg.com/564x/34/17/95/341795ade242aa0fa426690b7d069a28.jpg", "link"));
        imgArrayList.add(new SlideImg("https://img.freepik.com/premium-vector/car-sale-instagram-social-media-banner-post-template_619717-120.jpg?w=1060", "link"));
        return imgArrayList;
    }

    public void Slide_Img() {
        vp2_SlideImg.setOffscreenPageLimit(3);
        vp2_SlideImg.setClipToPadding(false);
        vp2_SlideImg.setClipChildren(false);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float a = 1 - Math.abs(position);
                page.setScaleY(0.85f + a * 0.15f);
            }
        });
        vp2_SlideImg.setPageTransformer(compositePageTransformer);

        adapter_slide_img = new Adapter_Slide_Img(getListImage(), getContext());
        vp2_SlideImg.setAdapter(adapter_slide_img);


        runnable = new Runnable() {
            @Override
            public void run() {
                int currentPos = vp2_SlideImg.getCurrentItem();
                if (currentPos == imgArrayList.size() - 1) {
                    vp2_SlideImg.setCurrentItem(0);
                } else {

                    vp2_SlideImg.setCurrentItem(currentPos + 1);
                }
            }
        };
        vp2_SlideImg.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 2000);
            }
        });
    }

    // onPause và onResume để khi nhấn nút home và trở lại app sẽ hieenh ảnh đã dừng lúc ấn home
    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 2000);
        if (MainActivity.xeFavArrayList != null) {
            bage.setText(String.valueOf(MainActivity.xeFavArrayList.size()));
        }

        if (MainActivity.manggiohang != null) {
            long tongSoLuong = 0;
            for (int i = 0; i < MainActivity.manggiohang.size(); i++) {
                tongSoLuong = tongSoLuong + MainActivity.manggiohang.get(i).getSoluongxe();
            }
            bageCart.setText(String.valueOf(tongSoLuong));
        }
    }

    public void LoadBrand() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
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
                    } catch (JSONException e) {
                        Toast.makeText(getContext(), "loi", Toast.LENGTH_SHORT).show();

                    }
                }
                adapter_hangXe.notifyDataSetChanged();
            }
        };

        Response.ErrorListener thatbai = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "tHATbai", Toast.LENGTH_SHORT).show();
            }
        };

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(SERVER.hangpath, thanhcong, thatbai);
        requestQueue.add(jsonArrayRequest);

        adapter_hangXe = new Adapter_HangXe(getContext(), hangXeArrayList, new Adapter_HangXe.itf_ClickItemListener() {
            @Override
            public void clickBand(HangXe hangXe) {
                Intent intent = new Intent(getActivity(), ListActivity.class);
                intent.putExtra("mahang", hangXe.getMaHangXe());
                intent.putExtra("tenhang", hangXe.getTenHangXe());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


            }
        });
        rc_HX.setAdapter(adapter_hangXe);
        rc_HX.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

    }

    public void LoadPK() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        Response.Listener<JSONArray> thanhcong = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        dataPk.add(new PhanKhuc(
                                jsonObject.getString("mapk"),
                                jsonObject.getString("tenpk"),
                                jsonObject.getString("hinhpk")));
                    } catch (JSONException e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                adapter_pk.notifyDataSetChanged();
            }
        };

        Response.ErrorListener thatbai = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "LOI", Toast.LENGTH_SHORT).show();
            }
        };

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(SERVER.pkpath, thanhcong, thatbai);
        requestQueue.add(jsonArrayRequest);

        adapter_pk = new Adapter_PK(getContext(), dataPk, new Adapter_PK.itf_ClickItemListenerPK() {
            @Override
            public void clickPK(PhanKhuc phanKhuc) {
                Intent intent1 = new Intent(getActivity(), ListActivity.class);
                intent1.putExtra("mapk", phanKhuc.getMaPK());
                intent1.putExtra("tenpk", phanKhuc.getTenPK());
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
            }
        });
        rc_PK.setAdapter(adapter_pk);
        rc_PK.setLayoutManager(new GridLayoutManager(getContext(), 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
    }

    public void LoadBestCar() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
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
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                adapter_bestCar.notifyDataSetChanged();
            }
        };

        Response.ErrorListener thatbai = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "LOI", Toast.LENGTH_SHORT).show();
            }
        };

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(SERVER.bestCarPath, thanhcong, thatbai);
        requestQueue.add(jsonArrayRequest);

        adapter_bestCar = new Adapter_BestCar(getContext(), xeArrayList);
        rc_BestCar.setAdapter(adapter_bestCar);
        rc_BestCar.setLayoutManager(new GridLayoutManager(getContext(), 2) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
    }


}