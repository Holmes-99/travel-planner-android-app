package com.example.a1231279_1230239_courseproject.network;

import com.example.a1231279_1230239_courseproject.database.Trip;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TripJsonParser {

    public static List<Trip> getTripsFromJson(String json) {
        List<Trip> trips = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
        for (int i=0; i<jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Trip trip = new Trip();
            trip.setId(jsonObject.getInt("id"));
            trip.setCountry(jsonObject.getString("country"));
            trip.setDestination(jsonObject.getString("destination"));
            trip.setDurationDays(jsonObject.getInt("durationDays"));
            trip.setPrice(jsonObject.getDouble("price"));
            trip.setRating(jsonObject.getDouble("rating"));
            trip.setDescription(jsonObject.getString("description"));
            trip.setImage(jsonObject.getString("image"));
            trips.add(trip);
        }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return trips;


        }
    }

