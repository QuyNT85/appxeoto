package com.example.otomarket.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.otomarket.R;
import com.example.otomarket.server.SERVER;


import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText username_register,email_register,password_resgister,address_register,phone_register;
    Button sigup_register;
     String ten,mail,pass,addr,phone;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username_register=findViewById(R.id.edtUsername_resgister);
        email_register=findViewById(R.id.edtEmail_resgister);
        password_resgister=findViewById(R.id.edtPassword_resgister);
        address_register=findViewById(R.id.edtAdress_resgister);
        phone_register=findViewById(R.id.edtPhone_resgister);
        sigup_register=findViewById(R.id.buttonSignUp_resgister);

        sigup_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ten= username_register.getText().toString();
                mail=email_register.getText().toString();
                pass=password_resgister.getText().toString();
                addr=address_register.getText().toString();
                phone=phone_register.getText().toString();

                if(ten.isEmpty() || mail.isEmpty() || pass.isEmpty()
                    || addr.isEmpty() || phone.isEmpty()){
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getApplicationContext());
                    builder1.setMessage("Ban nhap chua du thong tin.");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert1 = builder1.create();
                    alert1.show();
                }else{
                    adduserdatabse();
                }
            }
        });
    }

    void adduserdatabse(){

        String url= SERVER.addpath;
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.toString().equals("1")){
                    Intent intent= new Intent(getApplicationContext(),LoginActivity.class);
                    intent.putExtra("email_res",email_register.getText().toString());
                    intent.putExtra("pass_res",password_resgister.getText().toString());
                    startActivity(intent);

                }else {
                    Toast.makeText(RegisterActivity.this, "them that bai", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this, "Xay ra loi", Toast.LENGTH_SHORT).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String>params=new HashMap<>();
                params.put("tenKH",username_register.getText().toString());
                params.put("emailKH",email_register.getText().toString());
                params.put("passwordKH",password_resgister.getText().toString());
                params.put("diachiKH",address_register.getText().toString());
                params.put("sodtKH",phone_register.getText().toString());

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
