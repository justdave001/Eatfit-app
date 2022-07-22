package com.example.eatfit;

import android.content.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavoritesModel {

     Context context;
    private String restaurantName;
    private List menuItems;

    public FavoritesModel(Context context,
                      List menuItems, String restaurantName)
    {
        this.context=context;
        this.menuItems = menuItems;
        this.restaurantName = restaurantName;

    }

    public List getMenuItems(){
        return menuItems;
    }

    public void setMenuItems(List menuItems){
        this.menuItems=menuItems;
    }


    public String getRestaurantName(){
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName){
        this.restaurantName=restaurantName;
    }


}