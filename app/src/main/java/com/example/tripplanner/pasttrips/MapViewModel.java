package com.example.tripplanner.pasttrips;

import android.util.Log;

import com.example.tripplanner.core.net.DirectionsResponse;
import com.example.tripplanner.core.net.RetrofitClient;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.google.android.gms.maps.model.LatLng;

public class MapViewModel extends ViewModel {

    private MutableLiveData<List<LatLng>> route;
    //private List<List<LatLng>> allRoutesPoints;
    private RetrofitClient retrofitClient;
    private String TAG = "MapViewModel";

    public MapViewModel(){
        retrofitClient = RetrofitClient.getInstance();
        //allRoutesPoints = new ArrayList<List<LatLng>>();
        route = new MutableLiveData<List<LatLng>>();
    }

    public void setRoutes(Map<String, String> trips){
        for(Map.Entry<String, String> trip : trips.entrySet()) {
            Call<DirectionsResponse> call = retrofitClient.getDirections(trip.getKey(), trip.getValue());
            call.enqueue(new Callback<DirectionsResponse>() {
                @Override
                public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                    DirectionsResponse directionsResponse = response.body();

                    if(directionsResponse.getRoutes().size() > 0) {
                        List<LatLng> oneRoutePoints = PolyUtil.decode(directionsResponse.getRoutes().get(0).getOverview_polyline().getPoints());
                        route.postValue(oneRoutePoints);
                    }
                }

                @Override
                public void onFailure(Call<DirectionsResponse> call, Throwable t) {
                    Log.i(TAG, t.toString());
                }
            });
        }

    }
    public LiveData<List<LatLng>> getRoutes(){
        return route;
    }

}
