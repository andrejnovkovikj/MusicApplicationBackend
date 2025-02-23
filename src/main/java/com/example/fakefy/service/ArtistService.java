package com.example.fakefy.service;

import com.example.fakefy.model.Artist;

import java.util.List;

public interface ArtistService {
    Artist findById(Long id);
    Artist findByName(String name);
    List<Artist> findAll();
    Artist create(String name, String bio,String imageUrl);
    Artist update(Long id, String name, String bio,String imageUrl);
    Artist delete(Long id);
}