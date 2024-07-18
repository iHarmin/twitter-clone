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
        if (user.getRecoveryAnswer().equals(recoveryAnswer)) {
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

    @Override
    public String updateUserRole(int id, String role) {
        Optional<Twitter2> userOpt = twitter2Repository.findById(id);
        if (userOpt.isPresent()) {
            Twitter2 user = userOpt.get();
            user.setRole(role);
            twitter2Repository.save(user);
            return "User role updated successfully";
        } else {
            return "User not found";
        }
    }

    @Override
    public boolean isAdmin(String email) {
        Twitter2 user = twitter2Repository.findByEmail(email);
        return user != null && user.getRole() != null && user.getRole().equals("Admin");
    }

    @Override
    public String addUserByAdmin(String username, String password, String firstname, String lastname, String userEmail, String recoveryAnswer, String adminEmail, String personalInterests) {
        Twitter2 adminUser = twitter2Repository.findByEmail(adminEmail);
        Twitter2 userOpt = twitter2Repository.findByEmail(userEmail);

        if(userOpt == null) {
            return "User already exist";
        }

        if(adminUser.getRole().equals("Admin")) {
            Twitter2 new_user = new Twitter2();
            new_user.setUserName(username);
            new_user.setRole("Student");
            new_user.setPassword(password);
            new_user.setFirstName(firstname);
            new_user.setLastName(lastname);
            new_user.setEmail(userEmail);
            new_user.setRecoveryAnswer(recoveryAnswer);
            new_user.setPersonalInterests(personalInterests);
            twitter2Repository.save(new_user);
            return "User saved successfully";
        }

        return "This user is not authorized to create new user";
    }


    @Override
    public String removeUserByAdmin(String adminEmail, String userEmail){
        Twitter2 adminUser = twitter2Repository.findByEmail(adminEmail);
        Twitter2 user = twitter2Repository.findByEmail(userEmail);

        if(!adminUser.getRole().equals("Admin")) {
            return "This user is not authorized to remove user";
        }

        if(user == null) {
            return "User does not exist with this email";
        }

        twitter2Repository.delete(user);
        return "User deleted successfully.";
    }
}
