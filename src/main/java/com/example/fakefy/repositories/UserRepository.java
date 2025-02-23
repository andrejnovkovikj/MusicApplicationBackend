package com.example.fakefy.repositories;

import com.example.fakefy.model.MusicUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<MusicUser, String> {
    Optional<MusicUser> findByEmail(String email);
}
