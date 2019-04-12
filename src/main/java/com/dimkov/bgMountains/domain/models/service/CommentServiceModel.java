package com.dimkov.bgMountains.domain.models.service;

import com.dimkov.bgMountains.domain.entities.Freelancer;

public class CommentServiceModel {
    private String comment;
    private String date;
    private String freelancerId;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFreelancerId() {
        return freelancerId;
    }

    public void setFreelancerId(String freelancerId) {
        this.freelancerId = freelancerId;
    }
}
