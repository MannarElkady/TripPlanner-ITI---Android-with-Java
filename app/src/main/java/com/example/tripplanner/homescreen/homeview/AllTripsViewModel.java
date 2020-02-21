package com.example.tripplanner.homescreen.homeview;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tripplanner.core.TripRepository;
import com.example.tripplanner.core.localDataSource.TripDatabase;
import com.example.tripplanner.core.model.room_model.NoteEntity;
import com.example.tripplanner.core.model.room_model.TripEntity;
import com.example.tripplanner.core.model.room_model.TripWithNotes;

import java.util.List;

public class AllTripsViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    TripRepository repository;



    public MutableLiveData<List<TripWithNotes>> allTrips = new MutableLiveData<>();

    public AllTripsViewModel(@NonNull Application application) {
        super(application);
        repository = new TripRepository(application);
    }


    public LiveData<List<TripWithNotes>> getAllTrips(String status) {
        return repository.getAllTripsStatus(status);
    }
}
