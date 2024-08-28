# Define the SQL query
$query = @"
WITH RankedClerks AS (
    SELECT
        h.hea_id,
        cc.case_number,
        cc.cas_id,
        ch.cth_id,
        ch.courthouse_name,
        TO_CHAR(m.start_ts AT TIME ZONE 'UTC', 'YYYY-MM-DD"T"HH24:MI:SS.MS"z"') AS start_ts,
        TO_CHAR(m.end_ts AT TIME ZONE 'UTC', 'YYYY-MM-DD"T"HH24:MI:SS.MS"z"') AS end_ts,
        ua.usr_id,
        ua.user_email_address AS Email,
        'PerfTester@01' AS Password,
        ROW_NUMBER() OVER (PARTITION BY ua.usr_id ORDER BY RANDOM()) AS rn
    FROM
        darts.hearing h
    INNER JOIN 
        darts.courtroom cr ON h.ctr_id = cr.ctr_id
    INNER JOIN 
        darts.courthouse ch ON cr.cth_id = ch.cth_id
    INNER JOIN 
        darts.court_case cc ON cc.cas_id = h.cas_id
    INNER JOIN 
        darts.hearing_media_ae hma ON hma.hea_id = h.hea_id
    INNER JOIN 
        darts.media m ON m.med_id = hma.med_id
    INNER JOIN 
        darts.security_group_courthouse_ae sgcae ON ch.cth_id = sgcae.cth_id
    INNER JOIN 
        darts.security_group_user_account_ae sguae ON sgcae.grp_id = sguae.grp_id
    INNER JOIN 
        darts.user_account ua ON sguae.usr_id = ua.usr_id
    WHERE     
        h.hearing_date BETWEEN '2023-02-26' AND '2024-05-27'
    AND
        sguae.usr_id NOT IN (-100,-99,-69,-68,-67,-48,-44,-4,-3,-2,-1,0,1,-101,221,241,1141)
    AND
        ua.user_email_address NOT LIKE '%PerfCourtManager%'
    AND
        ua.user_email_address NOT LIKE '%PerfTranscriber%'
    AND
        ua.user_email_address LIKE '%PerfCourtClerk%'
)
SELECT 
    rc.hea_id,
    rc.case_number,
    rc.cas_id,
    rc.cth_id,
    rc.courthouse_name,
    rc.usr_id,
    rc.Email,
    rc.Password
FROM RankedClerks rc
WHERE rc.rn = 1
LIMIT 4000;


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
$headers = "hea_id,case_number,cas_id,cth_id,courthouse_name,usr_id,Email,Password"
$headers | Out-File -FilePath $outputFile -Encoding ASCII

# Append the query results to the CSV file with comma delimiters
# Use psql's -t flag to suppress header and footer information
& $psqlPath -h $postgresHost -p $port -U $user -d $database -A -F "," -t -c "$query" | Out-File -FilePath $outputFile -Append -Encoding ASCII

Write-Host "Query executed and results exported to $outputFile"