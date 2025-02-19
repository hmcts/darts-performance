package simulations.Scripts.NightlyRun;

import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Scenario.DartsApi.GetAudioRequestScenario;
import simulations.Scripts.Scenario.DartsApi.PostAudioRequestScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalAdvanceSearchScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalApproveAudioScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalChangeRetentionScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalDeleteAudioRequestScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalExternalLoginScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalExternalLogoutScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalInternalLoginScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalInternalLogoutScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalRequestAudioScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalRequestTranscriptionScenario;
import simulations.Scripts.Scenario.DartsPortal.TranscriberAttachFileAndDownloadAudioScenario;
import simulations.Scripts.Scenario.DartsSoap.AddCaseUserScenario;
import simulations.Scripts.Scenario.DartsSoap.AddCourtlogUserScenario;
import simulations.Scripts.Scenario.DartsSoap.AddDocumentCPPDailyListTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.AddDocumentCPPEventTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.AddDocumentXhibitDailyListTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.AddDocumentXhibitEventTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.GetCasesUserScenario;
import simulations.Scripts.Scenario.DartsSoap.RegisterWithTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.RegisterWithUsernameScenario;
import simulations.Scripts.Scenario.DartsApi.DeleteAudioRequestScenario;
import simulations.Scripts.Scenario.DartsApi.GetApiTokenScenario;
import java.time.Duration;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class NightlyRunSimulation extends Simulation {

    @Override
    public void before() {
        System.out.println("Simulation is about to start!");
    }

        // // Load all SQL queries in parallel and store them in distinct variables
        // private static final String HEARING_QUERY;
        // private static final String TEST1_QUERY;
        // private static final String TEST2_QUERY;
        // private static final String TEST3_QUERY;

        // static {
        //     // Load each SQL file asynchronously
        //     CompletableFuture<String> hearingQueryFuture = CompletableFuture.supplyAsync(() -> SQLQueryProvider.loadSQL("hearing_query.sql"));
        //     CompletableFuture<String> test1QueryFuture = CompletableFuture.supplyAsync(() -> SQLQueryProvider.loadSQL("test1_query.sql"));
        //     CompletableFuture<String> test2QueryFuture = CompletableFuture.supplyAsync(() -> SQLQueryProvider.loadSQL("test2_query.sql"));
        //     CompletableFuture<String> test3QueryFuture = CompletableFuture.supplyAsync(() -> SQLQueryProvider.loadSQL("test3_query.sql"));

        //     // Wait for all queries to load
        //     try {
        //         HEARING_QUERY = hearingQueryFuture.get();
        //         TEST1_QUERY = test1QueryFuture.get();
        //         TEST2_QUERY = test2QueryFuture.get();
        //         TEST3_QUERY = test3QueryFuture.get();
        //     } catch (InterruptedException | ExecutionException e) {
        //         throw new RuntimeException("Failed to load SQL queries", e);
        //     }
        // }

    public NightlyRunSimulation() {




        HttpProtocolBuilder httpProtocolSoap = http
                .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))

                .inferHtmlResources()
                .acceptEncodingHeader("gzip,deflate")
                .contentTypeHeader("text/xml;charset=UTF-8")
                .userAgentHeader("Apache-HttpClient/4.5.5 (Java/16.0.2)")
                .baseUrl(EnvironmentURL.PROXY_BASE_URL.getUrl());

                
        HttpProtocolBuilder httpProtocolSoapAddDocument = http
            .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
            .baseUrl(EnvironmentURL.GATEWAY_BASE_URL.getUrl())
            .inferHtmlResources()
            .acceptEncodingHeader("gzip,deflate")
            .contentTypeHeader("text/xml;charset=UTF-8")
            .userAgentHeader("Apache-HttpClient/4.5.5 (Java/16.0.2)");
            


        HttpProtocolBuilder httpProtocolApi = http
                .inferHtmlResources()
                .baseUrl(EnvironmentURL.B2B_Login.getUrl());

        HttpProtocolBuilder httpProtocolExternal = http
                .baseUrl(AppConfig.EnvironmentURL.B2B_Login.getUrl())
                .inferHtmlResources()
                .acceptHeader("application/json, text/plain, */*")
                .acceptEncodingHeader("gzip, deflate, br")
                .acceptLanguageHeader("en-US,en;q=0.9")
                .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36");   

    
        HttpProtocolBuilder httpProtocolInternal = http
                .baseUrl("https://login.microsoftonline.com") 
                .inferHtmlResources()
                .acceptHeader("application/json, text/plain, */*")
                .acceptEncodingHeader("gzip, deflate, br")
                .acceptLanguageHeader("en-US,en;q=0.9")
                .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36");
                
        setUpScenarios(httpProtocolSoap, httpProtocolSoapAddDocument, httpProtocolApi, httpProtocolExternal, httpProtocolInternal);
    }

    private void setUpScenarios(HttpProtocolBuilder httpProtocolSoap, HttpProtocolBuilder httpProtocolSoapAddDocument, HttpProtocolBuilder httpProtocolApi, HttpProtocolBuilder httpProtocolExternal, HttpProtocolBuilder httpProtocolInternal) {
        // Main SOAP scenario setup
        ScenarioBuilder saopScenarios = scenario("Soap Proxy Scenario")
         //Register with different VIQ
         .group("Register With VIQ External Username")
         .on(
             exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_PASSWORD.getUrl()))
            .exec(RegisterWithTokenScenario.registerWithToken(EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_PASSWORD.getUrl()))
            .repeat(1)
                .on(exec(AddCaseUserScenario.addCaseUser(EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_PASSWORD.getUrl())))
            .repeat(1)
                .on(exec(GetCasesUserScenario.GetCaseSOAPUser(EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_PASSWORD.getUrl())))
            .repeat(1)
                .on(exec(AddCourtlogUserScenario.addCourtLogUser(EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_PASSWORD.getUrl())))
            );
            
        ScenarioBuilder soapAddDocument = scenario("Soap Gateway Scenario")

        //Register with different CPP
        .group("Register With CPP External Username")
        .on(
            exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_CPP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_CPP_EXTERNAL_PASSWORD.getUrl()))
            .exec(RegisterWithTokenScenario.registerWithToken(EnvironmentURL.DARTS_SOAP_CPP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_CPP_EXTERNAL_PASSWORD.getUrl()))
            .repeat(1)
                .on(exec(AddDocumentCPPEventTokenScenario.AddDocumentCPPEventToken()))
            .repeat(1) 
                .on(exec(AddDocumentCPPDailyListTokenScenario.AddDocumentCPPDailyListToken()))
        )

        //Register with different XHIBIT
        .group("Register With XHIBIT External Username")
        .on(
            exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_PASSWORD.getUrl()))
            .exec(RegisterWithTokenScenario.registerWithToken(EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_PASSWORD.getUrl()))
            .repeat(1)
                    .on(exec(AddDocumentXhibitEventTokenScenario.AddDocumentXhibitEventToken()))
                .repeat(1)
                    .on(exec(AddDocumentXhibitDailyListTokenScenario.AddDocumentXhibitDailyListToken()))
        );

        // API scenario setups
        ScenarioBuilder postAudioScenario = scenario("Post Audio Request Scenario")
            .exec(GetApiTokenScenario.getApiToken())
            .repeat(AppConfig.NIGHTLY_RUN_REPEATS)
                .on(exec(PostAudioRequestScenario.PostaudioRequest())
                .exec(GetAudioRequestScenario.GetAudioRequest())
                .exec(DeleteAudioRequestScenario.DeleteAudioRequest())

                //  .uniformRandomSwitch().on(
                //     exec(GetAudioRequestScenario.GetAudioRequestDownload()),
                //     exec(GetAudioRequestScenario.GetAudioRequestPlayBack()))

        );  

        ScenarioBuilder courtClerkUsers = scenario("Smoke Test Two - DARTS - Portal - Court Clerk Users")               
            .group("Court Clerk Users")
            .on(
                exec(feed(Feeders.createCourtClerkUsers())) // Load court clerk user data
                .exec(DartsPortalInternalLoginScenario.DartsPortalInternalLoginRequest()) // Login request
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
                    .exec(DartsPortalAdvanceSearchScenario.DartsPortalAdvanceSearch()) // Perform advance search
                    .exec(DartsPortalRequestAudioScenario.DartsPortalRequestAudioDownload()) // Request audio download
                    .exec(DartsPortalRequestTranscriptionScenario.DartsPortalRequestTranscription()) // Request transcription
                )
                // .exec(DartsPortalPreviewAudioScenario.DartsPortalPreviewAudioScenario())
                .exec(DartsPortalInternalLogoutScenario.DartsPortalInternalLogoutRequest()) // Logout request
        );
            
        ScenarioBuilder courtManagerUsers = scenario("Smoke Test Two - DARTS - Portal - Court Manager Users") 
                .group("Court Managers Users")
                .on(
                    exec(feed(Feeders.createCourtManagerUsers())) // Load court clerk user data
                    .exec(DartsPortalInternalLoginScenario.DartsPortalInternalLoginRequest()) // Login request
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
                        .exec(DartsPortalAdvanceSearchScenario.DartsPortalAdvanceSearch()) // Perform advance search
                        .exec(DartsPortalApproveAudioScenario.DartsPortalApproveAudio())
                    )
                    // .exec(DartsPortalPreviewAudioScenario.DartsPortalPreviewAudioScenario())
                    .exec(DartsPortalInternalLogoutScenario.DartsPortalInternalLogoutRequest()) // Logout request
            );           
        
            ScenarioBuilder transcriberUsers = scenario("Smoke Test Two - DARTS - Portal - Transcriber Users") 
                    .group("Transcriber Users")
                    .on(
                        exec(feed(Feeders.createTranscriberUsers())) // Load court clerk user data
                        .exec(DartsPortalExternalLoginScenario.DartsPortalExternalLoginRequest())
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
                            .exec(DartsPortalAdvanceSearchScenario.DartsPortalAdvanceSearch()) // Perform advance search
                            .exec(DartsPortalRequestAudioScenario.DartsPortalRequestAudioDownload()) // Request audio download
                            .exec(TranscriberAttachFileAndDownloadAudioScenario.TranscriberAttachFileAndDownloadAudio()) // Add File to Transcription
                            .exec(DartsPortalDeleteAudioRequestScenario.DartsPortalDeleteAudioRequest()) // Delete a random Audio request
                        )
                        // .exec(DartsPortalPreviewAudioScenario.DartsPortalPreviewAudioScenario())
                        .exec(DartsPortalExternalLogoutScenario.DartsPortalExternalLogoutRequest()) // Logout request
                    );
            
            ScenarioBuilder languageShopUsers = scenario("Smoke Test Two - DARTS - Portal - Language Shop Users")     
                .group("Language Shop Users")
                .on(  
                    exec(feed(Feeders.createLanguageShopUsers()))
                    .exec(DartsPortalExternalLoginScenario.DartsPortalExternalLoginRequest())
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
                        .exec(DartsPortalAdvanceSearchScenario.DartsPortalAdvanceSearch()) 
                        .exec(DartsPortalRequestAudioScenario.DartsPortalRequestAudioDownload())
                       // .exec(DartsPortalPreviewAudioScenario.DartsPortalPreviewAudioScenario())
                    )
                    .exec(DartsPortalExternalLoginScenario.DartsPortalExternalLoginRequest())
                );

            ScenarioBuilder judgeUsers = scenario("Smoke Test Two - DARTS - Portal - Judge Users")     
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

        // Set up all scenarios together
        setUp(
            saopScenarios.injectOpen(atOnceUsers(AppConfig.NIGHTLY_RUN_USERS))
                .protocols(httpProtocolSoap),
            soapAddDocument.injectOpen(atOnceUsers(AppConfig.NIGHTLY_RUN_USERS))
                .protocols(httpProtocolSoapAddDocument)
                
            // postAudioScenario.injectOpen(atOnceUsers(AppConfig.NIGHTLY_RUN_USERS))
            //     .protocols(httpProtocolApi),

            // judgeUsers.injectOpen(
            //     rampUsers(AppConfig.JUDGE_RAMP_UP_USERS)
            //         .during(Duration.ofMinutes(AppConfig.RAMP_UP_DURATION_OF_JUDGES))
            // ).protocols(httpProtocolInternal),
            
            // courtClerkUsers.injectOpen(
            //     rampUsers(AppConfig.COURT_CLERK_RAMP_UP_USERS)
            //         .during(Duration.ofMinutes(AppConfig.RAMP_UP_DURATION_OF_COURT_CLERK))
            // ).protocols(httpProtocolInternal),
            
            // courtManagerUsers.injectOpen(
            //     rampUsers(AppConfig.COURT_MANAGER_RAMP_UP_USERS)
            //         .during(Duration.ofMinutes(AppConfig.RAMP_UP_DURATION_OF_COURT_MANAGER))
            // ).protocols(httpProtocolInternal),
            
            // transcriberUsers.injectOpen(
            //     rampUsers(AppConfig.TRANSCRIBER_RAMP_UP_USERS)
            //         .during(Duration.ofMinutes(AppConfig.RAMP_UP_DURATION_OF_TRANSCRIBER))
            // ).protocols(httpProtocolExternal),
            
            // languageShopUsers.injectOpen(
            //     rampUsers(AppConfig.LANGUAGE_SHOP_RAMP_UP_USERS)
            //         .during(Duration.ofMinutes(AppConfig.RAMP_UP_DURATION_OF_LANGUAGE_SHOP))
            // ).protocols(httpProtocolExternal)

        ).assertions(
            global().responseTime().max().lt(50000),
            global().successfulRequests().percent().gt(95.0)
        );
        
    }
        @Override
        public void after() {
            System.out.println("Simulation is finished!");
        }
}
