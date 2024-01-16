param (
    [string]$JMeterBatchPath,
    [string]$JMXFilePath,
    [string]$OutputLocation,
    [int]$Users,
    [int]$Iterations,
    [int]$RampTime
)

# Get the current date and time in a formatted string (e.g., "yyyyMMdd_HHmmss")
#$currentDateTime = Get-Date -Format "yyyyMMdd_HHmmss"

# Construct the output file path using the current date and time
#$jtlOutputFilePath = Join-Path $OutputLocation

# Build the JMeter command with parameters
$jmeterCommand = "& $JMeterBatchPath -n -t `"$JMXFilePath`" -l `"$OutputLocation`" -JUSER_COUNT=$Users -JUSER_ITERATIONS=$Iterations -JUSER_RAMP_TIME=$RampTime"

# Execute the JMeter command
Invoke-Expression $jmeterCommand
