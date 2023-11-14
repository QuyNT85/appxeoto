package com.example.otomarket.fragment;

import static com.example.otomarket.server.SERVER.khachhangpath;
import static com.example.otomarket.server.SERVER.khachhangpath;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.otomarket.R;
import com.example.otomarket.activity.LoginActivity;
import com.example.otomarket.activity.MainActivity;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import org.json.JSONArray;
import org.json.JSONObject;


public class AccountFragment extends Fragment {
    View view;
    TextView nameuser;
    TextView emailuser;
    TextView locationuser;
    TextView phoneuser;

    TextView tvMaKh;

    static Boolean checkdangnhap;
    String ten, mail, dc, sdt;
    static String emailnhan, passnhan;
    MainActivity main;
    private GoogleSignInClient gsc;
    private GoogleSignInOptions gso;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account, container, false);

        nameuser = view.findViewById(R.id.textname_profile);
        emailuser = view.findViewById(R.id.textNotedes_profile);
        locationuser = view.findViewById(R.id.textLocationdes_profile);
        phoneuser = view.findViewById(R.id.textPhonedes_profile);

        main = (MainActivity) getActivity();
        if (!main.getIsLogin() && !main.getIsLoginGG()) {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        } else if (main.getIsLogin() && !main.getIsLoginGG()) {
            emailnhan = MainActivity.email;
            passnhan = MainActivity.pass;
            Toast.makeText(getContext(), emailnhan, Toast.LENGTH_SHORT).show();
        } else if (main.getIsLoginGG()) {
            emailuser.setText(MainActivity.emailgg);
            nameuser.setText(MainActivity.namegg);

        }

//        if (!main.getIsLoginGG()) {
//            Intent intent = new Intent(getContext(), LoginActivity.class);
//            startActivity(intent);
//        } else {

//            Toast.makeText(getContext(), (CharSequence) emailuser, Toast.LENGTH_SHORT).show();
//        }


        recivedataLogin();


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ten = nameuser.getText().toString().trim();
        mail = emailuser.getText().toString().trim();
        dc = locationuser.getText().toString().trim();
        sdt = phoneuser.getText().toString().trim();


    }


    ////hàm nhận dữ liệu trả về sau khi login thành công
    void recivedataLogin() {
        ///
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = khachhangpath;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        if (emailnhan.equals(jsonObject.getString("Email"))
                                && passnhan.equals(jsonObject.getString("Password"))) {
                            nameuser.setText(jsonObject.getString("TenKH"));
                            emailuser.setText(emailnhan);
                            phoneuser.setText(jsonObject.getString("SoDT"));
                            locationuser.setText(jsonObject.getString("DiaChi"));
                            tvMaKh.setText(jsonObject.getString("MaKH"));
                            Toast.makeText(getContext(), "Thanh cong", Toast.LENGTH_SHORT).show();
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
}