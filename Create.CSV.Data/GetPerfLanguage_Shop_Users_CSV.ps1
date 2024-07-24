# Define the SQL query
$query = @"
SELECT 
	ua.user_email_address,
    'PerfTester@01' AS Password,
    ua.user_name, 
    'LanguageShop' AS Type
FROM 
    darts.user_account ua
INNER JOIN 
    darts.security_group_user_account_ae sgua ON sgua.usr_id = ua.usr_id
WHERE  
    ua.user_name LIKE '%PerfLanguageShop%'
GROUP BY 
	ua.user_email_address,
    ua.user_name
"@

# Database connection parameters
$postgresHost = "darts-api-test.postgres.database.azure.com"
$port = "5432" # Default is 5432
$database = "darts"
$user = "pgadmin"
$password = "oIYRDeLXDMLKahVUjP0D"

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

# Export column headers to a new CSV file
$headers = "Email,Password,user_name,Type"
$headers | Out-File -FilePath $outputFile -Encoding ASCII

# Append the query results to the CSV file with comma delimiters
# Use psql's -t flag to suppress header and footer information
& $psqlPath -h $postgresHost -p $port -U $user -d $database -A -F "," -t -c $query | Out-File -FilePath $outputFile -Append -Encoding ASCII

Write-Host "Query executed and results exported to $outputFile"