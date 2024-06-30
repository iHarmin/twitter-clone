package com.group06.twitter2.service;

import com.group06.twitter2.model.Friendship;

import java.util.List;

public interface FriendshipService {
    String addFriend(int id, int friendId);

    String acceptFriend(int id, int friendId);

    String deleteFriend(int id, int friendId);

    List<Friendship> getFriends(int id);

    List<Friendship> getFriendRequests(int id);
}
