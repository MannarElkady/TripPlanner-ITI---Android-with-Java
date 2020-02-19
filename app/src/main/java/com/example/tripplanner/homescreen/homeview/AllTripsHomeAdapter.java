package com.example.tripplanner.homescreen.homeview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripplanner.R;
import com.example.tripplanner.core.model.Trip;

import java.util.List;

public class AllTripsHomeAdapter extends RecyclerView.Adapter<AllTripsHomeAdapter.TripHolder> {
    Context ctx;
    List<Trip> myTripList;
    View generatedRow;

    public AllTripsHomeAdapter(Context context, List<Trip> myTripList){
        ctx = context;
        this.myTripList = myTripList;
    }
    @Override
    public AllTripsHomeAdapter.TripHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =LayoutInflater.from(ctx);
        generatedRow = layoutInflater.inflate(R.layout.one_row_trip_layout,parent,false);
        TripHolder tripHolder = new TripHolder(generatedRow);
        return tripHolder;
    }

    @Override
    public void onBindViewHolder(AllTripsHomeAdapter.TripHolder holder, int position) {
        holder.title.setText(myTripList.get(position).getTitle());
        holder.date.setText(myTripList.get(position).getTripDate());
       // Picasso.get().load().into(holder.tripImage);
        holder.locationFromTo.setText("From: "+myTripList.get(position).getStartLocation()
                +"to: "+myTripList.get(position).getEndLocation());
    }

    @Override
    public int getItemCount() {
        return myTripList.size();
    }

    public class TripHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView date;
        TextView locationFromTo;
        ImageView tripImage;
        public TripHolder(View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.trip_title_textview);
            this.tripImage = itemView.findViewById(R.id.trip_imageview);
            this.date = itemView.findViewById(R.id.date_textview);
            this.locationFromTo = itemView.findViewById(R.id.tripFromToLocationOverview);
        }
    }
}
