package com.group06.twitter2.service.Implementations;

import com.group06.twitter2.model.Twitter2;
import com.group06.twitter2.repository.Twitter2Repository;
import com.group06.twitter2.repository.FriendshipRepository;
import com.group06.twitter2.service.Twitter2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.group06.twitter2.DTO.UserDTO;

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
    public String updateUserInformation(UserDTO userDTO) {
        Optional<Twitter2> userOpt = twitter2Repository.findById(userDTO.getId());
        if (userOpt.isPresent()) {
            Twitter2 user = userOpt.get();
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());
            user.setPersonalInterests(userDTO.getPersonalInterests());
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
            return "Wrong security answer";
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
    public String addUserByAdmin(String username, String password, String firstname, String lastname, String userEmail, String recoveryAnswer, String adminEmail) {
        Twitter2 adminUser = twitter2Repository.findByEmail(adminEmail);
        Twitter2 user = twitter2Repository.findByEmail(userEmail);

        if(user != null){
            return "User already exist";
        }

        if(adminUser.getRole().equals("Admin")){
            Twitter2 newUser = new Twitter2();
            newUser.setUserName(username);
            newUser.setPassword(password);
            newUser.setFirstName(firstname);
            newUser.setLastName(lastname);
            newUser.setEmail(userEmail);
            newUser.setRecoveryAnswer(recoveryAnswer);
            newUser.setRole("Student");
            twitter2Repository.save(newUser);
            return "User added successfully";
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

    private boolean isAdminExists(Twitter2 admin) {
        return admin != null;
    }

    private boolean isAdminAuthorized(Twitter2 admin) {
        return admin.getRole().equals("Admin");
    }

    private boolean isUserValid(Twitter2 user) {
        return user != null;
    }

    private boolean isRoleValid(String role) {
        return role.equals("Employee") || role.equals("Student") || role.equals("Admin");
    }

    @Override
    public String changeUserRoleByAdmin(String adminEmail, String userEmail, String newRole) {
        Twitter2 admin = twitter2Repository.findByEmail(adminEmail);
        Twitter2 user = twitter2Repository.findByEmail(userEmail);

        if (!isAdminExists(admin)) {
            return "Admin user does not exist";
        }

        if (!isAdminAuthorized(admin)) {
            return "This user is not authorized to change user roles";
        }

        if (!isUserValid(user)) {
            return "User not found";
        }

        if (!isRoleValid(newRole)) {
            return "Invalid role specified";
        }


        user.setRole(newRole);
        twitter2Repository.save(user);

        return "User role updated successfully";
    }

    public List<Twitter2> searchUsers(String searchTerm) {
        return twitter2Repository.searchByUserNameOrEmailOrInterests(searchTerm);
    }

}
