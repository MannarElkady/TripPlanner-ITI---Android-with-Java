package com.example.tripplanner.homescreen.homeview;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tripplanner.MainActivity;
import com.example.tripplanner.core.constant.TripStatus;
import com.example.tripplanner.core.firestoredb.FirestoreConnection;
import com.example.tripplanner.core.model.Trip;
import com.example.tripplanner.core.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TripsHomeViewModel extends ViewModel {
    FirestoreConnection firestoreConnection;
    List<Trip> allTrips;
    private MutableLiveData<List<Trip>> allTripsLiveList;

    public TripsHomeViewModel(){
        firestoreConnection = FirestoreConnection.getInstance(new User(FirebaseAuth.getInstance().getCurrentUser().getUid()));
        allTrips=new ArrayList<>();
    }

    public LiveData<List<Trip>> getCurrentTrips(){
        if(allTripsLiveList==null){
            allTripsLiveList = new MutableLiveData<>();
            firestoreConnection.getTrip(TripStatus.UPCOMING).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if(document.exists()){
                            Trip temp= document.toObject(Trip.class);
                            allTrips.add(temp);
                        }
                        allTripsLiveList.postValue(allTrips);
                    }
                }
            });
        }
        return allTripsLiveList;
    }
}
