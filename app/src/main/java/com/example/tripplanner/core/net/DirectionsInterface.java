package com.example.tripplanner.core.net;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DirectionsInterface {

    @GET("directions/json")
    Call<DirectionsResponse> getDirections(@Query("origin") String origin, @Query("destination") String destination, @Query("key") String key);
}
