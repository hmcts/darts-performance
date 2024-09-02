# Define the SQL query
$query = @"
WITH RankedTranscriptions AS (
    SELECT 
        transcription.tra_id,
        transcription.created_by,
        courthouse.cth_id,
        '\"' || REPLACE(courthouse.display_name, '\"', '\"\"') || '\"' AS courthouse_name,
        user_account.usr_id,        
        user_account.user_email_address,
        'PerfTester@01' AS "Password",
        ROW_NUMBER() OVER (
            PARTITION BY user_account.user_email_address
            ORDER BY transcription.tra_id DESC
        ) as rn
    FROM 
        darts.transcription 
    INNER JOIN
        darts.courtroom
        ON transcription.ctr_id = darts.courtroom.ctr_id
    INNER JOIN
        darts.courthouse
        ON courtroom.cth_id = darts.courthouse.cth_id
    INNER JOIN
        darts.user_roles_courthouses
        ON courtroom.cth_id = user_roles_courthouses.cth_id
    INNER JOIN
        darts.user_account
        ON user_roles_courthouses.usr_id = user_account.usr_id
    WHERE 
        transcription.trs_id = 2
        AND transcription.created_by <> -48
        AND user_account.user_email_address LIKE 'PerfCourtManager%' -- Filter by specific user pattern
)
SELECT 
    tra_id,
    cth_id,
    courthouse_name,
    created_by,
    usr_id,
    user_email_address,
    "Password"
FROM 
    RankedTranscriptions
WHERE 
    rn = 1
ORDER BY  
    user_email_address ASC;
"@

# Database connection parameters
$postgresHost = "test"
$port = "test" # Default is test
$database = "test"
$user = "test"
$password = "test"

# Output file path
$outputFile = "C:\Users\a.cooper\Desktop\Performance.Testing\DARTS\darts-performance\src\gatling\resources\Accept_Transcription_Patch.csv"

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
$headers = "tra_id,cth_id,courthouse_name,created_by,usr_id,Email, Password"
$headers | Out-File -FilePath $outputFile -Encoding ASCII

# Append the query results to the CSV file with comma delimiters
# Use psql's -t flag to suppress header and footer information
& $psqlPath -h $postgresHost -p $port -U $user -d $database -A -F "," -t -c "$query" | Out-File -FilePath $outputFile -Append -Encoding ASCII

Write-Host "Query executed and results exported to $outputFile"