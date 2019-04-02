package com.dimkov.bgMountains.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;

@Entity(name = "routes")
public class Route extends BaseEntity {
    private String name;
    private String description;
    private double lenght;
    private User author;
    private Mountain location;

    @Column(nullable = false, updatable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
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

    @ManyToOne(targetEntity = User.class)
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @ManyToOne(targetEntity = Mountain.class)
    public Mountain getLocation() {
        return location;
    }

    public void setLocation(Mountain location) {
        this.location = location;
    }
}
