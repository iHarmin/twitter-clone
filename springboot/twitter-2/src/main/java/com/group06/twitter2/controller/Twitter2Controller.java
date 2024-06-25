package com.group06.twitter2.controller;


import com.group06.twitter2.model.Twitter2;
import com.group06.twitter2.service.Twitter2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/update")
    public String updatePassword(@RequestBody Twitter2 twitter2){
        return twitter2Service.updatePassword(twitter2);
    }


}
