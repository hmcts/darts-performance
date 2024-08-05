# Define the SQL query
$query = @"
WITH RankedHearings AS (
    SELECT 
        h.hea_id, 
        h.cas_id, 
        cr.cth_id, 
        h.ctr_id,
        ROW_NUMBER() OVER (PARTITION BY cr.cth_id ORDER BY h.hea_id DESC) AS rn
    FROM 
        darts.hearing AS h
    INNER JOIN 
        darts.courtroom AS cr ON h.ctr_id = cr.ctr_id
    WHERE 
        cr.cth_id IN (1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
					  11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
					  21, 22, 23, 24, 25, 28, 29, 30, 
					  31, 33, 34, 41, 44, 45, 47, 40, 48, 
                      78, 83, 85, 87, 93, 107)
)
SELECT 
    hea_id, 
    cas_id, 
    cth_id, 
    ctr_id
FROM 
    RankedHearings
WHERE 
    rn <= 10
ORDER BY 
    cth_id ASC, rn ASC;

"@

# Database connection parameters
$postgresHost = "darts-api-test.postgres.database.azure.com"
$port = "5432" # Default is 5432
$database = "darts"
$user = "pgadmin"
$password = "oIYRDeLXDMLKahVUjP0D"

# Output file path
$outputFile = "C:\Users\a.cooper\Desktop\Performance.Testing\DARTS\darts-performance\src\gatling\resources\Transcription_Post.csv"

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
$headers = "hea_id,cas_id,cth_id,ctr_id"
$headers | Out-File -FilePath $outputFile -Encoding ASCII

# Append the query results to the CSV file with comma delimiters
# Use psql's -t flag to suppress header and footer information
& $psqlPath -h $postgresHost -p $port -U $user -d $database -A -F "," -t -c "$query" | Out-File -FilePath $outputFile -Append -Encoding ASCII

Write-Host "Query executed and results exported to $outputFile"