package com.example.jorit.images_app.domain;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Image {
    @Id
    private long id;
    private String description;
    private String tag;
    private String type;
    private String location;

    public Image(long id, String description, String tag, String type, String location) {
        this.id = id;
        this.description = description;
        this.tag = tag;
        this.type = type;
        this.location = location;
    }

    public Image() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
