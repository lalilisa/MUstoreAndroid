package com.example.mustore.model;

public class SlideItem {

    private int image;

    public SlideItem(int image) {
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "SlideItem{" +
                "image=" + image +
                '}';
    }
}
