package com.example.fakefy.service;

import com.example.fakefy.model.Album;
import com.example.fakefy.model.MusicUser;
import com.example.fakefy.model.Song;

import java.util.List;
import java.util.Set;

public interface MusicUserService {
    MusicUser findByEmail(String email);
    MusicUser findById(String uid);
    List<MusicUser> listAll();
    MusicUser create(String uid, String email);
    MusicUser update(String uid, String email);
    MusicUser delete(String uid);
    void likeSong(String email, Long songId);
    void likeAlbum(String email, Long albumId);
    void unlikeSong(String email, Long songId);
    void unlikeAlbum(String email, Long albumId);
    Set<Song> getLikedSongsByEmail(String email);
    Set<Album> getLikedAlbumsByEmail(String email);
    /*MusicUser getCurrentUser();
    Set<Album> getLikedAlbumsByUsername(String username);
    Set<Album> getLikedAlbumsByUserId(Long id);
     */
}
