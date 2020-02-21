package com.example.tripplanner.core.model.room_model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.tripplanner.core.constant.TripStatus;
import com.example.tripplanner.core.model.Note;

import java.util.List;

@Entity (tableName = "Trip")
public class TripEntity {

    @PrimaryKey(autoGenerate = true)
    private Integer tripId;

    private String title;
    private String startLocation;
    private String endLocation;
    private String tripDate;
    private String tripStatus;

    public void setTripKey(String tripKey) {
        this.tripKey = tripKey;
    }

    private String tripKey ;

    //for Room initialization
    public TripEntity(){}

    @Ignore
    public TripEntity(String title, String tripData, String startLocation, String endLocation) {
        this.tripDate = tripData;
        this.title = title;
        this.endLocation = endLocation;
        this.startLocation = startLocation;

        // by default the trip not start yet and upcoming
        tripStatus = TripStatus.UPCOMING;
        //concat title with start and end location to identify the Trip.
        tripKey = title + startLocation + endLocation;
    }



    // setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public void setEndLocation(String endLocation) { this.endLocation = endLocation; }

    public void setTripDate(String tripDate) {
        this.tripDate = tripDate;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public void setTripStatus(String tripStatus) { this.tripStatus = tripStatus; }


    // getters
    public String getTitle() {
        return title;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public String getTripDate() {
        return tripDate;
    }

    public Integer getTripId() { return tripId; }

    public String getTripStatus() { return tripStatus; }


    public String getTripKey() {
        return tripKey;
    }

}
