package com.dimkov.bgMountains.domain.models.binding;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

public class FreelancerChangeBindingModel {
    private int ageExperience;
    private String mobileNumber;
    private BigDecimal fee;
    private String description;
    private String password;

    @Min(value = 1)
    public int getAgeExperience() {
        return ageExperience;
    }

    public void setAgeExperience(int ageExperience) {
        this.ageExperience = ageExperience;
    }

    @NotEmpty
    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Min(value = 1)
    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    @NotEmpty
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotEmpty
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
