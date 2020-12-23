package com.yyk.shopapplication.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.yyk.shopapplication.activity.ShopDetailActivity;
import com.yyk.shopapplication.bean.Shop;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {
//    为适配器添加所绑定的数据集合
    List<Shop> shopList;
    Context context;

    public ShopAdapter(List<Shop> shopList, Context context) {
        this.shopList = shopList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View inflate = LayoutInflater.from(context).inflate(R.layout.shop_item, parent, false);
        final ViewHolder viewHolder=new ViewHolder(inflate);
        inflate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shop shop = shopList.get(viewHolder.getAdapterPosition());
//                Toast.makeText(context, shopList.get(viewHolder.getAdapterPosition()).getShopName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ShopDetailActivity.class);
                intent.putExtra("shop",shop);
                context.startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        根据position获取shop对象
        Shop shop = shopList.get(position);
        holder.tvShopName.setText(shop.getShopName());
        holder.tvShopSell.setText("月销售:"+shop.getSaleNum());
        holder.tvDelivery.setText("起送:"+shop.getOfferPrice()+"￥"+"配送:"+shop.getDistributionCost()+"￥");
        holder.tvFlTxt.setText(shop.getWelfare());
        holder.tvDeliverTime.setText(shop.getTime());
        //glide插件实现图片加载
        Glide.with(context).load(shop.getShopPic()).into(holder.ivShopLoge);
    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivShopLoge;
        private TextView tvShopName;
        private TextView tvShopSell;
        private TextView tvDelivery;
        private TextView tvFuli;
        private TextView tvFlTxt;
        private TextView tvDeliverTime;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivShopLoge = (ImageView)itemView.findViewById(R.id.iv_shop_loge);
            tvShopName = (TextView) itemView.findViewById(R.id.tv_shop_name);
            tvShopSell = (TextView) itemView.findViewById(R.id.tv_shop_sell);
            tvDelivery = (TextView) itemView.findViewById(R.id.tv_delivery);
            tvFuli = (TextView) itemView.findViewById(R.id.tv_fuli);
            tvFlTxt = (TextView) itemView.findViewById(R.id.tv_fl_txt);
            tvDeliverTime = (TextView) itemView.findViewById(R.id.tv_deliver_time);
        }
    }
}
