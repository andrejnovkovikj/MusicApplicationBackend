package com.example.fakefy.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Entity
@JsonIgnoreProperties({"playlists"})
@Table(name = "music_user")
public class MusicUser {
    @Id
    private String uid;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany
    @JoinTable(
            name = "liked_songs",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id")
    )
    private Set<Song> likedSongs = new HashSet<>();


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Playlist> playlists = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "liked_albums",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "album_id")
    )
    private Set<Album> likedAlbums = new HashSet<>();

    public MusicUser(){}

    public MusicUser( String email) {

        this.email = email;
        this.likedSongs = new HashSet<>();
        this.likedAlbums = new HashSet<>();
        this.playlists = new ArrayList<>();
        this.role = Role.USER;
    }

    public Set<Song> getLikedSongs() {
        return likedSongs;
    }

    public void setLikedSongs(Set<Song> likedSongs) {
        this.likedSongs = likedSongs;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public Set<Album> getLikedAlbums() {
        return likedAlbums;
    }

    public void setLikedAlbums(Set<Album> likedAlbums) {
        this.likedAlbums = likedAlbums;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
