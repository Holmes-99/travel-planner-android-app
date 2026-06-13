package com.example.travelplanner.controllers;


import com.example.travelplanner.models.Trip;
import com.example.travelplanner.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TripController {
@Autowired
TripService tripService;
@GetMapping("/trip")
    public List<Trip> getAllTrips(){
    return tripService.getAllTrips();
}

}
