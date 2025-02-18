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
        'LanguageShop' AS Type,
        ROW_NUMBER() OVER (PARTITION BY ua.usr_id ORDER BY RANDOM()) AS courthouse_rn
    FROM 
        darts.user_account ua
    LEFT JOIN 
        darts.user_roles_courthouses urch ON ua.usr_id = urch.usr_id
    LEFT JOIN 
        darts.courthouse ch ON urch.cth_id = ch.cth_id
    WHERE  
        ua.user_name LIKE '%PerfLanguageShop%'
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
        cc.cas_id,       
        TO_CHAR(CAST(MIN(h.hearing_date) AS DATE), 'YYYY-MM-DD') AS date_from
    FROM 
        FilteredUserDetails fud
    LEFT JOIN 
        darts.court_case cc ON cc.cth_id = fud.cth_id
    LEFT JOIN 
        darts.defendant d ON d.cas_id = cc.cas_id
    INNER JOIN  
        darts.hearing h ON h.cas_id = cc.cas_id
    WHERE
        cc.cas_id IS NOT NULL
        AND cc.interpreter_used = true -- ✅ Ensures interpreter was used
        AND NOT EXISTS (SELECT 1 FROM darts.media_linked_case mlc WHERE mlc.cas_id = cc.cas_id)
        AND EXISTS (
            SELECT 1 
            FROM darts.hearing_media_ae hm 
            WHERE hm.hea_id = h.hea_id
        ) -- Ensures hearing has media
    GROUP BY 
        fud.usr_id, fud.user_email_address, fud.user_name, fud.cth_id, 
        fud.courthouse_name, fud.courthouse_code, fud.Password, fud.Type, cc.cas_id
),
RandomDefendant AS (
    -- Assign row numbers in a subquery
    SELECT * FROM (
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
            d.defendant_name IS NOT NULL
    ) subquery
    WHERE rn <= 5 -- Only keep up to 5 cases per user
)
SELECT 
    fud.user_email_address,
    fud.Password,
    fud.user_name,
    fud.cth_id,
    fud.courthouse_name,
    fud.courthouse_code,
    fud.Type,

    -- Case 1
    SUBSTRING(
        rd1.defendant_name 
        FROM 1 
        FOR CASE WHEN POSITION(' ' IN rd1.defendant_name) > 0 THEN POSITION(' ' IN rd1.defendant_name) - 1 ELSE LENGTH(rd1.defendant_name) END
    ) AS first_name1,
    rd1.cas_id AS cas_id1,
    rd1.date_from AS date_from1,

    -- Case 2
    SUBSTRING(
        rd2.defendant_name 
        FROM 1 
        FOR CASE WHEN POSITION(' ' IN rd2.defendant_name) > 0 THEN POSITION(' ' IN rd2.defendant_name) - 1 ELSE LENGTH(rd2.defendant_name) END
    ) AS first_name2,
    rd2.cas_id AS cas_id2,
    rd2.date_from AS date_from2,

    -- Case 3
    SUBSTRING(
        rd3.defendant_name 
        FROM 1 
        FOR CASE WHEN POSITION(' ' IN rd3.defendant_name) > 0 THEN POSITION(' ' IN rd3.defendant_name) - 1 ELSE LENGTH(rd3.defendant_name) END
    ) AS first_name3,
    rd3.cas_id AS cas_id3,
    rd3.date_from AS date_from3,

    -- Case 4
    SUBSTRING(
        rd4.defendant_name 
        FROM 1 
        FOR CASE WHEN POSITION(' ' IN rd4.defendant_name) > 0 THEN POSITION(' ' IN rd4.defendant_name) - 1 ELSE LENGTH(rd4.defendant_name) END
    ) AS first_name4,
    rd4.cas_id AS cas_id4,
    rd4.date_from AS date_from4,

    -- Case 5
    SUBSTRING(
        rd5.defendant_name 
        FROM 1 
        FOR CASE WHEN POSITION(' ' IN rd5.defendant_name) > 0 THEN POSITION(' ' IN rd5.defendant_name) - 1 ELSE LENGTH(rd5.defendant_name) END
    ) AS first_name5,
    rd5.cas_id AS cas_id5,
    rd5.date_from AS date_from5

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
$outputFile = "C:\Users\a.cooper\Desktop\Performance.Testing\DARTS\darts-performance\src\gatling\resources\UsersLanguageShop.csv"

# Ensure PGPASSWORD environment variable is set
$env:PGPASSWORD = $password

# Full path to psql executable (update this to the actual path if needed)
$psqlPath = "C:\Program Files\PostgreSQL\16\bin\psql.exe"

# Check if the output file exists
if (Test-Path -Path $outputFile) {
    # Remove existing file to ensure overwrite
    Remove-Item -Path $outputFile -Force
}

# Update headers to match query results
$headers = "Email,Password,user_name,cth_id,courthouse_name,courthouse_code,Type,defendantFirstName,cas_id1,date_from1,defendantSecondName,cas_id2,date_from2,defendantThirdName,cas_id3,date_from3,defendantFourthName,cas_id4,date_from4,defendantFifthName,cas_id5,date_from5"
$headers | Out-File -FilePath $outputFile -Encoding ASCII

# Append the query results to the CSV file with comma delimiters
# Use psql's -t flag to suppress header and footer information
& $psqlPath -h $postgresHost -p $port -U $user -d $database -A -F "," -t -c $query | Out-File -FilePath $outputFile -Append -Encoding ASCII

Write-Host "Query executed and results exported to $outputFile"
