package com.group06.twitter2;

import com.group06.twitter2.model.Group;
import com.group06.twitter2.repository.GroupRepository;
import com.group06.twitter2.service.Implementations.GroupServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private GroupServiceImpl groupService;

    @Test
    public void testGetGroups() {
        List<Group> expectedGroups = new ArrayList<>();
        expectedGroups.add(new Group("Group 1", true));
        expectedGroups.add(new Group("Group 2", true));

        when(groupRepository.findAll()).thenReturn(expectedGroups);

        List<Group> actualGroups = groupService.getGroups();

        assertEquals(expectedGroups, actualGroups);
    }

    @Test
    public void testGetGroupsEmpty() {
        List<Group> expectedGroups = new ArrayList<>();

        when(groupRepository.findAll()).thenReturn(expectedGroups);

        List<Group> actualGroups = groupService.getGroups();

        assertEquals(expectedGroups, actualGroups);
    }

    @Test
    public void testCreateGroup() {
        Group group = new Group("Group 1", true);

        when(groupRepository.save(group)).thenReturn(group);

        Group createdGroup = groupService.createGroup(group);

        assertEquals(group, createdGroup);
    }

    @Test(expected = Exception.class)
    public void testCreateGroupException() {
        Group group = new Group("Group 1", true);

        when(groupRepository.save(group)).thenThrow(new RuntimeException());

        groupService.createGroup(group);
    }

    @Test
    public void testGetGroup() {
        Group expectedGroup = new Group("Group 1", true);
        expectedGroup.setId(1);

        when(groupRepository.findById(1)).thenReturn(Optional.of(expectedGroup));

        Group actualGroup = groupService.getGroup(1);

        assertEquals(expectedGroup, actualGroup);
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetGroupNotFound() {
        when(groupRepository.findById(1)).thenReturn(Optional.empty());

        groupService.getGroup(1);
    }

    @Test
    public void testSearchGroupsAvailable() {
        String searchTerm = "Group";
        List<Group> expectedGroups = new ArrayList<>();
        expectedGroups.add(new Group("Group 1", true));
        expectedGroups.add(new Group("Group 2", true));

        when(groupRepository.findByGroupNameStartingWithIgnoreCase(searchTerm)).thenReturn(expectedGroups);

        List<Group> actualGroups = groupService.searchGroups(searchTerm);

        assertEquals(expectedGroups, actualGroups);
    }

    @Test
    public void testSearchGroupsNoResults() {
        String searchTerm = "test";
        List<Group> expectedGroups = new ArrayList<>();

        when(groupRepository.findByGroupNameStartingWithIgnoreCase(searchTerm)).thenReturn(expectedGroups);

        List<Group> actualGroups = groupService.searchGroups(searchTerm);

        assertEquals(expectedGroups, actualGroups);
    }
}
