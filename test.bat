@echo off
title JUnit 5 Test Runner
echo.
echo  ============================================
echo   JUnit 5 Test Runner — Login Module
echo  ============================================
echo.

REM Check if JUnit jar exists, download if missing
if not exist lib\junit-standalone.jar (
  echo  Downloading JUnit 5 Console Standalone...
  curl -L "https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.10.0/junit-platform-console-standalone-1.10.0.jar" -o lib\junit-standalone.jar
  if %errorlevel% neq 0 (
    echo.
    echo  [ERROR] Could not download JUnit jar.
    echo  Please manually download from:
    echo  https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.10.0/
    echo  Save as: lib\junit-standalone.jar
    pause
    exit /b 1
  )
  echo  Downloaded successfully!
)

echo  Compiling main sources...
if not exist out mkdir out
javac -d out ^
  src\com\loginapp\model\User.java ^
  src\com\loginapp\model\UserDatabase.java ^
  src\com\loginapp\exception\LoginException.java ^
  src\com\loginapp\service\InputValidator.java ^
  src\com\loginapp\service\AuthService.java ^
  src\com\loginapp\ui\RetroUI.java ^
  src\com\loginapp\LoginApp.java

echo  Compiling test sources...
javac -cp out;lib\junit-standalone.jar -d out ^
  test\com\loginapp\LoginModuleTest.java

echo.
echo  Running 30 JUnit 5 Tests...
echo  ============================================
java -jar lib\junit-standalone.jar ^
  --class-path out ^
  --select-class com.loginapp.LoginModuleTest ^
  --details verbose

echo.
echo  ============================================
echo  Tests complete. Press any key to exit.
pause
