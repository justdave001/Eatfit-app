package com.example.eatfit;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class PlaceOrderAdapter extends RecyclerView.Adapter<PlaceOrderAdapter.MyViewHolder> {
    List<Restaurant> restaurantList;


    public PlaceOrderAdapter(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }

    public void updateData(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_order_items, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.menuName.setText(restaurantList.get(position).getName());
        holder.menuPrice.setText("Price: $"+String.format("%.2f", restaurantList.get(position).getCost()*restaurantList.get(position).getTotalInCart()));
        holder.menuQty.setText("Qty: " + restaurantList.get(position).getTotalInCart());
        Glide.with(holder.thumbImage)
                .load(restaurantList.get(position).getImg())
                .into(holder.thumbImage);
    }

    @Override
    public int getItemCount() {
        Log.e("size", restaurantList.toString());
        return restaurantList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView menuName;
        TextView  menuPrice;
        TextView  menuQty;
        ImageView thumbImage;

        public MyViewHolder(View view) {
            super(view);
            menuName = view.findViewById(R.id.menuName);
            menuPrice = view.findViewById(R.id.menuPrice);
            menuQty = view.findViewById(R.id.menuQty);
            thumbImage = view.findViewById(R.id.thumbImage);
        }
    }
}
