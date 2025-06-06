﻿# Define the SQL query
$query = @"
SELECT DISTINCT
    subquery.hea_id,
    subquery.start_ts,
    subquery.end_ts,
    subquery.usr_id,
    subquery.first_name,
    subquery.judge_name
FROM (
    SELECT
        h.hea_id,
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
	    h.hearing_date BETWEEN '2023-02-26' AND '2024-05-27' 
	And
		sguae.usr_id NOT IN (-100,-99,-69,-68,-67,-48,-44,-4,-3,-2,-1,0,1,-101,221,241,1141)
    ORDER BY
        RANDOM()
    LIMIT 4000
) subquery;
"@

# Database connection parameters
$postgresHost = "test"
$port = "test" # Default is test
$database = "test"
$user = "test"
$password = "test"

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
$headers = "hea_id,start_ts,end_ts,usr_id,defendant_name,judge_name"
$headers | Out-File -FilePath $outputFile -Encoding ASCII

# Append the query results to the CSV file with comma delimiters
# Use psql's -t flag to suppress header and footer information
& $psqlPath -h $postgresHost -p $port -U $user -d $database -A -F "," -t -c $query | Out-File -FilePath $outputFile -Append -Encoding ASCII

Write-Host "Query executed and results exported to $outputFile"