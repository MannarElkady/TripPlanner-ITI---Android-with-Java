package com.example.tripplanner.core.model;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    private String userId;
    private String userName;
    private String password;
    private String userEmail;

    public User(String userId){
        this.userName = userName;
        this.userId = userId;
    }
    public User(String userId, String userEmail, String password){
        this.password = password;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userId = userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getUserEmail() {
        return userEmail;
    }
}
