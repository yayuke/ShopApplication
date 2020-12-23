package com.yyk.shopapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yyk.shopapplication.R;
import com.yyk.shopapplication.activity.FoodActivity;
import com.yyk.shopapplication.activity.ShopDetailActivity;
import com.yyk.shopapplication.bean.Food;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    List<Food>foodList;
    Context context;
//    添加构造方法
    public FoodAdapter(List<Food> foodList, Context context) {
        this.foodList = foodList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.foot_item_layout, parent, false);
        final ViewHolder viewHolder = new ViewHolder(inflate);
//        点击图片
        viewHolder.ivFoodPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Food food = foodList.get(viewHolder.getAdapterPosition());
                Intent intent = new Intent(context, FoodActivity.class);
                intent.putExtra("food",food);
                context.startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Food food = foodList.get(position);
        holder.tvFoodName.setText(food.getFoodName());
        holder.tvFoodDesc.setText(food.getTaste());
        holder.tvSaleNum.setText("月售:"+food.getSaleNum());
        holder.tvFoodPrice.setText("￥"+String.format("%.2f",food.getPrice()));
        //加载图片
        Glide.with(context).load(food.getFoodPic()).into(holder.ivFoodPic);
//        添加商品
        holder.tvAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = ((ShopDetailActivity) context).handler.obtainMessage();
                message.obj=food;
                message.what=1;
                ((ShopDetailActivity)context).handler.sendMessage(message);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivFoodPic;
        private TextView tvFoodName;
        private TextView tvFoodDesc;
        private TextView tvSaleNum;
        private TextView tvFoodPrice;
        private TextView tvAddCart;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFoodPic = (ImageView) itemView.findViewById(R.id.iv_food_pic);
            tvFoodName = (TextView) itemView.findViewById(R.id.tv_food_name);
            tvFoodDesc = (TextView) itemView.findViewById(R.id.tv_food_desc);
            tvSaleNum = (TextView) itemView.findViewById(R.id.tv_sale_num);
            tvFoodPrice = (TextView) itemView.findViewById(R.id.tv_food_price);
            tvAddCart = (TextView) itemView.findViewById(R.id.tv_add_cart);
        }
    }
}
