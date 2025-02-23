package com.example.fakefy.service.impl;

import com.example.fakefy.model.Album;
import com.example.fakefy.model.MusicUser;
import com.example.fakefy.model.Song;
import com.example.fakefy.repositories.AlbumRepository;
import com.example.fakefy.repositories.MusicUserRepository;
import com.example.fakefy.repositories.SongRepository;
import com.example.fakefy.service.MusicUserService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MusicUserServiceImpl implements MusicUserService {
    private final MusicUserRepository musicUserRepository;
    private final SongRepository songRepository;
    private final AlbumRepository albumRepository;

    public MusicUserServiceImpl(MusicUserRepository musicUserRepository, SongRepository songRepository, AlbumRepository albumRepository) {
        this.musicUserRepository = musicUserRepository;
        this.songRepository = songRepository;
        this.albumRepository = albumRepository;
    }

    @Override
    public MusicUser findByEmail(String email) {
        return this.musicUserRepository.findByEmail(email);
    }

    @Override
    public MusicUser findById(String uid) {
        return this.musicUserRepository.findByUid(uid);
    }

    @Override
    public List<MusicUser> listAll() {
        return musicUserRepository.findAll();
    }

    @Override
    public MusicUser create(String uid,String email) {
        MusicUser musicUser = new MusicUser();
        musicUser.setUid(uid);
        musicUser.setEmail(email);
        return this.musicUserRepository.save(musicUser);
    }

    @Override
    public MusicUser update(String uid,String email) {
        MusicUser user = this.musicUserRepository.findByEmail(email);
        user.setUid(uid);
        user.setEmail(email);
        return this.musicUserRepository.save(user);
    }

    @Override
    public MusicUser delete(String uid) {
        MusicUser user = this.musicUserRepository.findById(uid).orElseThrow();

        this.musicUserRepository.delete(user);
        return user;
    }
    @Override
    public void likeSong(String email, Long songId) {
        MusicUser user = musicUserRepository.findByEmail(email);
        Song song = songRepository.findById(songId).orElseThrow();
        user.getLikedSongs().add(song);
        musicUserRepository.save(user);
        songRepository.save(song);
    }
    @Override
    public void unlikeSong(String email, Long songId) {
        MusicUser user = musicUserRepository.findByEmail(email);
        Song song = songRepository.findById(songId).orElseThrow();
        user.getLikedSongs().remove(song);
        songRepository.save(song);
        musicUserRepository.save(user);
    }
    @Override
    public void likeAlbum(String email, Long albumId) {
        MusicUser user = musicUserRepository.findByEmail(email);
        Album album = albumRepository.findById(albumId).orElseThrow();
        user.getLikedAlbums().add(album);
        albumRepository.save(album);
        musicUserRepository.save(user);
    }
    @Override
    public void unlikeAlbum(String email, Long albumId) {
        MusicUser user = musicUserRepository.findByEmail(email);
        Album album = albumRepository.findById(albumId).orElseThrow();
        user.getLikedAlbums().remove(album);
        albumRepository.save(album);
        musicUserRepository.save(user);
    }

    @Override
    public Set<Song> getLikedSongsByEmail(String email) {
        Set<Song> songs = new HashSet<>();
        MusicUser user = this.musicUserRepository.findByEmail(email);
        songs.addAll(user.getLikedSongs());
        return songs;
    }

    @Override
    public Set<Album> getLikedAlbumsByEmail(String email) {
        Set<Album> albums = new HashSet<>();
        MusicUser user = this.musicUserRepository.findByEmail(email);
        albums.addAll(user.getLikedAlbums());
        return albums;
    }
}
