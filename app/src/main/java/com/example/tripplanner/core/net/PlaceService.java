package com.example.tripplanner.core.net;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlaceService {


    //TODO: Add API_KEY with Base url to fetch places data

    @GET
    Call<String> searchForPlace(@Query("parameter")String query);


    /*
    *
    @GET
    Call<String> searchForPlace(@Query("api_key")String apiKey,@Query("parameter")String query);
    * */
}
