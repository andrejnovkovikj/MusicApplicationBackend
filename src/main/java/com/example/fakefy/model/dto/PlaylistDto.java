package com.example.fakefy.model.dto;

public class PlaylistDto {
    private String name;
    private String description;
    private String imageUrl;
    private String uid;

    public PlaylistDto(String name, String description, String imageUrl,String uid) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.uid = uid;

    }

    public PlaylistDto(String name, String description, String imageUrl) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public PlaylistDto(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
