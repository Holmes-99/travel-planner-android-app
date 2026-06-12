package com.example.travelplanner.models;
import jakarta.persistence.*;

        @Entity
        @Table(name = "trips")

public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Column(name = "destination")
    private String destination;
    @Column(name = "country")
    private String country;
    @Column(name = "duration_days")
    private Integer durationDays;
    @Column(name = "price")
    private Double price;
    @Column(name = "rating")
    private Double rating;
    @Column(name = "description")
    private String description;
    @Column(name = "image")
    private String image;

     public Trip(){

     }

            public Trip(Integer id, String destination, String country,
                        Integer durationDays, Double price, Double rating,
                        String description, String image) {
                this.id = id;
                this.destination = destination;
                this.country = country;
                this.durationDays = durationDays;
                this.price = price;
                this.rating = rating;
                this.description = description;
                this.image = image;
            }

            public Integer getId() {
                return id;
            }

            public String getDestination() {
                return destination;
            }

            public String getCountry() {
                return country;
            }

            public Integer getDurationDays() {
                return durationDays;
            }

            public Double getPrice() {
                return price;
            }

            public Double getRating() {
                return rating;
            }

            public String getDescription() {
                return description;
            }

            public String getImage() {
                return image;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public void setDestination(String destination) {
                this.destination = destination;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public void setDurationDays(Integer durationDays) {
                this.durationDays = durationDays;
            }

            public void setPrice(Double price) {
                this.price = price;
            }

            public void setRating(Double rating) {
                this.rating = rating;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public void setImage(String image) {
                this.image = image;
            }
        }
