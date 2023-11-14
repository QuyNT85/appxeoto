package com.example.otomarket.fragment;

import static android.content.Context.NOTIFICATION_SERVICE;

import static com.facebook.FacebookSdk.getApplicationContext;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.otomarket.R;
import com.example.otomarket.activity.ChannelID_Notifycation;

import java.util.Date;


public class NotifyFragment extends Fragment {
    View mViewNF;
    Toolbar mToolbarNF;
    TextView mTitle;


    AppCompatActivity appCompatActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewNF = inflater.inflate(R.layout.fragment_notify, container, false);

        mTitle = mViewNF.findViewById(R.id.toolbar_title);

        mToolbarNF = mViewNF.findViewById(R.id.toolbarNF);
        appCompatActivity = (AppCompatActivity) getActivity();
        appCompatActivity.setSupportActionBar(mToolbarNF);
        appCompatActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);

        return mViewNF;
    }

}