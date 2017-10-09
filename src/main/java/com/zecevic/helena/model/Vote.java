package com.zecevic.helena.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Vote {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private VoteStatus status;

    @JsonIgnore
    @ManyToOne
    private Rating rating;

    public Vote() {
    }

    public Vote(User user, VoteStatus status, Rating rating) {
        this.user = user;
        this.status = status;
        this.rating = rating;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public VoteStatus getStatus() {
        return status;
    }

    public void setStatus(VoteStatus status) {
        this.status = status;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }
}
