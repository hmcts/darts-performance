package simulations.Scripts.ParallelSimulations;

import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalInternalLoginScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalApproveAudioScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalExternalLoginScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalInternalLogoutScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalRequestAudioScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalRequestTranscriptionScenario;
import simulations.Scripts.Scenario.DartsPortal.TranscriberAttachFileAndDownloadAudioScenario;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class PortalBaslinePeakSimulationTest extends Simulation {   

    private static final String BASELINE_PEAK_JUDGE_USERS = "Baseline Peak - DARTS - Portal - Judge Users";
    private static final String BASELINE_PEAK_COURT_CLERK_USERS = "Baseline Peak - DARTS - Portal - Court Clerk Users";
    private static final String BASELINE_PEAK_COURT_MANAGER_USERS = "Baseline Peak - DARTS - Portal - Court Manager Users ";
    private static final String BASELINE_PEAK_TRANSCRIBER_USERS = "Baseline Peak - DARTS - Portal - Transcriber Users ";
    private static final String BASELINE_PEAK_LANGUAGE_USERS = "Baseline Peak - DARTS - Portal - Language Shop Users";

    @Override
    public void before() {
        System.out.println("Simulation is about to start!");
    }

    public PortalBaslinePeakSimulationTest() {
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
        ScenarioBuilder smokeJudgeUsers = setUpJudgeUsers(BASELINE_PEAK_JUDGE_USERS);
        ScenarioBuilder smokeCourtClerkUsers = setUpCourtClerkUsers(BASELINE_PEAK_COURT_CLERK_USERS);
        ScenarioBuilder smokeCourtManagerUsers = setUpCourtManagerUsers(BASELINE_PEAK_COURT_MANAGER_USERS);
        ScenarioBuilder smokeTranscriberUsers = setUpTranscriberUsers(BASELINE_PEAK_TRANSCRIBER_USERS);
        ScenarioBuilder smokeLanguageShopUsers = setUpLanguageShopUsers(BASELINE_PEAK_LANGUAGE_USERS);

        // Call setUp once with all scenarios
        setUp(
            smokeJudgeUsers.injectOpen(
                rampUsers(AppConfig.JUDGE_RAMP_UP_USERS).during(Duration.ofMinutes(AppConfig.RAMP_UP_DURATION_OF_JUDGES)) 
            ).protocols(httpProtocolInternal),
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

    private ScenarioBuilder setUpTranscriberUsers(String scenarioName) {
        return scenario(scenarioName)        
            .exec(feed(Feeders.createTranscriberUsers()))
            .exec(DartsPortalExternalLoginScenario.DartsPortalExternalLoginRequest())
            .repeat(1)
            .on(exec(DartsPortalRequestAudioScenario.DartsPortalRequestAudioDownload()))
            .exec(TranscriberAttachFileAndDownloadAudioScenario.TranscriberAttachFileAndDownloadAudio())
            .exec(DartsPortalExternalLoginScenario.DartsPortalExternalLoginRequest());
    }

    private ScenarioBuilder setUpCourtClerkUsers(String scenarioName) {
        return scenario(scenarioName)        
            .exec(feed(Feeders.createCourtClerkUsers()))
            .exec(DartsPortalInternalLoginScenario.DartsPortalInternalLoginRequest())      
            .exec(DartsPortalRequestAudioScenario.DartsPortalRequestAudioDownload())
            .exec(DartsPortalRequestTranscriptionScenario.DartsPortalRequestTranscription())
            .exec(DartsPortalInternalLogoutScenario.DartsPortalInternalLogoutRequest());
    }

    private ScenarioBuilder setUpCourtManagerUsers(String scenarioName) {
        return scenario(scenarioName)        
            .exec(feed(Feeders.createCourtManagerUsers()))
            .exec(DartsPortalInternalLoginScenario.DartsPortalInternalLoginRequest())      
            .repeat(20).on(
            exec(DartsPortalApproveAudioScenario.DartsPortalApproveAudio()))
            .exec(DartsPortalInternalLogoutScenario.DartsPortalInternalLogoutRequest());
    }

    private ScenarioBuilder setUpLanguageShopUsers(String scenarioName) {
        return scenario(scenarioName)        
            .exec(feed(Feeders.createLanguageShopUsers()))
            .exec(DartsPortalExternalLoginScenario.DartsPortalExternalLoginRequest())
            .exec(DartsPortalRequestAudioScenario.DartsPortalRequestAudioDownload())
            .exec(DartsPortalInternalLogoutScenario.DartsPortalInternalLogoutRequest());
    }

    private ScenarioBuilder setUpJudgeUsers(String scenarioName) {
        return scenario(scenarioName)        
            .exec(feed(Feeders.createJudgeUsers()))
            .exec(DartsPortalInternalLoginScenario.DartsPortalInternalLoginRequest())
            .exec(DartsPortalInternalLogoutScenario.DartsPortalInternalLogoutRequest());
    }
    @Override
    public void after() {
        System.out.println("Simulation is finished!");
    }
}
