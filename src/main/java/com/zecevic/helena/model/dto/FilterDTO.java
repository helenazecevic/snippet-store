package com.zecevic.helena.model.dto;

import com.zecevic.helena.model.ProgrammingLanguage;

import java.util.Date;

public class FilterDTO {

    private String description;

    private ProgrammingLanguage language;

    private Date startDate;

    private Date endDate;

    public FilterDTO() {
    }

    public FilterDTO(String description, ProgrammingLanguage language, Date startDate, Date endDate) {
        this.description = description;
        this.language = language;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProgrammingLanguage getLanguage() {
        return language;
    }

    public void setLanguage(ProgrammingLanguage language) {
        this.language = language;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
