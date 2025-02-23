package com.example.fakefy.repositories;
import java.util.List;

import com.example.fakefy.model.Album;
import com.example.fakefy.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    Song findByTitle(String title);
    List<Song> findAllByAlbum(Album album);
}
