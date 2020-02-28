package com.example.tripplanner.core.net;

import android.app.Application;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static DirectionsInterface directionsInterface;
    //TODO: Add API_KEY for Google Places
    private static final String API_KEY ="AIzaSyCEDSeyNw8JyoTw1bVM3YgzDxOs8GGx_uk";
    private static final String API_BASE_URL = "https://maps.googleapis.com/maps/api/";
    //TODO: Add Api endpoint for Weather data


    private static RetrofitClient INSTANCE ;

    private RetrofitClient(){}


    public static  RetrofitClient getInstance(){
        if(INSTANCE == null){

             INSTANCE = new RetrofitClient();
             directionsInterface = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(DirectionsInterface.class);

        }

        return INSTANCE;
    }

    public Call<DirectionsResponse> getDirections(String origin, String destination){
        return directionsInterface.getDirections(origin, destination, API_KEY);
    }
}
