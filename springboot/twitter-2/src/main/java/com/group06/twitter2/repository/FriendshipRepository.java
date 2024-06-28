package com.group06.twitter2.repository;

import com.group06.twitter2.model.Friendship;
import com.group06.twitter2.model.Twitter2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Integer> {
    Friendship findByUserName1AndUserName2(Twitter2 userName1, Twitter2 userName2);

}
