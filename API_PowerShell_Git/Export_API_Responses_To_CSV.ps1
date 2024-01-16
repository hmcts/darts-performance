param(
    [string]$ApiEndpoint,
    [string]$OutputCsvFile
)

# Make an HTTP GET request to the API
$response = Invoke-RestMethod -Uri $ApiEndpoint -Method Get

if ($response) {
    # Access the "data" field
    $data = $response.data

    if ($data -is [System.Object[]]) {
        # Replace null or empty values with "null" in each property
        $data = $data | ForEach-Object {
            $object = New-Object PSObject
            $_.PSObject.Properties | ForEach-Object {
                $propertyName = $_.Name
                $propertyValue = if ($null -eq $_.Value -or [string]::IsNullOrEmpty($_.Value)) {
                    "null"
                } else {
                    $_.Value
                }
                $object | Add-Member -MemberType NoteProperty -Name $propertyName -Value $propertyValue
            }
            $object
        }

        # Save the entire data array to the CSV file with a comma as the delimiter
        $data | Export-Csv -Path $OutputCsvFile -NoTypeInformation -Delimiter ","

        Write-Host "All data from the API has been saved to $OutputCsvFile"
    } else {
        Write-Host "The 'data' field does not contain an array of objects."
    }
} else {
    Write-Host "Failed to retrieve data from the API."
}
