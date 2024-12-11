package simulations.Scripts.PerformanceTests.DartsSmokeTests;

import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalInternalLoginScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalAdvanceSearchScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalExternalLoginScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalExternalLogoutScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalInternalLogoutScenario;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class PortalAdvanceSearchSimulation extends Simulation {   

    private static final String SMOKE_TEST_TWO_COURT_CLERK_USERS = "Smoke Test Two - DARTS - Portal - Court Clerk Users";
    private static final String SMOKE_TEST_TWO_COURT_MANAGER_USERS = "Smoke Test Two - DARTS - Portal - Court Manager Users";
    private static final String SMOKE_TEST_TWO_TRANSCRIBER_USERS = "Smoke Test Two - DARTS - Portal - Transcriber Users";
    private static final String SMOKE_TEST_TWO_LANGUAGE_USERS = "Smoke Test Two - DARTS - Portal - Language Shop Users";

    @Override
    public void before() {
        System.out.println("Simulation is about to start!");
    }

    public PortalAdvanceSearchSimulation() {
            HttpProtocolBuilder httpProtocolExternal = http
                .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
                .baseUrl(AppConfig.EnvironmentURL.B2B_Login.getUrl())
                .inferHtmlResources()
                .acceptHeader("application/json, text/plain, */*")
                .acceptEncodingHeader("gzip, deflate, br")
                .acceptLanguageHeader("en-US,en;q=0.9")
                .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36");   

    
            HttpProtocolBuilder httpProtocolInternal = http
            .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
                //.baseUrl(AppConfig.EnvironmentURL.B2B_Login.getUrl())
                .baseUrl("https://login.microsoftonline.com") 
                .inferHtmlResources()
                .acceptHeader("application/json, text/plain, */*")
                .acceptEncodingHeader("gzip, deflate, br")
                .acceptLanguageHeader("en-US,en;q=0.9")
                .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36");
      
                setUpScenarios(httpProtocolExternal, httpProtocolInternal);
            }
           
        
    private void setUpScenarios(HttpProtocolBuilder httpProtocolExternal, HttpProtocolBuilder httpProtocolInternal) {
        // Set up scenarios with configurable parameters
        ScenarioBuilder smokeCourtClerkUsers = setUpCourtClerkUsers(SMOKE_TEST_TWO_COURT_CLERK_USERS);
        ScenarioBuilder smokeCourtManagerUsers = setUpCourtManagerUsers(SMOKE_TEST_TWO_COURT_MANAGER_USERS);
        ScenarioBuilder smokeTranscriberUsers = setUpTranscriberUsers(SMOKE_TEST_TWO_TRANSCRIBER_USERS);
        ScenarioBuilder smokeLanguageShopUsers = setUpLanguageShopUsers(SMOKE_TEST_TWO_LANGUAGE_USERS);

        // Call setUp once with all scenarios
        setUp(
            smokeCourtClerkUsers.injectOpen(
                rampUsers(AppConfig.COURT_CLERK_RAMP_UP_USERS).during(Duration.ofMinutes(AppConfig.RAMP_UP_DURATION_OF_COURT_CLERK)) 
            ).protocols(httpProtocolInternal),
            smokeCourtManagerUsers.injectOpen(
                rampUsers(AppConfig.COURT_MANAGER_RAMP_UP_USERS).during(Duration.ofMinutes(AppConfig.RAMP_UP_DURATION_OF_COURT_MANAGER)) 
            ).protocols(httpProtocolInternal),
            smokeTranscriberUsers.injectOpen(
                rampUsers(AppConfig.TRANSCRIBER_RAMP_UP_USERS).during(Duration.ofMinutes(AppConfig.RAMP_UP_DURATION_OF_TRANSCRIBER))
            ).protocols(httpProtocolExternal),
            smokeLanguageShopUsers.injectOpen(
                rampUsers(AppConfig.LANGUAGE_SHOP_RAMP_UP_USERS).during(Duration.ofMinutes(AppConfig.RAMP_UP_DURATION_OF_LANGUAGE_SHOP)) 
            ).protocols(httpProtocolExternal)
        );
    }

    private ScenarioBuilder setUpCourtClerkUsers(String scenarioName) {
        return scenario(scenarioName)
            .group("Court Clerk Users")
            .on(
                exec(feed(Feeders.createCourtClerkUsers())) // Load court clerk user data
                .exec(DartsPortalInternalLoginScenario.DartsPortalInternalLoginRequest()) // Login request
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
                    .exec(DartsPortalAdvanceSearchScenario.DartsPortalAdvanceSearch()) // Perform advance search
                )
                // .exec(DartsPortalPreviewAudioScenario.DartsPortalPreviewAudioScenario())
                .exec(DartsPortalInternalLogoutScenario.DartsPortalInternalLogoutRequest()) // Logout request
            );
    }

    private ScenarioBuilder setUpCourtManagerUsers(String scenarioName) {
        return scenario(scenarioName)
            .group("Court Managers Users")
            .on(
                exec(feed(Feeders.createCourtManagerUsers())) // Load court clerk user data
                .exec(DartsPortalInternalLoginScenario.DartsPortalInternalLoginRequest()) // Login request
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
                    .exec(DartsPortalAdvanceSearchScenario.DartsPortalAdvanceSearch()) // Perform advance search
                )
                .exec(DartsPortalInternalLogoutScenario.DartsPortalInternalLogoutRequest()) // Logout request
            );
    }

    private ScenarioBuilder setUpTranscriberUsers(String scenarioName) {
        return scenario(scenarioName)
            .group("Transcriber Users")
            .on(
                exec(feed(Feeders.createTranscriberUsers())) // Load court clerk user data
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
                    .exec(DartsPortalAdvanceSearchScenario.DartsPortalAdvanceSearch()) // Perform advance search
                )
                .exec(DartsPortalExternalLogoutScenario.DartsPortalExternalLogoutRequest()) // Logout request
            );
    }
    
    private ScenarioBuilder setUpLanguageShopUsers(String scenarioName) {
        return scenario(scenarioName)        
        .group("Language Shop Users")
        .on(  
            exec(feed(Feeders.createLanguageShopUsers()))
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
                .exec(DartsPortalAdvanceSearchScenario.DartsPortalAdvanceSearch()) 
            )
            .exec(DartsPortalExternalLoginScenario.DartsPortalExternalLoginRequest()));
    }
    @Override
    public void after() {
        System.out.println("Simulation is finished!");
    }
}
