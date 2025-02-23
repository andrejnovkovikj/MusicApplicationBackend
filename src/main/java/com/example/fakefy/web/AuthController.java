package com.example.fakefy.web;
import com.example.fakefy.model.MusicUser;
import com.example.fakefy.model.Role;
import com.example.fakefy.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public MusicUser loginUser(@RequestBody MusicUser userData) {
        Optional<MusicUser> existingUser = userRepository.findByEmail(userData.getEmail());

        if (existingUser.isPresent()) {
            return existingUser.get();
        }

        MusicUser newUser = new MusicUser();
        newUser.setUid(userData.getUid());
        newUser.setEmail(userData.getEmail());

        return userRepository.save(newUser);
    }
    @PostMapping("/register")
    public ResponseEntity<MusicUser> registerUser(@RequestBody MusicUser userData) {
        System.out.println("Received registration data: " + userData);

        Optional<MusicUser> existingUser = userRepository.findByEmail(userData.getEmail());

        if (existingUser.isPresent()) {
            System.out.println("User already exists");
            return ResponseEntity.badRequest().body(null);
        }

        MusicUser newUser = new MusicUser();
        newUser.setUid(userData.getUid());
        newUser.setEmail(userData.getEmail());
        newUser.setRole(userData.getRole());

        MusicUser savedUser = userRepository.save(newUser);
        System.out.println("User saved: " + savedUser);

        return ResponseEntity.ok(savedUser);
    }


}
