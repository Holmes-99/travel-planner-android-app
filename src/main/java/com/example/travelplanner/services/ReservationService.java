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
public Reservation addReservation(Reservation reservation){
    return reservationRepository.save(reservation);
}

public List<Reservation> getReservationsByUserId(Integer userId){
    List <Reservation> all = reservationRepository.findAll();
    List<Reservation> result;

    for(int i= 0 ; i< all.size() ; i++ ){
        if (userId){
            result.add(all.get(i));
             }
        }
    return result;
    }
}

