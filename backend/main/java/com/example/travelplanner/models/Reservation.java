package com.example.travelplanner.models;
import jakarta.persistence.*;

@Entity @Table (name = "reservations")
public class Reservation {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private Integer userId;

    @Column
    private Integer tripId;

    @Column
    private String tripDestination;

    @Column
    private Integer quantity;

    @Column
    private String type;

    @Column
    private String date;

    @Column
    private String status;

    public Reservation(){
    }

    public Reservation(Integer id, Integer userId,
                       Integer tripId, String tripDestination,
                       Integer quantity, String type, String date,
                       String status) {
        this.id = id;
        this.userId = userId;
        this.tripId = tripId;
        this.tripDestination = tripDestination;
        this.quantity = quantity;
        this.type = type;
        this.date = date;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getTripId() {
        return tripId;
    }

    public String getTripDestination() {
        return tripDestination;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public void setTripDestination(String tripDestination) {
        this.tripDestination = tripDestination;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}





