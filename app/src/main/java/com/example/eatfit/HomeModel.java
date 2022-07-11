package com.example.eatfit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeModel {

    private String restaurantName;
    private List menuItems;
    private boolean isExpandable;



    public HomeModel( String restaurantName,
                      List menuItems

    )
    {
        this.restaurantName = restaurantName;
        this.menuItems = menuItems;

        isExpandable = false;
    }

    public void setExpandable(boolean expandable){
        isExpandable=expandable;
    }

    public boolean isExpandable(){
        return  isExpandable;
    }

    public String getRestaurantName(){
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName){
        this.restaurantName=restaurantName;
    }

    public List getMenuItems(){
        return menuItems;
    }

    public void setMenuItems(List menuItems){
        this.menuItems=menuItems;
    }




}
