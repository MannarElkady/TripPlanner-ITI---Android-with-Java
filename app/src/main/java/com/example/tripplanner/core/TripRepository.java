package com.example.tripplanner.core;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.tripplanner.core.localDataSource.TripDatabase;
import com.example.tripplanner.core.model.room_model.NoteEntity;
import com.example.tripplanner.core.model.room_model.TripEntity;
import com.example.tripplanner.core.model.room_model.TripWithNotes;

import java.util.List;

public class TripRepository {


    TripDatabase db;
    public TripRepository (Context context){
        db = TripDatabase.getInstance(context);
    }

    LiveData<List<TripWithNotes>> getTripWithNotes() {
        return db.tripDao().getTripWithNotes();
    }

    public void insertTrip(TripEntity tripEntity) {
        db.tripDao().insertTrip(tripEntity);
    }

    public void insertNote(NoteEntity noteEntity) {
        db.tripDao().insertNotes(noteEntity);
    }

    public Integer getTripId(TripEntity tripEntity) {
        return db.tripDao().getTripId(tripEntity.getTripKey());
    }

    public LiveData<List<TripWithNotes>> getAllTrips() {
        return db.tripDao().getTripWithNotes();
    }
}
