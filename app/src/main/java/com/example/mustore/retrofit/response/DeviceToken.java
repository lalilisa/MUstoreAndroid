package com.example.mustore.retrofit.response;

public class DeviceToken {
    private Long id;

    private String fcmToken;

    private Long userId;

    public DeviceToken() {
    }

    public DeviceToken(Long id, String fcmToken, Long userId) {
        this.id = id;
        this.fcmToken = fcmToken;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "DeviceToken{" +
                "id=" + id +
                ", fcmToken='" + fcmToken + '\'' +
                ", userId=" + userId +
                '}';
    }
}


