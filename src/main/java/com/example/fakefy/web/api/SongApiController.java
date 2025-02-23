package com.example.fakefy.web.api;

import com.example.fakefy.model.Song;
import com.example.fakefy.service.AlbumService;
import com.example.fakefy.service.ArtistService;
import com.example.fakefy.service.MusicUserService;
import com.example.fakefy.service.SongService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/songs")
@CrossOrigin(origins = "*")
public class SongApiController {
    private final AlbumService albumService;
    private final ArtistService artistService;
    private final MusicUserService musicUserService;
    private final SongService songService;

    public SongApiController(AlbumService albumService, ArtistService artistService, MusicUserService musicUserService, SongService songService) {
        this.albumService = albumService;
        this.artistService = artistService;
        this.musicUserService = musicUserService;
        this.songService = songService;
    }

    @GetMapping
    public ResponseEntity<List<Song>> getAllSongs(){
        List<Song> songs = songService.listAll();
        return ResponseEntity.ok(songs);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Song> getSongById(@PathVariable Long id){
        Song song = songService.findById(id);
        return song != null ? ResponseEntity.ok(song) : ResponseEntity.notFound().build();
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSong(@PathVariable Long id){
        songService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping
    public ResponseEntity<Song> addSong(@RequestBody Song song){
        Song newSong = songService.create(
                song.getTitle(),
                song.getFilePath(),
                song.getLengthSeconds(),
                song.getAlbum().getId(),
                song.getArtist().getId()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(newSong);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Song> updateSong(@PathVariable Long id,@RequestBody Song song){
        Song updatedSong = songService.update(
                id,
                song.getTitle(),
                song.getFilePath(),
                song.getLengthSeconds(),
                song.getAlbum().getId(),
                song.getArtist().getId()
        );
        return ResponseEntity.ok(updatedSong);
    }

    @PostMapping("/{userEmail}/{songId}/like")
    public ResponseEntity<String> likeSong(@PathVariable Long songId, @PathVariable String userEmail) {

        if (userEmail == null || userEmail.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated. Please log in.");
        }

        musicUserService.likeSong(userEmail, songId);
        return ResponseEntity.ok("Song liked successfully.");
    }

    @PostMapping("/{userEmail}/{songId}/unlike")
    public ResponseEntity<String> unlikeSong(@PathVariable Long songId, @PathVariable String userEmail) {

        if (userEmail == null || userEmail.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated. Please log in.");
        }

        musicUserService.unlikeSong(userEmail, songId);
        return ResponseEntity.ok("Song unliked successfully.");
    }
    @GetMapping("/{userEmail}/liked-songs")
    public ResponseEntity<Set<Song>> getLikedSongsByUsername(@PathVariable String userEmail) {
        Set<Song> likedSongs = musicUserService.getLikedSongsByEmail(userEmail);

        if (likedSongs != null && !likedSongs.isEmpty()) {
            return ResponseEntity.ok(likedSongs);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

}