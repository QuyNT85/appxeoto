package com.example.otomarket.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.otomarket.R;
import com.example.otomarket.fragment.AccountFragment;
import com.example.otomarket.fragment.HomeFragment;
import com.example.otomarket.fragment.ListFragment;
import com.example.otomarket.fragment.MoreFragment;
import com.example.otomarket.fragment.NotifyFragment;
import com.example.otomarket.model.GioHang;
import com.example.otomarket.model.Xe_Fav;
import com.example.otomarket.server.SERVER;
import com.example.otomarket.R;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bot_Nav;
    FragmentTransaction fragmentTransaction;
    private static final int fragment_Home = 1;
    private static final int fragment_List = 2;
    private static final int fragment_Notify = 3;
    private static final int fragment_Account = 4;
    private static final int fragment_More = 5;

    private int currentFragment = fragment_Home;

    public static ArrayList<Xe_Fav> xeFavArrayList = new ArrayList<>();

    public static ArrayList<GioHang> manggiohang;

    public static Boolean isLogin, isCheckfb, isCheckgg;

    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;
    AccessToken accessToken;

    static String fullname_data, password_data, email_data, phone_data;

    public static String email, pass, emailgg, namegg = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();
        setClick_Bottom_Nav();
        email = getDataEmail();
        pass = getDataPassword();

        //Fav
        if (xeFavArrayList == null) {
            xeFavArrayList = new ArrayList<>();
        }

        //Cart
        if (manggiohang != null) {
        } else {
            manggiohang = new ArrayList<>();
        }

        //khi mở app
        openFragment(new HomeFragment());
        bot_Nav.getMenu().findItem(R.id.nav_home).setChecked(true);

        Intent i = getIntent();
        isLogin = i.getBooleanExtra("isLogin", false);
        isCheckfb = i.getBooleanExtra("checkloginfb", false);
        isCheckgg = i.getBooleanExtra("checklogingg", false);

        if (isCheckgg) {
            getdatagooglereturn();
            emailgg = getDataEmailfbgg();
            namegg = getDataNamefbgg();
            Toast.makeText(this, emailgg+ namegg, Toast.LENGTH_SHORT).show();
        }

    }

    public void AnhXa() {
        bot_Nav = findViewById(R.id.bottom_navigation);
    }


    public void setClick_Bottom_Nav() {
        bot_Nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.nav_home:
                        openHomeFragment();
                        bot_Nav.getMenu().findItem(R.id.nav_home).setChecked(true);
                        break;
                    case R.id.nav_list:
                        openListFragment();
                        bot_Nav.getMenu().findItem(R.id.nav_list).setChecked(true);
                        break;
                    case R.id.nav_notify:
                        openNotifyFragment();
                        bot_Nav.getMenu().findItem(R.id.nav_notify).setChecked(true);
                        break;
                    case R.id.nav_account:
                        openAccountFragment();
                        bot_Nav.getMenu().findItem(R.id.nav_account).setChecked(true);
                        break;
                    case R.id.nav_more:
                        openMoreFragment();
                        bot_Nav.getMenu().findItem(R.id.nav_more).setChecked(true);
                        break;
                }
                return true;
            }
        });
    }

    public void openFragment(Fragment frag) {
//        if (frag != null) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_frame, frag);
        fragmentTransaction.addToBackStack(frag.getClass().getSimpleName());
        fragmentTransaction.commit();
//        }
    }

    public void openHomeFragment() {
        if (currentFragment != R.id.nav_home) {
            openFragment(new HomeFragment());
            currentFragment = fragment_Home;
        }
    }

    public void openListFragment() {
        if (currentFragment != R.id.nav_list) {
            openFragment(new ListFragment());
            currentFragment = fragment_List;
        }
    }

    public void openNotifyFragment() {
        if (currentFragment != R.id.nav_notify) {
            openFragment(new NotifyFragment());
            currentFragment = fragment_Notify;
        }
    }

    public void openMoreFragment() {
        if (currentFragment != R.id.nav_more) {
            openFragment(new MoreFragment());
            currentFragment = fragment_More;
        }
    }

    public void openAccountFragment() {
        if (currentFragment != R.id.nav_account) {
            openFragment(new AccountFragment());
            currentFragment = fragment_Account;
        }
    }


    @Override
    public void onBackPressed() {
//        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
//            getSupportFragmentManager().popBackStack();
//        } else {
//            super.onBackPressed();
//        }

        if (bot_Nav.getSelectedItemId() == R.id.nav_home) {
            super.onBackPressed();
            finish();
        } else {
            bot_Nav.setSelectedItemId(R.id.nav_home);
        }
    }

    public Boolean getIsLogin() {
        return getIntent().getBooleanExtra("isLogin", false);
    }

    public String getDataEmail() {
        return getIntent().getStringExtra("key_email");
    }

    public String getDataPassword() {
        return getIntent().getStringExtra("key_password");
    }

    public Boolean getIsLoginFB() {
        return getIntent().getBooleanExtra("checkloginfb", false);
    }

    public Boolean getIsLoginGG() {
        return getIntent().getBooleanExtra("checklogingg", false);
    }

    public String getDataNamefbgg() {
        return fullname_data;
    }

    public String getDataEmailfbgg() {
        return email_data;
    }

    void getdatagooglereturn() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            fullname_data = account.getDisplayName();
            email_data = account.getEmail();
            adduserdatabse();
        }
    }

    void getdatafacebookreturn() {
        accessToken = AccessToken.getCurrentAccessToken();
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        // Application code
                        try {
                            fullname_data = object.getString("name");
                            email_data = object.getString("email");

                            adduserdatabse();
                            System.out.println(fullname_data);
                            System.out.println(email_data);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }

                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,picture.type(large),email");
        request.setParameters(parameters);
        request.executeAsync();
        adduserdatabse();
    }

    void adduserdatabse() {

        String url = SERVER.fbggpath;
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.toString().equals("1")) {
                    Toast.makeText(MainActivity.this, "You login Success", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("tenKH", fullname_data);
                params.put("emailKH", email_data);
                params.put("passwordKH", "");
                params.put("diachiKH", "");
                params.put("sodtKH", "");

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
