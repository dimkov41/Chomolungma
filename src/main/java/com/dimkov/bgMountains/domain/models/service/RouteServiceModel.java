package com.dimkov.bgMountains.domain.models.service;

public class RouteServiceModel {
    private String start;
    private String end;
    private String description;
    private double lenght;
    private UserServiceModel author;
    private MountainServiceModel location;

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLenght() {
        return lenght;
    }

    public void setLenght(double lenght) {
        this.lenght = lenght;
    }

    public UserServiceModel getAuthor() {
        return author;
    }

    public void setAuthor(UserServiceModel author) {
        this.author = author;
    }

    public MountainServiceModel getLocation() {
        return location;
    }

    public void setLocation(MountainServiceModel location) {
        this.location = location;
    }
}
