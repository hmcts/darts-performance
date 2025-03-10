package simulations.Scripts.ScenarioBuilder;

import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Scenario.DartsPortal.*;
import io.gatling.javaapi.core.*;

import static io.gatling.javaapi.core.CoreDsl.*;

public class CourtManagerUsersScenarioBuild {

    public static ScenarioBuilder build(String scenarioName) {
        return scenario(scenarioName)
            .group("Court Clerk Users")
            .on(
                exec(feed(Feeders.createCourtClerkUsers()))
                .exec(DartsPortalInternalLoginScenario.DartsPortalInternalLoginRequest())
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
                    .exec(DartsPortalAdvanceSearchScenario.DartsPortalAdvanceSearch())
                    .exec(DartsPortalRequestAudioScenario.DartsPortalRequestAudioDownload())
                    .exec(DartsPortalRequestTranscriptionScenario.DartsPortalRequestTranscription())
                )
                .exec(DartsPortalInternalLogoutScenario.DartsPortalInternalLogoutRequest()));
    }
}
