package com.example.eatfit;

import android.util.Log;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Restaurant {
    public String name;
    public int calories;
    public double cost;
    public int protein;
    public int phoneNumber;
    public String description;
    int fat;
    String img;



    public Restaurant(){}
    public Restaurant(String name, int phoneNumber, int calories,
                    double cost, int fat,int protein,String description, String img){
            this.name = name;
            this.phoneNumber = phoneNumber;
            this.calories = calories;
            this.cost = cost;
            this.fat = fat;
            this.protein=protein;
            this.description=description;
            this.img = img;


    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public double getCost() {

        return cost/100;
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




}
