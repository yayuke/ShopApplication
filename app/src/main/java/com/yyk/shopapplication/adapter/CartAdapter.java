package com.yyk.shopapplication.adapter;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yyk.shopapplication.R;
import com.yyk.shopapplication.activity.ShopDetailActivity;
import com.yyk.shopapplication.bean.Food;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    Context context;
    List<Food> foodList;

    public CartAdapter(Context context, List<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.cart_item_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(inflate);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (foodList!=null){
            final Food food = foodList.get(position);
            holder.tvCartFood.setText(food.getFoodName());
            holder.tvCartNum.setText(String.valueOf(food.getCount()));
            holder.tvCartPrice.setText("￥"+String.format("%.2f",food.getPrice()));
            //        添加商品
            holder.ivAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Message message = ((ShopDetailActivity) context).handler.obtainMessage();
                    message.obj=food;
                    message.what=3;
                    ((ShopDetailActivity)context).handler.sendMessage(message);
                }
            });
//        删除商品
            holder.ivSub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                Message message = ((ShopDetailActivity) context).handler.obtainMessage();
                message.obj=food;
                message.what=4;
                ((ShopDetailActivity)context).handler.sendMessage(message);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
//        如果列表为空返回0否则返回列表长度
        return foodList==null?0:foodList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCartFood;
        private TextView tvCartPrice;
        private ImageView ivAdd;
        private TextView tvCartNum;
        private ImageView ivSub;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCartFood = (TextView) itemView.findViewById(R.id.tv_cart_food);
            tvCartPrice = (TextView) itemView.findViewById(R.id.tv_cart_price);
            ivAdd = (ImageView) itemView.findViewById(R.id.iv_add);
            tvCartNum = (TextView) itemView.findViewById(R.id.tv_cart_num);
            ivSub = (ImageView) itemView.findViewById(R.id.iv_sub);
        }
    }
}
