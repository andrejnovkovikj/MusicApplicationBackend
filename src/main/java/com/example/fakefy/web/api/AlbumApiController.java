package com.example.fakefy.web.api;

import com.example.fakefy.model.Album;
import com.example.fakefy.model.Song;
import com.example.fakefy.model.dto.AlbumDto;
import com.example.fakefy.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/albums")
@CrossOrigin(origins = "*")
public class AlbumApiController {
    private final AlbumService albumService;
    private final ArtistService artistService;
    private final SongService songService;
    private final PlaylistService playlistService;
    private final MusicUserService musicUserService;

    public AlbumApiController(AlbumService albumService, ArtistService artistService, SongService songService, PlaylistService playlistService,MusicUserService musicUserService) {
        this.albumService = albumService;
        this.artistService = artistService;
        this.songService = songService;
        this.playlistService = playlistService;
        this.musicUserService = musicUserService;
    }

    @GetMapping
    public ResponseEntity<List<Album>> showAlbums(){
        List<Album> albums = albumService.listAll();
        return ResponseEntity.ok(albums);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Album> getAlbumById(@PathVariable Long id){
        Album album = this.albumService.getAlbumById(id);
        return album != null ? ResponseEntity.ok(album) : ResponseEntity.notFound().build();
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable Long id){
        this.albumService.deleteAlbum(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/create")
    public ResponseEntity<Album> createAlbum(@RequestBody AlbumDto album){
        Album newAlbum = albumService.createAlbum(
                album.getTitle(),
                album.getDateCreated(),
                album.getArtistId(),
                album.getGenre(),
                album.getImageUrl()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(newAlbum);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Album> updateAlbum(@PathVariable Long id, @RequestBody Album album){
        Album updatedAlbum = albumService.updateAlbum(
                id,
                album.getTitle(),
                album.getDateCreated(),
                album.getArtist().getId(),
                album.getGenre(),
                album.getImageUrl()
        );
        return ResponseEntity.ok(updatedAlbum);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<List<Song>> getSongsFromAlbum(@PathVariable Long id){
        Album album = this.albumService.getAlbumById(id);
        List<Song> songsAlbum = this.songService.findAllByAlbumId(id);
        return ResponseEntity.ok(songsAlbum);
    }


    @PostMapping("/{userEmail}/{albumId}/like")
    public ResponseEntity<String> likeSong(@PathVariable Long albumId, @PathVariable String userEmail) {

        if (userEmail == null || userEmail.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated. Please log in.");
        }

        musicUserService.likeAlbum(userEmail, albumId);
        return ResponseEntity.ok("Album liked successfully.");
    }

    @PostMapping("/{userEmail}/{albumId}/unlike")
    public ResponseEntity<String> unlikeSong(@PathVariable Long albumId, @PathVariable String userEmail) {

        if (userEmail == null || userEmail.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated. Please log in.");
        }

        musicUserService.unlikeAlbum(userEmail, albumId);
        return ResponseEntity.ok("Album unliked successfully.");
    }
    @GetMapping("/{userEmail}/liked-albums")
    public ResponseEntity<Set<Album>> getLikedAlbumsByUsername(@PathVariable String userEmail) {
        Set<Album> likedAlbums = musicUserService.getLikedAlbumsByEmail(userEmail);

        if (likedAlbums != null && !likedAlbums.isEmpty()) {
            return ResponseEntity.ok(likedAlbums);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }
    @GetMapping("/{id}/songs")
    public ResponseEntity<List<Song>> getSongsByAlbumId(@PathVariable Long id){
        Album album = this.albumService.getAlbumById(id);
        List<Song> songsAlbum = this.songService.findAllByAlbumId(id);
        return ResponseEntity.ok(songsAlbum);
    }

}
