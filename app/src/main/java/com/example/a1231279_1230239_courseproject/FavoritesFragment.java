package com.example.a1231279_1230239_courseproject;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


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
                "select trips.* from trips inner join favorites " +
                        "on trips.id = favorites.tripid " +
                        "where favorites.userid=?",new String[]{String.valueOf(userId)});
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int tripId = cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
                String destination = cursor.getString(cursor.getColumnIndexOrThrow("DESTINATION"));
                String country = cursor.getString(cursor.getColumnIndexOrThrow("COUNTRY"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("PRICE"));
                double rating = cursor.getDouble(cursor.getColumnIndexOrThrow("Rating"));
                int duration = cursor.getInt(cursor.getColumnIndexOrThrow("DURATION DAYS"));
                String description = cursor.getString(cursor.getColumnIndexOrThrow("DESCRIPTION"));
                String image = cursor.getString(cursor.getColumnIndexOrThrow("IMAGE"));
            Trip trip= new Trip(tripId,country,destination,duration,price,rating,description,image);
            //card layout
                LinearLayout card = new LinearLayout(getActivity());
                card.setOrientation(LinearLayout.VERTICAL);
                card.setBackgroundColor(0xFFFFFFFF);
                card.setPadding(24, 24, 24, 24);
                LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                cardParams.setMargins(0, 0, 0, 16);
                card.setLayoutParams(cardParams);

            }
        }
            }
}