package com.loginapp.service;

import com.loginapp.exception.LoginException;
import com.loginapp.exception.LoginException.ErrorType;

public class InputValidator {

    private static final int U_MIN = 3,  U_MAX = 20;
    private static final int P_MIN = 6,  P_MAX = 20;

    private static final String[] SQL_PATTERNS = {
        "'", "\"", "--", ";", "OR", "AND", "DROP",
        "SELECT", "INSERT", "DELETE", "UPDATE", "1=1"
    };

    public void validateUsername(String username) {
        if (username == null)
            throw new LoginException("Username cannot be null.", ErrorType.NULL_USERNAME);
        if (username.trim().isEmpty())
            throw new LoginException("Username cannot be empty.", ErrorType.EMPTY_USERNAME);
        if (username.length() < U_MIN)
            throw new LoginException("Username too short. Min " + U_MIN + " chars.", ErrorType.USERNAME_TOO_SHORT);
        if (username.length() > U_MAX)
            throw new LoginException("Username too long. Max " + U_MAX + " chars.", ErrorType.USERNAME_TOO_LONG);
        if (containsSqlInjection(username))
            throw new LoginException("Invalid characters in username.", ErrorType.SQL_INJECTION_DETECTED);
    }

    public void validatePassword(String password) {
        if (password == null)
            throw new LoginException("Password cannot be null.", ErrorType.NULL_PASSWORD);
        if (password.trim().isEmpty())
            throw new LoginException("Password cannot be empty.", ErrorType.EMPTY_PASSWORD);
        if (password.length() < P_MIN)
            throw new LoginException("Password too short. Min " + P_MIN + " chars.", ErrorType.PASSWORD_TOO_SHORT);
        if (password.length() > P_MAX)
            throw new LoginException("Password too long. Max " + P_MAX + " chars.", ErrorType.PASSWORD_TOO_LONG);
        if (containsSqlInjection(password))
            throw new LoginException("Invalid characters in password.", ErrorType.SQL_INJECTION_DETECTED);
    }

    public boolean containsSqlInjection(String input) {
        if (input == null) return false;
        String up = input.toUpperCase();
        for (String p : SQL_PATTERNS)
            if (up.contains(p.toUpperCase())) return true;
        return false;
    }
}
