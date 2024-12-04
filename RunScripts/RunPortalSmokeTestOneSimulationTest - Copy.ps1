# Define the directory where Gatling is installed
$gatlingDir = "C:\Users\a.cooper\Desktop\Performance.Testing\DARTS\darts-performance\RunScripts"

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
