package com.example.travelplanner.controllers;

import com.example.travelplanner.models.Reservation;
import com.example.travelplanner.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @GetMapping("/reservations")

    public List <Reservation> getAllReservations(){
        return reservationService.getAllReservations();
    }
    @PostMapping("/reservations")

    public Reservation addReservation(@RequestBody Reservation reservation) {
        return reservationService.addReservation(reservation);
    }

    @GetMapping("/reservations/user/{userId}")

    public List<Reservation> getReservationsByUser(Integer userId) {
        return reservationService.getReservationsByUserId(userId);
    }


}
