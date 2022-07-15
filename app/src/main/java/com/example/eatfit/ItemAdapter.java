package com.example.eatfit;

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

import com.bumptech.glide.Glide;
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
    List<Restaurant> menuItems = new ArrayList<>();




    public ItemAdapter(List<Restaurant> restaurantList, Context context,String restaurant_name,RecyclerViewClickListener listener,
                       List restt) {
        this.context = context;
        this.restaurantList = restaurantList;
        this.restaurant_name = restaurant_name;
        this.restt=restt;


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
                Restaurant restaurant  = restaurantList.get(pos);
//                String restaurant_name =  restaurant.getName();
//                Double menu_price = restaurant.getCost();
                menuItems.add(restaurant);
                if (!restaurant.toString().isEmpty()){
                    Log.d("reslist", "notempty");
                }
                restaurant.setTotalInCart(1);
//                clickListener.onAddToCartClick(restaurant.getName());
                holder.addToCartButton.setVisibility(View.GONE);
                holder.tvCount.setText(restaurant.getTotalInCart()+"");
                String ItemName = restaurantList.get(position).getName();
                String price = Double.toString(restaurantList.get(position).getCost());
                Log.d("cost", price.toString());
                String qty = holder.tvName.getText().toString();
                String image = restaurantList.get(position).getImg();
                Intent intent = new Intent("custom-message");
                //            intent.putExtra("quantity",Integer.parseInt(quantity.getText().toString()));
                intent.putExtra("quantity",qty);
                intent.putExtra("image",image);
                intent.putExtra("item",ItemName);
                intent.putExtra("price",price);

                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        });

        holder.imageMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Restaurant restaurant = restaurantList.get(pos);
                int total = restaurantList.get(holder.getAdapterPosition()).getTotalInCart();
                total--;
                if(total > 0 ) {
                    restaurantList.get(position).setTotalInCart(total);

                    holder.tvCount.setText(total +"");
                } else {
                    holder.addToCartButton.setVisibility(View.VISIBLE);
                    restaurantList.get(holder.getAdapterPosition()).setTotalInCart(total);
//                    clickListener.onRemoveFromCartClick(restaurant);
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
                if(total <= 100) {
                    restaurant.setTotalInCart(total);
//                    clickListener.onUpdateCartClick(restaurant);
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
        ImageView addRes;
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
            addRes = itemView.findViewById(R.id.addRes);
            imageMinus = itemView.findViewById(R.id.imageMinus);
            imageAddOne = itemView.findViewById(R.id.imageAddOne);
            tvCount = itemView.findViewById(R.id.tvCount);
            addToCartButton = itemView.findViewById(R.id.addToCartButton);
            buttonCheckout = itemView.findViewById(R.id.buttonCheckout);


            addRes.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            pos = getAdapterPosition();
                            ParseUser currentUser = ParseUser.getCurrentUser();
                            ParseObject rname = new ParseObject("Restaurants");
                            rname.put("user", currentUser);
                            rname.put("res_name", restaurant_name);
                            rname.addUnique("menu_items", restt.get(pos));
                            rname.saveInBackground();

                            ParseQuery<ParseObject> query = ParseQuery.getQuery("Restaurants");
                            query.whereEqualTo("user", currentUser);
                            query.whereEqualTo("res_name", restaurant_name);


                            query.findInBackground(new FindCallback<ParseObject>() {
                                @Override
                                public void done(List<ParseObject> objects, ParseException e) {
                                    if (e==null){
                                        for (ParseObject rname:objects){
                                            if (rname.getString("res_name").equals(restaurant_name)
                                         ) {
                                                rname.addUnique("menu_items", restt.get(pos));
                                                rname.saveInBackground();
                                            }
                                        }

                                    }

                                }

                            });

                        }
                    }
            );
        }


    }

}
