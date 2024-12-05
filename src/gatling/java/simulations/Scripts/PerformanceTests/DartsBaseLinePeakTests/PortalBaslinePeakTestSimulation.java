package simulations.Scripts.PerformanceTests.DartsBaseLinePeakTests;

import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalInternalLoginScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalAdvanceSearchScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalApproveAudioScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalChangeRetentionScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalDeleteAudioRequestScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalExternalLoginScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalExternalLogoutScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalInternalLogoutScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalRequestAudioScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalRequestTranscriptionScenario;
import simulations.Scripts.Scenario.DartsPortal.TranscriberAttachFileAndDownloadAudioScenario;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class PortalBaslinePeakTestSimulation extends Simulation {   
    
    public static AtomicInteger global400ErrorCounter = new AtomicInteger(0);

    private static final String SMOKE_TEST_TWO_JUDGE_USERS = "Baseline Peak - DARTS - Portal - Judge Users";
    private static final String SMOKE_TEST_TWO_COURT_CLERK_USERS = "Baseline Peak - DARTS - Portal - Court Clerk Users";
    private static final String SMOKE_TEST_TWO_COURT_MANAGER_USERS = "Baseline Peak - DARTS - Portal - Court Manager Users";
    private static final String SMOKE_TEST_TWO_TRANSCRIBER_USERS = "Baseline Peak - DARTS - Portal - Transcriber Users";
    private static final String SMOKE_TEST_TWO_LANGUAGE_USERS = "Baseline Peak - DARTS - Portal - Language Shop Users";

    @Override
    public void before() {
        System.out.println("Simulation is about to start!");
    }

    public PortalBaslinePeakTestSimulation() {
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
        ScenarioBuilder smokeJudgeUsers = setUpJudgeUsers(SMOKE_TEST_TWO_JUDGE_USERS);
        ScenarioBuilder smokeCourtClerkUsers = setUpCourtClerkUsers(SMOKE_TEST_TWO_COURT_CLERK_USERS);
        ScenarioBuilder smokeCourtManagerUsers = setUpCourtManagerUsers(SMOKE_TEST_TWO_COURT_MANAGER_USERS);
        ScenarioBuilder smokeTranscriberUsers = setUpTranscriberUsers(SMOKE_TEST_TWO_TRANSCRIBER_USERS);
        ScenarioBuilder smokeLanguageShopUsers = setUpLanguageShopUsers(SMOKE_TEST_TWO_LANGUAGE_USERS);

        // Call setUp once with all scenarios
        setUp(
            smokeJudgeUsers.injectOpen(
                rampUsers(AppConfig.JUDGE_RAMP_UP_USERS_PEAK).during(Duration.ofMinutes(AppConfig.RAMP_UP_DURATION_OF_JUDGES)) 
            ).protocols(httpProtocolInternal),
            smokeCourtClerkUsers.injectOpen(
                rampUsers(AppConfig.COURT_CLERK_RAMP_UP_USERS_PEAK).during(Duration.ofMinutes(AppConfig.RAMP_UP_DURATION_OF_COURT_CLERK)) 
            ).protocols(httpProtocolInternal),
            smokeCourtManagerUsers.injectOpen(
                rampUsers(AppConfig.COURT_MANAGER_RAMP_UP_USERS_PEAK).during(Duration.ofMinutes(AppConfig.RAMP_UP_DURATION_OF_COURT_MANAGER)) 
            ).protocols(httpProtocolInternal),
            smokeTranscriberUsers.injectOpen(
                rampUsers(AppConfig.TRANSCRIBER_RAMP_UP_USERS_PEAK).during(Duration.ofMinutes(AppConfig.RAMP_UP_DURATION_OF_TRANSCRIBER))
            ).protocols(httpProtocolExternal),
            smokeLanguageShopUsers.injectOpen(
                rampUsers(AppConfig.LANGUAGE_SHOP_RAMP_UP_USERS_PEAK).during(Duration.ofMinutes(AppConfig.RAMP_UP_DURATION_OF_LANGUAGE_SHOP)) 
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
                    .exec(DartsPortalRequestAudioScenario.DartsPortalRequestAudioDownload()) // Request audio download
                    .exec(DartsPortalRequestTranscriptionScenario.DartsPortalRequestTranscription()) // Request transcription
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
                    .exec(DartsPortalApproveAudioScenario.DartsPortalApproveAudio())
                )
                // .exec(DartsPortalPreviewAudioScenario.DartsPortalPreviewAudioScenario())
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
                  .exec(DartsPortalRequestAudioScenario.DartsPortalRequestAudioDownload()) // Request audio download
                    .exec(TranscriberAttachFileAndDownloadAudioScenario.TranscriberAttachFileAndDownloadAudio()) // Add File to Transcription
                   .exec(DartsPortalDeleteAudioRequestScenario.DartsPortalDeleteAudioRequest()) // Delete a random Audio request
                )
                // .exec(DartsPortalPreviewAudioScenario.DartsPortalPreviewAudioScenario())
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
                .exec(DartsPortalRequestAudioScenario.DartsPortalRequestAudioDownload())
                //.exec(DartsPortalPreviewAudioScenario.DartsPortalPreviewAudioScenario())
            )
            .exec(DartsPortalExternalLogoutScenario.DartsPortalExternalLogoutRequest())
        );
    }

    private ScenarioBuilder setUpJudgeUsers(String scenarioName) {
        return scenario(scenarioName)
            .group("Judge Users")
            .on(
                exec(feed(Feeders.createJudgeUsers()))
                .exec(DartsPortalInternalLoginScenario.DartsPortalInternalLoginRequest())
                .exec(session -> session.set("loopCounter", 0))  // Initialize loopCounter
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
                    .exec(DartsPortalChangeRetentionScenario.DartsPortalChangeRetention())
                )
                .exec(DartsPortalInternalLogoutScenario.DartsPortalInternalLogoutRequest())
            );
    }

    @Override
    public void after() {
        System.out.println("Total 400 Errors Encountered: " + global400ErrorCounter.get());

        System.out.println("Simulation is finished!");
    }
}
