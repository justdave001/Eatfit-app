package com.example.eatfit.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.bumptech.glide.Glide;
import com.example.eatfit.R;
import com.example.eatfit.Models.Restaurant;
import com.google.android.gms.common.util.ArrayUtils;
import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>  {

     List<Restaurant> restaurantList;
    Context context;
    String restaurant_name;
    public RecyclerViewClickListener listener;
    List restt = new ArrayList<>();
    int pos;
    ImageView buttonCheckout;

    MenuListClickListener clickListener;


    public ItemAdapter(List<Restaurant> restaurantList, Context context, String restaurant_name, RecyclerViewClickListener listener,
                       List restt, MenuListClickListener clickListener) {
        this.context = context;
        this.restaurantList = restaurantList;
        this.restaurant_name = restaurant_name;
        this.restt=restt;
        this.clickListener=clickListener;


    }


    public interface RecyclerViewClickListener {
         void onClick(View v, int position);
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
        holder.addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("reslist", Double.toString(restaurantList.get(position).getCost()));
                Restaurant restaurant  = restaurantList.get(position);
                restaurant.setTotalInCart(1);
                clickListener.onAddToCartClick(restaurant);
                holder.addToCartButton.setVisibility(View.GONE);
                holder.tvCount.setText(restaurant.getTotalInCart()+"");
            }
        });

        holder.imageMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Restaurant restaurant = restaurantList.get(position);
                int total = restaurantList.get(holder.getAdapterPosition()).getTotalInCart();
                total--;
                if(total > 0 ) {
                    restaurant.setTotalInCart(total);
                    clickListener.onUpdateCartClick(restaurant);
                    holder.tvCount.setText(total +"");
                } else {
                    holder.addToCartButton.setVisibility(View.VISIBLE);
                    restaurant.setTotalInCart(total);
                    clickListener.onRemoveFromCartClick(restaurant);
                }
            }
        });

        holder.imageAddOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Restaurant restaurant  = restaurantList.get(position);
                Log.d("menuu",restaurantList.get(position).toString());
                int total = restaurant.getTotalInCart();
                total++;
                if(total <= 100 && total >0) {
                    restaurant.setTotalInCart(total);
                    clickListener.onUpdateCartClick(restaurant);
                    holder.tvCount.setText(total +"");
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }



    public  class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvDescription;
        TextView tvCost;
        TextView tvFat;
        TextView tvProtein;
        TextView tvCalories;
        ImageView food_img;
        TextView name;
        ImageView imageMinus;
        ImageView imageAddOne;
        TextView  tvCount;
        AppCompatButton addToCartButton;
        ImageView buttonCheckout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            food_img = itemView.findViewById(R.id.food_img);
            tvName = itemView.findViewById(R.id.tvName);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvCalories = itemView.findViewById(R.id.tvCalories);
            tvProtein = itemView.findViewById(R.id.tvProtein);
            tvFat = itemView.findViewById(R.id.tvFat);
            tvCost = itemView.findViewById(R.id.tvCost);
            name = itemView.findViewById(R.id.name);
            imageMinus = itemView.findViewById(R.id.imageMinus);
            imageAddOne = itemView.findViewById(R.id.imageAddOne);
            tvCount = itemView.findViewById(R.id.tvCount);
            addToCartButton = itemView.findViewById(R.id.addToCartButton);
            buttonCheckout = itemView.findViewById(R.id.buttonCheckout);

        }


    }
    public interface MenuListClickListener {
        public void onAddToCartClick(Restaurant restaurant);
        public void onUpdateCartClick(Restaurant restaurant);
        public void onRemoveFromCartClick(Restaurant restaurant);
    }
}
