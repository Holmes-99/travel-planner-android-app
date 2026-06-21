package com.example.a1231279_1230239_courseproject;

import android.content.res.ColorStateList;
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


public class FavoritesFragment extends Fragment {
    LinearLayout listLayout;
    DataBaseHelper db;
    int userId;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listLayout= view.findViewById(R.id.layout_favorites_list);
        db= new DataBaseHelper(getActivity(),"TravelPlanner.db",null, 1);
        userId =SharedPreManager.getInstance(getActivity()).readInt("loggedInUserId",-1);
        loadFavorites();
    }
    private void loadFavorites() {
        listLayout.removeAllViews();
        Cursor cursor = db.getWritableDatabase().rawQuery(
                "SELECT TRIPS.* FROM TRIPS INNER JOIN FAVOURITES " +
                        "ON TRIPS.ID = FAVOURITES.TRIPID " +
                        "WHERE FAVOURITES.USERID=?",new String[]{String.valueOf(userId)});

        if (cursor!= null && cursor.getCount()>0){
            while (cursor.moveToNext()){
                int tripId =cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
                String destination= cursor.getString(cursor.getColumnIndexOrThrow("DESTINATION"));
                String country = cursor.getString(cursor.getColumnIndexOrThrow("COUNTRY"));
                double price= cursor.getDouble(cursor.getColumnIndexOrThrow("PRICE"));
                double rating =cursor.getDouble(cursor.getColumnIndexOrThrow("RATING"));
                int duration= cursor.getInt(cursor.getColumnIndexOrThrow("DURATIONDAYS"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("DESCRIPTION"));
                String image = cursor.getString(cursor.getColumnIndexOrThrow("IMAGE"));
            Trip trip= new Trip(tripId,country,destination,duration,price,rating,description,image);

            //card layout
                LinearLayout card= new LinearLayout(getActivity());
                card.setOrientation(LinearLayout.VERTICAL);
                card.setBackgroundColor(0xFFFFFFFF);
                card.setPadding(24, 24, 24, 24);
                LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                cardParams.setMargins(0, 0, 0, 16);
                card.setLayoutParams(cardParams);

                TextView tvInfo= new TextView(getActivity());
                tvInfo.setText(destination + ", " + country +
                        "\n " + rating + "/5   $ " + price +
                        "    " + duration + " days");
                tvInfo.setTextSize(15);
                tvInfo.setPadding(0,0,0,12);
                LinearLayout btnRow= new LinearLayout(getActivity());
                btnRow.setOrientation(LinearLayout.HORIZONTAL);

                Button btnDetail= new Button(getActivity());
                btnDetail.setText("View Details");
                btnDetail.setTextColor(0xFFFFFFFF);

                LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);

                btnParams.setMargins(0,0,8,0);
                btnDetail.setLayoutParams(btnParams);

                btnDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openTripDetail(trip);

                    }
                });

                Button btnRemove = new Button(getActivity());
                btnRemove.setText("Remove");
                btnRemove.setTextColor(0xFFFFFFFF);

                btnRemove.setLayoutParams(new LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));



                btnRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.deleteFavourite(userId, tripId);
                        Toast.makeText(getActivity(),
                                "Removed from favorites", Toast.LENGTH_SHORT).show();
                        loadFavorites();
                    }
                });

                Button btnReserve = new Button(getActivity());
                btnReserve.setText("Reserve");
                btnReserve.setTextColor(0xFFFFFFFF);
                btnReserve.setLayoutParams(new LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                btnReserve.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        openTripDetail(trip);

                    }
                });

                btnRow.addView(btnDetail);
                btnRow.addView(btnRemove);
                card.addView(tvInfo);
                card.addView(btnRow);
                btnRow.addView(btnReserve);

                listLayout.addView(card);
            }
            cursor.close();

        }
        //no favorites
        else{
            TextView tv = new TextView(getActivity());
            tv.setText("No favorites yet. Add some trips!");
            tv.setTextSize(16);
            tv.setPadding(24,48,24,24);
            listLayout.addView(tv);

        }
            }
    private void openTripDetail(Trip trip) {
        TripDetailFragment detailFragment = new TripDetailFragment();

        Bundle args = new Bundle();
        args.putInt("tripId", trip.getId());
        args.putString("destination", trip.getDestination());
        args.putString("country", trip.getCountry());
        args.putInt("durationDays", trip.getDurationDays());
        args.putDouble("price", trip.getPrice());
        args.putDouble("rating", trip.getRating());
        args.putString("description", trip.getDescription());
        args.putString("image", trip.getImage());

        detailFragment.setArguments(args);

        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .addToBackStack(null)
                .commit();
    }
}