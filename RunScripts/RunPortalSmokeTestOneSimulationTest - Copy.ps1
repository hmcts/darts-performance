# Define the directory where Gatling is installed
$scriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path

# Navigate to the Gatling directory
Set-Location $gatlingDir

# Function to run Gatling simulation
function Run-GatlingSimulation {
    param(
        [string]$simulationClass
    )
    
    # Construct the Gradle command
    $gradleCommand = "& .\gradlew.bat gatlingRun-simulations.$simulationClass"

    # Execute the Gradle command
    Write-Output "Running Gatling simulation: $simulationClass"
    Invoke-Expression $gradleCommand
}

# Example usage:
# Replace 'simulations.Scripts.DartsApi.AudioRequestTest1' with the desired simulation class
Run-GatlingSimulation -simulationClass "Scripts.DartsSmokeTests.PortalSmokeTestOneSimulation"
