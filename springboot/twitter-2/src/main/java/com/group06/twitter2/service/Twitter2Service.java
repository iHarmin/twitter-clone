package com.group06.twitter2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.group06.twitter2.DTO.UserDTO;
import com.group06.twitter2.model.Post;
import com.group06.twitter2.model.Twitter2;
import java.util.*;

public interface Twitter2Service {
    public String createUser(Twitter2 twitter2);

    String updateUserInformation(UserDTO userDTO);

    String updateUserStatus(int id, String status);

    Twitter2 getUserByID(int id);

    String resetPassword(String email, String recoveryAnswer, String newPassword);

    Twitter2 checkPasswordValid(String email, String password);

    String addUserByAdmin(String username, String password, String firstname, String lastname, String userEmail,
            String recoveryAnswer, String adminEmail);

    String removeUserByAdmin(String adminEmail, String userEmail);

    boolean isAdmin(String email);

    String approveRequest(Long requestId, String adminEmail);

    String rejectRequest(Long requestId, String adminEmail);

    List<Twitter2> getPendingRequests();

    String changeUserRoleByAdmin(String adminEmail, String userEmail, String newRole);

    List<Twitter2> searchUsers(String searchTerm);

}
