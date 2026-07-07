@echo off
REM 1. Create the necessary folders inside a dist
mkdir dist
mkdir dist\data

REM 2. Copy file JAR from target to dist
xcopy /Y target\cinema-booking.jar dist\

REM 3. Copy all samples data to dist/data
xcopy /E /Y /I src\main\resources\data\* dist\data

REM 4. Create a short file in dist
echo @echo off > dist\run.bat
echo java -jar cinema-booking.jar >> dist\run.bat
echo pause >> dist\run.bat

echo Done! Dist directory ready to share.
pause