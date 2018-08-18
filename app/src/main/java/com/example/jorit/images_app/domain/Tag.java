package com.example.jorit.images_app.domain;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Tag {

    @Id
    private long id;

    private String name;
    private boolean preferred;

    public Tag(long id, String name, boolean preferred) {
        this.id = id;
        this.name = name;
        this.preferred = preferred;
    }

    public Tag() {

    }

    public Tag(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPreferred() {
        return preferred;
    }

    public void setPreferred(boolean preferred) {
        this.preferred = preferred;
    }
}
