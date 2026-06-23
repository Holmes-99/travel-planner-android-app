package com.example.a1231279_1230239_courseproject.database;

public class Reservation {
    private int id;
    private int tripId;
    private int userId;
    private String type;
    private String date;
    private String status;
    private String tripDestination;
    private int quantity;

    public Reservation() {
    }

    public Reservation(int id, int tripId, int userId,
                       String type, String date, String status,
                       String tripDestination, int quantity) {
        this.id = id;
        this.tripId = tripId;
        this.userId = userId;
        this.type = type;
        this.date = date;
        this.status = status;
        this.tripDestination = tripDestination;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTripDestination() {
        return tripDestination;
    }

    public void setTripDestination(String tripDestination) {
        this.tripDestination = tripDestination;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}


