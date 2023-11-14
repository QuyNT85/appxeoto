package com.example.otomarket.activity;

import static com.example.otomarket.adapter.Adapter_Cart.TongTien;
import static com.example.otomarket.server.SERVER.hoadonpath;
import static com.example.otomarket.server.SERVER.khachhangpath;
import static com.example.otomarket.server.SERVER.khachhangpath;
import static com.facebook.FacebookSdk.getApplicationContext;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.otomarket.R;
import com.example.otomarket.fragment.AccountFragment;
import com.example.otomarket.fragment.NotifyFragment;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class OrderActivity extends AppCompatActivity {
    private static final int NOTIFICATION_ID = 1;
    Toolbar toolbar;
    TextView tenKh, emailKH;
    TextInputEditText sdtKH, dcKH;
    TextView tvTongTien;
    Button btnOrder;
    public static String emailnhan, passnhan, maKH;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        AnhXa();
        setToolbar();
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvTongTien.setText(decimalFormat.format(TongTien()) + " $");

        emailnhan = MainActivity.email;
        passnhan = MainActivity.pass;

        recivedataLogin();
        SuKienXacNhanThanhToan();


    }

    private void SuKienXacNhanThanhToan() {
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), thanh_toan_thanh_cong.class);
                startActivity(intent);
                Pushnotification();
            }
        });
    }


    private void AnhXa() {
        toolbar = findViewById(R.id.toolbarOrder);
        tenKh = findViewById(R.id.tenkh_dathang);
        sdtKH = findViewById(R.id.sdt_dathang);
        dcKH = findViewById(R.id.diachi_dathang);
        emailKH = findViewById(R.id.email_dathang);
        btnOrder = findViewById(R.id.button_xac_nhan_thanh_toan_san_pham);
        tvTongTien = findViewById(R.id.tvTongtien);

    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Order");
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void recivedataLogin() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = khachhangpath;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        if (emailnhan.equals(jsonObject.getString("Email")) && passnhan.equals(jsonObject.getString("Password"))) {
                            tenKh.setText(jsonObject.getString("TenKH"));
                            emailKH.setText(emailnhan);
                            maKH = jsonObject.getString("MaKH");
                            sdtKH.setText(jsonObject.getString("SoDT"));
                            dcKH.setText(jsonObject.getString("DiaChi"));
                            postDataUsingVolley(sdtKH.getText().toString(), dcKH.getText().toString());

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

    private void postDataUsingVolley(String sdt, String diachi) {
        String url = hoadonpath;
        RequestQueue queue = Volley.newRequestQueue(this);


        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "Response: " + response);

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(getApplicationContext(), "Fail" + error, Toast.LENGTH_SHORT).show();
                Log.e("TAG", "Error: " + error.getMessage());

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
// storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();
                params.put("makh",maKH);
                params.put("sdt", sdt);
                params.put("diachi", diachi);
                params.put("tongtien", String.valueOf(TongTien()));
                params.put("ngaygio", getCurrentDateTime());
//                params.put("ngaygio", getCurrentDateTime());
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);

        Toast.makeText(this, "Run", Toast.LENGTH_SHORT).show();
    }

    public static String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        return dateFormat.format(currentDate);
    }

    void Pushnotification() {
        Context context = getApplicationContext();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.suppercar);

        Notification notification = new NotificationCompat.Builder(context, ChannelID_Notifycation.CHANNEL_ID)
                .setContentTitle("Thông Báo")
                .setContentText("Bạn đã mua hàng thành công")
                .setSmallIcon(R.drawable.notification)
                .setLargeIcon(bitmap)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (notificationManager != null) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            notificationManager.notify(getNotificationID(), notification);
        }
    }
    private int getNotificationID(){
        return (int) new Date().getTime();
    }
}