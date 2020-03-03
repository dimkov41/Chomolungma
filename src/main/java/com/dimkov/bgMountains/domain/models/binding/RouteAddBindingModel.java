package com.dimkov.bgMountains.domain.models.binding;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;

public class RouteAddBindingModel {
    private String startPoint;
    private String endPoint;
    private String description;
    private double lenght;
    private String username;
    private String mountainName;

    @NotEmpty
    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    @NotEmpty
    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    @NotEmpty
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Range(min = 1, message = "Lenght must be at least 1km !")
    public double getLenght() {
        return lenght;
    }

    public void setLenght(double lenght) {
        this.lenght = lenght;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotEmpty
    public String getMountainName() {
        return mountainName;
    }

    public void setMountainName(String mountainName) {
        this.mountainName = mountainName;
    }
}
