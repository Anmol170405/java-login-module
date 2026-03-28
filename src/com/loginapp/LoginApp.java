package com.loginapp;

import com.loginapp.exception.LoginException;
import com.loginapp.service.AuthService;
import com.loginapp.ui.RetroUI;

import java.util.Scanner;

public class LoginApp {

    public static void main(String[] args) {
        AuthService auth = new AuthService();
        Scanner sc = new Scanner(System.in);

        RetroUI.printBanner();

        boolean running = true;
        while (running) {
            System.out.print(RetroUI.WHITE + "  > Enter Username: " + RetroUI.RESET);
            String user = sc.nextLine();

            System.out.print(RetroUI.WHITE + "  > Enter Password: " + RetroUI.RESET);
            String pass = sc.nextLine();
            System.out.println(RetroUI.DIM + "  > Password: " + RetroUI.maskPassword(pass) + RetroUI.RESET);

            RetroUI.printAuthenticating();

            try {
                auth.login(user, pass);
                RetroUI.printAccessGranted(user);
            } catch (LoginException e) {
                switch (e.getErrorType()) {
                    case EMPTY_USERNAME: case EMPTY_PASSWORD:
                    case NULL_USERNAME:  case NULL_PASSWORD:
                    case USERNAME_TOO_SHORT: case USERNAME_TOO_LONG:
                    case PASSWORD_TOO_SHORT: case PASSWORD_TOO_LONG:
                        RetroUI.printValidationError(e.getMessage()); break;
                    case SQL_INJECTION_DETECTED:
                        RetroUI.printAccessDenied("Security threat detected. Input rejected."); break;
                    default:
                        RetroUI.printAccessDenied(e.getMessage()); break;
                }
            }

            RetroUI.printSeparator();
            System.out.print(RetroUI.CYAN + "\n  Try again? (y/n): " + RetroUI.RESET);
            running = sc.nextLine().trim().equalsIgnoreCase("y");
            System.out.println();
        }

        RetroUI.type(RetroUI.DIM + "\n  [System] Session terminated. Goodbye." + RetroUI.RESET, 18);
        sc.close();
    }
}
