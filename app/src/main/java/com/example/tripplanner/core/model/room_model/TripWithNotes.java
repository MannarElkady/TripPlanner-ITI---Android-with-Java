package com.example.tripplanner.core.model.room_model;




import androidx.room.Embedded;
import androidx.room.Relation;


import java.util.List;

public class TripWithNotes {

    @Embedded
    public TripEntity trip;


    @Relation(
            parentColumn = "tripId",
            entityColumn = "noteOwner",
            entity = NoteEntity.class
    )
    public List<NoteEntity> tripNotes;

    public TripWithNotes(TripEntity trip, List<NoteEntity> tripNotes){
        this.trip = trip;
        this.tripNotes = tripNotes;
    }

    public TripEntity getTrip() {
        return trip;
    }

    public void setTrip(TripEntity trip) {
        this.trip = trip;
    }

    public List<NoteEntity> getTripNotes() {
        return tripNotes;
    }

    public void setTripNotes(List<NoteEntity> tripNotes) {
        this.tripNotes = tripNotes;
    }
}
