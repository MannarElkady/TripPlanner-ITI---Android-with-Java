package com.example.tripplanner.core.model;

public class MyDirectionData {
    private float startLongitude;
    private float endLongitude;
    private float startLatitude;
    private float endLatitude;

    public float getStartLongitude() {
        return startLongitude;
    }

    public float getEndLongitude() {
        return endLongitude;
    }

    public float getStartLatitude() {
        return startLatitude;
    }

    public float getEndLatitude() {
        return endLatitude;
    }

    public void setStartLongitude(float startLongitude) {
        this.startLongitude = startLongitude;
    }

    public void setEndLongitude(float endLongitude) {
        this.endLongitude = endLongitude;
    }

    public void setStartLatitude(float startLatitude) {
        this.startLatitude = startLatitude;
    }

    public void setEndLatitude(float endLatitude) {
        this.endLatitude = endLatitude;
    }
}
