package com.group06.twitter2.repository;

import com.group06.twitter2.model.Group;
import com.group06.twitter2.model.GroupMembership;
import com.group06.twitter2.model.Twitter2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface GroupMembershipRepository extends JpaRepository<GroupMembership, Integer> {

    @Query(value = "SELECT * FROM `group_membership` WHERE `group_id` = ?", nativeQuery = true)
    ArrayList<GroupMembership> findAllByGroupId(int groupId);

    List<GroupMembership> findByGroupAndUser(Group group, Twitter2 user);
}
