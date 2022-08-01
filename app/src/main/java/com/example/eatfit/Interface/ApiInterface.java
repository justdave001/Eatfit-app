package com.example.eatfit.Interface;

import com.example.eatfit.Models.Restaurant;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("/example")
    Call<List<Restaurant>> getRestaurants();
}
