package com.group06.twitter2.repository;

import com.group06.twitter2.model.Friendship;
import com.group06.twitter2.model.Twitter2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Integer> {
    @Query("SELECT f FROM Friendship f where (f.userName1 = :user1 AND f.userName2 = :user2) OR (f.userName1 = :user2 AND f.userName2 = :user1)")
    Friendship findFriendshipBetweenUsers(@Param("user1") Twitter2 user1, @Param("user2") Twitter2 user2);

    @Query("SELECT f FROM Friendship f WHERE (f.userName1 = :user OR f.userName2 = :user) AND f.accepted = false")
    List<Friendship> findFriendRequestsByUser(@Param("user") Twitter2 user);

    @Query("SELECT f FROM Friendship f WHERE (f.userName1 = :user OR f.userName2 = :user) AND f.accepted = true")
    List<Friendship> findFriendsByUser(@Param("user") Twitter2 user);

}
