package com.group06.twitter2.service.Implementations;

import com.group06.twitter2.model.Group;
import com.group06.twitter2.model.GroupMembership;
import com.group06.twitter2.model.Twitter2;
import com.group06.twitter2.repository.GroupMembershipRepository;
import com.group06.twitter2.repository.GroupRepository;
import com.group06.twitter2.repository.Twitter2Repository;
import com.group06.twitter2.service.GroupMembershipService;
import com.group06.twitter2.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {
    @Autowired
    GroupRepository groupRepository;

    @Autowired
    Twitter2Repository twitter2Repository;

    @Autowired
    GroupMembershipRepository groupMembershipRepository;

    @Override
    public ArrayList<Group> getGroups() {
        return (ArrayList<Group>) groupRepository.findAll();
    }

    @Override
    public Group getGroup(int id) {
        Optional<Group> group = groupRepository.findById(id);

        if (group.isEmpty()) throw new NoSuchElementException();

        return group.get();
    }

    @Override
    public Group createGroup(Group group) {
        try {
            groupRepository.save(group);
        }
        catch (Exception ex) {
            throw ex;
        }
        return group;
    }

    @Override
    public ArrayList<Group> searchGroups(String searchTerm) {
        return (ArrayList<Group>) groupRepository.findByGroupNameStartingWithIgnoreCase(searchTerm);
    }

    public Group joinGroup(int groupId, int userId) {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found"));
        Twitter2 user = twitter2Repository.findById(userId).orElseThrow(() -> new RuntimeException( "User not found"));

        GroupMembership membership = new GroupMembership();
        membership.setGroup(group);
        membership.setUser(user);
        groupMembershipRepository.save(membership);

        group.getMembers().add(membership);
        return groupRepository.save(group);
    }
}
