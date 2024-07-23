package com.group06.twitter2.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Optional;

@Entity
@Table(name = "group_requests")
public class GroupRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Twitter2 user;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    private Date requestDate;

    @Column(nullable = true)
    private Date approvedDate;

    private boolean isApproved;

    public GroupRequest() {
    }

    public int getId() { return this.id; }
    public void setId(int id) { this.id = id; }

    public Twitter2 getUser() { return this.user; }
    public void setUser(Twitter2 user) { this.user = user; }

    public Group getGroup() { return this.group; }
    public void setGroup(Group group) { this.group = group; }

    public Date getRequestDate() { return this.requestDate; }
    public void setRequestDate(Date date) { this.requestDate = date; }

    public Date getApprovedDate() { return this.approvedDate; }
    public void setApprovedDate(Date date) { this.approvedDate = date; }

    public boolean isApproved() { return this.isApproved; }
    public void setApproved(boolean approved) { this.isApproved = approved; }
}
