# Define the SQL query
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
        'Judge' AS Type,
        ROW_NUMBER() OVER (PARTITION BY ua.usr_id ORDER BY RANDOM()) AS courthouse_rn
    FROM 
        darts.user_account ua
    LEFT JOIN 
        darts.user_roles_courthouses urch ON ua.usr_id = urch.usr_id
    LEFT JOIN 
        darts.courthouse ch ON urch.cth_id = ch.cth_id
    WHERE  
        ua.user_name LIKE '%PerfJudge%'
        AND urch.cth_id NOT IN (111, 153, 112, 154, 95, 55, 65, 114, 155, 152, 133, 129, 70, 136, 113, 1002, 1003, 76, 1000)
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
        cc.cas_id
    FROM 
        FilteredUserDetails fud
    LEFT JOIN 
        darts.court_case cc ON cc.cth_id = fud.cth_id
    WHERE
        cc.cas_id IS NOT NULL
        AND cc.case_closed = true
        AND EXISTS (
            SELECT 1 FROM darts.hearing h 
            WHERE h.cas_id = cc.cas_id 
            AND h.hea_id IS NOT NULL
            AND NOT EXISTS (
                SELECT 1 FROM darts.hearing_media_ae hm
                WHERE hm.hea_id = h.hea_id
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
        d.defendant_name,
        ROW_NUMBER() OVER (PARTITION BY uc.usr_id ORDER BY RANDOM()) AS rn,
        (SELECT MIN(h2.hearing_date) 
         FROM darts.hearing h2 
         WHERE h2.cas_id = uc.cas_id
         AND NOT EXISTS (
             SELECT 1 FROM darts.hearing_media_ae hm
             WHERE hm.hea_id = h2.hea_id
         )
        ) AS from_date
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
    rd1.from_date AS from_date1,  
    COALESCE(
        CASE 
            WHEN POSITION(' ' IN rd1.defendant_name) > 0 THEN LEFT(rd1.defendant_name, POSITION(' ' IN rd1.defendant_name) - 1)
            ELSE rd1.defendant_name 
        END, 'Unknown'
    ) AS first_name1,
    rd2.cas_id AS cas_id2,
    rd2.from_date AS from_date2,  
    COALESCE(
        CASE 
            WHEN POSITION(' ' IN rd2.defendant_name) > 0 THEN LEFT(rd2.defendant_name, POSITION(' ' IN rd2.defendant_name) - 1)
            ELSE rd2.defendant_name 
        END, 'Unknown'
    ) AS first_name2,
    rd3.cas_id AS cas_id3,
    rd3.from_date AS from_date3,  
    COALESCE(
        CASE 
            WHEN POSITION(' ' IN rd3.defendant_name) > 0 THEN LEFT(rd3.defendant_name, POSITION(' ' IN rd3.defendant_name) - 1)
            ELSE rd3.defendant_name 
        END, 'Unknown'
    ) AS first_name3,
    rd4.cas_id AS cas_id4,
    rd4.from_date AS from_date4,  
    COALESCE(
        CASE 
            WHEN POSITION(' ' IN rd4.defendant_name) > 0 THEN LEFT(rd4.defendant_name, POSITION(' ' IN rd4.defendant_name) - 1)
            ELSE rd4.defendant_name 
        END, 'Unknown'
    ) AS first_name4,
    rd5.cas_id AS cas_id5,
    rd5.from_date AS from_date5,  
    COALESCE(
        CASE 
            WHEN POSITION(' ' IN rd5.defendant_name) > 0 THEN LEFT(rd5.defendant_name, POSITION(' ' IN rd5.defendant_name) - 1)
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
$outputFile = "C:\Users\a.cooper\Desktop\Performance.Testing\DARTS\darts-performance\src\gatling\resources\UsersJudge.csv"

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
$headers = "Email,Password,user_name,cth_id,courthouse_name,courthouse_code,Type,cas_id1,date_from1,defendantFirstName,cas_id2,date_from2,defendantSecondName,cas_id3,date_from3,defendantThirdName,cas_id4,date_from4,defendantFourthName,cas_id5,date_from5,defendantFifthName"
$headers | Out-File -FilePath $outputFile -Encoding ASCII

# Append the query results to the CSV file with comma delimiters
# Use psql's -t flag to suppress header and footer information
& $psqlPath -h $postgresHost -p $port -U $user -d $database -A -F "," -t -c $query | Out-File -FilePath $outputFile -Append -Encoding ASCII

Write-Host "Query executed and results exported to $outputFile"