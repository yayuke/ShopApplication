package com.yyk.shopapplication.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yyk.shopapplication.R;
import com.yyk.shopapplication.adapter.CartAdapter;
import com.yyk.shopapplication.adapter.FoodAdapter;
import com.yyk.shopapplication.bean.Food;
import com.yyk.shopapplication.bean.Shop;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShopDetailActivity extends AppCompatActivity {
    private TextView tvTitleText;
    private ImageView backImg;
    private RecyclerView rvFood;
    private TextView tvShopName;
    private TextView tvDeliverTime;
    private TextView tvNotice;
    private CircleImageView ivShopLoge;
    private TextView tvMoney;
    private TextView tvDelivery;
    private TextView tvDeliveryFee;
    private ImageView ivCartPic;
    private Shop shop;
    private RelativeLayout rlCartList;
    private RecyclerView rvCart;
    private TextView tvClear;
    private TextView tvCartNumber;
//    购物车的商品列表(没有商品的购物车对象)
    private List<Food> cartList=new ArrayList<>() ;
/**
 * 添加购物车1
 * 清空购物车2
 * +        3
 * -        4
 * **/
    public Handler handler=new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    Food obj = (Food) msg.obj;
//                    判断是否有商品,如果有增加数量,如果没有就将数量设置为1
                    boolean isIncart=false;
                    for (Food f:cartList){
                        if (f.getFoodName().equals(obj.getFoodName())){
//                            int num=f.getCount();
//                            num++;
                            f.setCount(f.getCount()+1);
                            isIncart=true;
                            break;
                        }
                    }
                    if (!isIncart){
                        obj.setCount(1);
                        cartList.add(obj);
                    }
                    break;
                case 2:
                    break;
                case 3:
                    Food obj3 = (Food) msg.obj;
                    for (Food f:cartList){
                        if (f.getFoodName().equals(obj3.getFoodName())){
                            f.setCount(f.getCount()+1);
                        }
                    }
                    break;
                case 4:
                    Food obj4 = (Food) msg.obj;
                    for (Food f:cartList){
                        if (f.getFoodName().equals(obj4.getFoodName())){
                            if (f.getCount()>1){
                                int num4=f.getCount()-1;
                                f.setCount(num4);
                            }else {
                                cartList.remove(f);
                                break;
                            }
                        }
                    }
                    break;

            }
            updateCart();
            super.handleMessage(msg);
        }
    };
    private CartAdapter cartAdapter;

    //    更新购物车显示的业务逻辑
    public void updateCart(){
//        购物车为空
        /**
         * 1.显示空购物车图片
         * 2.显示角标
         * 3.显示未加入购物车
         * 4.不显示商品总价
         * 5.显示还差多少起送
         * 6.隐藏购物车列表
         * */
        if (cartList.size()==0){
//            显示空购物车图标
            ivCartPic.setImageResource(R.drawable.shop_car_empty);
//            不显示角标
            tvCartNumber.setVisibility(View.GONE);
//            显示未加入购物车文字
            tvMoney.setText("未加入购物车");
//            不显示另需配送费
            tvDelivery.setVisibility(View.GONE);
//            还差多钱起送
            tvDeliveryFee.setText("还差"+shop.getOfferPrice()+"起送");
            tvDeliveryFee.setBackgroundColor(0xff454547);
//            隐藏购物车列表
            rlCartList.setVisibility(View.GONE);
        }else {//购物车有商品
            /**
             * 1.显示有色购物车图片
             * 2.显示商品总数
             * 3.显示总价
             * 4.判断是否够起送金额,够显示去结算,不够显示还差xxx起送
             * */
//            显示有色购物车图标
//            显示列表
//            rlCartList.setVisibility(View.VISIBLE);
            ivCartPic.setImageResource(R.drawable.shop_car);
//            设置角标
            tvCartNumber.setText(calAllNum()+"");
            tvCartNumber.setVisibility(View.VISIBLE);
//            设置金额
            tvMoney.setText(String.format("%.2f",calAllMoney())+"￥");
//            显示另需配送费xx
            tvDelivery.setVisibility(View.VISIBLE);
            tvDelivery.setText("另需配送费￥"+shop.getDistributionCost());
//            判断购物车金额是否超过起送金额
            if (calAllMoney()>shop.getOfferPrice()){
                tvDeliveryFee.setBackgroundColor(Color.RED);
                tvDeliveryFee.setText("去结算");
            }else {
                tvDeliveryFee.setBackgroundColor(0xff454547);
                float dis=shop.getOfferPrice()-calAllMoney();
                tvDeliveryFee.setText("还差"+String.format("%.2f",dis)+"￥起送");
            }
        }
        cartAdapter.notifyDataSetChanged();
    }

//    计算总数
    private int calAllNum(){
        int sum=0;
        for (Food f:cartList){
            sum+=f.getCount();
        }
        return sum;
    }
//    计算总价
    private float calAllMoney(){
        float sumMoney=0;
        for (Food f:cartList){
            sumMoney+=f.getCount()*f.getPrice();
        }
        return sumMoney;
    }
//    public Handler handler=new Handler(new Handler.Callback() {
//        @Override
//        public boolean handleMessage(@NonNull Message msg) {
//            switch (msg.what){
//                case 1:
//                    Toast.makeText(ShopDetailActivity.this, "cnm", Toast.LENGTH_SHORT).show();
//                    break;
//                case 2:
//                    break;
//                case 3:
//                    break;
//                case 4:
//                    break;
//            }
//            return false;
//        }
//    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        Intent intent = getIntent();
        //获取意图中传递的对象
        shop = (Shop) intent.getSerializableExtra("shop");
        initView();
        initData();
    }

    private void initView() {
        tvTitleText = (TextView) findViewById(R.id.tv_title_text);
        backImg = (ImageView) findViewById(R.id.back_img);
        rvFood = (RecyclerView) findViewById(R.id.rv_food);
        tvShopName = (TextView) findViewById(R.id.tv_shop_name);
        tvDeliverTime = (TextView) findViewById(R.id.tv_deliver_time);
        tvNotice = (TextView) findViewById(R.id.tv_notice);
        ivShopLoge = (CircleImageView) findViewById(R.id.iv_shop_loge);
        tvMoney = (TextView) findViewById(R.id.tv_money);
        tvDelivery = (TextView) findViewById(R.id.tv_delivery);
        tvDeliveryFee = (TextView) findViewById(R.id.tv_delivery_fee);
        ivCartPic = (ImageView) findViewById(R.id.iv_cart_pic);
        //购物车列表局部布局控件
        rlCartList = (RelativeLayout) findViewById(R.id.rl_cart_list);
        rvCart = (RecyclerView) findViewById(R.id.rv_cart);
        tvClear = (TextView) findViewById(R.id.tv_clear);
        //购物车脚标
        tvCartNumber = (TextView) findViewById(R.id.tv_cart_number);

        //为控件添加初始的内容
        tvTitleText.setText(shop.getShopName());
        tvShopName.setText(shop.getShopName());
        tvDeliverTime.setText(shop.getTime());
        tvNotice.setText(shop.getShopNotice());
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Glide.with(this).load(shop.getShopPic()).into(ivShopLoge);
//        隐藏购物车列表
        rlCartList.setVisibility(View.GONE);
//        隐藏购物车角标
        tvCartNumber.setVisibility(View.GONE);
        ivCartPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calAllNum()>0){
                    if (rlCartList.getVisibility()==View.GONE){
                        rlCartList.setVisibility(View.VISIBLE);
                    }else {
                        rlCartList.setVisibility(View.GONE);
                    }
                }

    }
});
//        清空购物车
        tvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(ShopDetailActivity.this)
                .setTitle("确认清空购物车")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                cartList.clear();
                                updateCart();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                 dialog = builder.create();
                 dialog.show();
            }
        });
//        结算
        tvDeliveryFee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calAllMoney()>0){
                    Intent intent = new Intent(ShopDetailActivity.this, OrderActivity.class);
                    intent.putExtra("cartList", (Serializable) cartList);
                    intent.putExtra("shop", shop);
                    startActivity(intent);
            }
            }
        });
//        点击任意地方收回列表
        rlCartList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (rlCartList.getVisibility()==View.VISIBLE){
                    rlCartList.setVisibility(View.GONE);
                }
                return false;
            }
        });
    }

    //    对列表添加数据
    private void initData() {
//        显示店铺商品列表
        rvFood.setLayoutManager(new LinearLayoutManager(this));
        FoodAdapter foodAdapter = new FoodAdapter(shop.getFoodList(), this);
        rvFood.setAdapter(foodAdapter);
//        显示购物车商品列表
        rvCart.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter = new CartAdapter(this, cartList);
        rvCart.setAdapter(cartAdapter);

    }

}