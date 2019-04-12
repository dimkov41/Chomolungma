package com.dimkov.bgMountains.domain.models.binding;

import com.dimkov.bgMountains.domain.models.service.UserServiceModel;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;

public class RouteAddBindingModel {
    private String name;
    private String description;
    private double lenght;
    private String username;
    private String mountainName;

    @NotEmpty
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotEmpty
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Range(min = 1)
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
