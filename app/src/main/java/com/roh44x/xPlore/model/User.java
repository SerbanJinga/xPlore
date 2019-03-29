package com.roh44x.xPlore.model;


import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {
    public String firstName;
    public String lastName;
    public String email;
    public String degree;

    public User() {
    }


    public User(String firstName, String lastName, String email, String degree)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.degree = degree;
    }
}
