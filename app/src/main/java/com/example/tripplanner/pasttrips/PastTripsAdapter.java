package com.example.tripplanner.pasttrips;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tripplanner.R;
import com.example.tripplanner.core.model.Trip;
import com.example.tripplanner.homescreen.homeview.CurrentTripsHomeFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

public class PastTripsAdapter extends RecyclerView.Adapter<PastTripsAdapter.PastTripsViewHolder> {

    private List<Trip> pastTrips;
    private CurrentTripsHomeFragment.OnRecycleItemClickListener pastTripListner;

    public PastTripsAdapter(List<Trip> pastTrips, CurrentTripsHomeFragment.OnRecycleItemClickListener listener) {
        this.pastTrips = pastTrips;
        pastTripListner = listener;
    }

    public static class PastTripsViewHolder extends RecyclerView.ViewHolder{
        public View myView;

        public PastTripsViewHolder(@NonNull View itemView) {
            super(itemView);
            myView = itemView;
        }
    }

    @NonNull
    @Override
    public PastTripsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.one_row_trip_layout, parent, false);

        return new PastTripsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PastTripsViewHolder holder, int position) {
        TextView tripTitle = holder.myView.findViewById(R.id.trip_title_textview);
        tripTitle.setText(pastTrips.get(position).getTitle());

        TextView tripDate = holder.myView.findViewById(R.id.date_textview);
        tripDate.setText(pastTrips.get(position).getTripDate());
        tripDate.setTypeface(tripDate.getTypeface(), Typeface.BOLD);

        TextView tripLocationFromTo = holder.myView.findViewById(R.id.tripFromToLocationOverview);
        tripLocationFromTo.setText("From:  "+pastTrips.get(position).getStartLocation()
                +"  to: "+pastTrips.get(position).getEndLocation());
        tripLocationFromTo.setTextSize(14f); // Why this is done here, not in XML?!
        tripLocationFromTo.setTypeface(tripTitle.getTypeface(), Typeface.BOLD);

        // What to do with the image?!
        //ImageView tripImage = holder.myView.findViewById(R.id.trip_imageview);

        holder.myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pastTripListner.onItemClick(holder.myView, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pastTrips.size();
    }
}
