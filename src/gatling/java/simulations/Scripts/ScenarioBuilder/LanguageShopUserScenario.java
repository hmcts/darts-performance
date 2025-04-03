package simulations.Scripts.ScenarioBuilder;

import io.gatling.javaapi.core.ScenarioBuilder;
import static io.gatling.javaapi.core.CoreDsl.*;
import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Scenario.DartsPortal.*;

public class LanguageShopUserScenario {

    public static ScenarioBuilder build(String scenarioName) {
        return scenario(scenarioName)
            .group("Language Shop Users")
            .on(
                exec(feed(Feeders.createLanguageShopUsers())) // Load language shop user data
                .exec(DartsPortalExternalLoginScenario.DartsPortalExternalLoginRequest())
                .exec(session -> session.set("loopCounter", 0)) // Initialize loop counter
                .repeat(5).on(
                    exec(session -> {
                        // Increment the loop counter
                        int iteration = session.getInt("loopCounter") + 1;
                
                        // Determine the column name based on the iteration number
                        String casIdColumn = "";
                        String getfromDate = "";
                        String defendantColumn = "";
                        switch (iteration) {
                            case 1: 
                                casIdColumn = "cas_id1"; 
                                getfromDate = "date_from1";
                                defendantColumn = "defendantFirstName"; 
                                break;
                            case 2: 
                                casIdColumn = "cas_id2"; 
                                getfromDate = "date_from2";
                                defendantColumn = "defendantSecondName"; 
                                break;
                            case 3: 
                                casIdColumn = "cas_id3"; 
                                getfromDate = "date_from3";
                                defendantColumn = "defendantThirdName"; 
                                break;
                            case 4: 
                                casIdColumn = "cas_id4"; 
                                getfromDate = "date_from4";
                                defendantColumn = "defendantFourthName"; 
                                break;
                            case 5: 
                                casIdColumn = "cas_id5"; 
                                getfromDate = "date_from5";
                                defendantColumn = "defendantFifthName"; 
                                break;
                            default: 
                                throw new RuntimeException("Unexpected iteration: " + iteration);
                        }
    
                        // Retrieve the cas_id and defendant name from the session and set them for use in the scenario
                        String casId = session.getString(casIdColumn);
                        String defendantName = session.getString(defendantColumn);
                        session = session
                                    .set("getCaseId", casId)         // Set the case_id for #{case_id} usage
                                    .set("defendantFirstName", defendantName) // Set the defendant name for #{defendantFirstName} usage
                                    .set("getfromDate", getfromDate); // Set the from Date
    
                        // Update the loop counter in the session for the next iteration
                        return session.set("loopCounter", iteration);
                    })
                    .exec(DartsPortalAdvanceSearchScenario.DartsPortalAdvanceSearch()) // Perform advance search
                    .exec(DartsPortalRequestAudioScenario.DartsPortalRequestAudioDownload()) // Request audio download
                    .exec(DartsPortalRequestTranscriptionScenario.DartsPortalRequestTranscription()) // Request transcription
                )
                .exec(DartsPortalExternalLogoutScenario.DartsPortalExternalLogoutRequest()) // Logout request
            );
    }
}
