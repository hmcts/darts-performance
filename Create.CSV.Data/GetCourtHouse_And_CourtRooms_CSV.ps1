# SQL query to be executed
$query = @"
SELECT 
    '\"' || darts.courtroom.cth_id || '\"' AS cth_id,
    '\"' || darts.courtroom.courtroom_name || '\"' AS courtroom_name,
    '\"' || darts.courtroom.ctr_id || '\"' AS ctr_id,
    '\"' || REPLACE(darts.courthouse.courthouse_name, '\"', '\"\"') || '\"' AS courthouse_name,
    '\"' || darts.courthouse.display_name || '\"' AS display_name,
    '\"' || darts.courthouse.courthouse_code || '\"' AS courthouse_code
FROM 
    darts.courtroom
INNER JOIN 
    darts.courthouse 
ON 
    darts.courtroom.cth_id = darts.courthouse.cth_id
ORDER BY 
    darts.courthouse.courthouse_name;
"@

# Database connection parameters
$postgresHost = "darts-api-test.postgres.database.azure.com"
$port = "5432" # Default is 5432
$database = "darts"
$user = "pgadmin"
$password = "oIYRDeLXDMLKahVUjP0D"

# Output file path
$outputFile = "C:\Users\a.cooper\Desktop\Performance.Testing\DARTS\darts-performance\src\gatling\resources\GetAllCourtroomsAndCourthouses.csv"

# Ensure PGPASSWORD environment variable is set
$env:PGPASSWORD = $password

# Full path to psql executable
$psqlPath = "C:\Program Files\PostgreSQL\16\bin\psql.exe"

# Check if the output file exists and remove it to ensure overwrite
if (Test-Path -Path $outputFile) {
    Remove-Item -Path $outputFile -Force
}

# Export column headers to a new CSV file
$headers = '"cth_id","courtroom_name","ctr_id","courthouse_name","display_name","courthouse_code"'
$headers | Out-File -FilePath $outputFile -Encoding ASCII

# Execute the query and append the results to the CSV file
# Use -A to output in unaligned mode (no padding) and -F "|" for pipe delimiter to avoid issues with commas
& $psqlPath -h $postgresHost -p $port -U $user -d $database -A -F "|" -t -c "$query" | ForEach-Object {
    $_ -replace "\|", "," # Replace pipe delimiters with commas
} | Out-File -FilePath $outputFile -Append -Encoding ASCII

Write-Host "Query executed and results exported to $outputFile"e"nd results exported to $outputFile"