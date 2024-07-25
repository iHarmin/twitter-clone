package com.group06.twitter2.DTO;
public class UserDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String personalInterests;

    public UserDTO(int id, String firstName, String lastName, String email, String personalInterests) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.personalInterests = personalInterests;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPersonalInterests() { return personalInterests; }
}
