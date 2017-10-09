package com.zecevic.helena.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Rating {

    @Id
    @GeneratedValue
    private long id;

    private int positive;

    private int negative;

    @OneToMany(mappedBy = "rating", cascade = CascadeType.REMOVE)
    private List<Vote> votes;

    @JsonIgnore
    @OneToOne(mappedBy = "rating")
    private Comment comment;

    public Rating() {
        votes = new ArrayList<Vote>();
    }

    public Rating(int positive, int negative, List<Vote> votes, Comment comment) {
        this.positive = positive;
        this.negative = negative;
        this.votes = votes;
        this.comment = comment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPositive() {
        return positive;
    }

    public void setPositive(int positive) {
        this.positive = positive;
    }

    public int getNegative() {
        return negative;
    }

    public void setNegative(int negative) {
        this.negative = negative;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}