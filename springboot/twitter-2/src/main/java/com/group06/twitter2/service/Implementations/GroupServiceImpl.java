package com.group06.twitter2.service.Implementations;

import com.group06.twitter2.model.Group;
import com.group06.twitter2.repository.GroupRepository;
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
}
