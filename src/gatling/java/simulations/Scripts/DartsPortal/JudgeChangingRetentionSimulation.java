package simulations.Scripts.DartsPortal;

import simulations.Scripts.Scenario.DartsPortal.DartsPortalChangeRetentionScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalInternalLoginScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalInternalLogoutScenario;
import simulations.Scripts.Utilities.*;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class JudgeChangingRetentionSimulation extends Simulation {   
  {    
      HttpProtocolBuilder httpProtocol = http
        .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
        .baseUrl("https://login.microsoftonline.com") 

        .inferHtmlResources()
        .acceptHeader("application/json, text/plain, */*")
        .acceptEncodingHeader("gzip, deflate, br")
        .acceptLanguageHeader("en-US,en;q=0.9")
        .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36");
      
    final ScenarioBuilder scn1 = scenario("Darts Portal Login")
        .exec(feed(Feeders.createJudgeUsers()))
        .exec(DartsPortalInternalLoginScenario.DartsPortalInternalLoginRequest())
        .exec(session -> session.set("loopCounter", 0)) // Initialize loop counter
        .repeat(5).on( // Repeat 5 times, once for each cas_id and defendant name
            exec(session -> {
                // Increment the loop counter
                int iteration = session.getInt("loopCounter") + 1;
    
                // Determine the cas_id and defendant name column names based on the iteration number
                String casIdColumn = "";
                String defendantColumn = "";
                switch (iteration) {
                    case 1: 
                        casIdColumn = "cas_id1"; 
                        defendantColumn = "defendantFirstName"; 
                        break;
                    case 2: 
                        casIdColumn = "cas_id2"; 
                        defendantColumn = "defendantSecondName"; 
                        break;
                    case 3: 
                        casIdColumn = "cas_id3"; 
                        defendantColumn = "defendantThirdName"; 
                        break;
                    case 4: 
                        casIdColumn = "cas_id4"; 
                        defendantColumn = "defendantFourthName"; 
                        break;
                    case 5: 
                        casIdColumn = "cas_id5"; 
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
                            .set("defendantFirstName", defendantName); // Set the defendant name for #{defendantFirstName} usage
    
                // Update the loop counter in the session for the next iteration
                return session.set("loopCounter", iteration);
            })
            .exec(DartsPortalChangeRetentionScenario.DartsPortalChangeRetention()))
        .exec(DartsPortalInternalLogoutScenario.DartsPortalInternalLogoutRequest());

    setUp(
        scn1.injectOpen(constantUsersPerSec(1).during(1)).protocols(httpProtocol));
    }    
} 
    

