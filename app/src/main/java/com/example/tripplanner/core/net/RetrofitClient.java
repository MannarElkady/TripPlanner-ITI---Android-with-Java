package com.example.tripplanner.core.net;

import android.app.Application;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static PlaceService service;
    //TODO: Add API_KEY for Google Places
    private static final String API_KEY ="";
    private static final String PLACE_BASE_URL = "";
    //TODO: Add Api endpoint for Weather data


    private static RetrofitClient INSTANCE ;

    private RetrofitClient(){}


    public static  RetrofitClient getInstance(Application application){
        if(INSTANCE == null){

             INSTANCE = new RetrofitClient();
             service = new Retrofit.Builder()
                    .baseUrl(PLACE_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(PlaceService.class);

        }

        return INSTANCE;
    }

    Call<String> searchForPlace(String query){
        //TODO: pass API_KEY for google places if needed
        return service.searchForPlace(query);
    }

    PlaceService getPlaceService() {
        return service;
    }
}
