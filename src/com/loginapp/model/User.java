package com.loginapp.model;

public class User {
    private final String username;
    private final String password;
    private int failedAttempts;
    private boolean locked;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String  getUsername()       { return username; }
    public String  getPassword()       { return password; }
    public int     getFailedAttempts() { return failedAttempts; }
    public boolean isLocked()          { return locked; }

    public void incrementFailedAttempts() {
        failedAttempts++;
        if (failedAttempts >= 3) locked = true;
    }

    public void resetFailedAttempts() {
        failedAttempts = 0;
        locked = false;
    }
}
