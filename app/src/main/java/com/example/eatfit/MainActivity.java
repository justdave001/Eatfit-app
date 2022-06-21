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
                        Toast.makeText(MainActivity.this, "home!", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.search:
                        Toast.makeText(MainActivity.this, "search!", Toast.LENGTH_SHORT).show();
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.person:
                        Toast.makeText(MainActivity.this, "person!", Toast.LENGTH_SHORT).show();
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });
    }
}