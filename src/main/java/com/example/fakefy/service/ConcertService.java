package com.example.fakefy.service;

import com.example.fakefy.model.Artist;
import com.example.fakefy.model.Concert;
import com.google.type.DateTime;

import java.time.LocalDateTime;
import java.util.List;

public interface ConcertService {
    Concert getConcertById(Long id);
    Concert getConcertByName(String name);
    List<Concert> getAllConcerts();
    List<Concert> getAllConcertsFromArtist(String artistName);
    List<Concert> getAllConcertsFromLocation(String location);
    Concert createConcert(Artist artist,String name, String location, LocalDateTime startTime);
    Concert updateConcert(Long id, Artist artist, String name, String location, LocalDateTime startTime);
    Concert deleteConcert(Long id);

}