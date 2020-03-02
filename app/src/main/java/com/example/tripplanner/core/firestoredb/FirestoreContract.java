package com.example.tripplanner.core.firestoredb;

import com.example.tripplanner.core.model.Trip;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public interface FirestoreContract {


    //add trip to user collection of trips
    Task<Void> addTrip(Trip trip);

    // get all user trips
    Task<QuerySnapshot> getAllTrips();


    // delete trip from user trip collection
    Task<Void> deleteTrip(Trip trip);


    //update trip in user trip collection
    Task<Void> updateTrip(Trip oldTrip, Trip newTrip);

    //update trip in user trip collection with the same id
    Task<Void> updateTrip(Trip newTrip);

    //get a specific trip
    Task<DocumentSnapshot> getTrip(Trip trip);

    //get document reference
    DocumentReference getUserDocumentReference();

    Task<QuerySnapshot> getTrips(String tripStatus);

    Task<QuerySnapshot> getTrip(String tripStatus);
}