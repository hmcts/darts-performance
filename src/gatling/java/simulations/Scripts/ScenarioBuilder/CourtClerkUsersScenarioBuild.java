package simulations.Scripts.ScenarioBuilder;

import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Scenario.DartsPortal.*;
import io.gatling.javaapi.core.*;

import static io.gatling.javaapi.core.CoreDsl.*;

public class CourtClerkUsersScenarioBuild {

    public static ScenarioBuilder build(String scenarioName) {
        return scenario(scenarioName)
            .group("Court Clerk Users")
            .on(
                exec(feed(Feeders.createCourtManagerUsers())) // Load court clerk user data
                .exec(DartsPortalInternalLoginScenario.DartsPortalInternalLoginRequest()) // Login request
                .exec(session -> session.set("loopCounter", 0)) // Initialize loop counter
                .repeat(5).on(
                    exec(session -> {
                        // Increment the loop counter
                        int iteration = session.getInt("loopCounter") + 1;
    
                        // Determine the column name based on the iteration number
                        String casIdColumn = "";
                        String heaidColumn = "";
                        switch (iteration) {
                            case 1: 
                                casIdColumn = "cas_id1"; 
                                heaidColumn = "hea_id1";                               
                                break;
                            case 2: 
                                casIdColumn = "cas_id2"; 
                                heaidColumn = "hea_id2";
                                break;
                            case 3: 
                                casIdColumn = "cas_id3"; 
                                heaidColumn = "hea_id3"; 
                                break;
                            case 4: 
                                casIdColumn = "cas_id4"; 
                                heaidColumn = "hea_id4"; 
                                break;
                            case 5: 
                                casIdColumn = "cas_id5"; 
                                heaidColumn = "hea_id5";
                                break;
                            default: 
                                throw new RuntimeException("Unexpected iteration: " + iteration);
                        }
    
                        // Retrieve the cas_id and defendant name from the session and set them for use in the scenario
                        String casId = session.getString(casIdColumn);
                        String getHearingId = session.getString(heaidColumn);
                        session = session
                                    .set("getCaseId", casId)         // Set the case_id for #{case_id} usage
                                    .set("getHearingId", getHearingId); // Set the hearing Id #{getHearingId} usage
    
                        // Update the loop counter in the session for the next iteration
                        return session.set("loopCounter", iteration);
                    })
                    .exec(DartsPortalAdvanceSearchScenario.DartsPortalAdvanceSearch()) // Perform advance search
                    .exec(DartsPortalRequestAudioScenario.DartsPortalRequestAudioDownload()) // Request audio download
                    .exec(DartsPortalRequestTranscriptionScenario.DartsPortalRequestTranscription()) // Request transcription
                )
                // .exec(DartsPortalPreviewAudioScenario.DartsPortalPreviewAudioScenario())
                .exec(DartsPortalInternalLogoutScenario.DartsPortalInternalLogoutRequest())); // Logout request);
    }
}