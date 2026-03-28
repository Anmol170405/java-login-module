package com.loginapp;

import com.loginapp.exception.LoginException;
import com.loginapp.exception.LoginException.ErrorType;
import com.loginapp.model.UserDatabase;
import com.loginapp.service.AuthService;
import com.loginapp.service.InputValidator;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Login Module — Full JUnit 5 Test Suite (30 Tests)")
public class LoginModuleTest {

    static AuthService auth;
    static InputValidator validator;

    @BeforeAll static void init() {
        auth      = new AuthService();
        validator = new InputValidator();
    }

    @BeforeEach void reset() { UserDatabase.resetAllUsers(); }

    // ── SECTION 1: FUNCTIONAL TESTING ────────────────────────────────────────

    @Test @Order(1)  @DisplayName("TC-01 | Functional | Valid login — admin / admin123")
    void tc01() { assertTrue(auth.login("admin", "admin123")); }

    @Test @Order(2)  @DisplayName("TC-02 | Functional | Valid login — user1 / pass@123")
    void tc02() { assertTrue(auth.login("user1", "pass@123")); }

    @Test @Order(3)  @DisplayName("TC-03 | Functional | Valid login — alice / alice$99")
    void tc03() { assertTrue(auth.login("alice", "alice$99")); }

    @Test @Order(4)  @DisplayName("TC-04 | Functional | Wrong password → INVALID_CREDENTIALS")
    void tc04() {
        LoginException e = assertThrows(LoginException.class, () -> auth.login("admin", "wrongpass"));
        assertEquals(ErrorType.INVALID_CREDENTIALS, e.getErrorType());
    }

    @Test @Order(5)  @DisplayName("TC-05 | Functional | Non-existent username → INVALID_CREDENTIALS")
    void tc05() {
        LoginException e = assertThrows(LoginException.class, () -> auth.login("ghost", "ghost123"));
        assertEquals(ErrorType.INVALID_CREDENTIALS, e.getErrorType());
    }

    // ── SECTION 2: NEGATIVE TESTING ─────────────────────────────────────────

    @Test @Order(6)  @DisplayName("TC-06 | Negative | Empty username → EMPTY_USERNAME")
    void tc06() {
        LoginException e = assertThrows(LoginException.class, () -> auth.login("", "password1"));
        assertEquals(ErrorType.EMPTY_USERNAME, e.getErrorType());
    }

    @Test @Order(7)  @DisplayName("TC-07 | Negative | Empty password → EMPTY_PASSWORD")
    void tc07() {
        LoginException e = assertThrows(LoginException.class, () -> auth.login("admin", ""));
        assertEquals(ErrorType.EMPTY_PASSWORD, e.getErrorType());
    }

    @Test @Order(8)  @DisplayName("TC-08 | Negative | Null username → NULL_USERNAME")
    void tc08() {
        LoginException e = assertThrows(LoginException.class, () -> auth.login(null, "password1"));
        assertEquals(ErrorType.NULL_USERNAME, e.getErrorType());
    }

    @Test @Order(9)  @DisplayName("TC-09 | Negative | Null password → NULL_PASSWORD")
    void tc09() {
        LoginException e = assertThrows(LoginException.class, () -> auth.login("admin", null));
        assertEquals(ErrorType.NULL_PASSWORD, e.getErrorType());
    }

    @Test @Order(10) @DisplayName("TC-10 | Negative | Both null → NULL_USERNAME")
    void tc10() {
        LoginException e = assertThrows(LoginException.class, () -> auth.login(null, null));
        assertEquals(ErrorType.NULL_USERNAME, e.getErrorType());
    }

    @Test @Order(11) @DisplayName("TC-11 | Negative | Whitespace username → EMPTY_USERNAME")
    void tc11() {
        LoginException e = assertThrows(LoginException.class, () -> auth.login("   ", "password1"));
        assertEquals(ErrorType.EMPTY_USERNAME, e.getErrorType());
    }

    // ── SECTION 3: BOUNDARY TESTING ──────────────────────────────────────────

    @Test @Order(12) @DisplayName("TC-12 | Boundary | Username = 3 chars (min) → passes validation")
    void tc12() { assertDoesNotThrow(() -> auth.login("bob", "b0bSecure")); }

    @Test @Order(13) @DisplayName("TC-13 | Boundary | Username = 2 chars (below min) → USERNAME_TOO_SHORT")
    void tc13() {
        LoginException e = assertThrows(LoginException.class, () -> auth.login("ab", "password1"));
        assertEquals(ErrorType.USERNAME_TOO_SHORT, e.getErrorType());
    }

    @Test @Order(14) @DisplayName("TC-14 | Boundary | Username = 21 chars (above max) → USERNAME_TOO_LONG")
    void tc14() {
        LoginException e = assertThrows(LoginException.class, () -> auth.login("a".repeat(21), "password1"));
        assertEquals(ErrorType.USERNAME_TOO_LONG, e.getErrorType());
    }

    @Test @Order(15) @DisplayName("TC-15 | Boundary | Username = 20 chars (max) → validation passes, auth fails")
    void tc15() {
        LoginException e = assertThrows(LoginException.class, () -> auth.login("a".repeat(20), "password1"));
        assertEquals(ErrorType.INVALID_CREDENTIALS, e.getErrorType());
    }

    @Test @Order(16) @DisplayName("TC-16 | Boundary | Password = 5 chars (below min) → PASSWORD_TOO_SHORT")
    void tc16() {
        LoginException e = assertThrows(LoginException.class, () -> auth.login("admin", "12345"));
        assertEquals(ErrorType.PASSWORD_TOO_SHORT, e.getErrorType());
    }

    @Test @Order(17) @DisplayName("TC-17 | Boundary | Password = 21 chars (above max) → PASSWORD_TOO_LONG")
    void tc17() {
        LoginException e = assertThrows(LoginException.class, () -> auth.login("admin", "p".repeat(21)));
        assertEquals(ErrorType.PASSWORD_TOO_LONG, e.getErrorType());
    }

    @Test @Order(18) @DisplayName("TC-18 | Boundary | Password = 6 chars (min) → validation passes, auth fails")
    void tc18() {
        LoginException e = assertThrows(LoginException.class, () -> auth.login("admin", "123456"));
        assertEquals(ErrorType.INVALID_CREDENTIALS, e.getErrorType());
    }

    @Test @Order(19) @DisplayName("TC-19 | Boundary | Password = 20 chars (max) → validation passes, auth fails")
    void tc19() {
        LoginException e = assertThrows(LoginException.class, () -> auth.login("admin", "p".repeat(20)));
        assertEquals(ErrorType.INVALID_CREDENTIALS, e.getErrorType());
    }

    // ── SECTION 4: SECURITY TESTING ──────────────────────────────────────────

    @Test @Order(20) @DisplayName("TC-20 | Security | SQL injection in username → SQL_INJECTION_DETECTED")
    void tc20() {
        LoginException e = assertThrows(LoginException.class, () -> auth.login("' OR '1'='1", "pass1"));
        assertEquals(ErrorType.SQL_INJECTION_DETECTED, e.getErrorType());
    }

    @Test @Order(21) @DisplayName("TC-21 | Security | SQL injection in password → SQL_INJECTION_DETECTED")
    void tc21() {
        LoginException e = assertThrows(LoginException.class, () -> auth.login("admin", "pass' OR '1'='1"));
        assertEquals(ErrorType.SQL_INJECTION_DETECTED, e.getErrorType());
    }

    @Test @Order(22) @DisplayName("TC-22 | Security | DROP TABLE injection → SQL_INJECTION_DETECTED")
    void tc22() {
        LoginException e = assertThrows(LoginException.class, () -> auth.login("DROP TABLE users", "pass1"));
        assertEquals(ErrorType.SQL_INJECTION_DETECTED, e.getErrorType());
    }

    @Test @Order(23) @DisplayName("TC-23 | Security | Account locks after 3 failed attempts")
    void tc23() {
        assertThrows(LoginException.class, () -> auth.login("admin", "wrong1"));
        assertThrows(LoginException.class, () -> auth.login("admin", "wrong2"));
        assertThrows(LoginException.class, () -> auth.login("admin", "wrong3"));
        LoginException e = assertThrows(LoginException.class, () -> auth.login("admin", "admin123"));
        assertEquals(ErrorType.ACCOUNT_LOCKED, e.getErrorType());
    }

    @Test @Order(24) @DisplayName("TC-24 | Security | Failed attempts reset after successful login")
    void tc24() {
        assertThrows(LoginException.class, () -> auth.login("user1", "bad1"));
        assertThrows(LoginException.class, () -> auth.login("user1", "bad2"));
        assertTrue(auth.login("user1", "pass@123"));
        assertEquals(0, auth.getFailedAttempts("user1"));
    }

    @Test @Order(25) @DisplayName("TC-25 | Security | isAccountLocked() returns true after lockout")
    void tc25() {
        assertThrows(LoginException.class, () -> auth.login("alice", "bad1"));
        assertThrows(LoginException.class, () -> auth.login("alice", "bad2"));
        assertThrows(LoginException.class, () -> auth.login("alice", "bad3"));
        assertTrue(auth.isAccountLocked("alice"));
    }

    // ── SECTION 5: EXCEPTION TESTING ─────────────────────────────────────────

    @Test @Order(26) @DisplayName("TC-26 | Exception | LoginException is thrown (not generic)")
    void tc26() { assertThrows(LoginException.class, () -> auth.login("admin", "wrong")); }

    @Test @Order(27) @DisplayName("TC-27 | Exception | Exception message is not null/empty")
    void tc27() {
        LoginException e = assertThrows(LoginException.class, () -> auth.login("admin", "wrong"));
        assertNotNull(e.getMessage());
        assertFalse(e.getMessage().isEmpty());
    }

    @Test @Order(28) @DisplayName("TC-28 | Exception | ErrorType enum is correctly set")
    void tc28() {
        LoginException e = assertThrows(LoginException.class, () -> auth.login(null, "pass"));
        assertNotNull(e.getErrorType());
        assertEquals(ErrorType.NULL_USERNAME, e.getErrorType());
    }

    // ── SECTION 6: VALIDATOR UNIT TESTS ──────────────────────────────────────

    @Test @Order(29) @DisplayName("TC-29 | Validator | containsSqlInjection() detects malicious input")
    void tc29() {
        assertTrue(validator.containsSqlInjection("' OR '1'='1"));
        assertTrue(validator.containsSqlInjection("DROP TABLE"));
        assertTrue(validator.containsSqlInjection("admin'--"));
    }

    @Test @Order(30) @DisplayName("TC-30 | Validator | containsSqlInjection() passes clean input")
    void tc30() {
        assertFalse(validator.containsSqlInjection("alice99"));
        assertFalse(validator.containsSqlInjection("mySecure!"));
    }
}
