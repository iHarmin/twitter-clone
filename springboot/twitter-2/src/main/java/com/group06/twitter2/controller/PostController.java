package com.group06.twitter2.controller;


import com.group06.twitter2.model.Post;
import com.group06.twitter2.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    PostService postService;

    @GetMapping("/getPosts")
    public ArrayList<Post> getPosts() {
        return postService.getPosts();
    }

    @PutMapping("/createPost")
    public String createPost(@RequestBody Post post) {
        postService.createPost(post);
        return "Post created";
    }
}
