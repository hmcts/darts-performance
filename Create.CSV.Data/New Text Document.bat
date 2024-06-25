@echo off

REM Path to PowerShell executable
set "powershellPath=C:\Windows\System32\WindowsPowerShell\v1.0\powershell.exe"

REM Path to the PowerShell scripts
set "script1=GetAudio_Request_CSV.ps1"
set "script2=GetCourtHouse_And_CourtRooms_CSV.ps1"
set "script3=GetTransformed_Media_Id_CSV.ps1"

REM Execute the PowerShell scripts
%powershellPath% -NoProfile -ExecutionPolicy Bypass -File "%script1%"
if %errorlevel% neq 0 (
    echo Error executing %script1%
    pause
    exit /b %errorlevel%
)

%powershellPath% -NoProfile -ExecutionPolicy Bypass -File "%script2%"
if %errorlevel% neq 0 (
    echo Error executing %script2%
    pause
    exit /b %errorlevel%
)

%powershellPath% -NoProfile -ExecutionPolicy Bypass -File "%script3%"
if %errorlevel% neq 0 (
    echo Error executing %script3%
    pause
    exit /b %errorlevel%
)

echo All scripts executed successfully.
pause
