package com.example.mustore.retrofit.response;

public class DataCategoryChart {
    private Long categoryId;
    private String categoryName;
    private Double totalPrice;
    private Integer totalQuantity;
    private Double percentQuantity;
    private Double percentPrice;

    public DataCategoryChart() {
    }

    public DataCategoryChart(Long categoryId, String categoryName, Double totalPrice, Integer totalQuantity, Double percentQuantity, Double percentPrice) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.totalPrice = totalPrice;
        this.totalQuantity = totalQuantity;
        this.percentQuantity = percentQuantity;
        this.percentPrice = percentPrice;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Double getPercentQuantity() {
        return percentQuantity;
    }

    public void setPercentQuantity(Double percentQuantity) {
        this.percentQuantity = percentQuantity;
    }

    public Double getPercentPrice() {
        return percentPrice;
    }

    public void setPercentPrice(Double percentPrice) {
        this.percentPrice = percentPrice;
    }

    @Override
    public String toString() {
        return "DataCategoryChart{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", totalPrice=" + totalPrice +
                ", totalQuantity=" + totalQuantity +
                ", percentQuantity=" + percentQuantity +
                ", percentPrice=" + percentPrice +
                '}';
    }
}
