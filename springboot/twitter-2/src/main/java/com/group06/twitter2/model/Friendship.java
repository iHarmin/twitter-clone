package com.group06.twitter2.model;

import jakarta.persistence.*;
import jakarta.persistence.Id;

@Entity
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friend_id")
    private int friendID;

    @ManyToOne
    @JoinColumn(name = "username1")
    private Twitter2 userName1;

    @ManyToOne
    @JoinColumn(name = "username2")
    private Twitter2 userName2;

    private boolean accepted;

    public Friendship() {
    }

    public Friendship(int friendID, Twitter2 userName1, Twitter2 userName2, boolean accepted) {
        this.friendID = friendID;
        this.userName1 = userName1;
        this.userName2 = userName2;
        this.accepted = accepted;
    }

    public int getFriendID() {
        return friendID;
    }

    public void setId(int id) {
        this.friendID = id;
    }

    public Twitter2 getUserName1() {
        return userName1;
    }

    public void setUserName1(Twitter2 userName1) {
        this.userName1 = userName1;
    }

    public Twitter2 getUserName2() {
        return userName2;
    }

    public void setUserName2(Twitter2 userName2) {
        this.userName2 = userName2;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
