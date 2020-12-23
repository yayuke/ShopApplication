package com.yyk.shopapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yyk.shopapplication.R;
import com.yyk.shopapplication.bean.Food;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private List<Food> foodList;
    Context context;


    public OrderAdapter(List<Food> foodList, Context context) {
        this.foodList = foodList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.order_item_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.tvName.setText(food.getFoodName());
        holder.tvMoney.setText(String.format("%.2f",food.getPrice()));
        holder.tvNum.setText("×"+food.getCount());
        Glide.with(context).load(food.getFoodPic()).into(holder.tvPic);
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView tvPic;
        private TextView tvName;
        private TextView tvNum;
        private TextView tvMoney;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPic = (ImageView) itemView.findViewById(R.id.tv_pic);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvNum = (TextView) itemView.findViewById(R.id.tv_num);
            tvMoney = (TextView) itemView.findViewById(R.id.tv_money);
        }
    }
}
