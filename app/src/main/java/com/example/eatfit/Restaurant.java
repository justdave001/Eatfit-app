package com.example.eatfit;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Restaurant implements Parcelable {
    public String name;
    public int calories;
    public double cost;
    public int protein;
    public int phoneNumber;
    public String description;
    int fat;
    String img;
    List<Restaurant> restaurantList;


    private float price;
    int totalInCart;

    public Restaurant(){}
    public Restaurant(String name, int phoneNumber, int calories,
                    double cost, int fat,int protein,String description, String img,
                      int totalInCart){
            this.name = name;
            this.phoneNumber = phoneNumber;
            this.calories = calories;
            this.cost = cost;
            this.fat = fat;
            this.protein=protein;
            this.description=description;
            this.img = img;
            this.totalInCart=totalInCart;


    }

    protected Restaurant(Parcel in) {
        name = in.readString();
        calories = in.readInt();
        cost = in.readDouble();
        protein = in.readInt();
        phoneNumber = in.readInt();
        description = in.readString();
        fat = in.readInt();
        img = in.readString();
        restaurantList = in.createTypedArrayList(Restaurant.CREATOR);
        price = in.readFloat();
        totalInCart = in.readInt();
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    public int getCalories() {
        return calories;
    }



    public List<Restaurant> getMenus() {
        return restaurantList;
    }

    public void setMenus(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }


    public void setCalories(int calories) {
        this.calories = calories;
    }

    public double getCost() {

        return cost/100;
    }
    public void setTotalInCart(int totalInCart) {
        this.totalInCart = totalInCart;
    }
    public int getTotalInCart() {
        return totalInCart;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }
    public String getImg(){
        return img;
    }
    public void setImg(String img){
        this.img=img;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeFloat(price);
        dest.writeString(img);
        dest.writeInt(totalInCart);
    }
}
