package com.example.eatfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity {
    public NavigationBarView.OnItemSelectedListener selectedListener;
    public ChipNavigationBar bottomNavigationView;

    private RecyclerView recyclerView;
    private HomeAdapter homeAdapter;
    private List<HomeModel> modelList;
    String restaurantName;
    String tempName = "";
    Set<String> hash_Set = new HashSet<String>();
ShimmerFrameLayout shimmerFrameLayout;
    List menuItems = new ArrayList<>();
    ParseUser currentUser = ParseUser.getCurrentUser();
RecyclerView nestedRv;
     List list = new ArrayList<>();
    List<HomeModel> templist = new ArrayList<>();
    private ViewGroup MainView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shimmerFrameLayout = findViewById(R.id.shimmerLayout);
        bottomNavigationView = findViewById(R.id.bottom_navigator);


        bottomNavigationView.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id) {
                Activity activity = null;
                switch (id){
                    case R.id.home:
                        break;
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(), SearchActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.person:
                        startActivity(new Intent(getApplicationContext(), PersonActivity.class));
                        overridePendingTransition(0,0);
                        break;

                }

            }


        });
        recyclerView = findViewById(R.id.mainRv);

        modelList = new ArrayList<>();
        getDataFromServer();
        recyclerView.setVisibility(View.INVISIBLE);
        shimmerFrameLayout.startShimmerAnimation();
        Handler handler = new Handler();
        handler.postDelayed(()->{
            recyclerView.setVisibility(View.VISIBLE);
            shimmerFrameLayout.stopShimmerAnimation();
            shimmerFrameLayout.setVisibility(View.INVISIBLE);

        }, 2000);
        prepareRV();

        swipeRefreshLayout = findViewById(R.id.swipeContainer);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


    }

    private void refresh() {
         prepareRV();
        getDataFromServer();
        swipeRefreshLayout.setRefreshing(false);
    }


    private void prepareRV() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        homeAdapter = new HomeAdapter(this, modelList);

        recyclerView.setAdapter(homeAdapter);

    }

    private void getDataFromServer() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Restaurants");

        query.whereEqualTo("user", currentUser);
        query.orderByAscending("updated_at");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e==null){
                    for (ParseObject object:objects){
                        String restaurantName = object.getString("res_name");
                        menuItems= (object.getList("menu_items"));

                        if (!hash_Set.contains(restaurantName)) {

                            modelList.add(new HomeModel(restaurantName, menuItems));
                        }

                        hash_Set.add(restaurantName);
                    }query.orderByAscending("updatedAt");


                    homeAdapter.notifyDataSetChanged();

                }
            }
        });


    }


}