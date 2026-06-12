package com.example.travelplanner.services;

import com.example.travelplanner.models.Trip;
import com.example.travelplanner.repositories.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class TripService {

    @Autowired
    TripRepository tripRepository;

    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    public List<Trip> getTopRatedTrips() {
        List<Trip> all = tripRepository.findAll();

        List<Trip> topRated = new ArrayList<Trip>();

        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getRating() != null && all.get(i).getRating() >= 4.5) {
                topRated.add(all.get(i));
            }
        }
        return topRated;
    }

    public Trip addTrip(Trip trip){
    return tripRepository.save(trip);
    }

    public Trip update Trip(Trip trip , Integer id){
       trip.setId(id);
       return tripRepository.save(trip);
    }
}


