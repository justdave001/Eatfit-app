package com.example.eatfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.parse.ParseInstallation;

public class MainActivity extends AppCompatActivity {
    public NavigationBarView.OnItemSelectedListener selectedListener;
    public BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.home);


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener()  {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:

                        return true;

                    case R.id.search:

                        startActivity(new Intent(getApplicationContext(), SearchActivity.class));

                        return true;

                    case R.id.person:

                        startActivity(new Intent(getApplicationContext(), PersonActivity.class));

                        return true;

                }
                return false;
            }
        });
    }
}