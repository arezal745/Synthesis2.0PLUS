@echo off

set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_301

cd /d %~dp0

echo Running Java jar file...

"%JAVA_HOME%\bin\java.exe" -jar synthesis-1.0-SNAPSHOT.jar

pause