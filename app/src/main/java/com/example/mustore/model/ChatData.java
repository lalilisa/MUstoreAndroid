package com.example.mustore.model;

import java.io.Serializable;

public class ChatData implements Serializable {
    private Long senderId;
    private Long reciverId;
    private String content;
    private String room;

    public ChatData() {
    }

    public ChatData(Long senderId, Long reciverId, String content, String room) {
        this.senderId = senderId;
        this.reciverId = reciverId;
        this.content = content;
        this.room = room;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReciverId() {
        return reciverId;
    }

    public void setReciverId(Long reciverId) {
        this.reciverId = reciverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return "ChatData{" +
                "senderId=" + senderId +
                ", reciverId=" + reciverId +
                ", content='" + content + '\'' +
                ", room='" + room + '\'' +
                '}';
    }
}
