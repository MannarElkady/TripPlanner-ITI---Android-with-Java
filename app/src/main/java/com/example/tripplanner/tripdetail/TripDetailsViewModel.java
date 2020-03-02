package com.example.tripplanner.tripdetail;

import androidx.lifecycle.ViewModel;

import com.example.tripplanner.core.firestoredb.FirestoreConnection;
import com.example.tripplanner.core.model.Trip;
import com.example.tripplanner.core.model.User;
import com.google.firebase.auth.FirebaseAuth;
/*Manar*/
public class TripDetailsViewModel extends ViewModel {
    FirestoreConnection firestoreConnection;
    public TripDetailsViewModel(){
        firestoreConnection = FirestoreConnection.getInstance(new User(FirebaseAuth.getInstance().getCurrentUser().getUid()));
    }
    public void updateTrip(Trip oldTrip, Trip newTrip){
        firestoreConnection.updateTrip(oldTrip, newTrip);
    }
}
/*Manar*/
