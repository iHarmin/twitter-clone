package com.group06.twitter2.service;

import com.group06.twitter2.model.Friendship;

import java.util.List;

public interface FriendshipService {
    String addFriend(int userID1, int userID2);

    String acceptFriend(int userID1, int userID2);

    String deleteFriend(int userID1, int userID2);

    List<Friendship> getFriends(int userID);

    List<Friendship> getFriendRequests(int userID);
}
