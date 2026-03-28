# Retro Login Terminal — Experiment H

> A Java authentication system with retro terminal UI, input validation, SQL injection detection, account lockout, and 30 JUnit 5 unit tests.

**Live Demo →** https://YOUR-USERNAME.netlify.app

---

## What It Does

This project simulates a real-world login system built in plain Java (no frameworks). A user enters a username and password in the terminal. The system validates the input, checks it against an in-memory database, and either grants or denies access — with animated retro output.

**Key features:**
- Username and password validation (length, null, empty checks)
- SQL injection detection
- Account lockout after 3 failed attempts
- Animated character-by-character terminal output with ANSI colours
- 30 JUnit 5 unit tests covering 6 categories

**Tech stack:** Java 11 · JUnit 5 · VS Code · Netlify (for the demo page)

---

## Project Structure

```
login-module/
├── src/
│   └── com/loginapp/
│       ├── LoginApp.java           ← Entry point (main)
│       ├── model/
│       │   ├── User.java           ← User entity
│       │   └── UserDatabase.java   ← In-memory user store
│       ├── service/
│       │   ├── AuthService.java    ← Core auth logic
│       │   └── InputValidator.java ← Validation + SQL detection
│       ├── exception/
│       │   └── LoginException.java ← Custom exception + ErrorType enum
│       └── ui/
│           └── RetroUI.java        ← Terminal animations + ANSI colours
├── test/
│   └── com/loginapp/
│       └── LoginModuleTest.java    ← 30 JUnit 5 test cases
├── lib/                            ← JUnit jar goes here (auto-downloaded)
├── index.html                      ← Live demo page (deploy to Netlify)
├── run.bat                         ← Compile + run the app (Windows)
├── test.bat                        ← Download JUnit + run all 30 tests
└── .vscode/
    ├── tasks.json
    └── launch.json
```

---

## How to Run in VS Code

### Step 1 — Install Java JDK (if not already installed)
Download from: https://adoptium.net  
After install, confirm in terminal:
```
java -version
javac -version
```

### Step 2 — Open the project
```
File → Open Folder → select the login-module folder
```

### Step 3 — Run the app
Open the VS Code terminal (`Ctrl + backtick`) and type:
```bat
run.bat
```
This compiles all Java files and launches the retro login terminal.

### Step 4 — Run all 30 tests
```bat
test.bat
```
This auto-downloads the JUnit 5 jar (first time only), compiles the test file, and runs all 30 tests with a detailed report.

---

## Valid Credentials (for testing)

| Username | Password   |
|----------|------------|
| admin    | admin123   |
| user1    | pass@123   |
| alice    | alice$99   |
| bob      | b0bSecure  |
| charlie  | charL!e01  |

---

## Test Coverage

| # | Section     | Tests | What is tested |
|---|-------------|-------|----------------|
| 1 | Functional  | 5     | Valid logins, wrong password, unknown user |
| 2 | Negative    | 6     | Empty/null username and password, whitespace |
| 3 | Boundary    | 8     | Min/max length for username (3–20) and password (6–20) |
| 4 | Security    | 6     | SQL injection, account lockout, attempt reset |
| 5 | Exception   | 3     | Exception type, message content, ErrorType enum |
| 6 | Validator   | 2     | Direct unit tests on InputValidator |
|   | **Total**   | **30**| |

---

## Push to GitHub

```bash
# 1. Initialise git inside the folder
git init
git add .
git commit -m "Initial commit — Retro Login Terminal + JUnit 5"

# 2. Create a new repo on github.com (name it: login-module)
# 3. Connect and push
git remote add origin https://github.com/YOUR_USERNAME/login-module.git
git branch -M main
git push -u origin main
```

---

## Deploy Live with Netlify

1. Go to https://netlify.com → sign in with GitHub
2. Click **Add new site → Import an existing project**
3. Choose your `login-module` GitHub repo
4. Build command: *(leave blank)*
5. Publish directory: `.` (the root)
6. Click **Deploy site**

Your live URL will be: `https://random-name.netlify.app`  
Rename it under **Site settings → Change site name**

---

## Observations

- Modular design made it easy to test each layer independently
- The `@BeforeEach` reset ensured no test contaminated another
- SQL injection was caught at the validation layer — it never reached the database
- Account lockout was verified by simulating 3 wrong attempts then checking `isAccountLocked()`
- Boundary tests confirmed exact enforcement at min/max edges (3/20 chars for username, 6/20 for password)

---

## Why It Was Created

This project was built as **Experiment H** for a software engineering lab on modular design and unit testing. The goal was to implement a login system that demonstrates real-world authentication concepts — validation, security, exception handling — and verify every behaviour with systematic JUnit 5 tests.
