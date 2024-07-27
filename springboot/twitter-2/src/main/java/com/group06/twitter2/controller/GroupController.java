package com.group06.twitter2.controller;

import com.group06.twitter2.model.Group;
import com.group06.twitter2.model.GroupMembership;
import com.group06.twitter2.model.Twitter2;
import com.group06.twitter2.service.GroupMembershipService;
import com.group06.twitter2.service.GroupRequestService;
import com.group06.twitter2.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/groups")
public class GroupController {
    @Autowired
    GroupService groupService;

    @Autowired
    GroupMembershipService groupMembershipService;

    @Autowired
    GroupRequestService groupRequestService;

    @GetMapping("/getGroups")
    public ArrayList<Group> getGroups() {
        return groupService.getGroups();
    }

    @GetMapping("/{id}")
    public Group GetGroup(@PathVariable int id) {
        return groupService.getGroup(id);
    }

    @PostMapping("/createGroup")
    public Group createGroup(@RequestBody Group group) {
        return groupService.createGroup(group);
    }

    @GetMapping("/{id}/members")
    public ArrayList<GroupMembership> getGroupMembersByGroupId(@PathVariable int id) {
        return groupMembershipService.getGroupMembersByGroupId(id);
    }

    @GetMapping("/search")
    public List<Group> searchGroups(@RequestParam String searchTerm) {
        return groupService.searchGroups(searchTerm);
    }

    @PostMapping("/{id}/join")
    public ResponseEntity<Group> joinGroup(@PathVariable int id, @RequestBody Map<String, Integer> request) {
        int userId = request.get("userId");
        Group updatedGroup = groupService.joinGroup(id, userId);
        return ResponseEntity.ok(updatedGroup);
    }

    @PostMapping("/{id}/leave")
    public ResponseEntity<Group> leaveGroup(@PathVariable int id, @RequestBody Map<String, Integer> request) {
        int userId = request.get("userId");
        Group updatedGroup = groupService.leaveGroup(id, userId);
        return ResponseEntity.ok(updatedGroup);
    }

    @GetMapping("/{id}/isUserInGroup")
    public ResponseEntity<Boolean> isUserInGroup(@PathVariable int id, @RequestParam int userId) {
        boolean isInGroup = groupService.isUserInGroup(id, userId);
        return ResponseEntity.ok(isInGroup);
    }
}
