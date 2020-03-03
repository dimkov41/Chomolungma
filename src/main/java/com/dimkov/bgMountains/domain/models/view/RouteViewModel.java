package com.dimkov.bgMountains.domain.models.view;

public class RouteViewModel {
    private String startPoint;
    private String endPoint;
    private String description;
    private double lenght;
    private UserViewModel author;
    private MountainViewModel location;

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

    public UserViewModel getAuthor() {
        return author;
    }

    public void setAuthor(UserViewModel author) {
        this.author = author;
    }

    public MountainViewModel getLocation() {
        return location;
    }

    public void setLocation(MountainViewModel location) {
        this.location = location;
    }
}
