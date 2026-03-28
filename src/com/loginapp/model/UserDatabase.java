package com.loginapp.model;

import java.util.HashMap;
import java.util.Map;

public class UserDatabase {
    private static final Map<String, User> users = new HashMap<>();

    static {
        users.put("admin",   new User("admin",   "admin123"));
        users.put("user1",   new User("user1",   "pass@123"));
        users.put("alice",   new User("alice",   "alice$99"));
        users.put("bob",     new User("bob",     "b0bSecure"));
        users.put("charlie", new User("charlie", "charL!e01"));
    }

    public static User    getUser(String u)   { return users.get(u); }
    public static boolean userExists(String u) { return users.containsKey(u); }
    public static void    resetAllUsers()      { users.values().forEach(User::resetFailedAttempts); }
}
