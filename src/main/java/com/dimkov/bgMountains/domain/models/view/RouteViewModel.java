package com.dimkov.bgMountains.domain.models.view;

public class RouteViewModel {
    private String start;
    private String end;
    private String description;
    private double lenght;
    private UserViewModel author;
    private MountainViewModel location;

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
