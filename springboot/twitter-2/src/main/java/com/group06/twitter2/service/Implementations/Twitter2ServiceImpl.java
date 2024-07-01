package com.group06.twitter2.service.Implementations;

import com.group06.twitter2.model.Twitter2;
import com.group06.twitter2.repository.Twitter2Repository;
import com.group06.twitter2.repository.FriendshipRepository;
import com.group06.twitter2.service.Twitter2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class Twitter2ServiceImpl implements Twitter2Service {
    @Autowired
    Twitter2Repository twitter2Repository;
    @Autowired
    private FriendshipRepository friendshipRepository;

    @Override
    public String createUser(Twitter2 twitter2) {
        if(!twitter2.getEmail().endsWith("@dal.ca")) {
            return "Invalid email address";
        }
        twitter2Repository.save(twitter2);
        return "User profile created";
    }

    @Override
    public Twitter2 getUserByID(int id) {
        Optional<Twitter2> optionalTwitter2 = twitter2Repository.findById(id);
        if (optionalTwitter2.isPresent()) {
            Twitter2 twitter2 = optionalTwitter2.get();
            System.out.println("Successfully fetched user information: " + twitter2);
            return twitter2;
        } else {
            System.out.println("Customer not found with id: " + id);
            throw new RuntimeException("Customer not found with id: " + id);
        }
    }

    @Override
    public String updatePassword(Twitter2 twitter2) {
        Optional<Twitter2> passOpt = twitter2Repository.findById(twitter2.getId());
        if(passOpt.isPresent()) {
            Twitter2 p = passOpt.get();
            p.setPassword(twitter2.getPassword());
            twitter2Repository.save(p);
            return "Password updated successfully";
        } else {
            return "Password not updated";
        }
    }

    @Override
    public String updateUserStatus(int id, String status) {
        Optional<Twitter2> userOpt = twitter2Repository.findById(id);
        if (userOpt.isPresent()) {
            Twitter2 user = userOpt.get();
            user.setStatus(status);
            twitter2Repository.save(user);
            return "User status updated successfully";
        } else {
            return "User not found with id: " + id;
        }
    }

    @Override
    public String updateUserInformation(int id, String firstName, String lastName, String email,
                                        String personalInterests) {
        Optional<Twitter2> userOpt = twitter2Repository.findById(id);
        if (userOpt.isPresent()) {
            Twitter2 user = userOpt.get();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPersonalInterests(personalInterests);
            twitter2Repository.save(user);
            return "User information updated successfully";
        } else {
            return "User not found";
        }
    }

    @Override
    public String resetPassword(String email, String recoveryAnswer, String newPassword) {
        Twitter2 user = twitter2Repository.findByEmail(email);
        if (user.getSecurityAnswer().equals(recoveryAnswer)) {
            user.setPassword(newPassword);
            twitter2Repository.save(user);
            return "New password set";
        } else {
            return "Wrong security answer try";
        }
    }

    @Override
    public Twitter2 checkPasswordValid(String email, String password) {
        Twitter2 user = twitter2Repository.findByEmail(email);
        String actualPassword = user.getPassword();
        if(actualPassword.equals(password)) {
            return user;
        } else {
            return null;
        }
    }
}
