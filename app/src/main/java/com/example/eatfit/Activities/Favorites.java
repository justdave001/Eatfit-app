package com.example.eatfit.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.eatfit.R;
import com.google.android.material.snackbar.Snackbar;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class Favorites extends AppCompatActivity {
    List<String> menuItems = new ArrayList<>();
    RecyclerView recyclerView;
    List templist= new ArrayList<>();

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
        com.example.eatfit.Adapters.FavoritesAdapter favoritesAdapter=new com.example.eatfit.Adapters.FavoritesAdapter(menuItems);
        recyclerView.setAdapter(favoritesAdapter);

        ItemTouchHelper.SimpleCallback simpleCallback =
                new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        ParseUser currentUser = ParseUser.getCurrentUser();
                        final  int position = viewHolder.getAdapterPosition();
                        switch (direction){
                            case ItemTouchHelper.LEFT:
                                Object removee = menuItems.remove(position);
                                ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
                                query.whereEqualTo("username", currentUser.getUsername());

                                query.findInBackground(new FindCallback<ParseObject>() {
                                    @Override
                                    public void done(List<ParseObject> objects, ParseException e) {
                                        if (e==null){
                                            for (ParseObject removed:objects){
                                                templist = removed.getList("favorites");
                                                removed.remove("favorites");
                                                if (templist.contains(removee)){
                                                    templist.remove(removee);
                                                    removed.addAll("favorites", templist);
                                                    removed.saveInBackground();

                                                }
                                            }

                                        }

                                    }

                                });
                                favoritesAdapter.notifyDataSetChanged();

                                Snackbar.make(viewHolder.itemView,removee.toString(), Snackbar.LENGTH_LONG )
                                        .setAction("Undo", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                query.findInBackground(new FindCallback<ParseObject>() {
                                                    @Override
                                                    public void done(List<ParseObject> objects, ParseException e) {
                                                        if (e==null){
                                                            for (ParseObject removed:objects){
                                                                removed.addUnique("favorites", removee);
                                                                removed.saveInBackground();
                                                            }

                                                        }

                                                    }

                                                });
                                                favoritesAdapter.notifyDataSetChanged();
                                            }
                                        }).show();
                                break;


                        }



                    }
                    @Override
                    public void onChildDraw (Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive){
                        new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                                .addSwipeLeftBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.red))
                                .addSwipeLeftActionIcon(R.drawable.ic_delete)
                                .addSwipeLeftLabel(getApplicationContext().getString(R.string.delete))
                                .create()
                                .decorate();
                        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    }
                };
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
    }

}