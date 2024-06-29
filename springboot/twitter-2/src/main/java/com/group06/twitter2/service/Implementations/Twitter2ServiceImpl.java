package com.group06.twitter2.service.Implementations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.group06.twitter2.model.Post;
import com.group06.twitter2.model.Twitter2;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group06.twitter2.model.Friendship;
import com.group06.twitter2.repository.PostsRepository;
import com.group06.twitter2.repository.Twitter2Repository;
import com.group06.twitter2.repository.FriendshipRepository;
import com.group06.twitter2.service.Twitter2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.*;
@Service
public class Twitter2ServiceImpl implements Twitter2Service {

    @Autowired
    Twitter2Repository twitter2Repository;
    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    PostsRepository postsRepository;

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

//    @Override
//    public String updateUserStatus(int id, String status) {
//        Optional<Twitter2> userOpt = twitter2Repository.findById(id);
//        if (userOpt.isPresent()) {
//            Twitter2 user = userOpt.get();
//            user.setStatus(status);
//            twitter2Repository.save(user);
//            return "Status updated to " + status;
//        } else {
//            return "User not found";
//        }
//    }

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
    public String addFriend(int id, int friendId) {
        Optional<Twitter2> userOpt = twitter2Repository.findById(id);
        Optional<Twitter2> friendOpt = twitter2Repository.findById(friendId);

        if(userOpt.isPresent() && friendOpt.isPresent()) {
            Twitter2 user = userOpt.get();
            Twitter2 friend = friendOpt.get();
            if (friendshipRepository.findByUserName1AndUserName2(user, friend) == null) {
                Friendship f = new Friendship();
                f.setUserName1(user);
                f.setUserName2(friend);
                f.setAccepted(false);
                friendshipRepository.save(f);
                return "Added friend successfully";
            } else {
                return "Not added";
            }
        } else {
            return "Friend not found";
        }
    }

    @Override
    public String acceptFriend(int id, int friendId) {
        Optional<Twitter2> userOpt = twitter2Repository.findById(id);
        Optional<Twitter2> friendOpt = twitter2Repository.findById(friendId);

        if(userOpt.isPresent() && friendOpt.isPresent()) {
            Twitter2 user = userOpt.get();
            Twitter2 friend = friendOpt.get();
            Friendship f = friendshipRepository.findByUserName1AndUserName2(friend, user);
            if(f != null) {
                f.setAccepted(true);
                friendshipRepository.save(f);
                return "Friend request accepted";
            } else {
                return "Not accepted";
            }
        } else {
            return "Friend not found";
        }
    }

    @Override
    public String deleteFriend(int id, int friendId) {
        Optional<Twitter2> userOpt = twitter2Repository.findById(id);
        Optional<Twitter2> friendOpt = twitter2Repository.findById(friendId);

        if(userOpt.isPresent() && friendOpt.isPresent()) {
            Twitter2 user = userOpt.get();
            Twitter2 friend = friendOpt.get();

            Friendship f = friendshipRepository.findByUserName1AndUserName2(user, friend);
            if(f != null) {
                friendshipRepository.delete(f);
                return "Deleted friend successfully";
            }

            Friendship f1 = friendshipRepository.findByUserName1AndUserName2(friend, user);
            if(f1 != null) {
                friendshipRepository.delete(f1);
                return "Deleted friend successfully";
            }
            return "Friendship not found";
        } else {
            return "User or Friend not found";
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
    public ArrayList<Post> getPosts() {
        ArrayList<Post> output = (ArrayList<Post>) postsRepository.findAll();
        return output;
    }
}
