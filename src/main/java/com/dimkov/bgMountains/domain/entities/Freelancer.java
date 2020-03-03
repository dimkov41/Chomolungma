package com.dimkov.bgMountains.domain.entities;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "freelancers")
public class Freelancer extends BaseEntity{
    private int ageExperience;
    private int certificateNumber;
    private String mobileNumber;
    private BigDecimal fee;
    private String imageUrl;
    private String fullName;
    private String description;
    private User user;
    private List<Date> employment;

    public Freelancer() {
        this.employment = new ArrayList<>();
    }

    @Column(nullable = false)
    public int getAgeExperience() {
        return ageExperience;
    }

    public void setAgeExperience(int ageExperience) {
        this.ageExperience = ageExperience;
    }

    @Column(nullable = false)
    public int getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(int certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    @Column(nullable = false)
    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Column(nullable = false)
    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    @Column(nullable = false)
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    @Column(nullable = false)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Column(nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToOne(targetEntity = User.class)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ElementCollection
    public List<Date> getEmployment() {
        return employment;
    }

    public void setEmployment(List<Date> employment) {
        this.employment = employment;
    }
}
