package com.example.a1231279_1230239_courseproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TripDetailFragment extends Fragment {

        public TripDetailFragment() {
            // Required empty public constructor
        }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
                // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trip_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        int tripId = args.getInt("tripId");
        String destination = args.getString("destination");
        String country = args.getString("country");
        int durationDays = args.getInt("durationDays");
        double price = args.getDouble("price");
        double rating = args.getDouble("rating");
        String description = args.getString("description");

        TextView textViewDestination = view.findViewById(R.id.textView_detailDestination);
        TextView textViewCountry = view.findViewById(R.id.textView_detailCountry);
        TextView textViewDuration = view.findViewById(R.id.textView_detailDuration);
        TextView textViewPrice = view.findViewById(R.id.textView_detailPrice);
        TextView textViewRating = view.findViewById(R.id.textView_detailRating);
        TextView textViewDescription = view.findViewById(R.id.textView_detailDescription);
        Button buttonFavorite = view.findViewById(R.id.button_favorite);
        Button buttonReserve = view.findViewById(R.id.button_reserve);

        textViewDestination.setText(destination);
        textViewCountry.setText("Country: " + country);
        textViewDuration.setText("Duration: " + durationDays + " days");
        textViewPrice.setText("Price: ₪" + price);
        textViewRating.setText("Rating: " + rating + "/5");
        textViewDescription.setText(description);

        SharedPreManager prefs = SharedPreManager.getInstance(getActivity());
        int userId = prefs.readInt("loggedInUserId", -1);

        DataBaseHelper db = new DataBaseHelper(
                getActivity(), "TravelPlanner.db", null, 1);

        // update favorite button
        if (db.isFavourite(userId, tripId)) {
            buttonFavorite.setText("<3> Favorited");
        } else {
            buttonFavorite.setText("Add to Favorites");
        }

        buttonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (db.isFavourite(userId, tripId)) {
                    db.deleteFavourite(userId, tripId);
                    buttonFavorite.setText("Add to Favorites");
                    Toast.makeText(getActivity(),
                            "Removed from favorites",
                            Toast.LENGTH_SHORT).show();
                } else {
                    db.insertFavourite(userId, tripId);
                    buttonFavorite.setText("❤ Favorited");
                    Toast.makeText(getActivity(),
                            "Added to favorites!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReservationDialog(userId, tripId, destination, db);
            }
        });
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