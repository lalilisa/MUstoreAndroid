package com.example.mustore.model;

import java.io.Serializable;

public class OptionChart implements Serializable {
    private Integer month;
    private Integer year;
    private String type;
    private String typeChart;

    public OptionChart(Integer month, Integer year, String type, String typeChart) {
        this.month = month;
        this.year = year;
        this.type = type;
        this.typeChart = typeChart;
    }

    public OptionChart() {
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeChart() {
        return typeChart;
    }

    public void setTypeChart(String typeChart) {
        this.typeChart = typeChart;
    }

    @Override
    public String toString() {
        return "OptionChart{" +
                "month=" + month +
                ", year=" + year +
                ", type='" + type + '\'' +
                ", typeChart='" + typeChart + '\'' +
                '}';
    }
}
