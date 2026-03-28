@echo off
title Retro Login Terminal
echo.
echo  Compiling Java sources...
echo.

if not exist out mkdir out

javac -d out -sourcepath src src\com\loginapp\model\User.java ^
  src\com\loginapp\model\UserDatabase.java ^
  src\com\loginapp\exception\LoginException.java ^
  src\com\loginapp\service\InputValidator.java ^
  src\com\loginapp\service\AuthService.java ^
  src\com\loginapp\ui\RetroUI.java ^
  src\com\loginapp\LoginApp.java

if %errorlevel% neq 0 (
  echo.
  echo  [ERROR] Compilation failed. Make sure Java JDK is installed.
  echo  Download: https://adoptium.net
  pause
  exit /b 1
)

echo  Compilation successful!
echo.
java -cp out com.loginapp.LoginApp
pause
