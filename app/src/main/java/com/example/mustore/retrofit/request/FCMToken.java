package com.example.mustore.retrofit.request;

public class FCMToken {
    private String token;

    public FCMToken() {
    }

    public FCMToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "FCMToken{" +
                "token='" + token + '\'' +
                '}';
    }
}
