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


public class AdminTripsFragment extends Fragment {


    LinearLayout listLayout;
    DataBaseHelper db;

    public AdminTripsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_trips, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listLayout = view.findViewById(R.id.layout_admin_trips_list);
        db = new DataBaseHelper(getActivity(), "TravelPlanner.db", null, 1);
        loadTrips();
    }

    private void loadTrips() {

        //get trips
        listLayout.removeAllViews();
        Cursor cursor = db.getAllTrips();

        if (cursor !=null&& cursor.moveToFirst()) {
            do {
                int tripId= cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
                String destination= cursor.getString(cursor.getColumnIndexOrThrow("DESTINATION"));
                String country= cursor.getString(cursor.getColumnIndexOrThrow("COUNTRY"));
                double price= cursor.getDouble(cursor.getColumnIndexOrThrow("PRICE"));

                double rating= cursor.getDouble(cursor.getColumnIndexOrThrow("RATING"));
                int duration= cursor.getInt(cursor.getColumnIndexOrThrow("DURATIONDAYS"));
                String description= cursor.getString(cursor.getColumnIndexOrThrow("DESCRIPTION"));
                String image= cursor.getString(cursor.getColumnIndexOrThrow("IMAGE"));
                Trip trip= new Trip(tripId,country,destination,duration,price,rating, description, image);
                //trip card
                LinearLayout card= new LinearLayout(getActivity());

                card.setOrientation(LinearLayout.VERTICAL);
                card.setBackgroundColor(0xFFFFFFFF);
                card.setPadding(24, 24, 24, 24);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 0, 0, 12);
                card.setLayoutParams(params);

                TextView tvInfo= new TextView(getActivity());
                tvInfo.setText("📍 " + destination + ", " + country +
                        "\n⭐ " + rating + "/5   💲" + price +
                        "   🕐 " + duration + " days");
                tvInfo.setTextSize(14);
                tvInfo.setPadding(0,0,0,12);

                LinearLayout btnRow= new LinearLayout(getActivity());
                btnRow.setOrientation(LinearLayout.HORIZONTAL);
                //edit trip
                Button btnEdit= new Button(getActivity());
                btnEdit.setText("Edit");
                btnEdit.setBackgroundColor(0xFF306D29);
                btnEdit.setTextColor(0xFFFFFFFF);
                LinearLayout.LayoutParams editParams= new LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                editParams.setMargins(0,0,8,0);
                btnEdit.setLayoutParams(editParams);
                //delete trip
                Button btnDelete = new Button(getActivity());
                btnDelete.setText("Delete");
                btnDelete.setBackgroundColor(0xFFE57373);
                btnDelete.setTextColor(0xFFFFFFFF);
                btnDelete.setLayoutParams(new LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        AdminEditTripFragment editFragment= new AdminEditTripFragment();
                        Bundle args= new Bundle();
                        args.putInt("tripId", trip.getId());
                        args.putString("destination",trip.getDestination());
                        args.putString("country",trip.getCountry());
                        args.putInt("durationDays",trip.getDurationDays());
                        args.putDouble("price",trip.getPrice());
                        args.putDouble("rating",trip.getRating());
                        args.putString("description",trip.getDescription());
                        args.putString("image",trip.getImage());
                        editFragment.setArguments(args);
                        getParentFragmentManager()
                                .beginTransaction()
                                .replace(R.id.admin_fragment_container, editFragment)
                                .addToBackStack(null)
                                .commit();
                    }
                });


                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        db.deleteTrip(tripId);
                        Toast.makeText(getActivity(),"Trip deleted", Toast.LENGTH_SHORT).show();
                        loadTrips();
                    }
                });

                btnRow.addView(btnEdit);
                btnRow.addView(btnDelete);
                card.addView(tvInfo);
                card.addView(btnRow);
                listLayout.addView(card);

            } while (cursor.moveToNext());
            cursor.close();
        }
        else{
            TextView tv= new TextView(getActivity());
            tv.setText("No trips found.");
            tv.setPadding(24,48,24,24);
            listLayout.addView(tv);
        }
    }
}
