package com.group06.twitter2.service;

import com.group06.twitter2.model.Group;
import com.group06.twitter2.model.GroupMembership;

import java.util.ArrayList;
import java.util.Set;

public interface GroupMembershipService {
    // TODO: Add methods here
    public ArrayList<GroupMembership> getGroupMembersByGroupId(int groupId);
}
