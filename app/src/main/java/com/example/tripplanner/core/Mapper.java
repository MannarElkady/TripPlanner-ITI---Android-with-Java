package com.example.tripplanner.core;

import androidx.annotation.NonNull;

import com.example.tripplanner.core.model.Note;
import com.example.tripplanner.core.model.Trip;
import com.example.tripplanner.core.model.room_model.NoteEntity;
import com.example.tripplanner.core.model.room_model.TripEntity;
import com.example.tripplanner.core.model.room_model.TripWithNotes;

import java.util.ArrayList;
import java.util.List;

public class Mapper {

    //this class to map from TripEntity to Trip
    // or from Trip to TripEntity



    public Trip getTripForFirestore(@NonNull TripWithNotes tripWithNotes){
        //get TripEntity and NoteEntity list
        TripEntity tripEntity = tripWithNotes.getTrip();
        List<NoteEntity>  noteEntities = tripWithNotes.getTripNotes();

        //initialize Trip object from TripEntity object
        Trip mappedTrip = new Trip(tripEntity.getTitle(),tripEntity.getTripDate(),tripEntity.getStartLocation(),tripEntity.getEndLocation());


        //initialize List of Notes from NoteEntities list
        List<Note> notes = new ArrayList<>();
        for(NoteEntity entity: noteEntities){
            notes.add(new Note(entity.content));
        }

        //assign Trip notes
        mappedTrip.setListOfNotes(notes);

        //return mapped trip to store it in FireStore
        return mappedTrip;
    }
}
