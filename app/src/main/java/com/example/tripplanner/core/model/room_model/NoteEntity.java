package com.example.tripplanner.core.model.room_model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.tripplanner.core.model.Trip;

import static androidx.room.ForeignKey.CASCADE;

@Entity( foreignKeys =@ForeignKey(entity = TripEntity.class,parentColumns = "tripId", childColumns = "noteOwner", onDelete = CASCADE) )
public class NoteEntity {

    @PrimaryKey(autoGenerate = true)
    public Integer noteId;

    public String content;

    @ColumnInfo(index = true)
    public Long noteOwner;


    @Ignore
    public NoteEntity(Long noteOwner,String content) {
        this.content = content;
        this.noteOwner = noteOwner;
    }

    public NoteEntity(){}

}

