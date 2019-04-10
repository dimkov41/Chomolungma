package com.dimkov.bgMountains.domain.models.view;

import java.math.BigDecimal;

public class UserFreelancerViewModel {
    private String id;
    private String username;
    private String password;
    private String email;

    private boolean isFreelancer;
    private int ageExperience;
    private int certificateNumber;
    private String mobileNumber;
    private BigDecimal fee;
    private String imageUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isFreelancer() {
        return isFreelancer;
    }

    public void setFreelancer(boolean freelancer) {
        isFreelancer = freelancer;
    }

    public int getAgeExperience() {
        return ageExperience;
    }

    public void setAgeExperience(int ageExperience) {
        this.ageExperience = ageExperience;
    }

    public int getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(int certificateNumber) {
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
}
