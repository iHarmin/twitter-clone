package com.group06.twitter2.service;

import com.group06.twitter2.model.Twitter2;

import java.util.Map;

public interface Twitter2Service {
    public String createUser(Twitter2 twitter2);

    public String updatePassword(Twitter2 twitter2);

    String updateUserStatus(int id, Map<String, String> data);

    String addFriend(int id, int friendId);

    String acceptFriend(int id, int friendId);

    String deleteFriend(int id, int friendId);
}
