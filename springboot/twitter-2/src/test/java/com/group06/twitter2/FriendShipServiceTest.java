package com.group06.twitter2;

import com.group06.twitter2.model.Twitter2;
import com.group06.twitter2.repository.FriendshipRepository;
import com.group06.twitter2.repository.Twitter2Repository;
import com.group06.twitter2.service.Implementations.FriendshipServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

}
