package com.group06.twitter2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class GroupMembership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    @JsonIgnore
    private Group group;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Twitter2 user;

    private boolean canApproveRequests;

    public GroupMembership() {
    }

    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }

    public Group getGroup() { return this.group; }
    public void setGroup(Group group) { this.group = group; }

    public Twitter2 getUser() { return this.user; }
    public void setUser(Twitter2 user) { this.user = user; }

    public boolean canApproveRequests() { return this.canApproveRequests; }
    public void setCanApproveRequests(boolean canApproveRequests) { this.canApproveRequests = canApproveRequests; }
}
