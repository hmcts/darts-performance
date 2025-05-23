# Define the SQL query (with date formatting in SQL)
$query = @"
WITH UserDetails AS (
    SELECT 
        ua.usr_id,
        ua.user_email_address,
        'PerfTester@01' AS Password,
        ua.user_name, 
        urch.cth_id,
        '\"' || REPLACE(ch.display_name, '\"', '\"\"') || '\"' AS courthouse_name,
        ch.courthouse_code,
        'Transcriber' AS Type,
        ROW_NUMBER() OVER (PARTITION BY ua.usr_id ORDER BY RANDOM()) AS courthouse_rn
    FROM 
        darts.user_account ua
    LEFT JOIN 
        darts.user_roles_courthouses urch ON ua.usr_id = urch.usr_id
    LEFT JOIN 
																			
			  
        darts.courthouse ch ON urch.cth_id = ch.cth_id
    WHERE  
        ua.user_name LIKE '%PerfTranscriber%'							   
								
								
        AND urch.cth_id NOT IN (111, 153, 112, 154, 95, 55, 65, 114, 155, 152, 133, 129, 70, 136, 113, 1002, 1003, 76, 1000)
														
		 
	   
),

FilteredUserDetails AS (
    SELECT *
    FROM UserDetails
    WHERE courthouse_rn = 1
),

ValidHearings AS (
    SELECT hea_id, cas_id, hearing_date
    FROM (
        SELECT 
            h.hea_id,
            h.cas_id,
            h.hearing_date,
            cc.cth_id,
            ROW_NUMBER() OVER (PARTITION BY cc.cth_id ORDER BY h.hearing_date DESC) AS rn
        FROM darts.hearing h
        JOIN darts.court_case cc ON cc.cas_id = h.cas_id
        JOIN darts.hearing_media_ae hm ON hm.hea_id = h.hea_id
        JOIN darts.media m ON hm.med_id = m.med_id AND m.channel = 1 AND m.is_hidden = false
        WHERE m.is_current = true
          AND NOT EXISTS (
              SELECT 1
              FROM darts.media_request_aud mra
              WHERE mra.hea_id = h.hea_id
								  
																	
          )
    ) sub
    WHERE rn <= 100
),

UserCases AS (
    SELECT 
        fud.usr_id,
        fud.user_email_address,
        fud.user_name,
        fud.cth_id,
        fud.courthouse_name,
        fud.courthouse_code,
        fud.Password,
        fud.Type,
        cc.cas_id,
        vh.hea_id,
        vh.hearing_date
    FROM 
        FilteredUserDetails fud
    LEFT JOIN 
        darts.court_case cc ON cc.cth_id = fud.cth_id
    LEFT JOIN 
        ValidHearings vh ON vh.cas_id = cc.cas_id
    WHERE
        cc.cas_id IS NOT NULL
        AND vh.hea_id IS NOT NULL
),

CaseCounts AS (														
    SELECT 
        hearing_date,
        courthouse_name,
        COUNT(cas_id) AS case_count_per_date
    FROM 
        UserCases
    GROUP BY 
        hearing_date, courthouse_name
),

UserCasesWithCounts AS (
    SELECT 
        fud.usr_id,
        fud.user_email_address,
        fud.user_name,
        fud.cth_id,
        fud.courthouse_name,
        fud.courthouse_code,
        fud.Password,
        fud.Type,
        uc.cas_id,
        uc.hea_id,
        uc.hearing_date,
        ccc.case_count_per_date
    FROM 
        FilteredUserDetails fud
    LEFT JOIN 
        UserCases uc ON fud.usr_id = uc.usr_id
    LEFT JOIN 
        CaseCounts ccc ON ccc.hearing_date = uc.hearing_date 
                      AND ccc.courthouse_name = fud.courthouse_name
    WHERE
        uc.cas_id IS NOT NULL
      AND ccc.case_count_per_date <= 499
),

DedupUserCases AS (
    SELECT DISTINCT
           usr_id,
           cas_id,
           hea_id
    FROM UserCasesWithCounts
),

RandomCases AS (
    SELECT 
        duc.usr_id,
        duc.cas_id,
        duc.hea_id,
        ROW_NUMBER() OVER (PARTITION BY duc.usr_id ORDER BY RANDOM()) AS rn
    FROM DedupUserCases duc
							  
)

SELECT 
    fud.user_email_address,
    fud.Password,
    fud.user_name,
    fud.cth_id,
    fud.courthouse_name,
    fud.courthouse_code,
    fud.Type,
    rc1.cas_id AS cas_id1,
    rc1.hea_id AS hea_id1,
    rc2.cas_id AS cas_id2,
    rc2.hea_id AS hea_id2,
    rc3.cas_id AS cas_id3,
    rc3.hea_id AS hea_id3,
    rc4.cas_id AS cas_id4,
    rc4.hea_id AS hea_id4,
    rc5.cas_id AS cas_id5,
    rc5.hea_id AS hea_id5
FROM 
    FilteredUserDetails fud
LEFT JOIN 
    RandomCases rc1 ON fud.usr_id = rc1.usr_id AND rc1.rn = 1
LEFT JOIN 
    RandomCases rc2 ON fud.usr_id = rc2.usr_id AND rc2.rn = 2
LEFT JOIN 
    RandomCases rc3 ON fud.usr_id = rc3.usr_id AND rc3.rn = 3
LEFT JOIN 
    RandomCases rc4 ON fud.usr_id = rc4.usr_id AND rc4.rn = 4
LEFT JOIN 
    RandomCases rc5 ON fud.usr_id = rc5.usr_id AND rc5.rn = 5
ORDER BY 
    fud.user_email_address;

"@

# Database connection parameters
$postgresHost = "test"
$port = "test"
$database = "test"
$user = "test"
$password = "test"

# Output file path
$outputFile = "C:\Users\a.cooper\Desktop\Performance.Testing\DARTS\darts-performance\src\gatling\resources\UsersTranscribers.csv"

# Ensure PGPASSWORD environment variable is set
$env:PGPASSWORD = $password

# Full path to psql executable
$psqlPath = "C:\Program Files\PostgreSQL\16\bin\psql.exe"

# Log start time
$startTime = Get-Date
Write-Host "[$startTime] Starting query execution..."

# Check if the output file exists
if (Test-Path -Path $outputFile) {
    # Remove existing file to ensure overwrite
    Remove-Item -Path $outputFile -Force
}

# Update headers to match query results
$headers = "Email,Password,user_name,cth_id,courthouse_name,courthouse_code,Type,cas_id1,hea_id1,cas_id2,hea_id2,cas_id3,hea_id3,cas_id4,hea_id4,cas_id5,hea_id5"
$headers | Out-File -FilePath $outputFile -Encoding ASCII

# Measure how long the query execution takes
$executionTime = Measure-Command {
    & $psqlPath -h $postgresHost -p $port -U $user -d $database -A -F "," -t -c $query | Out-File -FilePath $outputFile -Append -Encoding ASCII
}

# Log end time and duration
$endTime = Get-Date
Write-Host "[$endTime] Query executed and results exported to $outputFile"
Write-Host "Query execution time: $($executionTime.TotalSeconds) seconds"
