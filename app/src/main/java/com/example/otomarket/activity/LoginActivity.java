package com.example.otomarket.activity;



import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    static EditText email_login;
    static EditText password_login;
    CheckBox checkRememberLogin;
    Boolean checklogingg=false,getCheckloginfb=false,checkloginsuccess;
    Button google_login,facebook_login,login_login,register_login;

    ////google
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;


    ////Remember
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ///facebook
    CallbackManager callbackManager;
    AccessTokenTracker accessTokenTracker;
    AccessToken accessToken;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email_login=findViewById(R.id.edtEmail);
        password_login=findViewById(R.id.edtPassword);
        checkRememberLogin=findViewById(R.id.checked);
        google_login=findViewById(R.id.buttonGoogle);
        facebook_login=findViewById(R.id.buttonFacebook);
        login_login=findViewById(R.id.buttonLogin);
        register_login=findViewById(R.id.buttonRegister);
        ///////////////

        /////////////// lấy gmail của google
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account= GoogleSignIn.getLastSignedInAccount(this);
        if (account!=null){
            goToHome();
        }
        /////

        callbackManager = CallbackManager.Factory.create();
        accessToken=AccessToken.getCurrentAccessToken();
        if (accessToken!=null && accessToken.isExpired()){
            goToHome();
        }
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                goToHome();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(@NonNull FacebookException e) {

            }
        });

        //////////////
        Intent intent=getIntent();
        email_login.setText(intent.getStringExtra("email_res"));
        password_login.setText(intent.getStringExtra("pass_res"));
        /////////////////
        login_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                String url= SERVER.loginpath;
                StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.toString().equals("1")){

                            if (!email_login.getText().toString().isEmpty()&&!password_login.getText().toString().isEmpty()){
                                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("isLogin",true);
                                intent.putExtra("key_email", email_login.getText().toString());
                                intent.putExtra("key_password", password_login.getText().toString());
                                /////////////////
                                RememberMe();
                                startActivity(intent);
                                finish();
                            }
                            Toast.makeText(getApplicationContext(), "thanh cong", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(), "That bai", Toast.LENGTH_SHORT).show();
                        }
                    }
                    //////

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
                {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String>params=new HashMap<>();
                        params.put("email",email_login.getText().toString().trim());
                        params.put("password",password_login.getText().toString().trim());
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });

        register_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
////////////////////////google
        google_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSigIn();
                checklogingg=true;
            }
        });
        facebook_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile"));
                Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("isLogin",true);
                intent.putExtra("checkloginfb",true);
                startActivity(intent);
                getCheckloginfb=true;
            }
        });
    }
    void RememberMe(){
        editor=sharedPreferences.edit();
        if (checkRememberLogin.isChecked()){
            editor.putString("email_rem",email_login.getText().toString());
            editor.putString("password_rem",password_login.getText().toString());
            editor.putBoolean("remember_me",checkRememberLogin.isChecked());
            editor.apply();
        }else {
            sharedPreferences.edit().clear().apply();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferences=getSharedPreferences("clicklogin",MODE_PRIVATE);
        if (sharedPreferences.getBoolean("remember_me",false)){
            email_login.setText(sharedPreferences.getString("email_rem"," "));
            password_login.setText(sharedPreferences.getString("password_rem"," "));
            checkRememberLogin.setChecked(true);
        }
    }

    private void goToHome(){
        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("isLogin",true);
        intent.putExtra("checklogingg",true);
        startActivity(intent);
    }
    private void goToSigIn(){
        Intent sigInInten = gsc.getSignInIntent();
        startActivityForResult(sigInInten,1000);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (checklogingg==true){
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode==1000){
                Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
                try {
                    task.getResult();
                    goToHome();
                }catch (Exception e){
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        } else if (getCheckloginfb==true) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
    ///////////////////////////////////////// end all function//////////////////////////////////////
}