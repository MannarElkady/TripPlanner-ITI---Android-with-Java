package com.example.tripplanner.core.model;

import java.io.Serializable;

public class MyDirectionData implements Serializable {
    private double startLongitude;
    private double endLongitude;
    private double startLatitude;
    private double endLatitude;

    public double getStartLongitude() {
        return startLongitude;
    }

    public double getEndLongitude() {
        return endLongitude;
    }

    public double getStartLatitude() {
        return startLatitude;
    }

    public double getEndLatitude() {
        return endLatitude;
    }

    public void setStartLongitude(double startLongitude) {
        this.startLongitude = startLongitude;
    }

    public void setEndLongitude(double endLongitude) {
        this.endLongitude = endLongitude;
    }

    public void setStartLatitude(double startLatitude) {
        this.startLatitude = startLatitude;
    }

    public void setEndLatitude(double endLatitude) {
        this.endLatitude = endLatitude;
    }
}
