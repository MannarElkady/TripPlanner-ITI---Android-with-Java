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
    private String tripDate;
    private String tripStatus;

    public void setTripKey(String tripKey) {
        this.tripKey = tripKey;
    }

    private String tripKey ;

    //for Room initialization
    public TripEntity(){}

    @Ignore
    public TripEntity(String title, String tripData, String startLocation, String endLocation,String tripStatus) {
        this.tripDate = tripData;
        this.title = title;
        this.endLocation = endLocation;
        this.startLocation = startLocation;

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
