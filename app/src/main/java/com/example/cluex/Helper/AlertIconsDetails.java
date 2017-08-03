package com.example.cluex.Helper;

/**
 * Created by Awais on 7/4/2017.
 */
public class AlertIconsDetails {

    private String name;
    private int photo;

    public AlertIconsDetails(String name, int photo) {
        this.name = name;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}