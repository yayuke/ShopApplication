package com.yyk.shopapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yyk.shopapplication.R;
import com.yyk.shopapplication.adapter.CartAdapter;
import com.yyk.shopapplication.adapter.OrderAdapter;
import com.yyk.shopapplication.bean.Food;
import com.yyk.shopapplication.bean.Shop;

import java.io.Serializable;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    private List<Food> orderlist;
    private Shop shop;
    private TextView tvTitleText;
    private ImageView backImg;
    private android.widget.LinearLayout LinearLayout;
    private EditText editText;
    private RecyclerView recyclerView;
    private TextView tvMoney;
    private TextView tvSendMoney;
    private TextView textView;
    private TextView tvAllMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Intent intent = getIntent();
        orderlist = (List<Food>) intent.getSerializableExtra("cartList");
        shop = (Shop) intent.getSerializableExtra("shop");
        Log.e("TAG", "onCreate: "+shop.getDistributionCost());
        initView();
    }

    private void initView() {
        tvTitleText = (TextView) findViewById(R.id.tv_title_text);
        backImg = (ImageView) findViewById(R.id.back_img);
        LinearLayout = (LinearLayout) findViewById(R.id.LinearLayout);
        editText = (EditText) findViewById(R.id.editText);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        tvMoney = (TextView) findViewById(R.id.tv_money);
        tvSendMoney = (TextView) findViewById(R.id.tv_send_money);
        textView = (TextView) findViewById(R.id.textView);
        tvAllMoney = (TextView) findViewById(R.id.tv_all_money);

        tvTitleText.setText("订单信息");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity.this);
                builder.setView(R.layout.dialog_layout);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        float sumMoney=0;
        for (int i=0;i<orderlist.size();i++){
            float price = orderlist.get(i).getPrice();
            sumMoney+=price*orderlist.get(i).getCount();
        }
        tvTitleText.setBackgroundColor(0xff555555);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvMoney.setText(String.format("%.2f",sumMoney));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        OrderAdapter orderAdapter = new OrderAdapter( orderlist,this);
        recyclerView.setAdapter(orderAdapter);
        tvSendMoney.setText("配送费: "+shop.getDistributionCost());
        tvAllMoney.setText(String.format("%.2f",sumMoney+shop.getDistributionCost()));
    }
}