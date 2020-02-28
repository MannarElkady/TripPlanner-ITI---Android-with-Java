package com.example.tripplanner.core.repository.remote;

import com.example.tripplanner.core.firestoredb.FirestoreConnection;
import com.example.tripplanner.core.firestoredb.FirestoreContract;
import com.example.tripplanner.core.model.Trip;
import com.example.tripplanner.core.model.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

//Entities Names ( Trip , Note )
public class FirestoreRepository implements FirestoreContract {

    private FirestoreConnection firestoreConnection;


    public FirestoreRepository (String userId ){
        firestoreConnection = FirestoreConnection.getInstance(userId);
    }


    @Override
    public Task<Void> addTrip(Trip trip) {
        return firestoreConnection.addTrip(trip);
    }

    @Override
    public Task<QuerySnapshot> getAllTrips() {
        return firestoreConnection.getAllTrips();
    }

    @Override
    public Task<Void> deleteTrip(Trip trip) {
        return firestoreConnection.deleteTrip(trip);
    }

    @Override
    public Task<Void> updateTrip(Trip oldTrip, Trip newTrip) {
        return firestoreConnection.updateTrip(oldTrip,newTrip);
    }

    @Override
    public Task<DocumentSnapshot> getTrip(Trip trip) {
        return firestoreConnection.getTrip(trip);
    }

    @Override
    public DocumentReference getUserDocumentReference() {
        return firestoreConnection.getUserDocumentReference();
    }
}
