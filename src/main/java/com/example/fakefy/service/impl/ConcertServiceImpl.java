package com.example.fakefy.service.impl;

import com.example.fakefy.model.Artist;
import com.example.fakefy.model.Concert;
import com.example.fakefy.repositories.ArtistRepository;
import com.example.fakefy.repositories.ConcertRepository;
import com.example.fakefy.service.ConcertService;
import com.google.type.DateTime;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConcertServiceImpl implements ConcertService {
    private final ArtistRepository artistRepository;
    private final ConcertRepository concertRepository;

    public ConcertServiceImpl(ArtistRepository artistRepository, ConcertRepository concertRepository) {
        this.artistRepository = artistRepository;
        this.concertRepository = concertRepository;
    }

    @Override
    public Concert getConcertById(Long id) {
        return this.concertRepository.findById(id).orElse(null);
    }

    @Override
    public Concert getConcertByName(String name) {
        return this.concertRepository.findByName(name);
    }

    @Override
    public List<Concert> getAllConcerts() {
        return this.concertRepository.findAll();
    }

    @Override
    public List<Concert> getAllConcertsFromArtist(String artistName) {
        Artist artist = this.artistRepository.findByName(artistName);
        return this.concertRepository.findAllByArtist(artist);
    }

    @Override
    public List<Concert> getAllConcertsFromLocation(String location) {
        return this.concertRepository.findAllByLocation(location);
    }

    @Override
    public Concert createConcert(Artist artist, String name, String location, LocalDateTime startTime) {
        Concert concert = new Concert(artist,name,location,startTime);
        return this.concertRepository.save(concert);
    }

    @Override
    public Concert updateConcert(Long id, Artist artist, String name, String Location, LocalDateTime startTime) {
        Concert concert = this.getConcertById(id);
        concert.setArtist(artist);
        concert.setName(name);
        concert.setLocation(Location);
        concert.setStartTime(startTime);
        return this.concertRepository.save(concert);


    }

    @Override
    public Concert deleteConcert(Long id) {
        Concert concert = this.getConcertById(id);
        this.concertRepository.delete(concert);
        return concert;
    }
}
