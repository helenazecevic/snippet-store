package com.zecevic.helena.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Comment {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private String text;

    @NotNull
    private Date date;

    @ManyToOne
    private User user;

    @OneToOne
    private Rating rating;

    @NotNull
    @ManyToOne
    @JsonIgnore
    private Snippet snippet;

    public Comment() {
        this.rating = new Rating();
    }

    public Comment(String text, Date date, User user, Rating rating, Snippet snippet) {
        this.text = text;
        this.date = date;
        this.user = user;
        this.rating = rating;
        this.snippet = snippet;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public Snippet getSnippet() {
        return snippet;
    }

    public void setSnippet(Snippet snippet) {
        this.snippet = snippet;
    }
}
