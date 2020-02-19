package com.example.tripplanner.core.model;

import java.util.Date;
import java.util.List;

public class Trip {
    private String title;
    private String startLocation;
    private String endLocation;
    private String tripDate;
    List<Note> listOfNotes;

    //this for trip identification
    private String tripId ;

    /*public Trip(String title) {
        this.title = title;
    }*/

    public Trip(String title, String tripData, String startLocation, String endLocation) {
        this.tripDate = tripData;
        this.title = title;
        this.endLocation = endLocation;
        this.startLocation = startLocation;

        //concat title with start and end location to identify the Trip.
        tripId = title + startLocation + endLocation;
    }

    public Trip(String title, String tripData, String startLocation, String endLocation,List<Note> notes) {
        this.tripDate = tripData;
        this.title = title;
        this.endLocation = endLocation;
        this.startLocation = startLocation;
        listOfNotes = notes;

        //concat title with start and end location to identify the Trip.
        tripId = title + startLocation + endLocation;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public void setTripDate(String tripDate) {
        this.tripDate = tripDate;
    }

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

    public String getTripId (){
        return tripId;
    }
}
