package com.group06.twitter2.model;

import jakarta.persistence.*;
import jakarta.persistence.Id;

@Entity
public class Twitter2 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String userName;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String securityAnswer;
    private String personalInterests;
    private String status;

    public Twitter2() {
    }

    public Twitter2(int id, String userName, String email, String password, String firstName, String lastName, String securityAnswer, String personalInterests, String status) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.securityAnswer = securityAnswer;
        this.personalInterests = personalInterests;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getPersonalInterests() {
        return personalInterests;
    }

    public void setPersonalInterests(String personalInterests) {
        this.personalInterests = personalInterests;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

