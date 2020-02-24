package com.example.tripplanner.core.localDataSource;


import android.app.Application;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.tripplanner.core.model.room_model.NoteEntity;
import com.example.tripplanner.core.model.room_model.TripDao;
import com.example.tripplanner.core.model.room_model.TripEntity;

@Database(entities = {TripEntity.class, NoteEntity.class} , version = 1)
public abstract class TripDatabase extends RoomDatabase {

    public static final String DATABASE_NAME ="TripDatabase";
    private static TripDatabase INSTANCE ;

    public abstract TripDao tripDao ();

    public static TripDatabase getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context,
                    TripDatabase.class,DATABASE_NAME)
                    .build();
        }
        return INSTANCE;
    }
}
