package com.example.fakefy.web.api;

import com.example.fakefy.model.Artist;
import com.example.fakefy.model.Concert;
import com.example.fakefy.model.Playlist;
import com.example.fakefy.model.dto.ConcertDto;
import com.example.fakefy.service.ArtistService;
import com.example.fakefy.service.ConcertService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/concerts")
@CrossOrigin(origins = "*")
public class ConcertApiController {
    private final ConcertService concertService;
    private final ArtistService artistService;

    public ConcertApiController(ConcertService concertService, ArtistService artistService) {
        this.concertService = concertService;
        this.artistService = artistService;
    }

    @GetMapping
    public ResponseEntity<List<Concert>> getAllConcerts() {
        List<Concert> concerts = concertService.getAllConcerts();
        return ResponseEntity.ok(concerts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Concert> getConcertById(@PathVariable Long id) {
        Concert concert = concertService.getConcertById(id);
        return concert != null ? ResponseEntity.ok(concert) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteConcertById(@PathVariable Long id) {
        this.concertService.deleteConcert(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping()
    public ResponseEntity<Concert> addConcert(@RequestBody ConcertDto concert) {
        System.out.println("Received Concert: " + concert); // Debugging

        Artist artist = this.artistService.findById(concert.getArtistId());

        Concert newConcert = concertService.createConcert(
                artist,
                concert.getName(),
                concert.getLocation(),
                concert.getStartTime()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(newConcert);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Concert> updateConcertById(@PathVariable Long id, @RequestBody Concert concert) {
        Artist artist = this.artistService.findById(concert.getArtist().getId());
        Concert updatedConcert = concertService.updateConcert(
                id,
                artist,
                concert.getName(),
                concert.getLocation(),
                concert.getStartTime()
        );
        return ResponseEntity.ok(updatedConcert);
    }
    @GetMapping("/sort/{artistName}")
    public ResponseEntity<List<Concert>> getConcertsByArtist(@PathVariable String artistName) {
        List<Concert> concertsByArtist = this.concertService.getAllConcertsFromArtist(artistName);
        return ResponseEntity.ok(concertsByArtist);
    }
}
