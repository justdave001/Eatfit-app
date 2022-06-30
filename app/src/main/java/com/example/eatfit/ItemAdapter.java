package com.example.eatfit;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>  {

    private List<Restaurant> restaurantList;

    public ItemAdapter(List<Restaurant> restaurantList) {
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
        holder.tvCalories.setText(Integer.toString(restaurantList.get(position).getCalories()));
        holder.tvFat.setText(Integer.toString(restaurantList.get(position).getFat()));
        holder.tvProtein.setText(Integer.toString(restaurantList.get(position).getProtein()));
        holder.tvCost.setText(Integer.toString(restaurantList.get(position).getCost()));
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvCalories = itemView.findViewById(R.id.tvCalories);
            tvProtein = itemView.findViewById(R.id.tvProtein);
            tvFat = itemView.findViewById(R.id.tvFat);
            tvCost = itemView.findViewById(R.id.tvCost);
        }

    }
}
