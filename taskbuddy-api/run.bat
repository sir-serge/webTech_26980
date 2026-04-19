@echo off
REM ═══════════════════════════════════════════════════════════════
REM  TaskBuddy API — Launcher Script
REM  Runs Spring Boot using installed Java + Maven full paths
REM ═══════════════════════════════════════════════════════════════

echo.
echo  ╔══════════════════════════════════════╗
echo  ║     TaskBuddy API — Starting Up      ║
echo  ╚══════════════════════════════════════╝
echo.

REM ── Set JAVA_HOME to the installed JDK ──────────────────────────
set JAVA_HOME=C:\Program Files\Java\jdk-21.0.10

REM ── Full paths to executables ───────────────────────────────────
set JAVA_EXE=%JAVA_HOME%\bin\java.exe
set MVN_CMD=C:\Program Files\apache-maven-3.8.9\bin\mvn.cmd

REM ── Verify Java exists ──────────────────────────────────────────
if not exist "%JAVA_EXE%" (
    echo [ERROR] Java not found at: %JAVA_EXE%
    echo Please install Java 17+ from https://adoptium.net/
    pause
    exit /b 1
)

REM ── Verify Maven exists ──────────────────────────────────────────
if not exist "%MVN_CMD%" (
    echo [ERROR] Maven not found at: %MVN_CMD%
    echo Please install Maven from https://maven.apache.org/
    pause
    exit /b 1
)

echo [OK] Java found: %JAVA_EXE%
echo [OK] Maven found: %MVN_CMD%
echo.

REM ── Check if MongoDB service is running ──────────────────────────
sc query MongoDB > nul 2>&1
if %errorlevel% equ 0 (
    echo [INFO] MongoDB service is already running.
) else (
    echo [INFO] Starting MongoDB...
    net start MongoDB > nul 2>&1
    if %errorlevel% neq 0 (
        echo [WARN] Could not auto-start MongoDB service.
        echo        Make sure MongoDB is running before continuing.
        echo        Run: "C:\Program Files\MongoDB\Server\8.2\bin\mongod.exe"
        echo.
        pause
    ) else (
        echo [OK] MongoDB started.
    )
)

echo.
echo [INFO] Launching Spring Boot API on http://localhost:8080
echo [INFO] Press CTRL+C to stop the server.
echo.

REM ── Run Spring Boot ──────────────────────────────────────────────
"%MVN_CMD%" spring-boot:run -f "%~dp0pom.xml"

pause
