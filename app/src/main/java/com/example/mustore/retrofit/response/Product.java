package com.example.mustore.retrofit.response;

import java.io.Serializable;

public class Product implements Serializable {

    private Long id;

    private String code;

    private Integer quantity;

    private Double discount;

    private Double price;

    private String name;

    private String img;

    private Integer view;

    private Long categoryId;

    private String description;

    private String categoryName;

    public Product(Long id, String code, Integer quantity, Double discount, Double price, String name, String img, Integer view, Long categoryId, String description, String categoryName) {
        this.id = id;
        this.code = code;
        this.quantity = quantity;
        this.discount = discount;
        this.price = price;
        this.name = name;
        this.img = img;
        this.view = view;
        this.categoryId = categoryId;
        this.description = description;
        this.categoryName = categoryName;
    }

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getView() {
        return view;
    }

    public void setView(Integer view) {
        this.view = view;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", quantity=" + quantity +
                ", discount=" + discount +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", img='" + img + '\'' +
                ", view=" + view +
                ", categoryId=" + categoryId +
                ", description='" + description + '\'' +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
