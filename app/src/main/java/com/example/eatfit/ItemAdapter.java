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

    private List<Restaurant> restaurantList;
    Context context;
    String restaurant_name;
    public RecyclerViewClickListener listener;
    List restt = new ArrayList<>();
    int pos;
    public ItemAdapter(List<Restaurant> restaurantList, Context context,String restaurant_name,RecyclerViewClickListener listener,
                       List restt) {
        this.context = context;
        this.restaurantList = restaurantList;
        this.restaurant_name = restaurant_name;
        this.listener=listener;
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
        TextView name;
        ImageView addRes;
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

                                            rname.addUnique("menu_items", restt.get(pos));
                                            rname.saveInBackground();
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
