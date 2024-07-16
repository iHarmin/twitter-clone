package com.group06.twitter2.service.Implementations;

import com.group06.twitter2.model.Post;
import com.group06.twitter2.model.Twitter2;
import com.group06.twitter2.repository.PostsRepository;
import com.group06.twitter2.repository.Twitter2Repository;
import com.group06.twitter2.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    PostsRepository postsRepository;
    Twitter2Repository twitter2Repository;

    @Override
    public ArrayList<Post> getPosts() {
        ArrayList<Post> output = (ArrayList<Post>) postsRepository.findAll();
        return output;
    }

    @Override
    public Post createPost(Post post) {
        postsRepository.save(post);
        return post;
    }

    @Override
    public ArrayList<Post> findPostsByUserID(Twitter2 userID) {
        ArrayList<Post> output = (ArrayList<Post>) postsRepository.findByUserID(userID);
        return output;
    }
}
