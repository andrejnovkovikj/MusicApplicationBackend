package com.example.fakefy.service.impl;

import com.example.fakefy.model.MusicUser;
import com.example.fakefy.model.Playlist;
import com.example.fakefy.model.Song;
import com.example.fakefy.repositories.*;
import com.example.fakefy.service.PlaylistService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlaylistServiceImpl implements PlaylistService {
    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;
    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;
    private final UserRepository userRepository;

    public PlaylistServiceImpl(AlbumRepository albumRepository, ArtistRepository artistRepository, PlaylistRepository playlistRepository, SongRepository songRepository, UserRepository userRepository) {
        this.albumRepository = albumRepository;
        this.artistRepository = artistRepository;
        this.playlistRepository = playlistRepository;
        this.songRepository = songRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Playlist findById(Long id) {
        return this.playlistRepository.findById(id).orElse(null);
    }

    @Override
    public Playlist findByName(String name) {
        return this.playlistRepository.findByName(name);
    }

    @Override
    public List<Playlist> listAll() {
        return this.playlistRepository.findAll();
    }

    @Override
    public Playlist create(String name, String description, String imageUrl,String email) {
        MusicUser user = this.userRepository.findByEmail(email).orElseThrow();
        Playlist playlist = new Playlist(name,description,imageUrl,user);
        return this.playlistRepository.save(playlist);
    }

    @Override
    public Playlist update(Long id, String name, String description, String imageUrl) {
        Playlist playlist = this.findById(id);
        playlist.setName(name);
        playlist.setDescription(description);
        playlist.setImageUrl(imageUrl);
        return this.playlistRepository.save(playlist);
    }

    @Override
    public Playlist delete(Long id) {
        Playlist playlist = this.findById(id);
        this.playlistRepository.delete(playlist);
        return playlist;
    }

    @Override
    public void addSongToPlaylist(Long playlistId, Long songId) {
        Playlist playlist = this.findById(playlistId);
        Song song = this.songRepository.findById(songId)
                .orElseThrow(() -> new RuntimeException("Song not found with ID: " + songId));
        playlist.getSongs().add(song);
        this.playlistRepository.save(playlist);
    }

    @Override
    public void removeSongFromPlaylist(Long playlistId, Long songId) {
        Playlist playlist = this.findById(playlistId);
        Song song = this.songRepository.findById(songId)
                .orElseThrow(() -> new RuntimeException("Song not found with ID: " + songId));
        playlist.getSongs().remove(song);
        this.playlistRepository.save(playlist);
    }

    @Override
    public List<Playlist> getAllPlaylistsForUser(String email) {
        List<Playlist> playlists = new ArrayList<>();
        MusicUser user = this.userRepository.findByEmail(email).orElseThrow();
        playlists.addAll(user.getPlaylists());
        return playlists;

    }
}

