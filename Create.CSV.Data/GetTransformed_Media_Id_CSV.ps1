# Define the SQL query
$sqlQuery = @"
SELECT trm_id FROM darts.transformed_media
ORDER BY trm_id ASC;
"@

# Database connection parameters
$postgresHost = "test"
$port = "test" # Default is test
$database = "test"
$user = "test"
$password = "test"

# Output file path
$outputFile = "$env:USERPROFILE\Desktop\Transformed_Media_Id.csv"

# Ensure PGPASSWORD environment variable is set
$env:PGPASSWORD = $password

# Full path to psql executable (update this to the actual path if needed)
$psqlPath = "psql"

# Check if the output file exists
if (Test-Path -Path $outputFile) {
    # Remove existing file to ensure overwrite
    Remove-Item -Path $outputFile -Force
}

# Export column headers to a new CSV file
$headers = "getTransformedMediaId"
$headers | Out-File -FilePath $outputFile -Encoding ASCII

# Append the query results to the CSV file with comma delimiters
# Use psql's -t flag to suppress header and footer information
& $psqlPath -h $postgresHost -p $port -U $user -d $database -A -F "," -t -c "$sqlQuery" | Out-File -FilePath $outputFile -Append -Encoding ASCII

Write-Host "Query executed and results exported to $outputFile"