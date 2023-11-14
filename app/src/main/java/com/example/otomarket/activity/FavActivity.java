package com.example.otomarket.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.otomarket.R;
import com.example.otomarket.adapter.Adaper_XeFav;


public class FavActivity extends AppCompatActivity {
    Toolbar mToolbar;
    RecyclerView rcFav;
    Adaper_XeFav adaper_xeFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);

        rcFav = findViewById(R.id.rc_fav);
        mToolbar = findViewById(R.id.toolbarFav);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Favorites");

        rcFav.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rcFav.setLayoutManager(layoutManager);
        adaper_xeFav = new Adaper_XeFav(getApplicationContext(), MainActivity.xeFavArrayList);
        rcFav.setAdapter(adaper_xeFav);


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
}