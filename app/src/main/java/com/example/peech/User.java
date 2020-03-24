package com.example.peech;

public class User {

    private int id;
    private String firstName, lastName, username, email, phonenumber, password;

    public User(int id, String firstName, String lastName, String username, String email, String phonenumber, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName= lastName;
        this.password=password;
        this.phonenumber=phonenumber;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getPassword() {
        return password;
    }
}
