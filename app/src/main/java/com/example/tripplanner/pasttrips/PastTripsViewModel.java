package com.example.tripplanner.pasttrips;

import com.example.tripplanner.MainActivity;
import com.example.tripplanner.core.firestoredb.FirestoreConnection;
import com.example.tripplanner.core.model.Trip;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PastTripsViewModel extends ViewModel {
    private FirestoreConnection firestoreConnection;
    private List<Trip> pastTripsTemp;
    private MutableLiveData<List<Trip>> pastTrips;

    // Instead of MainActivity.me
    /*private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = firebaseAuth.getCurrentUser();*/

    public PastTripsViewModel() {
        firestoreConnection = FirestoreConnection.getInstance(MainActivity.me);
        pastTripsTemp = new ArrayList<Trip>();
    }

    public LiveData<List<Trip>> getPastTrips(){

        if(pastTrips == null){
            pastTrips = new MutableLiveData<>();
            firestoreConnection.getAllTrips().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if(document.exists()){
                            Trip trip = document.toObject(Trip.class);
                            pastTripsTemp.add(trip);
                        }
                    }
                    pastTrips.postValue(pastTripsTemp);
                }
            });
        }
        return pastTrips;
    }
}
