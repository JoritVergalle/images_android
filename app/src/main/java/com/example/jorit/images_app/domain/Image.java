package com.example.jorit.images_app.domain;

public class Image {
    private String id;
    private String description;
    private String tag;

    public Image(String id, String description, String tag) {
        this.id = id;
        this.description = description;
        this.tag = tag;
    }

    public Image() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
