cd C:\Users\a.cooper\Desktop\Performance_Tools\API_PowerShell_Git

C:\Users\a.cooper\Desktop\Performance_Tools\API_PowerShell_Git\RunCommand.ps1 -ApiEndpoint "http://localhost:5003/CourtCases/GetAllCourtroomsAndCourthouses" -OutputDirectory "C:\Users\a.cooper\Desktop\Performance_Tools\CSV_Output" -ApiScriptPath "C:\Users\a.cooper\Desktop\Performance.Testing\DARTS\darts-performance\API_PowerShell_Git\RunTheApi.ps1" -Data "GetAllCourtroomsAndCourthouses"

#.C:\Users\a.cooper\Desktop\Performance_Tools\API_PowerShell_Git\RunCommand.ps1 -ApiScriptPath "C:\Users\a.cooper\Desktop\Performance_Tools\API_PowerShell_Git\RunTheApi.ps1" -ApiEndpoint "http://localhost:5003/CourtCases/GetCourtCases" -OutputDirectory "C:\Users\a.cooper\Desktop\Performance_Tools\CSV_Output" -Data "GetCourtCases"

#.\RunCommand.ps1 -ApiEndpoint "http://localhost:5002/CourtCases/GetAllCaseDetailsForFilter" -OutputDirectory "C:\Users\a.cooper\Desktop\Performance_Tools\CSV_Output" -ApiScriptPath "C:\Users\a.cooper\Desktop\Performance_Tools\API_PowerShell_Git\RunTheApi.ps1" -Data "GetAllCaseDetailsForFilter"

#.\RunCommand.ps1 -ApiScriptPath "C:\Users\a.cooper\Desktop\Performance_Tools\API_PowerShell_Git\RunTheApi.ps1" -ApiEndpoint "http://localhost:5003/CourtCases/GetCaseByCaseNumber?caseNumber=" -OutputDirectory "C:\Users\a.cooper\Desktop\Performance_Tools\CSV_Output" -Data "GetCaseByCaseNumber" -QueryParameter "CASEAC1001"

#.\RunCommand.ps1 -ApiScriptPath "C:\Users\a.cooper\Desktop\Performance_Tools\API_PowerShell_Git\RunTheApi.ps1" -ApiEndpoint "http://localhost:5003/Users/GetAllUsers" -OutputDirectory "C:\Users\a.cooper\Desktop\Performance_Tools\CSV_Output" -Data "GetAllUsers"