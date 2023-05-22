package com.example.mustore.retrofit.request;

public class UpdateCart {
    private Long productId;
    private Integer quantity;

    public UpdateCart() {
    }

    public UpdateCart(Long productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "UpdateCart{" +
                "productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
