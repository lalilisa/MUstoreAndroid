package com.example.mustore.retrofit.response;

import androidx.annotation.NonNull;

import com.example.mustore.common.Category;

import java.util.Date;

public class LoginResponse {

    private Long userId;
    private Category.Role role;
    private String accessToken;
    private String refreshToken;
    private Date expireAccressToken;

    private Integer isNotifi;

    public LoginResponse() {
    }

    public LoginResponse(Long userId, Category.Role role, String accessToken, String refreshToken, Date expireAccressToken,Integer isnoti) {
        this.userId = userId;
        this.role = role;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expireAccressToken = expireAccressToken;
        this.isNotifi=isnoti;
    }

    public Integer getIsNotifi() {
        return isNotifi;
    }

    public void setIsNotifi(Integer isNotifi) {
        this.isNotifi = isNotifi;
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

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
