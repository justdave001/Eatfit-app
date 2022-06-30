package com.example.eatfit;

import java.util.ArrayList;

public class Restaurant {
    public String name;
    public int calories;
    public int cost;
    public int protein;
    public int phoneNumber;
    public String description;
    int fat;


    public Restaurant(){}
    public Restaurant(String name, int phoneNumber, int calories,
                    int cost, int fat,int protein,String description){
            this.name = name;
            this.phoneNumber = phoneNumber;
            this.calories = calories;
            this.cost = cost;
            this.fat = fat;
            this.protein=protein;
            this.description=description;

    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
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




}
