package simulations.Scripts.DartsGroupTest.Scenarios;

import io.gatling.javaapi.core.ScenarioBuilder;
import static io.gatling.javaapi.core.CoreDsl.*;
import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Scenario.DartsPortal.*;

public class TranscriberUsersScenario {

    public static ScenarioBuilder build(String scenarioName) {
        return scenario(scenarioName)
            .group("Transcriber Users")
            .on(
                exec(feed(Feeders.createTranscriberUsers())) // Load transcriber user data
                .exec(DartsPortalExternalLoginScenario.DartsPortalExternalLoginRequest())
                .exec(session -> session.set("loopCounter", 0)) // Initialize loop counter
                .repeat(5).on(
                    exec(session -> {
                        // Increment the loop counter
                        int iteration = session.getInt("loopCounter") + 1;
    
                        // Determine the column name based on the iteration number
                        String defendantColumn = switch (iteration) {
                            case 1 -> "defendantFirstName";
                            case 2 -> "defendantSecondName";
                            case 3 -> "defendantThirdName";
                            case 4 -> "defendantFourthName";
                            case 5 -> "defendantFifthName";
                            default -> throw new RuntimeException("Unexpected iteration: " + iteration);
                        };
    
                        // Retrieve the defendant name from the session and set it for use
                        String defendantName = session.getString(defendantColumn);
                        session = session.set("defendantFirstName", defendantName);
    
                        // Update the loop counter in the session for the next iteration
                        return session.set("loopCounter", iteration);
                    })
                    .exec(DartsPortalAdvanceSearchScenario.DartsPortalAdvanceSearchScenario()) // Perform advance search
                    .exec(DartsPortalRequestAudioScenario.DartsPortalRequestAudioDownload()) // Request audio download
                    .exec(TranscriberAttachFileAndDownloadAudioScenario.TranscriberAttachFileAndDownloadAudio()) // Attach file
                    .exec(DartsPortalDeleteAudioRequestScenario.DartsPortalDeleteAudioRequestScenario()) // Delete audio request
                )
                .exec(DartsPortalExternalLogoutScenario.DartsPortalExternalLogoutRequest()) // Logout request
            );
    }
}
