package com.group06.twitter2.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "groupslol")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String groupName;

    private boolean isPublic;

    @OneToMany(mappedBy = "group")
    private Set<GroupMembership> members;

    @OneToMany(mappedBy = "group")
    private Set<GroupRequest> requests;

    public Group() {}

    public Group(String groupName, boolean isPublic) {
        this.groupName = groupName;
        this.isPublic = isPublic;
        this.members = new HashSet<>();
    }

    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }

    public String getGroupName() { return this.groupName; }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public boolean isPublic() { return this.isPublic; }
    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Set<GroupMembership> getMembers() { return this.members; }
}
