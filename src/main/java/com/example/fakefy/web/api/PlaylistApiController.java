package com.example.fakefy.web.api;

import com.example.fakefy.model.*;
import com.example.fakefy.model.dto.PlaylistDto;
import com.example.fakefy.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/playlists")
@CrossOrigin(origins = "*")
public class PlaylistApiController {
    private final AlbumService albumService;
    private final ArtistService artistService;
    private final SongService songService;
    private final PlaylistService playlistService;
    private final MusicUserService musicUserService;

    public PlaylistApiController(AlbumService albumService, ArtistService artistService, SongService songService, PlaylistService playlistService, MusicUserService musicUserService) {
        this.albumService = albumService;
        this.artistService = artistService;
        this.songService = songService;
        this.playlistService = playlistService;
        this.musicUserService = musicUserService;
    }

    @GetMapping
    public ResponseEntity<List<Playlist>> getAllPlaylists(){
        List<Playlist> playlists = playlistService.listAll();
        return ResponseEntity.ok(playlists);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Playlist> getPlaylistById(@PathVariable Long id){
        Playlist playlist = this.playlistService.findById(id);
        return playlist != null ? ResponseEntity.ok(playlist) : ResponseEntity.notFound().build();
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable Long id){
       this.playlistService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping
    public ResponseEntity<Playlist> addPlaylist(@RequestBody PlaylistDto playlist){
        MusicUser user = this.musicUserService.findById(playlist.getUid());
        Playlist newPlaylist = playlistService.create(
                playlist.getName(),
                playlist.getDescription(),
                playlist.getImageUrl(),
                user.getEmail()

        );
        return ResponseEntity.status(HttpStatus.CREATED).body(newPlaylist);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Playlist>updatePlaylist(@PathVariable Long id, @RequestBody PlaylistDto playlist){
        Playlist updatedPlaylist = playlistService.update(
                id,
                playlist.getName(),
                playlist.getDescription(),
                playlist.getImageUrl()

        );
        return ResponseEntity.ok(updatedPlaylist);
    }
    @GetMapping("/userPlaylists/{userEmail}")
    public ResponseEntity<List<Playlist>> getAllPlaylistByUserUid(@PathVariable String userEmail){
        List<Playlist> playlists = playlistService.getAllPlaylistsForUser(userEmail);
        return ResponseEntity.ok(playlists);
    }
    @PostMapping("/addSongToPlaylist/{playlistId}/{songId}")
    public ResponseEntity<String> addSongToPlaylist(@PathVariable Long playlistId, @PathVariable Long songId) {
        Playlist playlist = playlistService.findById(playlistId);

        if (playlist == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Playlist not found");
        }

        boolean songExists = playlist.getSongs().stream().anyMatch(song -> song.getId().equals(songId));

        if (songExists) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Song is already in the playlist");
        }

        playlistService.addSongToPlaylist(playlistId, songId);
        return ResponseEntity.ok("Song added to playlist");
    }

    @PostMapping("/removeSongFromPlaylist/{playlistId}/{songId}")
    public ResponseEntity<String> removeSongFromPlaylist(@PathVariable Long playlistId, @PathVariable Long songId) {
        Playlist playlist = playlistService.findById(playlistId);

        if (playlist == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Playlist not found");
        }

        boolean songExists = playlist.getSongs().stream().anyMatch(song -> song.getId().equals(songId));

        if (!songExists) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Song not found in the playlist");
        }

        playlistService.removeSongFromPlaylist(playlistId, songId);
        return ResponseEntity.ok("Song removed from playlist " + playlistId);
    }

}
