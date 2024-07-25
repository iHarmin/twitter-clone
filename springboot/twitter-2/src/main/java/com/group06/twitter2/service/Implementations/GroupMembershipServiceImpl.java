package com.group06.twitter2.service.Implementations;

import com.group06.twitter2.model.GroupMembership;
import com.group06.twitter2.repository.GroupMembershipRepository;
import com.group06.twitter2.service.GroupMembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GroupMembershipServiceImpl implements GroupMembershipService {
    @Autowired
    GroupMembershipRepository groupMembershipRepository;

    @Override
    public ArrayList<GroupMembership> getGroupMembersByGroupId(int groupId) {
        return groupMembershipRepository.findAllByGroupId(groupId);
    }

    // TODO: Add methods here

}
