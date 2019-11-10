package com.abdul.githubcontributions.model;

public class ContributionModel {
    private String color;
    private Integer count;
    private String date;

    public ContributionModel(String color, Integer count, String date) {
        this.color = color;
        this.count = count;
        this.date = date;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
