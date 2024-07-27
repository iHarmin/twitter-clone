package com.group06.twitter2;

import com.group06.twitter2.model.Friendship;
import com.group06.twitter2.model.Twitter2;
import com.group06.twitter2.repository.FriendshipRepository;
import com.group06.twitter2.repository.Twitter2Repository;
import com.group06.twitter2.service.Implementations.FriendshipServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FriendShipServiceTest {
    @InjectMocks
    FriendshipServiceImpl friendshipService;

    @Mock
    FriendshipRepository friendshipRepository;

    @Mock
    Twitter2Repository twitter2Repository;

    @Test
    public void sampleTest() {
        assert(true);
    }

    @Test
    public void addFriendTest_Sucessfull() {
        Twitter2 user1 = new Twitter2();
        Twitter2 user2 = new Twitter2();
        int userID1 = user1.getId();
        int userID2 = user2.getId();

        when(twitter2Repository.findById(userID1)).thenReturn(Optional.of(user1));
        when(twitter2Repository.findById(userID2)).thenReturn(Optional.of(user2));
        String output = friendshipService.addFriend(userID1, userID2);
        assertEquals("Sent friend request successfully", output);
    }

    @Test
    public void addFriendTest_FriendNotFound() {
        Twitter2 user1 = new Twitter2();
        Twitter2 user2 = new Twitter2();
        int userID1 = user1.getId();
        int userID2 = user2.getId();

        when(twitter2Repository.findById(userID1)).thenReturn(Optional.of(user1));
        when(twitter2Repository.findById(userID2)).thenReturn(Optional.empty());
        String output = friendshipService.addFriend(userID1, userID2+1);
        assertEquals("Friend not found", output);
    }

    @Test
    public void addFriendTest_FriendshipAlreadyExists() {
        Twitter2 user1 = new Twitter2();
        Twitter2 user2 = new Twitter2();

        Twitter2 user = user1;
        Twitter2 friend = user2;

        int userID1 = user1.getId();
        int userID2 = user2.getId();

        when(twitter2Repository.findById(userID1)).thenReturn(Optional.of(user1));
        when(twitter2Repository.findById(userID2)).thenReturn(Optional.of(user2));
        Friendship friendship = new Friendship();
        friendship.setUser1(user);
        friendship.setUser2(friend);

        when(friendshipRepository.findFriendshipBetweenUsers(any(Twitter2.class), any(Twitter2.class))).thenReturn(friendship);
        String output = friendshipService.addFriend(userID1, userID2);
        assertEquals("Not added", output);
    }

    @Test
    public void acceptFriendshipTest_Sucessfull() {
        Twitter2 user1 = new Twitter2();
        Twitter2 user2 = new Twitter2();

        int userID1 = user1.getId();
        int userID2 = user2.getId();

        when(twitter2Repository.findById(userID1)).thenReturn(Optional.of(user1));
        when(twitter2Repository.findById(userID2)).thenReturn(Optional.of(user2));

        Friendship friendship = new Friendship();
        friendship.setUser1(user1);
        friendship.setUser2(user2);
        friendship.setAccepted(false);
        when(friendshipRepository.findFriendshipBetweenUsers(any(Twitter2.class), any(Twitter2.class))).thenReturn(friendship);

        String output =  friendshipService.acceptFriend(userID1, userID2);
        assertEquals("Friend request accepted", output);
    }


    @Test
    public void acceptFriendshipTest_FriendNotFound() {
        Twitter2 user1 = new Twitter2();
        Twitter2 user2 = new Twitter2();

        int userID1 = user1.getId();
        int userID2 = user2.getId();

        when(twitter2Repository.findById(userID1)).thenReturn(Optional.of(user1));
        when(twitter2Repository.findById(userID2)).thenReturn(Optional.empty());

        String output =  friendshipService.acceptFriend(userID1, userID2);
        assertEquals("Friend not found", output);
    }

    @Test
    public void acceptFriendshipTest_FriendshipAlreadyExists() {

        Twitter2 user1 = new Twitter2();
        Twitter2 user2 = new Twitter2();

        int userID1 = user1.getId();
        int userID2 = user2.getId();

        when(twitter2Repository.findById(userID1)).thenReturn(Optional.of(user1));
        when(twitter2Repository.findById(userID2)).thenReturn(Optional.of(user2));
        when(friendshipRepository.findFriendshipBetweenUsers(any(Twitter2.class), any(Twitter2.class))).thenReturn(null);

        String output =  friendshipService.acceptFriend(userID1, userID2);
        assertEquals("Not accepted", output);

    }

    @Test
    public void deleteFriendTest_successful() {
        Twitter2 user1 = new Twitter2();
        Twitter2 user2 = new Twitter2();

        int userID1 = user1.getId();
        int userID2 = user2.getId();

        Friendship friendship = new Friendship();
        friendship.setUser1(user1);
        friendship.setUser2(user2);
        friendship.setAccepted(true);

        when(friendshipRepository.findFriendshipBetweenUsers(any(Twitter2.class), any(Twitter2.class))).thenReturn(friendship);
        when(twitter2Repository.findById(userID1)).thenReturn(Optional.of(user1));
        when(twitter2Repository.findById(userID2)).thenReturn(Optional.of(user2));

        String output = friendshipService.deleteFriend(userID1, userID2);
        assertEquals("Deleted friend successfully", output);
    }


    @Test
    public void deleteFriendTest_FriendNotFound() {
        Twitter2 user1 = new Twitter2();
        Twitter2 user2 = new Twitter2();

        int userID1 = user1.getId();
        int userID2 = user2.getId();

        Friendship friendship = new Friendship();
        friendship.setUser1(user1);
        friendship.setUser2(user2);
        friendship.setAccepted(true);

        when(twitter2Repository.findById(userID1)).thenReturn(Optional.of(user1));
        when(twitter2Repository.findById(userID2)).thenReturn(Optional.of(user2));

        String output = friendshipService.deleteFriend(userID1, userID2+1);
        assertEquals("User or Friend not found", output);

    }

    @Test
    public void deleteFriendTest_FriendshipNotFound() {
        Twitter2 user1 = new Twitter2();
        Twitter2 user2 = new Twitter2();

        int userID1 = user1.getId();
        int userID2 = user2.getId();

        when(friendshipRepository.findFriendshipBetweenUsers(any(Twitter2.class), any(Twitter2.class))).thenReturn(null);
        when(twitter2Repository.findById(userID1)).thenReturn(Optional.of(user1));
        when(twitter2Repository.findById(userID2)).thenReturn(Optional.of(user2));

        String output = friendshipService.deleteFriend(userID1, userID2);
        assertEquals("Friendship not found", output);
    }

    @Test
    public void getFriendsTest_Successful() {
        Twitter2 user1 = new Twitter2();
        Twitter2 user2 = new Twitter2();

        int userID1 = user1.getId();
        int userID2 = user2.getId();

        Friendship friendship = new Friendship();
        friendship.setUser1(user1);
        friendship.setUser2(user2);
        friendship.setAccepted(true);
        List<Friendship> friendships = new ArrayList<>();
        friendships.add(friendship);

        when(friendshipRepository.findFriendsByUser(user1)).thenReturn(friendships);
        when(twitter2Repository.findById(userID1)).thenReturn(Optional.of(user1));

        List<Friendship> output = friendshipService.getFriends(userID1);
        assertEquals(output.size(), 1);
        assertEquals(output.get(0).getUser1(), user1);
    }

    @Test
    public void getFriendsTest_NoUserID() {
        boolean error = false;
        try {
            friendshipService.getFriends(0);
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), "User not found with id: 0");
            error = true;
        }
        assert(error);
    }

    @Test
    public void getFriendRequestsTest_Successful() {
        Twitter2 user = new Twitter2();
        Twitter2 user2 = new Twitter2();

        int userID = user.getId();
        Friendship friendship = new Friendship();
        friendship.setUser1(user);
        friendship.setUser2(user2);

        LinkedList<Friendship> friendships = new LinkedList<>();
        friendships.add(friendship);

        when(twitter2Repository.findById(userID)).thenReturn(Optional.of(user));
        when(friendshipRepository.findFriendRequestsByUser(user)).thenReturn(friendships);
        List<Friendship> output = friendshipService.getFriendRequests(userID);
        assertEquals(output.size(), 1);
        assertEquals(output.get(0).getUser1(), user);
    }
    @Test
    public void getFriendRequestsTest_NoUserID() {
        int userID = 0;
        boolean error = false;

        when(twitter2Repository.findById(userID)).thenReturn(Optional.empty());
        try {
            friendshipService.getFriendRequests(0);
        } catch (RuntimeException e) {
            error = true;
            assertEquals(e.getMessage(), "User not found with id: 0");
        }
        assert(error);
    }
}
