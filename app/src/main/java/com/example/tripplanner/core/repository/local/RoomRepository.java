package com.example.tripplanner.core.repository.local;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.tripplanner.core.localDataSource.TripDatabase;
import com.example.tripplanner.core.model.room_model.NoteEntity;
import com.example.tripplanner.core.model.room_model.TripDao;
import com.example.tripplanner.core.model.room_model.TripEntity;
import com.example.tripplanner.core.model.room_model.TripWithNotes;

import java.util.List;

//Entities Names (TripEntity , NoteEntity)
public class RoomRepository {

    private TripDao dao;


    public RoomRepository(Context context) {
        dao = TripDatabase.getInstance(context).tripDao();
    }

    LiveData<List<TripWithNotes>> getTripWithNotes() {
        return dao.getTripWithNotes();
    }

    public void insertTrip(TripEntity tripEntity) {
        dao.insertTrip(tripEntity);
    }

    public void insertNote(NoteEntity noteEntity) {
        dao.insertNotes(noteEntity);
    }

    public Integer getTripId(TripEntity tripEntity) {
        return dao.getTripId(tripEntity.getTripKey());
    }

    public LiveData<List<TripWithNotes>> getAllTrips() {
        return dao.getTripWithNotes();
    }

    public LiveData<List<TripWithNotes>> getAllTripsStatus(String tripStatus) {
        return dao.allTripsStatus(tripStatus);
    }

}
