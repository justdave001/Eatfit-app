package com.example.eatfit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.parse.ParseObject;
import com.parse.ParseUser;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class GoalActivity extends AppCompatActivity {

    RecyclerView rvItems;
    LinearLayoutManager layoutManager;
    ImageView addRes;
    ItemAdapter itemAdapter;
    public String BASE_URL = "https://apimocha.com/eatfit/example";
    List<Restaurant> restaurantList;
    Context context;

//    String restaurant_name;
//    List restaurant  = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);
        context = getApplicationContext();
        rvItems = findViewById(R.id.rvItems);
        restaurantList =  new ArrayList<>();

//        addRes  =findViewById(R.id.addRes);
//        addRes.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent
//
//
//                    }
//                }
//        );


        extractRestaurants();



    }

    private void extractRestaurants() {
        RequestQueue queue = Volley.newRequestQueue(this);
        Request request = new StringRequest(Request.Method.GET, BASE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String res_name = getIntent().getStringExtra("restaurant_name");
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray rest = jsonObject.getJSONArray("restaurants");

                    for (int i = 0; i < rest.length(); i++) {
                        JSONObject jsonObject1 = rest.getJSONObject(i);

                        if(jsonObject1.getString("name").equals(getIntent().getStringExtra("restaurant_name")) && getIntent().getStringExtra("button_id").equals("0")){
                        Log.e("restaurants",jsonObject1.toString());
                        Log.e("restaurant",getIntent().getStringExtra("restaurant_name"));

                            JSONArray menu = jsonObject1.getJSONArray("menu_item_list");
                        for (int j = 0; j < menu.length(); j++) {

                            JSONObject items = menu.getJSONObject(j);
                            if (items.has("calories") && items.getInt("calories")<700 && items.getInt("fat") < 60)  {
                                Restaurant restaurant = new Restaurant();
                                restaurant.setName(items.getString("name"));
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

                        }}
                        else if(jsonObject1.getString("name").equals(getIntent().getStringExtra("restaurant_name")) && getIntent().getStringExtra("button_id").equals("1")){
                            Log.e("restaurants",jsonObject1.toString());
                            Log.e("restaurant",getIntent().getStringExtra("restaurant_name"));
                            JSONArray menu = jsonObject1.getJSONArray("menu_item_list");
                            for (int j = 0; j < menu.length(); j++) {

                                JSONObject items = menu.getJSONObject(j);
                                if (items.has("calories") && items.getInt("calories")>700 && items.getInt("fat") > 60 && items.getInt("protein") > 30)  {
                                    Restaurant restaurant = new Restaurant();
                                    restaurant.setName(items.getString("name"));
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
                            }}

                        else if(jsonObject1.getString("name").equals(getIntent().getStringExtra("restaurant_name")) && getIntent().getStringExtra("button_id").equals("2")){
                            Log.e("restaurants",jsonObject1.toString());
                            Log.e("restaurant",getIntent().getStringExtra("restaurant_name"));
                            JSONArray menu = jsonObject1.getJSONArray("menu_item_list");
                            for (int j = 0; j < menu.length(); j++) {

                                JSONObject items = menu.getJSONObject(j);
                                if (items.has("price") && items.getInt("price")<= Integer.parseInt(getIntent().getStringExtra("cost_amt")))  {
                                   Log.e("price",getIntent().getStringExtra("cost_amt") );
                                    Log.e("prices", Integer.toString(items.getInt("price")));
                                    Restaurant restaurant = new Restaurant();
                                    restaurant.setName(items.getString("name"));
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
                            }}

                    }
                    layoutManager = new LinearLayoutManager(getApplicationContext());
                    rvItems.setLayoutManager(layoutManager);
                    itemAdapter = new ItemAdapter(restaurantList, context);
                    rvItems.setAdapter(itemAdapter);

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
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, BASE_URL, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//               try {
//                   JSONObject jsonObject = new JSONObject(response)
//               }
//
//            }
//        });

    }

}


