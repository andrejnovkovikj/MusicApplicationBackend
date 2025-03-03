package com.example.fakefy.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.type.DateTime;

import java.time.LocalDateTime;

public class ConcertDto {
    private Long artistId;
    private String name;
    private String location;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startTime;

    public ConcertDto() {
    }

    public ConcertDto(Long artistId, String name, String location, LocalDateTime startTime) {
        this.artistId = artistId;
        this.name = name;
        this.location = location;
        this.startTime = startTime;
    }


    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
