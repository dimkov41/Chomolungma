package com.dimkov.bgMountains.domain.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * @author rudi
 * @created 6.08.24 г.
 */
@Entity(name = "users_hires")
public class UserHires extends BaseEntity {
    private Freelancer freelancer;
    private User employer;
    private Date employmentDate;

    @ManyToOne
    public Freelancer getFreelancer() {
        return freelancer;
    }

    public void setFreelancer(Freelancer freelancer) {
        this.freelancer = freelancer;
    }

    @ManyToOne
    public User getEmployer() {
        return employer;
    }

    public void setEmployer(User employer) {
        this.employer = employer;
    }

    public Date getEmploymentDate() {
        return employmentDate;
    }

    public void setEmploymentDate(Date employmentDate) {
        this.employmentDate = employmentDate;
    }
}
