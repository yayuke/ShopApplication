package com.yyk.shopapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yyk.shopapplication.R;
import com.yyk.shopapplication.bean.Food;

import java.io.Serializable;

public class FoodActivity extends AppCompatActivity {
    private ImageView ivFoodPic;
    private TextView tvFoodName;
    private TextView tvSaleNum;
    private TextView tvPice;
    private Food food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        init();
        Intent intent = getIntent();
        food = (Food) intent.getSerializableExtra("food");
        initData();
    }
    private void init(){
        ivFoodPic = (ImageView) findViewById(R.id.iv_food_pic);
        tvFoodName = (TextView) findViewById(R.id.tv_food_name);
        tvSaleNum = (TextView) findViewById(R.id.tv_sale_num);
        tvPice = (TextView) findViewById(R.id.tv_pice);
    }
    private void initData(){
        tvFoodName.setText(food.getFoodName());
        tvSaleNum.setText("已售"+food.getSaleNum());
        tvPice.setText("￥"+food.getPrice());
        Glide.with(this).load(food.getFoodPic()).into(ivFoodPic);
    }
}