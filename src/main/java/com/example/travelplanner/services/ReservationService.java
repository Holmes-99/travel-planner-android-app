package com.example.travelplanner.services;

import com.example.travelplanner.models.Reservation;
import com.example.travelplanner.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

@Autowired
    ReservationRepository reservationRepository;

public List<Reservation> getAllReservations(){
    return reservationRepository.findAll();
}
}
