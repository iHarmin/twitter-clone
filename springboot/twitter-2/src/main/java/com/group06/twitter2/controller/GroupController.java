package com.group06.twitter2.controller;

import com.group06.twitter2.model.Group;
import com.group06.twitter2.model.GroupMembership;
import com.group06.twitter2.model.Twitter2;
import com.group06.twitter2.service.GroupMembershipService;
import com.group06.twitter2.service.GroupRequestService;
import com.group06.twitter2.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    public ArrayList<Group> getGroups() { return groupService.getGroups(); }

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
}
