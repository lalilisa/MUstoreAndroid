package com.example.mustore.model;

import java.io.Serializable;

public class HomeSearch implements Serializable {

    private String keyword;

    private Long categoryId;

    public HomeSearch(String keyword, Long categoryId) {
        this.keyword = keyword;
        this.categoryId = categoryId;
    }

    public HomeSearch() {
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

}
