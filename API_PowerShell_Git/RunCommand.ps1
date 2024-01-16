param (
    [string] $ApiEndpoint,
    [string] $OutputDirectory,
    [string] $ApiScriptPath,
    [string] $ExportScriptPath = "C:\Users\a.cooper\Desktop\Performance_Tools\API_PowerShell_Git\Export_API_Responses_To_CSV.ps1",
    [string] $Data,
    [string] $QueryParameter = $null
)

# Function to start the API job
function Start-ApiJob {
    param (
        [string] $ApiScriptPath
    )
    Write-Host "Starting the API"
    Start-Job -Name "ApiJob" -ScriptBlock {
        param ($apiScriptPath)
        Write-Host "Inside the API job"
        Invoke-Expression "powershell -file `"$apiScriptPath`""
    } -ArgumentList $ApiScriptPath | Wait-Job
}

# Function to parse query parameters from the URL
function Parse-QueryParameters {
    param (
        [string] $Url
    )
    # Extract the query string from the URL
    $queryString = [System.Uri] $Url -replace "^.*\?"
    
    # Create a dictionary to store query parameters
    $queryParameters = @{}
    
    # Split the query string into individual parameters
    $params = $queryString -split '&'
    foreach ($param in $params) {
        $key, $value = $param -split '=', 2
        if ($key -and $value) {
            $queryParameters[$key] = $value
        }
    }

    return $queryParameters
}

function Remove-QuotesFromCsv {
    param (
        [string] $CsvFilePath
    )
    $csvContent = Get-Content $CsvFilePath
    $csvContentWithoutQuotes = $csvContent | ForEach-Object { $_ -replace '"', '' }
    $csvContentWithoutQuotes | Set-Content $CsvFilePath
}

# Start the API job
Start-ApiJob -ApiScriptPath $ApiScriptPath
#Start-Sleep -Seconds 205

# Optional: You can adjust the sleep duration as needed to ensure the API job completes.
Start-Sleep -Seconds 10

# Construct the URL for the web request
if ($QueryParameter) {
    $url = "$ApiEndpoint$QueryParameter"
    Write-Host "$ApiEndpoint?$QueryParameter"
} else {
    $url = $ApiEndpoint
}
Write-Host "Running 'Invoke-WebRequest' with URL: $url"

# Run the web request
try {
    $response = Invoke-WebRequest -Uri $url
} catch {
    Write-Host "Error occurred while running 'Invoke-WebRequest': $_"
}

# Construct the command to run your second script with the complete API endpoint
$command = "& `"$ExportScriptPath`" -ApiEndpoint $url -OutputCsvFile $($OutputDirectory)\$Data.csv"

# Run the command
Write-Host "Running the second script with the following command:"
Write-Host $command
Invoke-Expression $command

# Run the command to remove quotes from CSV
Remove-QuotesFromCsv -CsvFilePath "$OutputDirectory\$Data.csv"
