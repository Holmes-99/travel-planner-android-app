package com.example.travelplanner.repositories;

import com.example.travelplanner.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository <Reservation,Integer>{
}
