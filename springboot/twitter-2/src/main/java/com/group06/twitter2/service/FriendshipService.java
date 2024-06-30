package com.group06.twitter2.service;

import com.group06.twitter2.model.Friendship;

import java.util.List;

public interface FriendshipService {
    String addFriend(int id, int friendID);

    String acceptFriend(int id, int friendID);

    String deleteFriend(int id, int friendID);

    List<Friendship> getFriends(int id);

    List<Friendship> getFriendRequests(int id);
}
