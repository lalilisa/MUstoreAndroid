package com.example.mustore.retrofit.response;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MyOrders implements Serializable {
    private String name;
    private Double totalPrice;
    private String code;
    private String address;
    private Long orderId;
    private Date createdAt;

    private Integer status;
    private List<Product> productViews;

    public MyOrders() {
    }

    public MyOrders(String name,Integer status, Double totalPrice, String code, String address, Long orderId, Date createdAt, List<Product> productViews) {
        this.name = name;
        this.status=status;
        this.totalPrice = totalPrice;
        this.code = code;
        this.address = address;
        this.orderId = orderId;
        this.createdAt = createdAt;
        this.productViews = productViews;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public List<Product> getProductViews() {
        return productViews;
    }

    public void setProductViews(List<Product> productViews) {
        this.productViews = productViews;
    }

    @Override
    public String toString() {
        return "MyOrders{" +
                "name='" + name + '\'' +
                ", totalPrice=" + totalPrice +
                ", address='" + address + '\'' +
                ", orderId=" + orderId +
                ", productViews=" + productViews +
                '}';
    }
}
