package com.example.jorit.images_app.domain;

public class Tag {
    private String name;
    private boolean preferred;

    public Tag(String name, boolean preferred) {
        this.name = name;
        this.preferred = preferred;
    }

    public Tag() {

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
