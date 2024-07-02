package com.group06.twitter2.repository;

import com.group06.twitter2.model.Post;
import com.group06.twitter2.model.Twitter2;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepository extends JpaRepository<Post, Integer> {
    ArrayList<Post> findByUserID(Twitter2 userID);
}
