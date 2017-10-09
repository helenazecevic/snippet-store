package com.zecevic.helena.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Column(unique = true, nullable = false)
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    private String lastName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String email;

    @NotNull
    @OneToOne
    private Address address;

    private String picture;

    private boolean blocked;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Snippet> snippetList;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Vote> votes;

    public User() {
        snippetList = new ArrayList<Snippet>();
        comments = new ArrayList<Comment>();
        votes = new ArrayList<Vote>();
    }

    public User(String username, String password, String name, String lastName, Role role, String phoneNumber,
                String email, Address address, String picture) {
        this();
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.picture = picture;
        this.blocked = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public List<Snippet> getSnippetList() {
        return snippetList;
    }

    public void setSnippetList(List<Snippet> snippetList) {
        this.snippetList = snippetList;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

}