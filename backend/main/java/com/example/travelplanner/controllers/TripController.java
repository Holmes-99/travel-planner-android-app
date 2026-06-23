package com.example.travelplanner.controllers;


import com.example.travelplanner.models.Trip;
import com.example.travelplanner.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class TripController {

@Autowired
TripService tripService;

    @GetMapping("/trips")

    public List<Trip> getAllTrips(){
    return tripService.getAllTrips();
}
    @GetMapping("/trips/top-rated")

    public List<Trip> getTopRatedTrips() {
       return tripService.getTopRatedTrips();
    }


    @PostMapping("/trips")

    public Trip addTrip(@RequestBody Trip trip) {
      return tripService.addTrip(trip);
     }

    @PutMapping("/trips/{id}")

    public Trip updateTrip(@PathVariable Integer id, @RequestBody Trip trip) {
        return tripService.updateTrip(trip,id);
    }

    @DeleteMapping("/trips/{id}")

    public void deleteTrip(@PathVariable Integer id) {
        tripService.deleteTrip(id); }

}
