package com.dimkov.bgMountains.domain.models.view;

import com.dimkov.bgMountains.domain.entities.Mountain;

public class PeakViewModel {
    private String id;
    private String name;
    private String description;
    private double elevation;
    private String imageUrl;
    private Mountain location;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getElevation() {
        return elevation;
    }

    public void setElevation(double elevation) {
        this.elevation = elevation;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Mountain getLocation() {
        return location;
    }

    public void setLocation(Mountain location) {
        this.location = location;
    }
}
