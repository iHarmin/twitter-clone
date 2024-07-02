package com.group06.twitter2.service.Implementations;

import com.group06.twitter2.model.Friendship;
import com.group06.twitter2.model.Twitter2;
import com.group06.twitter2.repository.FriendshipRepository;
import com.group06.twitter2.repository.Twitter2Repository;
import com.group06.twitter2.service.FriendshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FriendshipServiceImpl implements FriendshipService {

    @Autowired
    Twitter2Repository twitter2Repository;
    @Autowired
    FriendshipRepository friendshipRepository;

    @Override
    public String addFriend(int userID1, int userID2) {
        Optional<Twitter2> userOpt = twitter2Repository.findById(userID1);
        Optional<Twitter2> friendOpt = twitter2Repository.findById(userID2);

        if (userOpt.isPresent() && friendOpt.isPresent()) {
            Twitter2 user = userOpt.get();
            Twitter2 friend = friendOpt.get();
            if (friendshipRepository.findFriendshipBetweenUsers(user, friend) == null) {
                Friendship f = new Friendship();
                f.setUser1(user);
                f.setUser2(friend);
                f.setAccepted(false);
                friendshipRepository.save(f);
                return "Sent friend request successfully";
            } else {
                return "Not added";
            }
        } else {
            return "Friend not found";
        }
    }

    @Override
    public String acceptFriend(int userID1, int userID2) {
        Optional<Twitter2> userOpt = twitter2Repository.findById(userID1);
        Optional<Twitter2> friendOpt = twitter2Repository.findById(userID2);

        if (userOpt.isPresent() && friendOpt.isPresent()) {
            Twitter2 user = userOpt.get();
            Twitter2 friend = friendOpt.get();
            Friendship f = friendshipRepository.findFriendshipBetweenUsers(friend, user);
            if (f != null) {
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
    public String deleteFriend(int userID1, int userID2) {
        Optional<Twitter2> userOpt = twitter2Repository.findById(userID1);
        Optional<Twitter2> friendOpt = twitter2Repository.findById(userID2);

        if (userOpt.isPresent() && friendOpt.isPresent()) {
            Twitter2 user = userOpt.get();
            Twitter2 friend = friendOpt.get();

            Friendship f = friendshipRepository.findFriendshipBetweenUsers(user, friend);
            if (f != null) {
                friendshipRepository.delete(f);
                return "Deleted friend successfully";
            }

            Friendship f1 = friendshipRepository.findFriendshipBetweenUsers(friend, user);
            if (f1 != null) {
                friendshipRepository.delete(f1);
                return "Deleted friend successfully";
            }
            return "Friendship not found";
        } else {
            return "User or Friend not found";
        }
    }

    @Override
    public List<Friendship> getFriends(int userID) {
        Optional<Twitter2> userOpt = twitter2Repository.findById(userID);
        if (userOpt.isPresent()) {
            Twitter2 user = userOpt.get();
            return friendshipRepository.findFriendsByUser(user);
        } else {
            throw new RuntimeException("User not found with id: " + userID);
        }
    }

    @Override
    public List<Friendship> getFriendRequests(int userID) {
        Optional<Twitter2> userOpt = twitter2Repository.findById(userID);
        if (userOpt.isPresent()) {
            Twitter2 user = userOpt.get();
            return friendshipRepository.findFriendRequestsByUser(user);
        } else {
            throw new RuntimeException("User not found with id: " + userID);
        }
    }


}
