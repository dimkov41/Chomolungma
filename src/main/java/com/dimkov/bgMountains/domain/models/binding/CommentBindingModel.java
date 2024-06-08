package com.dimkov.bgMountains.domain.models.binding;

import javax.validation.constraints.NotEmpty;

public class CommentBindingModel {
    private String comment;
    private String date;
    private String freelancerId;
    private String userCreated;

    public CommentBindingModel(String comment, String date, String freelancerId, String userCreated) {
        this.comment = comment;
        this.date = date;
        this.freelancerId = freelancerId;
        this.userCreated = userCreated;
    }

    @NotEmpty
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @NotEmpty
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @NotEmpty
    public String getFreelancerId() {
        return freelancerId;
    }

    public void setFreelancerId(String freelancerId) {
        this.freelancerId = freelancerId;
    }

    @NotEmpty
    public String getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(String userCreated) {
        this.userCreated = userCreated;
    }
}
