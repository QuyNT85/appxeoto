package com.example.otomarket.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.otomarket.R;
import com.example.otomarket.activity.HistoryOrderActivity;


public class MoreFragment extends Fragment {
    View view;
    TextView txtHis;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_more, container, false);

        txtHis = view.findViewById(R.id.orderHis);
        txtHis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HistoryOrderActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}