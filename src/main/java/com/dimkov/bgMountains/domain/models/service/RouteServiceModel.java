package com.dimkov.bgMountains.domain.models.service;

public class RouteServiceModel {
    private String startPoint;
    private String endPoint;
    private String description;
    private double lenght;
    private UserServiceModel author;
    private MountainServiceModel location;

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
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
