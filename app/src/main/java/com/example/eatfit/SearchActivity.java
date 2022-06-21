package com.example.eatfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class SearchActivity extends AppCompatActivity {
    public BottomNavigationView bottomNavigationView;
    ImageView restaurant;
    public GoogleMap mMap;
    SupportMapFragment supportMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        restaurant = findViewById(R.id.restaurant);
        supportMapFragment = (SupportMapFragment)  getSupportFragmentManager().findFragmentById(R.id.google_map);

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.home);




        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener()  {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        Toast.makeText(SearchActivity.this, "home!", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.search:
                        return true;

                    case R.id.person:
                        startActivity(new Intent(getApplicationContext(), PersonActivity.class));
                        Toast.makeText(SearchActivity.this, "person!", Toast.LENGTH_SHORT).show();
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });

        //initializing type place
        String placeType = "restaurant";

        //initialize place name
        String placeName = "Restaurant";


    }
}