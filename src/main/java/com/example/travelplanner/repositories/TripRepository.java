package com.example.travelplanner.repositories;

import com.example.travelplanner.models.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip,Integer> {
}
