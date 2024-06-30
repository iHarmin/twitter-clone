package com.group06.twitter2.controller;

import com.group06.twitter2.model.Friendship;
import com.group06.twitter2.service.FriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/friends")
public class FriendshipController {

    @Autowired
    FriendshipService friendshipService;

    @GetMapping("/{id}")
    public ResponseEntity<List<Friendship>> getFriends(@PathVariable int id) {
        return ResponseEntity.ok(friendshipService.getFriends(id));
    }

    @GetMapping("/{id}/friendRequests")
    public ResponseEntity<List<Friendship>> getFriendRequests(@PathVariable int id) {
        return ResponseEntity.ok(friendshipService.getFriendRequests(id));
    }

    @PostMapping("/{id}/friends/{friendId}")
    public String addFriend(@PathVariable int id, @PathVariable int friendId) {
        return friendshipService.addFriend(id, friendId);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public String acceptFriend(@PathVariable int id, @PathVariable int friendId) {
        return friendshipService.acceptFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public String deleteFriend(@PathVariable int id, @PathVariable int friendId) {
        return friendshipService.deleteFriend(id, friendId);
    }
}
