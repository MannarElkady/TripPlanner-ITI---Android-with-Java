package com.example.tripplanner.homescreen.homeview;

import android.util.Log;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tripplanner.addtrip.TripAddition;
import com.example.tripplanner.core.constant.TripStatus;
import com.example.tripplanner.core.firestoredb.FirestoreConnection;
import com.example.tripplanner.core.model.Trip;
import com.example.tripplanner.core.model.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;
// @author (Mannar - Ashraf)
public class TripsHomeViewModel extends ViewModel {
    FirestoreConnection firestoreConnection;
    List<Trip> allTrips;
    private MutableLiveData<List<Trip>> allTripsLiveList = new MutableLiveData<>();

    public TripsHomeViewModel() {
        firestoreConnection = FirestoreConnection.getInstance(new User(FirebaseAuth.getInstance().getCurrentUser().getUid()));
        allTrips = new ArrayList<>();
        setTripsListener(TripStatus.UPCOMING);

    }

    public LiveData<List<Trip>> getTripLiveData() {
        return allTripsLiveList;
    }

    public void setTripsListener(String tripStatus) {
        firestoreConnection.getTripsCollectionReference(tripStatus).addSnapshotListener(MetadataChanges.INCLUDE, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {

                } else {
                    if (queryDocumentSnapshots != null) {
                        List<DocumentChange> changes = queryDocumentSnapshots.getDocumentChanges();
                        for(DocumentChange documentChange : changes){
                            Log.i("changes", "onEvent: "+documentChange.getType() + "   "+documentChange.getDocument().get("title"));
                            Trip trip = documentChange.getDocument().toObject(Trip.class);
                            trip.setDocumentName(documentChange.getDocument().getReference().getId());
                            switch(documentChange.getType()){
                                case ADDED :
                                    allTrips.add(trip);
                                    break;
                                case REMOVED:
                                    delete(trip);
                                    Log.i("changes", "size: "+allTrips.size());
                                    break;
                                case MODIFIED:
                                    Log.i("displayed", "trip modified: "+trip.getTitle());
                                    updateTrip(trip, TripAddition.updateTrip);
                                    break;
                            }

                        }
                        allTripsLiveList.postValue(allTrips);
                    }
                }
            }
        });
    }

    private void updateTrip(Trip newTrip,Trip oldTrip) {
        allTrips.add(newTrip);
        for(Trip t : allTrips){
            if(t.getTripId().equals(oldTrip.getTripId())){
                allTrips.remove(t);
                return;
            }
        }
        allTripsLiveList.postValue(allTrips);
    }

    public Task<Void> updateCurrentTrip(Trip trip){
        return firestoreConnection.updateTrip(trip,trip);
    }
    private void delete(Trip trip) {
        for(Trip t : allTrips){
            if(t.getTripId().equals(trip.getTripId())){
                allTrips.remove(t);
                return;
            }
        }
        allTripsLiveList.postValue(allTrips);

    }

    public void deleteTrip(Trip trip, int index) {
        firestoreConnection.deleteTrip(trip);
        allTripsLiveList.getValue().remove(index);
    }
}
