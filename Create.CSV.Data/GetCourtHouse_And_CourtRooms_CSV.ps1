# SQL query to be executed
$query = @"
SELECT 
    darts.courtroom.cth_id, 
    darts.courtroom.courtroom_name, 
    darts.courtroom.ctr_id, 
    darts.courthouse.courthouse_name,
	darts.courthouse.display_name,
	darts.courthouse.courthouse_code
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

# Full path to psql executable (update this to the actual path if needed)
$psqlPath = "C:\Program Files\PostgreSQL\16\bin\psql.exe"

# Check if the output file exists and remove it to ensure overwrite
if (Test-Path -Path $outputFile) {
    Remove-Item -Path $outputFile -Force
}

# Export column headers to a new CSV file
$headers = "cth_id,courtroom_name,ctr_id,courthouse_name, display_name, courthouse_code"
$headers | Out-File -FilePath $outputFile -Encoding ASCII

# Append the query results to the CSV file with comma delimiters
# Use psql's -t flag to suppress header and footer information
& $psqlPath -h $postgresHost -p $port -U $user -d $database -A -F "," -t -c $query | Out-File -FilePath $outputFile -Append -Encoding ASCII

Write-Host "Query executed and results exported to $outputFile"