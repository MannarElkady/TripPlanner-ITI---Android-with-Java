package com.example.tripplanner.core.model;

import com.example.tripplanner.core.constant.TripStatus;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

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
    private boolean isRoundTrip ;
    private String secondtripTime;
    private String secondtripDate;
    private long tripNumricId;
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
        tripId = generateKey(title ,startLocation ,endLocation);
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
        tripId = generateKey(title ,startLocation ,endLocation);
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
        tripId = generateKey(title ,startLocation ,endLocation);
    }

    // setters


    public void setRoundTrip(boolean roundTrip) {
        isRoundTrip = roundTrip;
    }

    public void setSecondtripTime(String secondtripTime) {
        this.secondtripTime = secondtripTime;
    }

    public void setSecondtripDate(String secondtripDate) {
        this.secondtripDate = secondtripDate;
    }

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


    public boolean isRoundTrip() {
        return isRoundTrip;
    }

    public String getSecondtripTime() {
        return secondtripTime;
    }

    public String getSecondtripDate() {
        return secondtripDate;
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

    public long getTripNumricId() {
        return tripNumricId;
    }

    private String generateKey(String title, String startLocation, String endLocation) {
        StringBuilder sb = new StringBuilder();
        sb.append(startLocation);
        sb.append(title);
        sb.append(endLocation);
        String str;
        char[] input = sb.toString().toCharArray();
        int length = input.length;
        for(int i = 0 ;i<length;i++){
            Random random = new Random();
            int temp = random.nextInt(length-1);
            char ch = input[temp];
            tripNumricId+=(ch+temp+i);
            input[temp] = input[i];
            input[i] = ch;
        }
        return new String(input);
    }
}