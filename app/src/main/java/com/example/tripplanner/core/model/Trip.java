package com.example.tripplanner.core.model;

import com.example.tripplanner.core.constant.TripStatus;

import java.io.Serializable;
import java.util.List;

public class Trip implements Serializable {
    private String title;
    private String startLocation;
    private String endLocation;
    private double startLatitude;
    private double startLongitude;
    private double endtLatitude;
    private double endLongitude;
    private String tripTime;
    private String tripDate;
    private List<Note> listOfNotes;
    private String tripStatus;

    //this for trip identification
    //No setter for tripId to prevent external modification
    private String tripId;

    //construct a trip without list of notes
    public Trip(){

    }
    public Trip(String title, String tripData, String tripTime, String startLocation, String endLocation,double startLat
            ,double startLon,double endLat,double endLon) {
        this.tripDate = tripData;
        this.title = title;
        this.endLocation = endLocation;
        this.startLocation = startLocation;
        this.tripTime = tripTime;
        this.startLatitude = startLat;
        this.startLongitude = startLon;
        this.endLongitude = endLon;
        this.endtLatitude = endLat;
        // by default the trip not start yet and upcoming
        tripStatus = TripStatus.UPCOMING;
        //concat title with start and end location to identify the Trip.
        tripId = title + startLocation + endLocation;
    }

    //construct a trip with Notes
    public Trip(String title, String tripData,String tripTime, String startLocation, String endLocation
            ,double startLat,double startLon,double endLat,double endLon,List<Note> notes) {
        this.tripDate = tripData;
        this.title = title;
        this.endLocation = endLocation;
        this.tripTime = tripTime;
        this.startLocation = startLocation;
        this.listOfNotes = notes;
        this.startLatitude = startLat;
        this.startLongitude = startLon;
        this.endLongitude = endLon;
        this.endtLatitude = endLat;
        // by default the trip not start yet and upcoming
        tripStatus = TripStatus.UPCOMING;
        // concat title with start and end location to identify the Trip.
        tripId = title + startLocation + endLocation;
    }


    //construct a trip with tripStatus
    public Trip(String title, String tripDate,String tripTime, String startLocation, String endLocation
            ,double startLat,double startLon,double endLat,double endLon,List<Note> notes,String status) {
        this.tripDate = tripDate;
        this.title = title;
        this.endLocation = endLocation;
        this.startLocation = startLocation;
        this.listOfNotes = notes;
        this.tripTime = tripTime;
        this.startLatitude = startLat;
        this.startLongitude = startLon;
        this.endLongitude = endLon;
        this.endtLatitude = endLat;
        // by default the trip not start yet and upcoming
        tripStatus = status;
        // concat title with start and end location to identify the Trip.
        tripId = title + startLocation + endLocation;
    }

    // setters

    public void setStartLongitude(double startLongitude) {
        this.startLongitude = startLongitude;
    }

    public void setEndtLatitude(double endtLatitude) {
        this.endtLatitude = endtLatitude;
    }

    public void setStartLatitude(double startLatitude) {
        this.startLatitude = startLatitude;
    }

    public void setEndLongitude(double endLongitude) {
        this.startLongitude = endLongitude;
    }

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

    public void setTripStatus(String tripStatus) { this.tripStatus = tripStatus; }

    public void setListOfNotes(List<Note> listOfNotes) { this.listOfNotes = listOfNotes; }

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

    public String getTripId() { return tripId; }

    public String getTripStatus() { return tripStatus; }

    public String getTripTime() {
        return tripTime;
    }

    public void setTripTime(String tripTime) {
        this.tripTime = tripTime;
    }

    public List<Note> getListOfNotes() { return listOfNotes; }

    public double getStartLatitude() {
        return startLatitude;
    }

    public double getEndLongitude() {
        return startLongitude;
    }

    public double getStartLongitude() {
        return startLongitude;
    }

    public double getEndtLatitude() {
        return endtLatitude;
    }
}