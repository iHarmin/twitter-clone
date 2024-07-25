package com.group06.twitter2;

<<<<<<< HEAD
=======
import com.group06.twitter2.model.Post;
import com.group06.twitter2.DTO.UserDTO;
>>>>>>> 4bfe7736c65de7b8b92db83a66425d6018a27e48
import com.group06.twitter2.model.Twitter2;
import com.group06.twitter2.repository.Twitter2Repository;
import com.group06.twitter2.service.Implementations.Twitter2ServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
        Twitter2 twitter2 = new Twitter2(0, "Name0", "n0@dal.ca", "password0", "FName0", "LName0", "rec0", "int0",
                "active", "Student", Twitter2.RequestStatus.APPROVED);

        when(twitter2Repository.save(twitter2)).thenReturn(twitter2);
        String result = twitter2Service.createUser(twitter2);

        assertEquals("User profile created", result);
        assertNotNull(twitter2.getId());
    }

    @Test
    public void createUserTest_InvalidEmail() {
        Twitter2 twitter2 = new Twitter2(0, "Name0", "n0@gmail.com", "password0", "FName0", "LName0", "rec0", "int0",
                "active", "Admin", Twitter2.RequestStatus.APPROVED);

        String result = twitter2Service.createUser(twitter2);

        assertEquals("Invalid email address", result);
    }

    @Test
    public void getUserByIDTest_Successful() {
        Twitter2 twitter2 = new Twitter2();
        twitter2.setId(0);
        int id = 0;
        when(twitter2Repository.findById(id)).thenReturn(Optional.of(twitter2));
        Twitter2 twitter3 = twitter2Service.getUserByID(0);
        assertEquals(twitter3, twitter2);
    }

    @Test
    public void getUserByIDTest_NotFound() {
        int id = 0;
        when(twitter2Repository.findById(id)).thenReturn(Optional.empty());
        boolean error = false;
        try {
            twitter2Service.getUserByID(0);
        } catch (RuntimeException e) {
            error = true;
            assertEquals("Customer not found with id: 0", e.getMessage());
        }
        assert (error);
    }

    @Test
    public void updateUserStatusTest_Successful() {
        Twitter2 twitter2 = new Twitter2();
        twitter2.setStatus("inactive");

        when(twitter2Repository.save(twitter2)).thenReturn(twitter2);
        when(twitter2Repository.findById(twitter2.getId())).thenReturn(Optional.of(twitter2));

        twitter2Service.updateUserStatus(twitter2.getId(), "active");
        assertEquals("active", twitter2.getStatus());
    }

    @Test
    public void updateUserStatusTest_UserNotFound() {
        int id = 0;
        when(twitter2Repository.findById(id)).thenReturn(Optional.empty());
        String output = twitter2Service.updateUserStatus(id, "active");
        assertEquals("User not found with id: 0", output);
    }

    @Test
    public void updateUserInformationTest_successful() {
        Twitter2 user = new Twitter2();

        int id = user.getId();
        UserDTO userDTO = new UserDTO(id, "Name", "Lname", "email@example.ca", "Interest");
        when(twitter2Repository.findById(id)).thenReturn(Optional.of(user));
        String output = twitter2Service.updateUserInformation(userDTO);
        assertEquals("User information updated successfully", output);
        assertEquals(user.getEmail(), "email@example.ca");
    }

    @Test
    public void updateUserInformationTest_UserNotFound() {
        int id = 0;
        UserDTO userDTO = new UserDTO(id, "Name", "Lname", "email@example.ca", "Interest");
        when(twitter2Repository.findById(id)).thenReturn(Optional.empty());
        String output = twitter2Service.updateUserInformation(userDTO);
        assertEquals("User not found", output);
    }

    @Test
    public void resetPasswordTest_Successful() {
        Twitter2 twitter2 = new Twitter2();
        twitter2.setRecoveryAnswer("answer");
        twitter2.setPassword("OldPassword");
        twitter2.setEmail("name@example.com");
        when(twitter2Repository.findByEmail(twitter2.getEmail())).thenReturn(twitter2);

        String output = twitter2Service.resetPassword(twitter2.getEmail(), twitter2.getRecoveryAnswer(), "NewPassword");
        assertEquals("New password set", output);
        assertEquals(twitter2.getPassword(), "NewPassword");
    }

    @Test
    public void resetPasswordTest_WrongAnswer() {
        Twitter2 twitter2 = new Twitter2();
        twitter2.setRecoveryAnswer("answer");
        twitter2.setPassword("OldPassword");
        twitter2.setEmail("name@example.com");
        when(twitter2Repository.findByEmail(twitter2.getEmail())).thenReturn(twitter2);

        String output = twitter2Service.resetPassword(twitter2.getEmail(), "wrong answer", "NewPassword");
        assertEquals("Wrong security answer", output);
    }

    @Test
    public void resetPasswordTest_NoUser() {

    }

    @Test
    public void addUserByAdmin_AdminAddsUserSuccessfully() {
        Twitter2 adminUser = new Twitter2(1, "adminUser", "admin@dal.ca", "adminPass", "FName0", "LName0", "rec0",
                "int0", "active", "Admin", Twitter2.RequestStatus.APPROVED);

        when(twitter2Repository.findByEmail("admin@dal.ca")).thenReturn(adminUser);

        when(twitter2Repository.findByEmail("newUser@dal.ca")).thenReturn(null);
        String result = twitter2Service.addUserByAdmin("newUser", "password", "FirstName", "LastName", "newUser@dal.ca",
                "recAnswer", "admin@dal.ca");

        assertEquals("User added successfully", result);
    }

    @Test
    public void addUserByAdmin_NonAdminUserCannotAddUser() {
        Twitter2 nonAdminUser = new Twitter2(1, "nonAdminUser", "user@dal.ca", "userPass", "NonAdmin", "User",
                "recUser", "intUser", "active", "Student", Twitter2.RequestStatus.APPROVED);

        when(twitter2Repository.findByEmail("user@dal.ca")).thenReturn(nonAdminUser);

        String result = twitter2Service.addUserByAdmin("newUser", "password", "FirstName", "LastName", "newUser@dal.ca",
                "recAnswer", "user@dal.ca");

        assertEquals("This user is not authorized to create new user", result);
    }

    @Test
    public void addUserByAdmin_UserAlreadyExists() {
        Twitter2 adminUser = new Twitter2(1, "adminUser", "admin@dal.ca", "adminPass", "FName0", "LName0", "rec0",
                "int0", "active", "Admin", Twitter2.RequestStatus.APPROVED);
        when(twitter2Repository.findByEmail("admin@dal.ca")).thenReturn(adminUser);

        Twitter2 existingUser = new Twitter2(2, "existingUser", "existing@dal.ca", "existingPass", "Fname1", "Lname1",
                "rec1", "int1", "active", "Student", Twitter2.RequestStatus.APPROVED);
        when(twitter2Repository.findByEmail("existing@dal.ca")).thenReturn(existingUser);

        String result = twitter2Service.addUserByAdmin("existingUser", "existingPass", "Fname1", "Lname1",
                "existing@dal.ca", "rec1", "admin@dal.ca");

        assertEquals("User already exist", result);
    }

    @Test
    public void removeUserByAdmin_AdminRemovesUserSuccessfully() {
        Twitter2 adminUser = new Twitter2(1, "adminUser", "admin@dal.ca", "adminPass", "FName0", "LName0", "rec0",
                "int0", "active", "Admin", Twitter2.RequestStatus.APPROVED);
        when(twitter2Repository.findByEmail("admin@dal.ca")).thenReturn(adminUser);

        Twitter2 user = new Twitter2(2, "user", "user@dal.ca", "password", "FirstName", "LastName", "recAnswer",
                "intUser", "active", "Student", Twitter2.RequestStatus.APPROVED);
        when(twitter2Repository.findByEmail("user@dal.ca")).thenReturn(user);

        String result = twitter2Service.removeUserByAdmin("admin@dal.ca", "user@dal.ca");

        assertEquals("User deleted successfully.", result);
    }

    @Test
    public void removeUserByAdmin_NonAdminUserCannotRemoveUser() {
        Twitter2 nonAdminUser = new Twitter2(1, "nonAdminUser", "nonAdminUser@dal.ca", "userPass", "NonAdmin", "User",
                "recUser", "intUser", "active", "Student", Twitter2.RequestStatus.APPROVED);
        when(twitter2Repository.findByEmail("nonAdminUser@dal.ca")).thenReturn(nonAdminUser);

        Twitter2 user = new Twitter2(2, "user", "user@dal.ca", "password", "FirstName", "LastName", "recAnswer",
                "intUser", "active", "Student", Twitter2.RequestStatus.APPROVED);
        when(twitter2Repository.findByEmail("user@dal.ca")).thenReturn(user);

        String result = twitter2Service.removeUserByAdmin("nonAdminUser@dal.ca", "user@dal.ca");

        assertEquals("This user is not authorized to remove user", result);
    }

    @Test
    public void removeUserByAdmin_UserDoesNotExist() {
        Twitter2 adminUser = new Twitter2(1, "adminUser", "admin@dal.ca", "adminPass", "FirstName", "LastName",
                "recAnswer", "intAdmin", "active", "Admin", Twitter2.RequestStatus.APPROVED);

        when(twitter2Repository.findByEmail("admin@dal.ca")).thenReturn(adminUser);

        when(twitter2Repository.findByEmail("nonExistentUser@dal.ca")).thenReturn(null);
        String result = twitter2Service.removeUserByAdmin("admin@dal.ca", "nonExistentUser@dal.ca");

        assertEquals("User does not exist with this email", result);
    }

    @Test
    public void approveRequest_ValidAdmin_ValidRequestId() {
        Twitter2 adminUser = new Twitter2();
        adminUser.setEmail("admin@dal.ca");
        adminUser.setRole("Admin");

        Twitter2 normalUser = new Twitter2();
        normalUser.setRequestStatus(String.valueOf(Twitter2.RequestStatus.PENDING));

        when(twitter2Repository.findByEmail("admin@dal.ca")).thenReturn(adminUser);
        when(twitter2Repository.findById(1)).thenReturn(Optional.of(normalUser));

        String result = twitter2Service.approveRequest(1L, "admin@dal.ca");

        assertEquals("Request successfully approved", result);
        assertEquals(Twitter2.RequestStatus.APPROVED, normalUser.getRequestStatus());
        verify(twitter2Repository, times(1)).save(normalUser);
    }

    @Test
    public void approveRequest_NonAdminUser() {
        Twitter2 nonAdminUser = new Twitter2();
        nonAdminUser.setEmail("user@dal.ca");
        nonAdminUser.setRole("Student");

        when(twitter2Repository.findByEmail("user@dal.ca")).thenReturn(nonAdminUser);

        String result = twitter2Service.approveRequest(1L, "user@dal.ca");

        assertEquals("Only admins can approve requests", result);
        verify(twitter2Repository, never()).save(any(Twitter2.class));
    }

    @Test
    public void approveRequest_InvalidRequestId() {
        Twitter2 adminUser = new Twitter2();
        adminUser.setEmail("admin@dal.ca");
        adminUser.setRole("Admin");

        when(twitter2Repository.findByEmail("admin@dal.ca")).thenReturn(adminUser);
        when(twitter2Repository.findById(999)).thenReturn(Optional.empty());

        String result = twitter2Service.approveRequest(999L, "admin@dal.ca");

        assertEquals("Invalid user ID. User ID is not present", result);
        verify(twitter2Repository, never()).save(any(Twitter2.class));
    }

    @Test
    public void rejectRequest_ValidAdmin_ValidRequestId() {
        Twitter2 adminUser = new Twitter2();
        adminUser.setEmail("admin@dal.ca");
        adminUser.setRole("Admin");

        Twitter2 normalUser = new Twitter2();
        normalUser.setRequestStatus(String.valueOf(Twitter2.RequestStatus.PENDING));

        when(twitter2Repository.findByEmail("admin@dal.ca")).thenReturn(adminUser);
        when(twitter2Repository.findById(1)).thenReturn(Optional.of(normalUser));

        String result = twitter2Service.rejectRequest(1L, "admin@dal.ca");

        assertEquals("Request rejected successfully", result);
        assertEquals(Twitter2.RequestStatus.REJECTED, normalUser.getRequestStatus());
        verify(twitter2Repository, times(1)).save(normalUser);
    }

    @Test
    public void rejectRequest_NonAdminUser() {
        Twitter2 nonAdminUser = new Twitter2();
        nonAdminUser.setEmail("user@dal.ca");
        nonAdminUser.setRole("Student");

        when(twitter2Repository.findByEmail("user@dal.ca")).thenReturn(nonAdminUser);

        String result = twitter2Service.rejectRequest(1L, "user@dal.ca");

        assertEquals("Only admins can reject requests", result);
        verify(twitter2Repository, never()).save(any(Twitter2.class));
    }

    @Test
    public void rejectRequest_InvalidRequestId() {
        Twitter2 adminUser = new Twitter2();
        adminUser.setEmail("admin@dal.ca");
        adminUser.setRole("Admin");

        when(twitter2Repository.findByEmail("admin@dal.ca")).thenReturn(adminUser);
        when(twitter2Repository.findById(999)).thenReturn(Optional.empty());

        String result = twitter2Service.rejectRequest(999L, "admin@dal.ca");

        assertEquals("Invalid user ID. User ID is not present", result);
        verify(twitter2Repository, never()).save(any(Twitter2.class));
    }

    @Test
    public void rejectRequest_AlreadyApprovedRequest() {
        Twitter2 adminUser = new Twitter2();
        adminUser.setEmail("admin@dal.ca");
        adminUser.setRole("Admin");

        Twitter2 normalUser = new Twitter2();
        normalUser.setRequestStatus("APPROVED");

        when(twitter2Repository.findByEmail("admin@dal.ca")).thenReturn(adminUser);
        when(twitter2Repository.findById(1)).thenReturn(Optional.of(normalUser));

        String result = twitter2Service.rejectRequest(1L, "admin@dal.ca");

        assertEquals("Request is already approved. Cannot reject, approved request.", result);
        verify(twitter2Repository, never()).save(normalUser);
    }

    public void changeUserRoleByAdmin_AdminChangesRoleSuccessfully() {
        Twitter2 adminUser = new Twitter2(1, "adminUser", "admin@dal.ca", "adminPass", "FirstName", "LastName",
                "recAnswer", "intAdmin", "active", "Admin", Twitter2.RequestStatus.APPROVED);
        Twitter2 user = new Twitter2(2, "user", "user@dal.ca", "userPass", "FirstName", "LastName", "recAnswer",
                "intUser", "active", "Student", Twitter2.RequestStatus.APPROVED);

        when(twitter2Repository.findByEmail("admin@dal.ca")).thenReturn(adminUser);
        when(twitter2Repository.findByEmail("user@dal.ca")).thenReturn(user);

        String result = twitter2Service.changeUserRoleByAdmin("admin@dal.ca", "user@dal.ca", "Employee");

        assertEquals("User role updated successfully", result);
        assertEquals("Employee", user.getRole());
    }

    @Test
    public void changeUserRoleByAdmin_NonAdminCannotChangeRole() {
        Twitter2 nonAdminUser = new Twitter2(1, "nonAdminUser", "nonAdmin@dal.ca", "userPass", "FirstName", "LastName",
                "recAnswer", "intUser", "active", "Student", Twitter2.RequestStatus.APPROVED);
        Twitter2 user = new Twitter2(2, "user", "user@dal.ca", "userPass", "FirstName", "LastName", "recAnswer",
                "intUser", "active", "Student", Twitter2.RequestStatus.APPROVED);

        when(twitter2Repository.findByEmail("nonAdmin@dal.ca")).thenReturn(nonAdminUser);

        String result = twitter2Service.changeUserRoleByAdmin("nonAdmin@dal.ca", "user@dal.ca", "Employee");

        assertEquals("This user is not authorized to change user roles", result);
    }

    @Test
    public void changeUserRoleByAdmin_UserNotFound() {
        Twitter2 adminUser = new Twitter2(1, "adminUser", "admin@dal.ca", "adminPass", "FirstName", "LastName",
                "recAnswer", "intAdmin", "active", "Admin", Twitter2.RequestStatus.APPROVED);

        when(twitter2Repository.findByEmail("admin@dal.ca")).thenReturn(adminUser);
        when(twitter2Repository.findByEmail("user@dal.ca")).thenReturn(null);

        String result = twitter2Service.changeUserRoleByAdmin("admin@dal.ca", "user@dal.ca", "Employee");

        assertEquals("User not found", result);
    }

    @Test
    public void changeUserRoleByAdmin_InvalidRoleSpecified() {
        Twitter2 adminUser = new Twitter2(1, "adminUser", "admin@dal.ca", "adminPass", "FirstName", "LastName",
                "recAnswer", "intAdmin", "active", "Admin", Twitter2.RequestStatus.APPROVED);
        Twitter2 user = new Twitter2(2, "user", "user@dal.ca", "userPass", "FirstName", "LastName", "recAnswer",
                "intUser", "active", "Student", Twitter2.RequestStatus.APPROVED);

        when(twitter2Repository.findByEmail("admin@dal.ca")).thenReturn(adminUser);
        when(twitter2Repository.findByEmail("user@dal.ca")).thenReturn(user);

        String result = twitter2Service.changeUserRoleByAdmin("admin@dal.ca", "user@dal.ca", "InvalidRole");

        assertEquals("Invalid role specified", result);
    }

    public void checkPasswordValidTest_successful() {
        Twitter2 user = new Twitter2();
        user.setPassword("password");
        user.setEmail("user@dal.ca");
        when(twitter2Repository.findByEmail("user@dal.ca")).thenReturn(user);
        Twitter2 output = twitter2Service.checkPasswordValid("user@dal.ca", "password");
        assertEquals(output, user);
    }

    @Test
    public void checkPasswordValidTest_WrongPassword() {
        Twitter2 user = new Twitter2();
        user.setPassword("password");
        user.setEmail("user@dal.ca");
        when(twitter2Repository.findByEmail("user@dal.ca")).thenReturn(user);
        Twitter2 output = twitter2Service.checkPasswordValid("user@dal.ca", "password1");
        assertNull(output);
    }

    @Test
    public void searchUser_ByUserName() {
        Twitter2 twitter2 = new Twitter2(0, "UserName0", "n0@dal.ca", "password0", "FName0",
                "LName0", "rec0", "int0", "active", "Student", Twitter2.RequestStatus.APPROVED);
        String searchTerm = "UserName0";

        when(twitter2Repository.searchByUserNameOrEmailOrInterests(searchTerm)).thenReturn(List.of(twitter2));
        List<Twitter2> result = twitter2Service.searchUsers(searchTerm);

        assertEquals("UserName0", result.get(0).getUserName());
    }

    @Test
    public void searchUser_ByEmail() {
        Twitter2 twitter2 = new Twitter2(0, "UserName0", "n0@dal.ca", "password0", "FName0",
                "LName0", "rec0", "int0", "active", "Student", Twitter2.RequestStatus.APPROVED);
        String searchTerm = "n0@dal.ca";

        when(twitter2Repository.searchByUserNameOrEmailOrInterests(searchTerm)).thenReturn(List.of(twitter2));
        List<Twitter2> result = twitter2Service.searchUsers(searchTerm);

        assertEquals("n0@dal.ca", result.get(0).getEmail());
    }

    @Test
    public void searchUser_ByInterests() {
        Twitter2 twitter2 = new Twitter2(0, "UserName0", "n0@dal.ca", "password0", "FName0",
                "LName0", "rec0", "reading", "active", "Student", Twitter2.RequestStatus.APPROVED);
        String searchTerm = "reading";

        when(twitter2Repository.searchByUserNameOrEmailOrInterests(searchTerm)).thenReturn(List.of(twitter2));
        List<Twitter2> result = twitter2Service.searchUsers(searchTerm);

        assertEquals("reading", result.get(0).getPersonalInterests());
    }
}
