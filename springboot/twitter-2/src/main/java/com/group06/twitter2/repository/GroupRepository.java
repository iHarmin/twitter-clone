package com.group06.twitter2.repository;

import com.group06.twitter2.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
    List<Group> findByGroupNameStartingWithIgnoreCase(String groupName);
}
