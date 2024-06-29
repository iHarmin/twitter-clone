package com.group06.twitter2.model;

import jakarta.persistence.*;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String body;

    @ManyToOne
    @JoinColumn(name = "userName")
    private Twitter2 userID;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Twitter2 getUserID() {
        return userID;
    }

    public void setUserID(Twitter2 userID) {
        this.userID = userID;
    }
}
