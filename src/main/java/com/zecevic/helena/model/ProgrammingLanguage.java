package com.zecevic.helena.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class ProgrammingLanguage {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "language")
    private List<Snippet> snippets;

    public ProgrammingLanguage() {
    }

    public ProgrammingLanguage(String name, List<Snippet> snippets) {
        this.name = name;
        this.snippets = snippets;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Snippet> getSnippets() {
        return snippets;
    }

    public void setSnippets(List<Snippet> snippets) {
        this.snippets = snippets;
    }
}