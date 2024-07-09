# Define the SQL query
$query = @"
SELECT 
	ua.user_email_address,
    'PerfTester@01' AS Password,
    ua.user_name, 
    ch.cth_id, 
    '\"' || REPLACE(ch.courthouse_name, '\"', '\"\"') || '\"' AS courthouse_name,
    ch.courthouse_code,
    'CourtClerk' AS Type
FROM 
    darts.user_account ua
INNER JOIN 
    darts.security_group_user_account_ae sgua ON sgua.usr_id = ua.usr_id
INNER JOIN 
    darts.security_group_courthouse_ae sgch ON sgch.grp_id = sgua.grp_id
INNER JOIN 
    darts.courthouse ch ON sgch.cth_id = ch.cth_id
WHERE  
    ua.user_name LIKE '%PerfCourtClerk%'
GROUP BY 
	ua.user_email_address, 
    ua.user_name, 
    ch.cth_id, 
    ch.courthouse_name,
    ch.courthouse_code;
"@

# Database connection parameters
$postgresHost = "darts-api-test.postgres.database.azure.com"
$port = "5432" # Default is 5432
$database = "darts"
$user = "pgadmin"
$password = "oIYRDeLXDMLKahVUjP0D"

# Output file path
$outputFile = "C:\Users\a.cooper\Desktop\Performance.Testing\DARTS\darts-performance\src\gatling\resources\UsersCourtClerks.csv"

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
$headers = "Email,Password,user_name,cth_id,courthouse_name,courthouse_code,Type"
$headers | Out-File -FilePath $outputFile -Encoding ASCII

# Append the query results to the CSV file with comma delimiters
# Use psql's -t flag to suppress header and footer information
& $psqlPath -h $postgresHost -p $port -U $user -d $database -A -F "," -t -c $query | Out-File -FilePath $outputFile -Append -Encoding ASCII

Write-Host "Query executed and results exported to $outputFile"