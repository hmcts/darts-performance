# Define the SQL query
$query = @"
SELECT DISTINCT
    h.hea_id,
    h.cas_id,
    cc.case_number,
    ch.cth_id,
    ch.courthouse_name,
    h.ctr_id,
    cr.courtroom_name,
    TO_CHAR(m.start_ts AT TIME ZONE 'UTC', 'YYYY-MM-DD""T""HH24:MI:SS.MS""z""') AS start_ts,
    TO_CHAR(m.end_ts AT TIME ZONE 'UTC', 'YYYY-MM-DD""T""HH24:MI:SS.MS""z""') AS end_ts,
    sguae.usr_id,
    CASE WHEN POSITION(' ' IN d.defendant_name) > 0 THEN SUBSTRING(d.defendant_name FROM 1 FOR POSITION(' ' IN d.defendant_name) - 1)
         ELSE d.defendant_name END AS first_name,
    darts.judge.judge_name
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
    darts.defendant d ON d.cas_id = cc.cas_id
INNER JOIN	
    darts.hearing_judge_ae ON darts.hearing_judge_ae.hea_id = h.hea_id
INNER JOIN		
    darts.judge ON darts.judge.jud_id = darts.hearing_judge_ae.jud_id
WHERE 
    ch.courthouse_name LIKE '%NEWCASTLE UPON TYNE%'
    AND sguae.usr_id NOT IN (-44, -38, -35, -51)
    -- AND cc.created_ts < CURRENT_DATE - INTERVAL '7 days'
ORDER BY 
    h.cas_id DESC
LIMIT 4000;
"@

# Database connection parameters
$postgresHost = "darts-api-test.postgres.database.azure.com"
$port = "5432" # Default is 5432
$database = "darts"
$user = "pgadmin"
$password = "oIYRDeLXDMLKahVUjP0D"

# Output file path
$outputFile = "C:\Users\a.cooper\Desktop\Performance.Testing\DARTS\darts-performance\src\gatling\resources\Audio_Request_Post.csv"

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
$headers = "hea_id,cas_id,case_number,cth_id,courthouse_name,ctr_id,courtroom_name,start_ts,end_ts,usr_id,defendant_name,judge_name"
$headers | Out-File -FilePath $outputFile -Encoding ASCII

# Append the query results to the CSV file with comma delimiters
# Use psql's -t flag to suppress header and footer information
& $psqlPath -h $postgresHost -p $port -U $user -d $database -A -F "," -t -c $query | Out-File -FilePath $outputFile -Append -Encoding ASCII

Write-Host "Query executed and results exported to $outputFile"
