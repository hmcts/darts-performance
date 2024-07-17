# Define the SQL query
$query = @"
WITH UserDetails AS (
    SELECT 
        ua.usr_id,
        ua.user_email_address,
        'PerfTester@01' AS Password,
        ua.user_name, 
        sgch.cth_id,
        '\"' || REPLACE(ch.courthouse_name, '\"', '\"\"') || '\"' AS courthouse_name,
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
        cc.cas_id
    FROM 
        FilteredUserDetails fud
    LEFT JOIN 
        darts.court_case cc ON cc.cth_id = fud.cth_id
    WHERE
        cc.cas_id IS NOT NULL
),
RandomDefendant AS (
    SELECT 
        uc.usr_id,
        uc.cas_id,
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
    COALESCE(
        CASE 
            WHEN POSITION(' ' IN rd.defendant_name) > 0 THEN SUBSTRING(rd.defendant_name FROM 1 FOR POSITION(' ' IN rd.defendant_name) - 1)
            ELSE rd.defendant_name 
        END, 'Unknown'
    ) AS first_name  -- Ensure defendant name is populated
FROM 
    FilteredUserDetails fud
LEFT JOIN 
    RandomDefendant rd ON fud.usr_id = rd.usr_id AND rd.rn = 1
ORDER BY 
    fud.user_email_address;
"@

# Database connection parameters
$postgresHost = "darts-api-test.postgres.database.azure.com"
$port = "5432" # Default is 5432
$database = "darts"
$user = "pgadmin"
$password = "oIYRDeLXDMLKahVUjP0D"

# Output file path
$outputFile = "C:\Users\a.cooper\Desktop\Performance.Testing\DARTS\darts-performance\src\gatling\resources\UsersTranscribers.csv"

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
$headers = "Email,Password,user_name,cth_id,courthouse_name,courthouse_code,Type,defendant_name"
$headers | Out-File -FilePath $outputFile -Encoding ASCII

# Append the query results to the CSV file with comma delimiters
# Use psql's -t flag to suppress header and footer information
& $psqlPath -h $postgresHost -p $port -U $user -d $database -A -F "," -t -c $query | Out-File -FilePath $outputFile -Append -Encoding ASCII

Write-Host "Query executed and results exported to $outputFile"