package com.example.tripplanner.core.model;

import java.util.Date;
import java.util.List;

public class Trip {
    private String title;
    private String startLocation;
    private String endLocation;
    private String tripDate;
    private List<Note> listOfNotes;

    //this for trip identification
    //No setter for tripId to prevent external modification
    private String tripId ;

    /*public Trip(String title) {
        this.title = title;
    }*/


    //construct a trip without list of notes
    public Trip(String title, String tripData, String startLocation, String endLocation) {
        this.tripDate = tripData;
        this.title = title;
        this.endLocation = endLocation;
        this.startLocation = startLocation;

        //concat title with start and end location to identify the Trip.
        tripId = title + startLocation + endLocation;
    }

    //construct a trip with Notes
    public Trip(String title, String tripData, String startLocation, String endLocation,List<Note> notes) {
        this.tripDate = tripData;
        this.title = title;
        this.endLocation = endLocation;
        this.startLocation = startLocation;
        listOfNotes = notes;

        //concat title with start and end location to identify the Trip.
        tripId = title + startLocation + endLocation;
    }

    public Trip(){
    }


    //setters
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


    //getters
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
