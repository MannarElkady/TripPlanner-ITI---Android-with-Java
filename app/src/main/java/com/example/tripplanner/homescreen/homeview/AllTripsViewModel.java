package com.example.tripplanner.homescreen.homeview;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tripplanner.repository.RoomRepository;
import com.example.tripplanner.core.model.room_model.TripWithNotes;

import java.util.List;

public class AllTripsViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    RoomRepository repository;



    public MutableLiveData<List<TripWithNotes>> allTrips = new MutableLiveData<>();

    public AllTripsViewModel(@NonNull Application application) {
        super(application);
        repository = new RoomRepository(application);
    }


    public LiveData<List<TripWithNotes>> getAllTrips(String status) {
        return repository.getAllTripsStatus(status);
    }
}
