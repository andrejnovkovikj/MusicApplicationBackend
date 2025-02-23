package com.example.fakefy.service;

import com.example.fakefy.model.MusicUser;
import com.example.fakefy.repositories.UserRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    public MusicUser authenticateUser(String firebaseToken) throws Exception {
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(firebaseToken);
        String uid = decodedToken.getUid();
        String email = decodedToken.getEmail();

        Optional<MusicUser> existingUser = userRepository.findById(uid);

        if (existingUser.isPresent()) {
            return existingUser.get();
        } else {
            MusicUser newUser = new MusicUser();
            newUser.setUid(uid);
            newUser.setEmail(email);
            newUser.setLikedSongs(Set.of());

            return userRepository.save(newUser);
        }
    }
}
