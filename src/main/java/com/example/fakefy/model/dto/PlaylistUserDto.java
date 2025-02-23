package com.example.fakefy.model.dto;

import com.example.fakefy.model.MusicUser;
import com.example.fakefy.model.Song;

import java.util.ArrayList;
import java.util.List;

public class PlaylistUserDto {
    private Long id;

    private String name;

    private MusicUser user;

    private String description;

    private String imageUrl;

    private List<Song> songs = new ArrayList<>();

    public PlaylistUserDto(Long id, String name, MusicUser user, String description, String imageUrl, List<Song> songs) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.description = description;
        this.imageUrl = imageUrl;
        this.songs = songs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MusicUser getUser() {
        return user;
    }

    public void setUser(MusicUser user) {
        this.user = user;
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

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

}