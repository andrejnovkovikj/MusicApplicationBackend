package com.example.fakefy.web.api;

import com.example.fakefy.model.Album;
import com.example.fakefy.model.Artist;
import com.example.fakefy.model.Playlist;
import com.example.fakefy.service.AlbumService;
import com.example.fakefy.service.ArtistService;
import com.example.fakefy.service.PlaylistService;
import com.example.fakefy.service.SongService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/playlists")
@CrossOrigin(origins = "*")
public class PlaylistApiController {
    private final AlbumService albumService;
    private final ArtistService artistService;
    private final SongService songService;
    private final PlaylistService playlistService;

    public PlaylistApiController(AlbumService albumService, ArtistService artistService, SongService songService, PlaylistService playlistService) {
        this.albumService = albumService;
        this.artistService = artistService;
        this.songService = songService;
        this.playlistService = playlistService;
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
    public ResponseEntity<Playlist> addPlaylist(@RequestBody Playlist playlist){
        Playlist newPlaylist = playlistService.create(
                playlist.getName(),
                playlist.getDescription(),
                playlist.getImageUrl(),
                playlist.getUser().getUid()

        );
        return ResponseEntity.status(HttpStatus.CREATED).body(newPlaylist);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Playlist>updatePlaylist(@PathVariable Long id, @RequestBody Playlist playlist){
        Playlist updatedPlaylist = playlistService.update(
                id,
                playlist.getName(),
                playlist.getDescription(),
                playlist.getDescription()

        );
        return ResponseEntity.ok(updatedPlaylist);
    }
    @GetMapping("/{id}/albums")
    public ResponseEntity<List<Playlist>> getAllPlaylistByUserUid(@PathVariable String email){
        List<Playlist> playlists = playlistService.getAllPlaylistsForUser(email);
        return ResponseEntity.ok(playlists);
    }
}
