package com.loginapp.service;

import com.loginapp.exception.LoginException;
import com.loginapp.exception.LoginException.ErrorType;
import com.loginapp.model.User;
import com.loginapp.model.UserDatabase;

public class AuthService {

    private final InputValidator validator;

    public AuthService()                        { this.validator = new InputValidator(); }
    public AuthService(InputValidator validator) { this.validator = validator; }

    public boolean login(String username, String password) {
        validator.validateUsername(username);
        validator.validatePassword(password);

        User user = UserDatabase.getUser(username);
        if (user == null)
            throw new LoginException("Invalid username or password.", ErrorType.INVALID_CREDENTIALS);

        if (user.isLocked())
            throw new LoginException("Account locked after 3 failed attempts.", ErrorType.ACCOUNT_LOCKED);

        if (!user.getPassword().equals(password)) {
            user.incrementFailedAttempts();
            if (user.isLocked())
                throw new LoginException("Account locked after 3 failed attempts.", ErrorType.ACCOUNT_LOCKED);
            int rem = 3 - user.getFailedAttempts();
            throw new LoginException("Invalid credentials. " + rem + " attempt(s) left.", ErrorType.INVALID_CREDENTIALS);
        }

        user.resetFailedAttempts();
        return true;
    }

    public int     getFailedAttempts(String u) { User x = UserDatabase.getUser(u); return x == null ? 0 : x.getFailedAttempts(); }
    public boolean isAccountLocked(String u)   { User x = UserDatabase.getUser(u); return x != null && x.isLocked(); }
}
