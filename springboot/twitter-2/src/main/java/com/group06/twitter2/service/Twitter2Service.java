package com.group06.twitter2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.group06.twitter2.model.Twitter2;

import java.util.Map;

public interface Twitter2Service {
    public String createUser(Twitter2 twitter2);

    String updateUserInformation(int id, String firstName, String lastName, String email,
                                 String interests);

    public String updatePassword(Twitter2 twitter2);

    String updateUserStatus(int id, String status) throws JsonProcessingException;

    String addFriend(int id, int friendId);

    String acceptFriend(int id, int friendId);

    String deleteFriend(int id, int friendId);

    Twitter2 getUserByID(int id);

    String resetPassword(String email, String recoveryAnswer, String newPassword);

}
