package com.example.eatfit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class Favorites extends AppCompatActivity {
    List<String> menuItems = new ArrayList<>();
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        recyclerView=findViewById(R.id.rV);

        ParseUser currentUser = ParseUser.getCurrentUser();
        menuItems = currentUser.getList("favorites");
        Log.e("faves", menuItems.toString());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FavoritesAdapter favoritesAdapter=new FavoritesAdapter(menuItems);
        recyclerView.setAdapter(favoritesAdapter);
    }
}