package com.group06.twitter2;

import com.group06.twitter2.repository.FriendshipRepository;
import com.group06.twitter2.service.Implementations.FriendshipServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FriendShipServiceTest {
    @InjectMocks
    FriendshipServiceImpl friendshipService;

    @Mock
    FriendshipRepository friendshipRepository;

    @Test
    public void sampleTest() {
        assert(true);
    }
}
