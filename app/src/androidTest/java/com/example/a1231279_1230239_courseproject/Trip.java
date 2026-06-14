package com.example.a1231279_1230239_courseproject;

public class Trip {
    private int id;
    private String country;
    private String destination;
    private int duration;
    private double price;
    private double rating;
    private String description;
    private String image;

    public Trip(){}


    public Trip(int id, String country, String destination,
                int duration, double price, double rating,
                String description, String image) {
        this.id = id;
        this.country = country;
        this.destination = destination;
        this.duration = duration;
        this.price = price;
        this.rating = rating;
        this.description = description;
        this.image = image;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getCountry() {return country;}

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
