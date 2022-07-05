package com.example.eatfit;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>  {

    private List<Restaurant> restaurantList;
    Context context;

    public ItemAdapter(List<Restaurant> restaurantList, Context context) {
        this.context = context;
        this.restaurantList = restaurantList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(restaurantList.get(position).getName());
        holder.tvDescription.setText(restaurantList.get(position).getDescription());
        holder.tvCalories.setText(Integer.toString(restaurantList.get(position).getCalories())+"cal");
        holder.tvFat.setText(Integer.toString(restaurantList.get(position).getFat())+"g");
        holder.tvProtein.setText(Integer.toString(restaurantList.get(position).getProtein())+"g");
        holder.tvCost.setText("$"+Double.toString(restaurantList.get(position).getCost()));
        Glide.with(context).load(restaurantList.get(position).getImg()).into(holder.food_img);

    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvDescription;
        TextView tvCost;
        TextView tvFat;
        TextView tvProtein;
        TextView tvCalories;
        ImageView food_img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            food_img = itemView.findViewById(R.id.food_img);
            tvName = itemView.findViewById(R.id.tvName);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvCalories = itemView.findViewById(R.id.tvCalories);
            tvProtein = itemView.findViewById(R.id.tvProtein);
            tvFat = itemView.findViewById(R.id.tvFat);
            tvCost = itemView.findViewById(R.id.tvCost);
        }

    }
}
