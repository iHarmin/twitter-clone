package com.group06.twitter2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.group06.twitter2.model.Post;
import com.group06.twitter2.model.Twitter2;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.*;

public interface Twitter2Service {
    public String createUser(Twitter2 twitter2);

    String updateUserInformation(int id, String firstName, String lastName, String email, String interests);

    public String updatePassword(Twitter2 twitter2);

    String updateUserStatus(int id, String status);

    Twitter2 getUserByID(int id);

    String resetPassword(String email, String recoveryAnswer, String newPassword);

    Twitter2 checkPasswordValid(String email, String password);

    String updateUserRole(int id, String role);

    boolean isAdmin(String email);

    String addUserByAdmin(String userName, String password, String firstname, String lastname, String userEmail, String recoveryAnswer, String adminEmail, String personalInterests);

    String removeUserByAdmin(String adminEmail, String userEmail);

     String approveRequest(Long requestId, String adminEmail);
     String rejectRequest(Long requestId, String adminEmail);
}
