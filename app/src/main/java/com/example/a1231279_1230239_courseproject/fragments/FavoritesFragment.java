package com.example.a1231279_1230239_courseproject.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1231279_1230239_courseproject.database.DataBaseHelper;
import com.example.a1231279_1230239_courseproject.R;
import com.example.a1231279_1230239_courseproject.database.Reservation;
import com.example.a1231279_1230239_courseproject.utils.SharedPreManager;
import com.example.a1231279_1230239_courseproject.database.Trip;
import com.example.a1231279_1230239_courseproject.activities.HomeActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


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
        userId = SharedPreManager.getInstance(getActivity()).readInt("loggedInUserId",-1);
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
                card.setBackgroundColor(0xFFE7E1B1);                card.setPadding(24, 24, 24, 24);
                LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                cardParams.setMargins(0, 0, 0, 16);
                card.setLayoutParams(cardParams);

                TextView tvInfo= new TextView(getActivity());
                tvInfo.setText(
                        "📍 " + destination + ", " + country +
                                "\n⭐ " + rating + "/5   💲" + price +
                                "   🕐 " + duration + " days");
                tvInfo.setTextSize(15);
                tvInfo.setTextColor(0xFF0D530E);
                tvInfo.setPadding(0,0,0,12);
                LinearLayout btnRow= new LinearLayout(getActivity());
                btnRow.setOrientation(LinearLayout.HORIZONTAL);

                Button btnDetail= new Button(getActivity());
                btnDetail.setText("View Details");
                btnDetail.setBackgroundColor(0xFF0D530E);
                btnDetail.setTextColor(0xFFFFFFFF);

                LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);

                btnParams.setMargins(0,0,8,0);
                btnDetail.setLayoutParams(btnParams);

                btnDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((HomeActivity) getActivity()).openTripDetail(trip);
                    }
                });

                Button btnRemove = new Button(getActivity());
                btnRemove.setText("Remove");
                btnRemove.setBackgroundColor(0xFFFBF5DD);
                btnRemove.setTextColor(0xFF0D530E);

                btnRemove.setLayoutParams(new LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));



                btnRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        db.deleteFavourite(userId, tripId);
                        Toast.makeText(getActivity(), "Removed from favorites", Toast.LENGTH_SHORT).show();
                        loadFavorites();
                    }
                });

                Button btnReserve = new Button(getActivity());
                btnReserve.setText("Reserve");
                btnReserve.setBackgroundColor(0xFF306D29);
                btnReserve.setTextColor(0xFFFFFFFF);
                btnReserve.setLayoutParams(new LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                btnReserve.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showReservationDialog(userId, trip.getId(), trip.getDestination(), db);
                    }
                });

                btnRow.addView(btnDetail);
                btnRow.addView(btnReserve);
                btnRow.addView(btnRemove);

                card.addView(tvInfo);
                card.addView(btnRow);
                listLayout.addView(card);

            }
            cursor.close();

        }
        //no favorites
        else{
            TextView tv = new TextView(getActivity());
            tv.setText("No favorites yet. Add some trips!");
            tv.setTextSize(16);
            tv.setTextColor(0xFF306D29);
            tv.setPadding(24,48,24,24);
            listLayout.addView(tv);

        }

    }
    private void showReservationDialog(int userId, int tripId,
                                       String destination, DataBaseHelper db) {
        View dialogView = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_reservation, null);

        EditText editTextQuantity = dialogView.findViewById(R.id.editText_quantity);
        Spinner spinnerType = dialogView.findViewById(R.id.spinner_type);

        String[] typeOptions = {"Solo", "Group", "Family", "Couple"};
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                typeOptions
        );
        spinnerType.setAdapter(typeAdapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Reserve: " + destination);
        builder.setView(dialogView);

        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String quantityStr = editTextQuantity.getText().toString().trim();

                if (quantityStr.isEmpty()) {
                    Toast.makeText(getActivity(),
                            "Please enter quantity",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                int quantity = Integer.parseInt(quantityStr);
                String type = spinnerType.getSelectedItem().toString();
                String date = new SimpleDateFormat("yyyy-MM-dd",
                        Locale.getDefault()).format(new Date());

                Reservation reservation = new Reservation();
                reservation.setUserId(userId);
                reservation.setTripId(tripId);
                reservation.setTripDestination(destination);
                reservation.setQuantity(quantity);
                reservation.setType(type);
                reservation.setDate(date);
                reservation.setStatus("Confirmed");

                db.insertReservation(reservation);
                Toast.makeText(getActivity(), "Reservation confirmed!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

}