package com.dimkov.bgMountains.domain.models.view;

import com.dimkov.bgMountains.domain.entities.Mountain;

public class PeakViewModel {
    private String id;
    private String name;
    private String description;
    private String imageUrl;
    private MountainViewModel location;


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
        return description.substring(0, 101) + "...";
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public MountainViewModel getLocation() {
        return location;
    }

    public void setLocation(MountainViewModel location) {
        this.location = location;
    }
}
