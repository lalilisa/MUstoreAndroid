package com.example.mustore.model;

import java.io.Serializable;

public class QRRawText implements Serializable {
    private String hashCode;

    public QRRawText() {
    }

    public QRRawText(String hashCode) {
        this.hashCode = hashCode;
    }

    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

    @Override
    public String toString() {
        return "QRRawText{" +
                "hashCode='" + hashCode + '\'' +
                '}';
    }
}
