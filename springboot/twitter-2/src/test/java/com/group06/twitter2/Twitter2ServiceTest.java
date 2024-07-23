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
import static org.mockito.Mockito.verify;

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
    public void createUserTest_InvalidEmail() {
        Twitter2 twitter2 = new Twitter2(0, "Name0", "n0@gmail.com", "password0", "FName0", "LName0", "rec0", "int0", "active", "Admin");

        String result = twitter2Service.createUser(twitter2);

        assertEquals("Invalid email address", result);
    }

    @Test
    public void addUserByAdmin_AdminAddsUserSuccessfully() {
        Twitter2 adminUser = new Twitter2(1, "adminUser", "admin@dal.ca", "adminPass", "FName0", "LName0", "rec0", "int0", "active", "Admin");
        when(twitter2Repository.findByEmail("admin@dal.ca")).thenReturn(adminUser);

        when(twitter2Repository.findByEmail("newUser@dal.ca")).thenReturn(null);
        String result = twitter2Service.addUserByAdmin("newUser", "password", "FirstName", "LastName", "newUser@dal.ca", "recAnswer", "admin@dal.ca");

        assertEquals("User added successfully", result);
    }


    @Test
    public void addUserByAdmin_NonAdminUserCannotAddUser() {
        Twitter2 nonAdminUser = new Twitter2(1, "nonAdminUser", "user@dal.ca", "userPass", "NonAdmin", "User", "recUser", "intUser", "active", "Student");
        when(twitter2Repository.findByEmail("user@dal.ca")).thenReturn(nonAdminUser);

        String result = twitter2Service.addUserByAdmin("newUser", "password", "FirstName", "LastName", "newUser@dal.ca", "recAnswer", "user@dal.ca");

        assertEquals("This user is not authorized to create new user", result);
    }


    @Test
    public void addUserByAdmin_UserAlreadyExists() {
        Twitter2 adminUser = new Twitter2(1, "adminUser", "admin@dal.ca", "adminPass", "FName0", "LName0", "rec0", "int0", "active", "Admin");
        when(twitter2Repository.findByEmail("admin@dal.ca")).thenReturn(adminUser);
        Twitter2 existingUser = new Twitter2(2, "existingUser", "existing@dal.ca", "existingPass", "Fname1", "Lname1", "rec1", "int1", "active", "Student");
        when(twitter2Repository.findByEmail("existing@dal.ca")).thenReturn(existingUser);

        String result = twitter2Service.addUserByAdmin("existingUser", "existingPass", "Fname1", "Lname1", "existing@dal.ca", "rec1", "admin@dal.ca");

        assertEquals("User already exist", result);
    }


    @Test
    public void removeUserByAdmin_AdminRemovesUserSuccessfully() {
        Twitter2 adminUser = new Twitter2(1, "adminUser", "admin@dal.ca", "adminPass", "FName0", "LName0", "rec0", "int0", "active", "Admin");
        when(twitter2Repository.findByEmail("admin@dal.ca")).thenReturn(adminUser);

        Twitter2 user = new Twitter2(2, "user", "user@dal.ca", "password", "FirstName", "LastName", "recAnswer", "intUser", "active", "Student");
        when(twitter2Repository.findByEmail("user@dal.ca")).thenReturn(user);

        String result = twitter2Service.removeUserByAdmin("admin@dal.ca", "user@dal.ca");

        assertEquals("User deleted successfully.", result);
    }

    @Test
    public void removeUserByAdmin_NonAdminUserCannotRemoveUser() {
        Twitter2 nonAdminUser = new Twitter2(1, "nonAdminUser", "nonAdminUser@dal.ca", "userPass", "NonAdmin", "User", "recUser", "intUser", "active", "Student");
        when(twitter2Repository.findByEmail("nonAdminUser@dal.ca")).thenReturn(nonAdminUser);

        Twitter2 user = new Twitter2(2, "user", "user@dal.ca", "password", "FirstName", "LastName", "recAnswer", "intUser", "active", "Student");
        when(twitter2Repository.findByEmail("user@dal.ca")).thenReturn(user);

        String result = twitter2Service.removeUserByAdmin("nonAdminUser@dal.ca", "user@dal.ca");

        assertEquals("This user is not authorized to remove user", result);
    }

    @Test
    public void removeUserByAdmin_UserDoesNotExist() {
        Twitter2 adminUser = new Twitter2(1, "adminUser", "admin@dal.ca", "adminPass", "FirstName", "LastName", "recAnswer", "intAdmin", "active", "Admin");
        when(twitter2Repository.findByEmail("admin@dal.ca")).thenReturn(adminUser);

        when(twitter2Repository.findByEmail("nonExistentUser@dal.ca")).thenReturn(null);
        String result = twitter2Service.removeUserByAdmin("admin@dal.ca", "nonExistentUser@dal.ca");

        assertEquals("User does not exist with this email", result);
    }
}
