# Define the path to the target directory
$targetPath = "C:\darts-performance"

# Navigate to the target directory
Set-Location -Path $targetPath

# Execute the Gradle command
& .\gradlew.bat runDartsBaseLinePeakTests
