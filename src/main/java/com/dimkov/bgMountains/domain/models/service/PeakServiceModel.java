package com.dimkov.bgMountains.domain.models.service;

import com.dimkov.bgMountains.domain.entities.Mountain;
import org.springframework.web.multipart.MultipartFile;

public class PeakServiceModel {
    private String id;
    private String name;
    private String description;
    private double elevation;
    private String image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Mountain getLocation() {
        return location;
    }

    public void setLocation(Mountain location) {
        this.location = location;
    }
}
