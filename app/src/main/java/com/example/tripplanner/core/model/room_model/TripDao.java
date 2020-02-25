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
    Long insertTrip(TripEntity trip);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertNotes(NoteEntity note);

    @Query("SELECT tripId from Trip where  tripKey == :key")
    Integer getTripId (String key);

    @Query("SELECT COUNT(tripId) from Trip ")
    Integer getRows();

    @Transaction
    @Query("SELECT * FROM Trip")
    LiveData<List<TripWithNotes>> lgetTripWithNotes();

    @Transaction
    @Query("SELECT * FROM Trip WHERE tripStatus == :tripStatus")
    LiveData<List<TripWithNotes>> allTripsStatus (String tripStatus);
}
