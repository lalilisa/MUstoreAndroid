package com.example.mustore.model;

import androidx.annotation.NonNull;

import java.util.Date;

public class Notification {
    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", title='" + title + '\'' +
                ", img='" + img + '\'' +
                ", userId=" + userId +
                ", mainRef=" + mainRef +
                ", type='" + type + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    private Long id;


    private String content;


    private String title;


    private String img;


    private Long userId;


    private Long mainRef;

    private String type;

    private Date createdAt;
    public Notification() {
    }

    public Notification(Long id, String content, Date createdAt) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMainRef() {
        return mainRef;
    }

    public void setMainRef(Long mainRef) {
        this.mainRef = mainRef;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
