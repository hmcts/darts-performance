﻿# Define the SQL query (with date formatting in SQL)
$query = @"
WITH UserDetails AS (
    SELECT 
        ua.usr_id,
        ua.user_email_address,
        'PerfTester@01' AS Password,
        ua.user_name, 
        sgch.cth_id,
        '\"' || REPLACE(ch.display_name, '\"', '\"\"') || '\"' AS courthouse_name,
        ch.courthouse_code,
        'Transcriber' AS Type,
        ROW_NUMBER() OVER (PARTITION BY ua.usr_id ORDER BY RANDOM()) AS courthouse_rn
    FROM 
        darts.user_account ua
    LEFT JOIN 
        darts.security_group_user_account_ae sgua ON sgua.usr_id = ua.usr_id
    LEFT JOIN 
        darts.security_group_courthouse_ae sgch ON sgch.grp_id = sgua.grp_id
    LEFT JOIN 
        darts.courthouse ch ON sgch.cth_id = ch.cth_id
    WHERE  
        ua.user_name LIKE '%PerfTranscriber%'
        AND sgch.cth_id IS NOT NULL
),
FilteredUserDetails AS (
    SELECT *
    FROM UserDetails
    WHERE courthouse_rn = 1
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
        TO_CHAR(MIN(h.hearing_date), 'YYYY-MM-DD') AS date_from
    FROM 
        FilteredUserDetails fud
    LEFT JOIN 
        darts.court_case cc ON cc.cth_id = fud.cth_id
    LEFT JOIN								 
        darts.hearing h ON h.cas_id = cc.cas_id
    WHERE
        cc.cas_id IS NOT NULL
        AND h.cas_id IS NOT NULL
        AND NOT EXISTS (SELECT 1 FROM darts.media_linked_case mlc WHERE mlc.cas_id = cc.cas_id)
        AND EXISTS (
            SELECT 1 
            FROM darts.hearing_media_ae hm 
            WHERE hm.hea_id = h.hea_id
        ) 
        AND NOT EXISTS (
            SELECT 1 
            FROM darts.hearing h_no_media 
            WHERE h_no_media.hea_id = h.hea_id
            AND NOT EXISTS (
                SELECT 1 
                FROM darts.hearing_media_ae hm 
                WHERE hm.hea_id = h_no_media.hea_id
            )
        )
    GROUP BY 
        fud.usr_id, fud.user_email_address, fud.user_name, fud.cth_id, 
        fud.courthouse_name, fud.courthouse_code, fud.Password, fud.Type, cc.cas_id
),
RandomDefendant AS (
    SELECT 
        uc.usr_id,
        uc.cas_id,
        uc.date_from, 
        d.defendant_name,
        ROW_NUMBER() OVER (PARTITION BY uc.usr_id ORDER BY RANDOM()) AS rn
    FROM 
        UserCases uc
    LEFT JOIN 
        darts.defendant d ON d.cas_id = uc.cas_id
    WHERE 
        d.cas_id IS NOT NULL
)
SELECT 
    fud.user_email_address,
    fud.Password,
    fud.user_name,
    fud.cth_id,
    fud.courthouse_name,
    fud.courthouse_code,
    fud.Type, 
    rd1.cas_id AS cas_id1,
    rd1.date_from AS date_from1,
    COALESCE(
        CASE 
            WHEN POSITION(' ' IN rd1.defendant_name) > 0 THEN SUBSTRING(rd1.defendant_name FROM 1 FOR POSITION(' ' IN rd1.defendant_name) - 1)
            ELSE rd1.defendant_name 
        END, 'Unknown'
    ) AS first_name1,
    rd2.cas_id AS cas_id2,
    rd2.date_from AS date_from2,
    COALESCE(
        CASE 
            WHEN POSITION(' ' IN rd2.defendant_name) > 0 THEN SUBSTRING(rd2.defendant_name FROM 1 FOR POSITION(' ' IN rd2.defendant_name) - 1)
            ELSE rd2.defendant_name 
        END, 'Unknown'
    ) AS first_name2,
    rd3.cas_id AS cas_id3,
    rd3.date_from AS date_from3,
    COALESCE(
        CASE 
            WHEN POSITION(' ' IN rd3.defendant_name) > 0 THEN SUBSTRING(rd3.defendant_name FROM 1 FOR POSITION(' ' IN rd3.defendant_name) - 1)
            ELSE rd3.defendant_name 
        END, 'Unknown'
    ) AS first_name3,
    rd4.cas_id AS cas_id4,
    rd4.date_from AS date_from4,
    COALESCE(
        CASE 
            WHEN POSITION(' ' IN rd4.defendant_name) > 0 THEN SUBSTRING(rd4.defendant_name FROM 1 FOR POSITION(' ' IN rd4.defendant_name) - 1)
            ELSE rd4.defendant_name 
        END, 'Unknown'
    ) AS first_name4,
    rd5.cas_id AS cas_id5,
    rd5.date_from AS date_from5,
    COALESCE(
        CASE 
            WHEN POSITION(' ' IN rd5.defendant_name) > 0 THEN SUBSTRING(rd5.defendant_name FROM 1 FOR POSITION(' ' IN rd5.defendant_name) - 1)
            ELSE rd5.defendant_name 
        END, 'Unknown'
    ) AS first_name5

FROM 
    FilteredUserDetails fud
LEFT JOIN 
    RandomDefendant rd1 ON fud.usr_id = rd1.usr_id AND rd1.rn = 1
LEFT JOIN 
    RandomDefendant rd2 ON fud.usr_id = rd2.usr_id AND rd2.rn = 2
LEFT JOIN 
    RandomDefendant rd3 ON fud.usr_id = rd3.usr_id AND rd3.rn = 3
LEFT JOIN 
    RandomDefendant rd4 ON fud.usr_id = rd4.usr_id AND rd4.rn = 4
LEFT JOIN 
    RandomDefendant rd5 ON fud.usr_id = rd5.usr_id AND rd5.rn = 5
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

# Check if the output file exists
if (Test-Path -Path $outputFile) {
    # Remove existing file to ensure overwrite
    Remove-Item -Path $outputFile -Force
}

# Export column headers to a new CSV file
$headers = "Email,Password,user_name,cth_id,courthouse_name,courthouse_code,Type,cas_id1,date_from1,defendantFirstName,cas_id2,date_from2,defendantSecondName,cas_id3,date_from3,defendantThirdName,cas_id4,date_from4,defendantFourthName,cas_id5,date_from5,defendantFifthName"
$headers | Out-File -FilePath $outputFile -Encoding ASCII

# Append the query results to the CSV file with comma delimiters
# Use psql's -t flag to suppress header and footer information
& $psqlPath -h $postgresHost -p $port -U $user -d $database -A -F "," -t -c $query | Out-File -FilePath $outputFile -Append -Encoding ASCII

Write-Host "Query executed and results exported to $outputFile"
