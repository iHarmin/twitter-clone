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
    @JoinColumn(name = "user_id1", referencedColumnName = "id")
    private Twitter2 user1;

    @ManyToOne
    @JoinColumn(name = "user_id2", referencedColumnName = "id")
    private Twitter2 user2;

    private boolean accepted;

    public Friendship() {
    }

    public Friendship(int friendID, Twitter2 user1, Twitter2 user2, boolean accepted) {
        this.friendID = friendID;
        this.user1 = user1;
        this.user2 = user2;
        this.accepted = accepted;
    }

    public int getFriendID() {
        return friendID;
    }

    public void setId(int id) {
        this.friendID = id;
    }

    public Twitter2 getUser1() {
        return user1;
    }

    public void setUser1(Twitter2 user1) {
        this.user1 = user1;
    }

    public Twitter2 getUser2() {
        return user2;
    }

    public void setUser2(Twitter2 user2) {
        this.user2 = user2;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
