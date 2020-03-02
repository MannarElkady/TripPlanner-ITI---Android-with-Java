package com.example.tripplanner.Weather_service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("current")
    Call<String> getWeatherData(@Query("access_keyy")String apiKey,@Query("Query")String countery);
}
