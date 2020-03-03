package com.dimkov.bgMountains.domain.models.binding;

import javax.validation.constraints.NotEmpty;

public class CommentBindingModel {
    private String comment;
    private String date;
    private String freelancerId;

    public CommentBindingModel(String comment, String date, String freelancerId) {
        this.comment = comment;
        this.date = date;
        this.freelancerId = freelancerId;
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
}
