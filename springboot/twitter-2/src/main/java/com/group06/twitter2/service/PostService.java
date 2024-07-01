package com.group06.twitter2.service;

import com.group06.twitter2.model.Post;
import com.group06.twitter2.model.Twitter2;
import java.util.ArrayList;

public interface PostService {
    ArrayList<Post> getPosts();
    Post createPost(Post post);
    ArrayList<Post> findPostsByUserID(Twitter2 userID);
}
