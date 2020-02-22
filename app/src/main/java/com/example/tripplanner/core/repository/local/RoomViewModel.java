package com.example.tripplanner.core.repository.local;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tripplanner.core.model.room_model.NoteEntity;
import com.example.tripplanner.core.model.room_model.TripEntity;
import com.example.tripplanner.core.model.room_model.TripWithNotes;

import java.util.List;

public class RoomViewModel extends AndroidViewModel {
    private RoomRepository repository ;
    public RoomViewModel(@NonNull Application application) {
        super(application);
        repository = new RoomRepository(application.getApplicationContext());
    }

    LiveData<List<TripWithNotes>> getTripWithNotes() {
        return repository.getTripWithNotes();
    }

    public void insertTrip(TripEntity tripEntity) {
        repository.insertTrip(tripEntity);
    }

    public void insertNote(NoteEntity noteEntity) {
        repository.insertNote(noteEntity);
    }

    public Integer getTripId(TripEntity tripEntity) {
        return repository.getTripId(tripEntity);
    }

    public LiveData<List<TripWithNotes>> getAllTrips() {
        return repository.getTripWithNotes();
    }

    public LiveData<List<TripWithNotes>> getAllTripsStatus(String tripStatus) {
        return repository.getAllTripsStatus(tripStatus);
    }


}
