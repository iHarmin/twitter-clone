package com.group06.twitter2.service.Implementations;

import com.group06.twitter2.model.Twitter2;
import com.group06.twitter2.model.Friendship;
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
    public String updateUserStatus(int id, Map<String,String> data) {
        List<String> allowedStatuses = Arrays.asList("away", "inactive", "available");
        if(!data.containsKey("status")) {
            return "status key not present in the request body";
        }

        String status = data.get("status");
        if (!allowedStatuses.contains(status.toLowerCase())) {
            return "Invalid status. Allowed statuses are: away, inactive, available";
        }

        Optional<Twitter2> statusOpt = twitter2Repository.findById(id);
        if (statusOpt.isPresent()) {
            Twitter2 s = statusOpt.get();
            s.setStatus(status);
            twitter2Repository.save(s);
            return "Status updated to " + status;
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
}