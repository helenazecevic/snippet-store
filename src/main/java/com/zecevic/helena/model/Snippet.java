package com.zecevic.helena.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Snippet {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private String description;

    @NotNull
    @Column(length = 2000)
    private String code;

    @NotNull
    @ManyToOne
    private ProgrammingLanguage language;

    private String url;

    @NotNull
    private Date date;

    private long timeRemaining;

    private boolean blocked;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "snippet", cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    public Snippet() {
        comments = new ArrayList<Comment>();
    }

    public Snippet(String description, String code, ProgrammingLanguage language, String url, Date date, long timeRemaining, boolean blocked, User user, List<Comment> comments) {
        this.description = description;
        this.code = code;
        this.language = language;
        this.url = url;
        this.date = date;
        this.timeRemaining = timeRemaining;
        this.blocked = blocked;
        this.user = user;
        this.comments = comments;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ProgrammingLanguage getLanguage() {
        return language;
    }

    public void setLanguage(ProgrammingLanguage language) {
        this.language = language;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(long timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
