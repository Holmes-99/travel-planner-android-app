package com.example.a1231279_1230239_courseproject;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SpecialFragment extends Fragment {

    LinearLayout listLayout;
    DataBaseHelper db;
    int userId;


    public SpecialFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_special, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listLayout = view.findViewById(R.id.layout_special_list);

        //load trips from db
        db = new DataBaseHelper(getActivity(), "TravelPlanner.db", null, 1);
        userId = SharedPreManager.getInstance(getActivity()).readInt("loggedInUserId", -1);
        loadTopRatedTrips();
    }

    private void loadTopRatedTrips() {
        listLayout.removeAllViews();

        Cursor cursor = db.getAllTrips();
        List<Trip> topRated = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                double rating= cursor.getDouble(
                        cursor.getColumnIndexOrThrow("RATING"));
                //special trips are ones with rating>= 4.5
                if (rating >= 4.5) {
                    Trip trip= new Trip();
                    trip.setId(cursor.getInt(cursor.getColumnIndexOrThrow("ID")));
                    trip.setCountry(cursor.getString(
                            cursor.getColumnIndexOrThrow("COUNTRY")));
                    trip.setDestination(cursor.getString(
                            cursor.getColumnIndexOrThrow("DESTINATION")));
                    trip.setDurationDays(cursor.getInt(
                            cursor.getColumnIndexOrThrow("DURATIONDAYS")));
                    trip.setPrice(cursor.getDouble(
                            cursor.getColumnIndexOrThrow("PRICE")));
                    trip.setRating(rating);
                    trip.setDescription(cursor.getString(
                            cursor.getColumnIndexOrThrow("DESCRIPTION")));
                    trip.setImage(cursor.getString(
                            cursor.getColumnIndexOrThrow("IMAGE")));
                    topRated.add(trip);

                }
            }

            while (cursor.moveToNext());
            cursor.close();
        }
        //no trips  with rating >=4.5
        if (topRated.isEmpty()) {
            TextView tv = new TextView(getActivity());
            tv.setText("No Top Rated trips found.");
            tv.setTextSize(16);
            tv.setPadding(24, 48, 24, 24);
            listLayout.addView(tv);
            return;
        }
        //show topRated
        for (Trip trip : topRated) {
            LinearLayout card = new LinearLayout(getActivity());
            card.setOrientation(LinearLayout.VERTICAL);
            card.setBackgroundColor(0xFFFFFFFF);
            card.setPadding(24, 24, 24, 24);
            LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            cardParams.setMargins(0, 0, 0, 16);
            card.setLayoutParams(cardParams);

            TextView tvInfo = new TextView(getActivity());
            tvInfo.setText("🔥 TOP RATED \n📍 " + trip.getDestination() +
                    ", " + trip.getCountry() +
                    "\n⭐ " + trip.getRating() + "/5   $" + trip.getPrice() +
                    "   🕐 " + trip.getDurationDays() + " days");
            tvInfo.setTextSize(15);
            tvInfo.setTextColor(0xFF1F6F5F);
            tvInfo.setPadding(0, 0, 0, 12);


            LinearLayout btnRow = new LinearLayout(getActivity());
            btnRow.setOrientation(LinearLayout.HORIZONTAL);
            //details of top rated trips
            Button btnDetail = new Button(getActivity());
            btnDetail.setText("View Details");
            btnDetail.setTextColor(0xFFFFFFFF);


            LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            btnParams.setMargins(0, 0, 8, 0);
            btnDetail.setLayoutParams(btnParams);

            btnDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((HomeActivity) getActivity()).openTripDetail(trip);
                }
            });

            Button btnFav = new Button(getActivity());
            boolean isFav = db.isFavourite(userId, trip.getId());
            btnFav.setText(isFav ? "❤ Favorited" : "🤍 Add Favorite");
            btnFav.setTextColor(0xFFFFFFFF);
            btnFav.setLayoutParams(new LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));


            btnFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (db.isFavourite(userId, trip.getId())) {
                        db.deleteFavourite(userId, trip.getId());
                        btnFav.setText("🤍 Favorite");
                        Toast.makeText(getActivity(),
                                "Removed from favorites", Toast.LENGTH_SHORT).show();
                    } else {
                        db.insertFavourite(userId, trip.getId());
                        btnFav.setText("❤ Favorited");
                        Toast.makeText(getActivity(),
                                "Added to favorites!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btnRow.addView(btnDetail);
            btnRow.addView(btnFav);
            card.addView(tvInfo);
            card.addView(btnRow);
            listLayout.addView(card);
        }
    }
}
