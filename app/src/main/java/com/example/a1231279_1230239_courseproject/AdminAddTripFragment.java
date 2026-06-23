package com.example.a1231279_1230239_courseproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AdminAddTripFragment extends Fragment {

    public AdminAddTripFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_add_trip, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText etDestination= view.findViewById(R.id.editText_addDestination);
        EditText etCountry= view.findViewById(R.id.editText_addCountry);
        EditText etDuration= view.findViewById(R.id.editText_addDuration);
        EditText etPrice= view.findViewById(R.id.editText_addPrice);
        EditText etRating= view.findViewById(R.id.editText_addRating);
        EditText etDescription= view.findViewById(R.id.editText_addDescription);
        EditText etImage= view.findViewById(R.id.editText_addImage);
        Button btnAdd=view.findViewById(R.id.button_addTrip);

        DataBaseHelper db=new DataBaseHelper(
                getActivity(), "TravelPlanner.db", null, 1);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String destination= etDestination.getText().toString().trim();
                String country= etCountry.getText().toString().trim();
                String durationStr= etDuration.getText().toString().trim();
                String priceStr= etPrice.getText().toString().trim();
                String ratingStr= etRating.getText().toString().trim();
                String description= etDescription.getText().toString().trim();
                String image= etImage.getText().toString().trim();
                //validate required fields
                if (destination.isEmpty()||country.isEmpty()||durationStr.isEmpty()|| priceStr.isEmpty()|| ratingStr.isEmpty()|| description.isEmpty()){
                    Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                double rating= Double.parseDouble(ratingStr);
                //validate rating range
                if (rating <0 ||rating> 5){
                    Toast.makeText(getActivity(), "Rating must be between 0 and 5", Toast.LENGTH_SHORT).show();
                    return;
                }
                //create obj for the new trip
                Trip trip= new Trip();
                trip.setDestination(destination);
                trip.setCountry(country);
                trip.setDurationDays(Integer.parseInt(durationStr));
                trip.setPrice(Double.parseDouble(priceStr));
                trip.setRating(rating);
                trip.setDescription(description);
                trip.setImage(image);


                long result=db.insertTrip(trip);
                if (result!=-1){
                    Toast.makeText(getActivity(), "Trip added successfully!", Toast.LENGTH_SHORT).show();
                    //clear fields after successful insertion
                    etDestination.setText("");
                    etCountry.setText("");
                    etDuration.setText("");
                    etPrice.setText("");
                    etRating.setText("");
                    etDescription.setText("");
                    etImage.setText("");
                }
                else {
                    Toast.makeText(getActivity(), "Failed to add trip", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}