package com.group06.twitter2.service;

import com.group06.twitter2.model.Group;

import java.util.ArrayList;

public interface GroupService {
    ArrayList<Group> getGroups();
    Group getGroup(int id);
    Group createGroup(Group group);
    ArrayList<Group> searchGroups(String searchTerm);

    Group joinGroup(int id, int userId);
}
