package com.dimkov.bgMountains.domain.models.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public class FreelancerServiceModel {
    private String id;
    private int ageExperience;
    private String certificateNumber;
    private String mobileNumber;
    private BigDecimal fee;
    private String imageUrl;
    private String fullName;
    private String description;
    private UserServiceModel user;

    private List<Date> workingDates;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAgeExperience() {
        return ageExperience;
    }

    public void setAgeExperience(int ageExperience) {
        this.ageExperience = ageExperience;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserServiceModel getUser() {
        return user;
    }

    public void setUser(UserServiceModel user) {
        this.user = user;
    }

    public List<Date> getWorkingDates() {
        return workingDates;
    }

    public void setWorkingDates(List<Date> workingDates) {
        this.workingDates = workingDates;
    }

    @Override
    public String toString() {
        return "FreelancerServiceModel{" +
                "id='" + id + '\'' +
                ", ageExperience=" + ageExperience +
                ", certificateNumber='" + certificateNumber + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", fee=" + fee +
                ", imageUrl='" + imageUrl + '\'' +
                ", fullName='" + fullName + '\'' +
                ", description='" + description + '\'' +
                ", user=" + user +
                ", workingDates=" + workingDates +
                '}';
    }
}
