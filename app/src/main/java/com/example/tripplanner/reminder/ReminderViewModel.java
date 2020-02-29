package com.example.tripplanner.reminder;

import androidx.lifecycle.ViewModel;

import com.example.tripplanner.core.firestoredb.FirestoreConnection;
import com.example.tripplanner.core.model.Trip;
import com.example.tripplanner.core.model.User;
import com.google.firebase.auth.FirebaseAuth;

public class ReminderViewModel extends ViewModel {
    FirestoreConnection firestoreConnection;
    // TODO: Implement the ViewModel
    public ReminderViewModel(){
        firestoreConnection = FirestoreConnection.getInstance(new User(FirebaseAuth.getInstance().getCurrentUser().getUid()));
    }
    public void updateTrip(Trip oldTrip,Trip newTrip){
        firestoreConnection.updateTrip(oldTrip, newTrip);
    }
}
