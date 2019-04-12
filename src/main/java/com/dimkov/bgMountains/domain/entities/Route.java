package com.dimkov.bgMountains.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;

@Entity(name = "routes")
public class Route extends BaseEntity {
    private String start;
    private String end;
    private String description;
    private double lenght;
    private User author;
    private Mountain location;

    @Column(nullable = false)
    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    @Column(nullable = false)
    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
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
