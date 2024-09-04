# Define the path to the directory containing the scripts
$scriptDirectory = "C:\Users\a.cooper\Desktop\Performance.Testing\DARTS\darts-performance\Create.CSV.Data"

# Define the scripts to run
$scripts = @(
    "$scriptDirectory\GetPerfCourtClerk_Users_CSV.ps1",
    "$scriptDirectory\GetPerfJudge_Users_CSV.ps1",
    "$scriptDirectory\GetPerfLanguage_Shop_Users_CSV.ps1",
    "$scriptDirectory\Accept_Transcriber_Details_PATCH_CSV.ps1",
    "$scriptDirectory\GetTranscriber_Details_POST_CSV.ps1"
)

# Run each script in sequence
foreach ($script in $scripts) {
    try {
        Write-Host "Running script: $script"
        & $script
        Write-Host "Completed script: $script`n"
    } catch {
        Write-Host "An error occurred while running script: $script"
        Write-Host "Error: $_"
    }
}

Write-Host "All scripts have been executed."
