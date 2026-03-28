package com.loginapp.exception;

public class LoginException extends RuntimeException {

    public enum ErrorType {
        EMPTY_USERNAME, EMPTY_PASSWORD,
        NULL_USERNAME,  NULL_PASSWORD,
        USERNAME_TOO_SHORT, USERNAME_TOO_LONG,
        PASSWORD_TOO_SHORT, PASSWORD_TOO_LONG,
        SQL_INJECTION_DETECTED,
        ACCOUNT_LOCKED, INVALID_CREDENTIALS
    }

    private final ErrorType errorType;

    public LoginException(String message, ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }

    public ErrorType getErrorType() { return errorType; }
}
