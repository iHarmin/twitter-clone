package com.group06.twitter2;

import com.group06.twitter2.model.Post;
import com.group06.twitter2.model.Twitter2;
import com.group06.twitter2.repository.PostsRepository;
import com.group06.twitter2.repository.Twitter2Repository;
import com.group06.twitter2.service.FriendshipService;
import com.group06.twitter2.service.Implementations.FriendshipServiceImpl;
import com.group06.twitter2.service.Implementations.PostServiceImpl;
import com.group06.twitter2.service.Implementations.Twitter2ServiceImpl;
import com.group06.twitter2.service.Twitter2Service;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class Twitter2ServiceTest {
    @InjectMocks
    Twitter2ServiceImpl twitter2Service;

    @Mock
    Twitter2Repository twitter2Repository;

    @Test
    public void createUserTest() {
        Twitter2 twitter2 = new Twitter2(0, "Name0", "n0@dal.ca", "password0", "FName0", "LName0", "rec0", "int0", "active", "Student");

        when(twitter2Repository.save(twitter2)).thenReturn(twitter2);
        String result = twitter2Service.createUser(twitter2);

        assertEquals("User profile created", result);
        assertNotNull(twitter2.getId());
    }

    @Test
    public void createUserTest_invalidEmail() {
        Twitter2 twitter2 = new Twitter2(0, "Name0", "n0@gmail.com", "password0", "FName0", "LName0", "rec0", "int0", "active", "Admin");

        String result = twitter2Service.createUser(twitter2);

        assertEquals("Invalid email address", result);
    }

    @Test
    public void addUserByAdmin_AdminAddsUserSuccessfully() {
        Twitter2 adminUser = new Twitter2(1, "adminUser", "admin@dal.ca", "adminPass", "FName0", "LName0", "rec0", "int0", "active", "Admin");
        when(twitter2Repository.findByEmail("admin@dal.ca")).thenReturn(adminUser);
        when(twitter2Repository.findByEmail("newUser@dal.ca")).thenReturn(null);

        String result = twitter2Service.addUserByAdmin("newUser", "password", "FirstName", "LastName", "newUser@dal.ca", "recAnswer", "admin@dal.ca", "int1");

        assertEquals("User saved successfully", result);
    }
}
