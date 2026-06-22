package com.example.a1231279_1230239_courseproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminEditTripFragment extends Fragment {

    public AdminEditTripFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_edit_trip, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        if (args == null) {
            Toast.makeText(getActivity(), "Trip data not found", Toast.LENGTH_SHORT).show();
            getParentFragmentManager().popBackStack();
            return;
        }
        int tripId = args.getInt("tripId");

        EditText etDestination = view.findViewById(R.id.editText_editDestination);
        EditText etCountry = view.findViewById(R.id.editText_editCountry);
        EditText etDuration = view.findViewById(R.id.editText_editDuration);
        EditText etPrice = view.findViewById(R.id.editText_editPrice);
        EditText etRating = view.findViewById(R.id.editText_editRating);
        EditText etDescription = view.findViewById(R.id.editText_editDescription);
        EditText etImage = view.findViewById(R.id.editText_editImage);
        Button btnSave = view.findViewById(R.id.button_saveTrip);

        //fill fields with previous trip data
        etDestination.setText(args.getString("destination"));
        etCountry.setText(args.getString("country"));
        etDuration.setText(String.valueOf(args.getInt("durationDays")));
        etPrice.setText(String.valueOf(args.getDouble("price")));
        etRating.setText(String.valueOf(args.getDouble("rating")));
        etDescription.setText(args.getString("description"));
        etImage.setText(args.getString("image"));

        DataBaseHelper db = new DataBaseHelper(getActivity(), "TravelPlanner.db", null, 1);
        //save changes
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get updated trip data
                String destination = etDestination.getText().toString().trim();
                String country = etCountry.getText().toString().trim();
                String durationStr = etDuration.getText().toString().trim();
                String priceStr = etPrice.getText().toString().trim();
                String ratingStr = etRating.getText().toString().trim();
                String description = etDescription.getText().toString().trim();
                String image = etImage.getText().toString().trim();

                //check id empty required fields
                if (destination.isEmpty() || country.isEmpty() || durationStr.isEmpty() || priceStr.isEmpty() || ratingStr.isEmpty() || description.isEmpty()) {
                    Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    int duration= Integer.parseInt(durationStr);
                    double price= Double.parseDouble(priceStr);
                    double rating= Double.parseDouble(ratingStr);

                    if(rating< 0|| rating > 5){
                        Toast.makeText(getActivity(), "Rating must be between 0 and 5", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    //obj for updated trip
                    Trip trip=new Trip();
                    trip.setId(tripId);
                    trip.setDestination(destination);
                    trip.setCountry(country);
                    trip.setDurationDays(duration);
                    trip.setPrice(price);
                    trip.setRating(rating);
                    trip.setDescription(description);
                    trip.setImage(image);

                    int result=db.updateTrip(trip);

                    if (result > 0) {
                        Toast.makeText(getActivity(),"Trip updated !",Toast.LENGTH_SHORT).show();
                        getParentFragmentManager().popBackStack();
                    } else {
                        Toast.makeText(getActivity(),"Update failed",Toast.LENGTH_SHORT).show();
                    }
                }
                catch(NumberFormatException e){
                    Toast.makeText(getActivity(),"Enter valid numbers",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    }