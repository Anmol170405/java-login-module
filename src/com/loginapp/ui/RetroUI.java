package com.loginapp.ui;

public class RetroUI {

    public static final String RESET  = "\u001B[0m";
    public static final String GREEN  = "\u001B[32m";
    public static final String RED    = "\u001B[31m";
    public static final String YELLOW = "\u001B[33m";
    public static final String CYAN   = "\u001B[36m";
    public static final String WHITE  = "\u001B[97m";
    public static final String DIM    = "\u001B[2m";
    public static final String BOLD   = "\u001B[1m";

    private static final String BORDER = "═".repeat(43);

    public static void printBanner() {
        System.out.println();
        type(CYAN + BOLD + "╔" + BORDER + "╗" + RESET, 4);
        type(CYAN + BOLD + "║" + pad("  RETRO LOGIN TERMINAL  v1.0  ", 43) + "║" + RESET, 4);
        type(CYAN + BOLD + "║" + pad("  Secure Authentication System  ", 43) + "║" + RESET, 4);
        type(CYAN + BOLD + "╚" + BORDER + "╝" + RESET, 4);
        System.out.println();
        type(DIM + "  [ System Ready ] [ Encryption: ON ] [ JUnit5: Active ]" + RESET, 8);
        System.out.println();
        sleep(150);
    }

    public static void printAuthenticating() {
        System.out.println();
        System.out.print(YELLOW + BOLD + "  >>> Authenticating" + RESET);
        for (int i = 0; i < 3; i++) { sleep(350); System.out.print(YELLOW + "." + RESET); }
        sleep(300);
        System.out.println();
    }

    public static void printAccessGranted(String username) {
        System.out.println();
        type(GREEN + BOLD + "  ╔══════════════════════════════════╗" + RESET, 3);
        type(GREEN + BOLD + "  ║       ACCESS GRANTED  ✓          ║" + RESET, 3);
        type(GREEN + BOLD + "  ╚══════════════════════════════════╝" + RESET, 3);
        System.out.println();
        type(GREEN + "  >>> Welcome back, " + WHITE + BOLD + username + RESET + GREEN + "!" + RESET, 16);
        type(GREEN + "  >>> Session active. Stay secure." + RESET, 16);
        System.out.println();
    }

    public static void printAccessDenied(String reason) {
        System.out.println();
        type(RED + BOLD + "  ╔══════════════════════════════════╗" + RESET, 3);
        type(RED + BOLD + "  ║        ACCESS DENIED  ✗           ║" + RESET, 3);
        type(RED + BOLD + "  ╚══════════════════════════════════╝" + RESET, 3);
        System.out.println();
        type(RED + "  >>> Error: " + reason + RESET, 16);
        System.out.println();
    }

    public static void printValidationError(String msg) {
        type(YELLOW + "  [!] " + msg + RESET, 14);
    }

    public static void printSeparator() {
        System.out.println(DIM + "  " + "─".repeat(43) + RESET);
    }

    public static String maskPassword(String p) {
        return p == null ? "" : "*".repeat(p.length());
    }

    public static void type(String text, int delayMs) {
        StringBuilder esc = new StringBuilder();
        boolean inEsc = false;
        for (char c : text.toCharArray()) {
            if (c == '\u001B') { inEsc = true; esc.append(c); }
            else if (inEsc)   { esc.append(c); if (c == 'm') { System.out.print(esc); esc.setLength(0); inEsc = false; } }
            else              { System.out.print(c); sleep(delayMs); }
        }
        System.out.println();
    }

    private static String pad(String s, int w) {
        int p = Math.max(0, (w - s.length()) / 2);
        return " ".repeat(p) + s + " ".repeat(Math.max(0, w - s.length() - p));
    }

    private static void sleep(int ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}
