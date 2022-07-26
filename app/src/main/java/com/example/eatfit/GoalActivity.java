
package com.example.eatfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class GoalActivity extends AppCompatActivity implements ItemAdapter.MenuListClickListener{

    RecyclerView rvItems;
    LinearLayoutManager layoutManager;
    ItemAdapter.MenuListClickListener clickListener;
    ItemAdapter itemAdapter;
    public String BASE_URL = "https://apimocha.com/eatfit/example";
    List<Restaurant> restaurantList;
    List restt = new ArrayList<>();
    Context context;
    ItemAdapter.RecyclerViewClickListener listener;
    List<Restaurant> itemsInCartList;
    ImageView buttonCheckout;
    int totalItemInCart = 0;
    List<Restaurant> menuList;
    Restaurant restaurant;
    String restaurant_name;
    ShimmerFrameLayout shimmerFrameLayout;
    int temp=0;
    List prices = new ArrayList<>();
    final int min_calories = 500;
    final int min_fat = 58;
    final int min_protein = 30;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);
        context = getApplicationContext();
        rvItems = findViewById(R.id.rvItems);
        shimmerFrameLayout = findViewById(R.id.shimmerLay);
        buttonCheckout = findViewById(R.id.buttonCheckout);
        restaurantList =  new ArrayList<>();


        extractRestaurants();
        rvItems.setVisibility(View.INVISIBLE);
        shimmerFrameLayout.startShimmerAnimation();
        Handler handler = new Handler();
        handler.postDelayed(()->{
            rvItems.setVisibility(View.VISIBLE);
            shimmerFrameLayout.stopShimmerAnimation();
            shimmerFrameLayout.setVisibility(View.INVISIBLE);

        }, 3000);
        String resname = getIntent().getStringExtra("restaurant_name");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(resname);
        actionBar.setDisplayHomeAsUpEnabled(true);
        Restaurant restaurant = new Restaurant();
        buttonCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemsInCartList != null && itemsInCartList.size() <= 0) {
                    Toast.makeText(GoalActivity.this, "Please add some items in cart.", Toast.LENGTH_SHORT).show();
                    return;
                }
                restaurant.setMenus(itemsInCartList);
                Intent i = new Intent(GoalActivity.this, PlaceOrderActivity.class);
                i.putExtra("resname", resname);
                i.putExtra("data", restaurant);
                startActivity(i);

            }
        });


    }



    private void extractRestaurants() {
        RequestQueue queue = Volley.newRequestQueue(this);
        Request request = new StringRequest(Request.Method.GET, BASE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray rest = jsonObject.getJSONArray("restaurants");

                    for (int i = 0; i < rest.length(); i++) {
                        JSONObject jsonObject1 = rest.getJSONObject(i);
                        if(jsonObject1.getString("name").equals(getIntent().getStringExtra("restaurant_name")) && getIntent().getStringExtra("button_id").equals("0")){
                            restaurant_name = (jsonObject1.getString("name"));
                            JSONArray menu = jsonObject1.getJSONArray("menu_item_list");
                            for (int j = 0; j < menu.length(); j++) {
                                JSONObject items = menu.getJSONObject(j);
                                if (items.has("calories") && items.getInt("calories")<min_calories && items.getInt("fat") < min_fat)  {

                                    Restaurant restaurant = new Restaurant();
                                    restaurant.setName(items.getString("name"));
                                    restt.add(items.getString("name"));
                                    restaurant.setDescription(items.getString("description"));
                                    restaurant.setCost(items.getInt("price"));
                                    if (items.has("image")) {
                                        restaurant.setImg(items.getString("image"));
                                    }
                                    if (items.has("calories") ) {
                                        restaurant.setCalories(items.getInt("calories"));
                                        restaurant.setFat(items.getInt("fat"));
                                        restaurant.setProtein(items.getInt("protein"));

                                    }
                                    restaurantList.add(restaurant);
                                }

                            }List<Restaurant> restaurantList1 = new ArrayList<>();

                            for (Restaurant r: restaurantList){prices.add(r.getCalories());
                            }Collections.sort(prices);
                            for (int i1=0; i1<prices.size();i1++){
                                for (Restaurant r: restaurantList){
                                    if (prices.get(i1).equals(r.getCalories())){
                                        restaurantList1.add(r);
                                    }

                                }}
                            restaurantList = restaurantList1;
                            prices.clear(); }
                        else if(jsonObject1.getString("name").equals(getIntent().getStringExtra("restaurant_name")) && getIntent().getStringExtra("button_id").equals("1")){
                            restaurant_name = (jsonObject1.getString("name"));
                            JSONArray menu = jsonObject1.getJSONArray("menu_item_list");
                            for (int j = 0; j < menu.length(); j++) {

                                JSONObject items = menu.getJSONObject(j);
                                if (items.has("calories") && items.getInt("calories")>min_calories && items.getInt("fat") > min_fat && items.getInt("protein") > min_protein)  {
                                    Restaurant restaurant = new Restaurant();
                                    restaurant.setName(items.getString("name"));
                                    restt.add(items.getString("name"));
                                    restaurant.setDescription(items.getString("description"));
                                    restaurant.setCost(items.getInt("price"));
                                    if (items.has("image")) {
                                        restaurant.setImg(items.getString("image"));
                                    }
                                    if (items.has("calories")) {
                                        restaurant.setCalories(items.getInt("calories"));
                                        restaurant.setFat(items.getInt("fat"));
                                        restaurant.setProtein(items.getInt("protein"));
                                    }
                                    restaurantList.add(restaurant);


                                }
                            }
                            List<Restaurant> restaurantList1 = new ArrayList<>();

                            for (Restaurant r: restaurantList){prices.add(r.getCalories());
                            }Collections.sort(prices, Collections.reverseOrder());

                            for (int i1=0; i1<prices.size();i1++){
                                for (Restaurant r: restaurantList){
                                    if (prices.get(i1).equals(r.getCalories())){
                                        restaurantList1.add(r);
                                    }

                                }}
                            restaurantList = restaurantList1;
                        prices.clear();}

                        else if(jsonObject1.getString("name").equals(getIntent().getStringExtra("restaurant_name")) && getIntent().getStringExtra("button_id").equals("2")){
                            restaurant_name = (jsonObject1.getString("name"));
                            JSONArray menu = jsonObject1.getJSONArray("menu_item_list");

                            for (int j = 0; j < menu.length(); j++) {

                                JSONObject items = menu.getJSONObject(j);
                                if (items.has("price") && items.getInt("price")<= Integer.parseInt(getIntent().getStringExtra("cost_amt")))  {

                                    Restaurant restaurant = new Restaurant();
                                    restaurant.setName(items.getString("name"));
                                    restt.add(items.getString("name"));
                                    restaurant.setDescription(items.getString("description"));
                                    restaurant.setCost(items.getInt("price"));
                                    if (items.has("image")) {
                                        restaurant.setImg(items.getString("image"));
                                    }
                                    if (items.has("calories")) {
                                        restaurant.setCalories(items.getInt("calories"));
                                        restaurant.setFat(items.getInt("fat"));
                                        restaurant.setProtein(items.getInt("protein"));
                                    }
                                    restaurantList.add(restaurant);



                                }
                            }
                            List<Restaurant> restaurantList1 = new ArrayList<>();
                            for (Restaurant r: restaurantList){
                                prices.add(r.getCost());
                            }Collections.sort(prices);
                            Log.d("sort",prices.toString());
                            for (int i1=0; i1<prices.size();i1++){
                              for (Restaurant r: restaurantList){
                                    if (prices.get(i1).equals(r.getCost())){
                                        restaurantList1.add(r);
                                    }

                                }}
                            restaurantList = restaurantList1;
                            prices.clear();
                        }
                        initRecyclerView();



                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(request);


    }

    private void initRecyclerView() {
        setOnClickListener();
        layoutManager = new LinearLayoutManager(getApplicationContext());
        rvItems.setLayoutManager(layoutManager);
        itemAdapter = new ItemAdapter(restaurantList, context, restaurant_name,listener, restt, this);
        rvItems.setAdapter(itemAdapter);
        setOnClickListener();
    }

    private void setOnClickListener() {
        listener=new ItemAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {

                ParseObject menus = new ParseObject("Restaurants");
                menus.addUnique("menu_items", restaurantList.get(position));
                menus.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e==null){
                            Toast.makeText(context, "worked", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        };
    }


    @Override
    public void onAddToCartClick(Restaurant restaurant) {
        if(itemsInCartList == null) {
            itemsInCartList = new ArrayList<>();
        }
        itemsInCartList.add(restaurant);


        totalItemInCart = 0;

        for(Restaurant m : itemsInCartList) {
            totalItemInCart += m.getTotalInCart();
        }
     

    }

    @Override
    public void onUpdateCartClick(Restaurant restaurant) {
        if(itemsInCartList.contains(restaurant)) {
            int index = itemsInCartList.indexOf(restaurant);
            itemsInCartList.remove(index);
            itemsInCartList.add(index, restaurant);

            totalItemInCart = 0;

            for(Restaurant m : itemsInCartList) {
                totalItemInCart = totalItemInCart + m.getTotalInCart();
            }
        }
    }

    @Override
    public void onRemoveFromCartClick(Restaurant restaurant) {
        if(itemsInCartList.contains(restaurant)) {
            itemsInCartList.remove(restaurant);
            totalItemInCart = 0;

            for(Restaurant m : itemsInCartList) {
                totalItemInCart = totalItemInCart + m.getTotalInCart();
            }
        }
    }
}
