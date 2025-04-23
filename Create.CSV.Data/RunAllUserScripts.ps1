# RunAllUserScripts.ps1

$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path

# Define the script names
$scripts = @(
    "GetPerfCourtClerk_Users_CSV.ps1",
    "GetPerfCourtManager_Users_CSV.ps1",
    "GetPerfJudge_Users_CSV.ps1",
    "GetPerfLanguage_Shop_Users_CSV.ps1",
    "GetPerfTranscirber_Users_CSV.ps1"
)

foreach ($script in $scripts) {
    $fullPath = Join-Path $scriptDir $script
    if (Test-Path $fullPath) {
        $startTime = Get-Date
        Write-Host "`n[$($startTime.ToString('HH:mm:ss'))] Starting $script..."

        try {
            & $fullPath
            $endTime = Get-Date
            $duration = New-TimeSpan -Start $startTime -End $endTime

            # Calculate duration in minutes and seconds
            $minutes = [math]::Floor($duration.TotalMinutes)
            $seconds = [math]::Floor($duration.Seconds)

            Write-Host "[$($endTime.ToString('HH:mm:ss'))] Completed $script in $($minutes) minute(s) and $($seconds) second(s).`n"
        } catch {
            $errorTime = Get-Date
            $errorMessage = $_.Exception.Message
            Write-Error ("[" + $errorTime.ToString('HH:mm:ss') + "] Error running " + $script + ": " + $errorMessage)
        }
    } else {
        Write-Warning "$script not found at $fullPath"
    }
}

$finalTime = Get-Date
Write-Host "`nAll scripts finished at $($finalTime.ToString('HH:mm:ss'))."
