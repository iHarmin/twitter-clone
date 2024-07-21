package com.group06.twitter2.repository;

import com.group06.twitter2.model.GroupMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface GroupMembershipRepository extends JpaRepository<GroupMembership, Integer> {

    @Query(value = "SELECT * FROM `group_membership` WHERE `group_id` = ?", nativeQuery = true)
    ArrayList<GroupMembership> findAllByGroupId(int groupId);
}