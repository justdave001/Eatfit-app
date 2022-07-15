package com.example.eatfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class DetailsActivity extends AppCompatActivity {
    TextView marker;
    EditText f_name;
    TextView firstName;
    EditText money;
    AppCompatButton lWeight;
    AppCompatButton gWeight;
    AppCompatButton sMoney;
    String cost;
    Restaurant restaurant = new Restaurant();
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        marker = findViewById(R.id.marker);
        lWeight = findViewById(R.id.lWeight);
        gWeight = findViewById(R.id.gWeight);
        sMoney = findViewById(R.id.sMoney);
        money = findViewById(R.id.money);

        String title = getIntent().getStringExtra("title");
        marker.setText(title);
        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener()  {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        return true;

                    case R.id.search:
                        return true;

                    case R.id.person:
                        startActivity(new Intent(getApplicationContext(), PersonActivity.class));
                        return true;

                }
                return false;
            }
        });


        lWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resname = marker.getText().toString();
                Intent i = new Intent(DetailsActivity.this, GoalActivity.class);
                i.putExtra("restaurant_name", resname);
                i.putExtra("button_id","0");
                i.putExtra("restaurantModel",restaurant);
                startActivity(i);
            }
        });

        gWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resname = marker.getText().toString();
                Intent i = new Intent(DetailsActivity.this, GoalActivity.class);
                i.putExtra("restaurant_name", resname);
                i.putExtra("button_id","1");
                startActivity(i);
            }
        });

        sMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resname = marker.getText().toString();
                if (!money.getText().toString().equals("")){
                    cost = Integer.toString(Integer.parseInt(money.getText().toString())*100);}else {
                    cost="9999999999999999";
                }
                Intent i = new Intent(DetailsActivity.this, GoalActivity.class);
                i.putExtra("restaurant_name", resname);
                i.putExtra("button_id","2");
                i.putExtra("cost_amt",cost);

                startActivity(i);
            }
        });
    }
}