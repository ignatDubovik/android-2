package com.example.quizapp;

public class User {
    public String email, password, maxPoints;

    public User(){}

    public User(String email, String password, int maxPoints)
    {
        this.email = email;
        this.password = password;
        this.maxPoints = ""+maxPoints;
    }


}
