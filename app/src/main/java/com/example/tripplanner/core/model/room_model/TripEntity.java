package com.example.tripplanner.core.model.room_model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.tripplanner.core.constant.TripStatus;
import com.example.tripplanner.core.model.Note;

import java.util.List;
import java.util.Random;

@Entity (tableName = "Trip" , indices = @Index(value = {"tripKey"},unique = true))
public class TripEntity {

    @PrimaryKey(autoGenerate = true)
    private Integer tripId;

    private String title;
    private String startLocation;
    private String endLocation;
    private double startLatitude;
    private double startLongitude;
    private double endtLatitude;
    private double endLongitude;
    private String tripDate;
    private String tripStatus;

    public void setTripKey(String tripKey) {
        this.tripKey = tripKey;
    }

    private String tripKey ;

    //for Room initialization
    public TripEntity(){}

    @Ignore
    public TripEntity(String title, String tripData, String startLocation, String endLocation,String tripStatus
            ,double startLatitude , double startLongitude ,double endtLatitude,double endLongitude) {
        this.tripDate = tripData;
        this.title = title;
        this.endLocation = endLocation;
        this.startLocation = startLocation;

        this.startLatitude=startLatitude;
        this.startLongitude=startLongitude;
        this.endtLatitude=endtLatitude;
        this.endLongitude=endLongitude;
        // by default the trip not start yet and upcoming
        this.tripStatus = tripStatus;
        //concat title with start and end location to identify the Trip.
        tripKey = generateKey(title , startLocation , endLocation);
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
            input[temp] = input[i];
            input[i] = ch;
        }
        return new String(input);
    }


    // setters

    public void setStartLatitude(double startLatitude) {
        this.startLatitude = startLatitude;
    }

    public void setStartLongitude(double startLongitude) {
        this.startLongitude = startLongitude;
    }

    public void setEndtLatitude(double endtLatitude) {
        this.endtLatitude = endtLatitude;
    }

    public void setEndLongitude(double endLongitude) {
        this.endLongitude = endLongitude;
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

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public void setTripStatus(String tripStatus) { this.tripStatus = tripStatus; }


    // getters
    public double getStartLatitude() {
        return startLatitude;
    }

    public double getStartLongitude() {
        return startLongitude;
    }

    public double getEndtLatitude() {
        return endtLatitude;
    }

    public double getEndLongitude() {
        return endLongitude;
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

    public Integer getTripId() { return tripId; }

    public String getTripStatus() { return tripStatus; }


    public String getTripKey() {
        return tripKey;
    }

}
