package com.dimkov.bgMountains.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "mountains")
public class Mountain extends BaseEntity {
    private String name;
    private String description;
    private String imageUrl;
    private List<Peak> peaks;
    private List<Hut> huts;
    private List<Route> routes;

    public Mountain() {
        this.peaks = new ArrayList<>();
        this.huts = new ArrayList<>();
        this.routes = new ArrayList<>();
    }

    @Column(nullable = false, updatable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false,columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(nullable = false)
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @OneToMany(targetEntity = Peak.class, mappedBy = "location")
    public List<Peak> getPeaks() {
        return peaks;
    }

    public void setPeaks(List<Peak> peaks) {
        this.peaks = peaks;
    }

    @OneToMany(targetEntity = Hut.class,  mappedBy = "location")
    public List<Hut> getHuts() {
        return huts;
    }

    public void setHuts(List<Hut> huts) {
        this.huts = huts;
    }

    @OneToMany(targetEntity = Route.class, mappedBy = "location")
    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }
}
