package com.dimkov.bgMountains.domain.models.binding;

import org.springframework.web.multipart.MultipartFile;

public class PeakAddBindingModel {
    private String name;
    private String description;
    private double elevation;
    private MultipartFile image;
    private String mountainName;

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

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getMountainName() {
        return mountainName;
    }

    public void setMountainName(String mountainName) {
        this.mountainName = mountainName;
    }
}
