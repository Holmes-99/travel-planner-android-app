package com.example.a1231279_1230239_courseproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {

    private List<Trip> trips;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public TripAdapter(List<Trip> trips,OnItemClickListener listener) {
        this.trips = trips;
        this.listener = listener;
    }

    @Override
    public TripViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trip_item, parent, false);
        return new TripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TripViewHolder holder, int position) {
        Trip trip = trips.get(position);

        holder.textViewCountry.setText(trip.getCountry());
        holder.textViewDestination.setText(trip.getDestination());
        holder.textViewDuration.setText(trip.getDurationDays() + " days");
        holder.textViewPrice.setText("₪" + trip.getPrice());
        holder.textViewRating.setText(trip.getRating() + "/5");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return trips.size();}

    public static class TripViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewCountry;
        public TextView textViewDestination;
        public TextView textViewDuration;
        public TextView textViewPrice;
        public TextView textViewRating;
        public TripViewHolder(View itemView) {
            super(itemView);
            textViewCountry = itemView.findViewById(R.id.textView_country);
            textViewDestination = itemView.findViewById(R.id.textView_destination);
            textViewDuration = itemView.findViewById(R.id.textView_duration);
            textViewPrice = itemView.findViewById(R.id.textView_price);
            textViewRating = itemView.findViewById(R.id.textView_rating);
        }
        }





}
