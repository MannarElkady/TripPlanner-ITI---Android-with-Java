package com.example.tripplanner.core.model.room_model;



import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface TripDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTrip(TripEntity trip);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNotes(NoteEntity note);

    @Query("SELECT tripId from Trip where  tripKey == :key")
    Integer getTripId (String key);

    @Transaction
    @Query("SELECT * FROM Trip")
    LiveData<List<TripWithNotes>> getTripWithNotes();
    //TODO:return liveData of list<TripWithNotes>

}
