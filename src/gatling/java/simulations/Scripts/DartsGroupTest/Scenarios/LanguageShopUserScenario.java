package simulations.Scripts.DartsGroupTest.Scenarios;

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
                        String defendantColumn = "";
                        switch (iteration) {
                            case 1: defendantColumn = "defendantFirstName"; break;
                            case 2: defendantColumn = "defendantSecondName"; break;
                            case 3: defendantColumn = "defendantThirdName"; break;
                            case 4: defendantColumn = "defendantFourthName"; break;
                            case 5: defendantColumn = "defendantFifthName"; break;
                            default: throw new RuntimeException("Unexpected iteration: " + iteration);
                        }
                
                        // Retrieve the defendant name from the session and set it for use
                        String defendantName = session.getString(defendantColumn);
                        session = session.set("defendantFirstName", defendantName);
                
                        // Update the loop counter in the session for the next iteration
                        return session.set("loopCounter", iteration);
                    })
                    .exec(DartsPortalAdvanceSearchScenario.DartsPortalAdvanceSearchScenario()) // Perform advance search
                    .exec(DartsPortalRequestAudioScenario.DartsPortalRequestAudioDownload()) // Request audio download
                    .exec(DartsPortalRequestTranscriptionScenario.DartsPortalRequestTranscription()) // Request transcription
                )
                .exec(DartsPortalExternalLogoutScenario.DartsPortalExternalLogoutRequest()) // Logout request
            );
    }
}
