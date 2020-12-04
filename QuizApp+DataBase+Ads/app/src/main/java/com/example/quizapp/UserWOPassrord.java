package com.example.quizapp;

public class UserWOPassrord {
    public String email, maxPoints;

    public UserWOPassrord(){}

    public UserWOPassrord(String email, String maxPoints)
    {
        this.email = email;
        this.maxPoints = maxPoints;
    }

    public int getMaxPoints(){
        int mp = 0;
        mp = Integer.parseInt(this.maxPoints);
        return mp;
    }
}
