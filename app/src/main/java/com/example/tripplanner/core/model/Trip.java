package com.example.tripplanner.core.model;

import java.util.Date;

public class Trip {
    private String title;
    private String startLocation;
    private String endLocation;
    private String tripDate;

    public Trip(String title,String tripData){
        this.tripDate=tripData;
        this.title = title;
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
}
