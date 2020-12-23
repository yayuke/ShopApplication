package com.yyk.shopapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yyk.shopapplication.R;
import com.yyk.shopapplication.adapter.ShopAdapter;
import com.yyk.shopapplication.bean.Shop;
import com.yyk.shopapplication.utils.NetUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.security.PublicKey;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private String string;
    private RecyclerView recyclerView;
    private List<Shop> o;
    private TextView textView;
    private ImageView imageView;
    private boolean exit = false;;
    private long exitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initview();
        NetUtils.doGet(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TAG", "onFailure: "+"网络请求失败请检查网络连接" );
//                Toast.makeText(MainActivity.this, "网络请求失败请检查网络连接!!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                获取服务器数据
                string = response.body().string();
                Log.e("TAG", "onResponse: "+ string);
//                将字符串转换为json对象
//                try {
//                    JSONArray jsonArray = new JSONArray(string);
                    Gson gson = new Gson();
//                构建解析的类型对象
                Type type = new TypeToken<List<Shop>>() {
                }.getType();o = gson.fromJson(string, type);
//                在子线程更新UI,可以使用Runonui
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                为rv设置布局管理器
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//                创建适配器
                        ShopAdapter shopAdapter = new ShopAdapter(o, MainActivity.this);
                        recyclerView.setAdapter(shopAdapter);
                    }
                });

//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }
        });
    }

    private void initview() {
        recyclerView = findViewById(R.id.shop_rv);
        textView = findViewById(R.id.tv_title_text);
        imageView = findViewById(R.id.back_img);
        imageView.setVisibility(View.GONE);
        textView.setBackgroundColor(0xff33ccff);
    }
//    添加手机返回按钮的监听
//    方法1
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        1.按下返回键
//        2.按下操作
        if (keyCode==KeyEvent.KEYCODE_BACK&&event.getAction()==KeyEvent.ACTION_DOWN){
//    获取系统时间,毫秒
            long time=System.currentTimeMillis();
            if (time-exitTime>2000){
                Toast.makeText(this, "再次返回退出应用", Toast.LENGTH_SHORT).show();
                exitTime=time;
            }else {
                finish();
                System.exit(0);
            }
//            返回true表示当前的方法已处理了全部的事件
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

//    方法2
//    @Override
//    public void onBackPressed() {
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                exit =false;
//            }
//        },2000);
//        if (exit==true){
//            finish();
//            System.exit(0);
//        }else {
//            exit=true;
//            Toast.makeText(this, "再次返回退出应用", Toast.LENGTH_SHORT).show();
//        }
//    }
}