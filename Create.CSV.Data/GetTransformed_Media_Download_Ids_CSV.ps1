# Define the SQL query
$query = @"
SELECT darts.transformed_media.trm_id, darts.transformed_media.mer_id, darts.media_request.hea_id, darts.media_request.request_status, darts.media_request.request_type
FROM darts.transformed_media
INNER JOIN
    darts.media_request
    ON 
    darts.transformed_media.mer_id = darts.media_request.mer_id
WHERE darts.media_request.request_type = 'DOWNLOAD'
AND darts.media_request.request_status = 'COMPLETED'
ORDER BY trm_id DESC LIMIT 1000;
"@

# Database connection parameters
$postgresHost = "darts-api-test.postgres.database.azure.com"
$port = "5432" # Default is 5432
$database = "darts"
$user = "pgadmin"
$password = "oIYRDeLXDMLKahVUjP0D"

# Output file path
$outputFile = "C:\Users\a.cooper\Desktop\Performance.Testing\DARTS\darts-performance\src\gatling\resources\Transformed_Media_Download.csv"

# Ensure PGPASSWORD environment variable is set
$env:PGPASSWORD = $password

# Full path to psql executable (update this to the actual path if needed)
$psqlPath = "C:\Program Files\PostgreSQL\16\bin\psql.exe"

# Check if the output file exists
if (Test-Path -Path $outputFile) {
    # Remove existing file to ensure overwrite
    Remove-Item -Path $outputFile -Force
}

# Export column headers to a new CSV file
$headers = "trm_id,mer_id,hea_id,request_status,request_type"
$headers | Out-File -FilePath $outputFile -Encoding ASCII

# Append the query results to the CSV file with comma delimiters
# Use psql's -t flag to suppress header and footer information
& $psqlPath -h $postgresHost -p $port -U $user -d $database -A -F "," -t -c "$query" | Out-File -FilePath $outputFile -Append -Encoding ASCII

Write-Host "Query executed and results exported to $outputFile"