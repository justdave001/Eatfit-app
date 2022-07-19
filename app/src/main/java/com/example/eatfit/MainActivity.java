package com.example.eatfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
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

public class MainActivity extends AppCompatActivity {
    public NavigationBarView.OnItemSelectedListener selectedListener;
    public ChipNavigationBar bottomNavigationView;

    private RecyclerView recyclerView;
    private HomeAdapter homeAdapter;
    NestedAdapter nestedAdapter;
    private List<HomeModel> modelList;
    String restaurantName;
    String tempName = "";
    Set<String> hash_Set = new HashSet<String>();
    List menuItems = new ArrayList<>();
    ParseUser currentUser = ParseUser.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
        prepareRV();




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
                        String objid = object.getObjectId();
                        Log.d("id", objid);

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