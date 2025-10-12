package com.abhijeet.ishout;

public class Followers_ui_model {
    private String name;
    private int imageResId;

    public Followers_ui_model(String name, int imageResId) {
        this.name = name;
        this.imageResId = imageResId;
    }

    public String getName() {
        return name;
    }

    public int getImageResId() {
        return imageResId;
    }
}
