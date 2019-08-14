package com.example.characters.model;

import java.io.Serializable;

public class User implements Serializable {

    private static final int MAX_SCORE = 1000;
    private String username;
    private int level;
    private double score;

    public User(String username) {
        this.username = username;
        this.level = 1;
        this.score = 0;
    }

    public String getUsername() {
        return username;
    }

    public int getLevel() {
        return level;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", level=" + level +
                '}';
    }
}
