package com.dimkov.bgMountains.domain.models.binding;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class FreelancerRegisterBindingModel {
    private int ageExperience;
    private int certificateNumber;
    private String mobileNumber;
    private BigDecimal fee;
    private MultipartFile image;

    @NotNull
    public int getAgeExperience() {
        return ageExperience;
    }

    public void setAgeExperience(int ageExperience) {
        this.ageExperience = ageExperience;
    }

    @NotNull
    public int getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(int certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    @NotEmpty
    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @DecimalMin("0.00")
    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    @NotNull
    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
