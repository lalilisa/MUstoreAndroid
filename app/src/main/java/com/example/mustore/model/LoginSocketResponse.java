package com.example.mustore.model;

import com.example.mustore.common.Category;
import com.example.mustore.retrofit.response.LoginResponse;

import java.util.Date;

public class LoginSocketResponse {
    private Integer status;
    private Long userId;
    private Category.Role role;
    private String accessToken;
    private String refreshToken;
    private Date expireAccressToken;

    public LoginSocketResponse() {
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Category.Role getRole() {
        return role;
    }

    public void setRole(Category.Role role) {
        this.role = role;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Date getExpireAccressToken() {
        return expireAccressToken;
    }

    public void setExpireAccressToken(Date expireAccressToken) {
        this.expireAccressToken = expireAccressToken;
    }

    public LoginSocketResponse(Integer status, Long userId, Category.Role role, String accessToken, String refreshToken, Date expireAccressToken) {
        this.status = status;
        this.userId = userId;
        this.role = role;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expireAccressToken = expireAccressToken;
    }
}
