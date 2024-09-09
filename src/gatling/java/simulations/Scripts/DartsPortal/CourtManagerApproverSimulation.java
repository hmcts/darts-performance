package simulations.Scripts.DartsPortal;

import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalAdvanceSearchScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalApproveAudioScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalInternalLoginScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalInternalLogoutScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalRequestAudioScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalRequestTranscriptionScenario;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;


import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import java.time.Duration;

public class CourtManagerApproverSimulation extends Simulation {   
  {

      HttpProtocolBuilder httpProtocol = http
        .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
        .baseUrl(AppConfig.EnvironmentURL.B2B_Login.getUrl())
        .baseUrl("https://login.microsoftonline.com") 

        .inferHtmlResources()
        .acceptHeader("application/json, text/plain, */*")
        .acceptEncodingHeader("gzip, deflate, br")
        .acceptLanguageHeader("en-US,en;q=0.9")
        .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36");
      

    final ScenarioBuilder scn1 = scenario("Darts Portal Login")
        .exec(feed(Feeders.createCourtManagerUsers()))
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
                .exec(DartsPortalAdvanceSearchScenario.DartsPortalAdvanceSearchScenario())     
                .exec(DartsPortalApproveAudioScenario.DartsPortalApproveAudio())
            // .exec(DartsPortalPreviewAudioScenario.DartsPortalPreviewAudioScenario())
            )
            .exec(DartsPortalInternalLogoutScenario.DartsPortalInternalLogoutRequest()
        );
    setUp(
        scn1.injectOpen(rampUsers(1).during(Duration.ofMinutes(1))).protocols(httpProtocol));
    }    
} 
    

