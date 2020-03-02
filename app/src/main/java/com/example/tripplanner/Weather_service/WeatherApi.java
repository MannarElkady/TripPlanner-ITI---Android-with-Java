package com.example.tripplanner.Weather_service;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherApi {

    private static final String BASE_URL ="http://api.weatherstack.com/";
    public static final String API_KEY = "06d50e4a7b9bb5753eaecb809c3d5ed5";
    private static WeatherService weatherService;
    private static WeatherApi INSTANCE;

    public WeatherService getWeatherService() {
        return weatherService;
    }

    private WeatherApi() {}

    public static WeatherApi getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new WeatherApi();
            weatherService =
                    new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                            .create(WeatherService.class);
        }
        return INSTANCE;
    }

    public Call<String> getWeatherData(String query) {
        return weatherService.getWeatherData(WeatherApi.API_KEY, query);
    }
}
