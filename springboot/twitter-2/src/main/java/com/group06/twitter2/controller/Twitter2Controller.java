package com.group06.twitter2.controller;


import com.group06.twitter2.model.Twitter2;
import com.group06.twitter2.service.Twitter2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/users")
public class Twitter2Controller {

    @Autowired
    Twitter2Service twitter2Service;

    @PostMapping("/save")
    public String saveUserProfile(@RequestBody Twitter2 twitter2){
        return twitter2Service.createUser(twitter2);
    }

    @GetMapping("/{id}")
    public Twitter2 getUserById(@PathVariable("id") int id){
        return twitter2Service.getUserByID(id);
    }

    @PutMapping("/update/{password}")
    public String updatePassword(@PathVariable String password, @RequestBody Twitter2 twitter2){
        twitter2.setPassword(password);
        return twitter2Service.updatePassword(twitter2);
    }

    @PutMapping("/{id}/status")
    public String updateUserStatus(@PathVariable int id, @RequestBody Map<String,String> data) {
        return twitter2Service.updateUserStatus(id, data);
    }

    @PostMapping("/{id}/friends/{friendId}")
    public String addFriend(@PathVariable int id, @PathVariable int friendId) {
        return twitter2Service.addFriend(id, friendId);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public String acceptFriend(@PathVariable int id, @PathVariable int friendId) {
        return twitter2Service.acceptFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public String deleteFriend(@PathVariable int id, @PathVariable int friendId) {
        return twitter2Service.deleteFriend(id, friendId);
    }

}
