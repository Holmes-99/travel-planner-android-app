package com.example.a1231279_1230239_courseproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {
    @Override
    public int getItemCount() {
        return trips.size();}

    public static class TripViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewCountry;
        public TextView textViewDestination;
        public TextView textViewDuration;
        public TextView textViewPrice;
        public TextView textViewRating;
        public TextView textViewDescription;
        public ImageView imageView;

        public TripViewHolder(View itemView) {
            super(itemView);
            textViewCountry = itemView.findViewById(R.id.textView_country);
            textViewDestination = itemView.findViewById(R.id.textView_destination);
            textViewDuration = itemView.findViewById(R.id.textView_duration);
            textViewPrice = itemView.findViewById(R.id.textView_price);
            textViewRating = itemView.findViewById(R.id.textView_rating);
            textViewDescription = itemView.findViewById(R.id.textView_description);
            imageView = itemView.findViewById(R.id.imageView_trip);

        }
    }
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
        holder.textViewDescription.setText(trip.getDescription());
        String imageUrl = trip.getImage();

        if (imageUrl != null && !imageUrl.trim().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.travel_planner)
                    .error(R.drawable.travel_planner)
                    .into(holder.imageView);
        } else {
            holder.imageView.setImageResource(R.drawable.travel_planner);
        }
        Button buttonFavorite = holder.itemView.findViewById(R.id.button_favorite);
        Button buttonReserve = holder.itemView.findViewById(R.id.button_reserve);

        DataBaseHelper db = new DataBaseHelper(
                holder.itemView.getContext(), "TravelPlanner.db", null, 1);

        SharedPreManager prefs = SharedPreManager.getInstance(
                holder.itemView.getContext());
        int userId = prefs.readInt("loggedInUserId", -1);

        if (db.isFavourite(userId, trip.getId())) {
            buttonFavorite.setText("<3> Favorited");
        } else {
            buttonFavorite.setText("click to favorite");
        }

        buttonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (db.isFavourite(userId, trip.getId())) {
                    db.deleteFavourite(userId, trip.getId());
                    buttonFavorite.setText("<3 Favorite");
                } else {
                    db.insertFavourite(userId, trip.getId());
                    buttonFavorite.setText("❤ Favorited");
                }
            }
        });

        buttonReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(holder.getAdapterPosition());
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(holder.getAdapterPosition());
            }
        });
    }







}
