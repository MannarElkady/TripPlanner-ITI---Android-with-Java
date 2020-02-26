package com.example.tripplanner.pasttrips;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tripplanner.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {

    private SupportMapFragment supportMapFragment;
    private MapViewModel mapViewModel;
    private List<List<LatLng>> pastTripsRoutes;

    //private static final float START_COLOR = 0xff34a853;
    //private static final float END_COLOR = 0xff34a853;


    private static final String TAG = "MapFragment";

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);
        mapViewModel = new ViewModelProvider(requireActivity()).get(MapViewModel.class);

        mapViewModel.getRoutes().observe(getViewLifecycleOwner(), (routes) ->{
            pastTripsRoutes = routes;
            Log.i(TAG, routes.toString());
            supportMapFragment.getMapAsync(MapFragment.this);
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Map<String, String> trips = new HashMap<String, String>();
        trips.put("Disneyland park", "Universal+Studios+Hollywood");
        trips.put("Bakersfield, CA, USA", "San Jose, CA, USA");

        mapViewModel.setRoutes(trips);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        for(List<LatLng> points : pastTripsRoutes) {

            Polyline polyline = googleMap.addPolyline(new PolylineOptions().clickable(true)
                    .addAll(points));
            googleMap.addMarker(new MarkerOptions().position(points.get(0)).alpha(0.7f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            googleMap.addMarker(new MarkerOptions().position(points.get(points.size() - 1)).alpha(0.7f).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

            polyline.setEndCap(new RoundCap());


            polyline.setWidth(12);
            polyline.setColor(0xffF57F17);
            polyline.setJointType(JointType.ROUND);

            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(points.get(0), 6));
        }

        // zoom camera on Egypt.
        //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(26.28, 30.8), 15));
    }
}
