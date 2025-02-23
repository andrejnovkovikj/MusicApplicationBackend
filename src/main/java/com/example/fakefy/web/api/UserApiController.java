package com.example.fakefy.web.api;

import com.example.fakefy.model.MusicUser;
import com.example.fakefy.model.Role;
import com.example.fakefy.service.MusicUserService;
import com.example.fakefy.service.PlaylistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserApiController {

    private final MusicUserService musicUserService;
    private final PlaylistService playlistService;

    public UserApiController(MusicUserService musicUserService, PlaylistService playlistService) {
        this.musicUserService = musicUserService;
        this.playlistService = playlistService;
    }
    @GetMapping("/{uid}")
    public ResponseEntity<MusicUser> getUserByUid(@PathVariable String uid) {
        MusicUser user = musicUserService.findById(uid);

        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }
    @GetMapping("/{uid}/role")
    public ResponseEntity<Role> isAdminByUid(@PathVariable String uid) {
        MusicUser user = musicUserService.findById(uid);
        return ResponseEntity.ok(user.getRole());
    }
    @GetMapping
    public ResponseEntity<List<MusicUser>> getAllUsers() {
        List<MusicUser> users = musicUserService.listAll();

        if (users != null && !users.isEmpty()) {
            return ResponseEntity.ok(users);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }
    @DeleteMapping("/{uid}")
    public ResponseEntity<String> deleteUser(@PathVariable String uid) {
        try {
            MusicUser deletedUser = musicUserService.delete(uid);
            return ResponseEntity.ok("User with id " + uid + " deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("User not found");
        }

    }

}
