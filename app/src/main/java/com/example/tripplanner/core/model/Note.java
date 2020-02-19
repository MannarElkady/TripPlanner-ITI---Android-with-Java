package com.example.tripplanner.core.model;

public class Note {

    private String description;
    private int priority ;


    //construct Note with desc and priority if exist
    public Note(String description, int priority) {
        this.description = description;
        this.priority = priority;
    }

    //construct Note with desc only
    public Note(String description) {
        this.description = description;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
