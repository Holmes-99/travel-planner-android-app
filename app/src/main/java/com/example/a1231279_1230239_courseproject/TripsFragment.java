package com.example.a1231279_1230239_courseproject;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class TripsFragment extends Fragment {

    private List <Trip> allTrips = new ArrayList<>();
    private List<Trip> filteredTrips = new ArrayList<>();
    private TripAdapter tripAdapter;
    private EditText editTextSearch;
    private Spinner spinnerFilter;

    private void loadTripsFromDB() {
        allTrips.clear();
        DataBaseHelper db = new DataBaseHelper(
                getActivity(), "TravelPlanner.db", null, 1);
        Cursor cursor = db.getAllTrips();
        while (cursor.moveToNext()) {
            Trip trip = new Trip();
            trip.setId(cursor.getInt(0));
            trip.setCountry(cursor.getString(1));
            trip.setDestination(cursor.getString(2));
            trip.setDurationDays(cursor.getInt(3));
            trip.setPrice(cursor.getDouble(4));
            trip.setRating(cursor.getDouble(5));
            trip.setDescription(cursor.getString(6));
            trip.setImage(cursor.getString(7));
            allTrips.add(trip);
        }
        cursor.close();
        filteredTrips.clear();
        filteredTrips.addAll(allTrips);
    }

    private void filterTrips(String query, String country) {
        filteredTrips.clear();
        for (Trip trip : allTrips) {
            boolean matchesSearch = query.isEmpty() ||
                    trip.getDestination().toLowerCase()
                            .contains(query.toLowerCase()) ||
                    trip.getCountry().toLowerCase()
                            .contains(query.toLowerCase());
            boolean matchesFilter = country.equals("All") ||
                    trip.getCountry().equals(country);
            if (matchesSearch && matchesFilter) {
                filteredTrips.add(trip);
            }
        }
        tripAdapter.notifyDataSetChanged();
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





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trips, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextSearch = view.findViewById(R.id.editText_search);
        spinnerFilter = view.findViewById(R.id.spinner_filter);
        RecyclerView recyclerViewTrips = view.findViewById(R.id.recyclerView_trips);

        loadTripsFromDB();

        recyclerViewTrips.setLayoutManager(new LinearLayoutManager(getActivity()));
        tripAdapter = new TripAdapter(filteredTrips, new TripAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                openTripDetail(filteredTrips.get(position));
            }
            });
        recyclerViewTrips.setAdapter(tripAdapter);

        //filter
        String[] filterOptions = {"All","USA","Japan",
                "Germany", "France", "Italy","Egypt",
                "China","Spain","Australia","Zimbabwe"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_spinner_item, filterOptions);
        spinnerFilter.setAdapter(adapter);
        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String selectedCountry = parent.getItemAtPosition(position).toString();
            filterTrips(editTextSearch.getText().toString(), selectedCountry);

        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {}});
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                filterTrips(s.toString(),
                        spinnerFilter.getSelectedItem().toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

        }
