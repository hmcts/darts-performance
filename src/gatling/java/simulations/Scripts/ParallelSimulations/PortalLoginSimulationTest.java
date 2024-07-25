package simulations.Scripts.ParallelSimulations;

import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalInternalLoginScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalExternalLogoutScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalExternalLoginScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalInternalLogoutScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalRequestAudioScenario;
import simulations.Scripts.Scenario.DartsPortal.TranscriberAttachFileAndDownloadAudioScenario;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class PortalLoginSimulationTest extends Simulation {   

    private static final String SMOKE_LOGIN_JUDGE_USERS = "Smoke - DARTS - Portal - Login Judge Users";
    private static final String SMOKE_LOGIN_COURT_CLERK_USERS = "Smoke - DARTS - Portal - Login Court Clerk Users";
    private static final String SMOKE_LOGIN_COURT_MANAGER_USERS = "Smoke - DARTS - Portal - Login Court Manager Users ";
    private static final String SMOKE_LOGIN_TRANSCRIBER_USERS = "Smoke - DARTS - Portal - Login Transcriber Users ";
    private static final String SMOKE_LOGIN_LANGUAGE_USERS = "Smoke - DARTS - Portal - Login Language Shop Users";

    @Override
    public void before() {
        System.out.println("Simulation is about to start!");
    }

    public PortalLoginSimulationTest() {
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
        ScenarioBuilder smokeJudgeUsers = setUpJudgeUsers(SMOKE_LOGIN_JUDGE_USERS);
        ScenarioBuilder smokeCourtClerkUsers = setUpCourtClerkUsers(SMOKE_LOGIN_COURT_CLERK_USERS);
        ScenarioBuilder smokeCourtManagerUsers = setUpCourtManagerUsers(SMOKE_LOGIN_COURT_MANAGER_USERS);
        ScenarioBuilder smokeTranscriberUsers = setUpTranscriberUsers(SMOKE_LOGIN_TRANSCRIBER_USERS);
        ScenarioBuilder smokeLanguageShopUsers = setUpLanguageShopUsers(SMOKE_LOGIN_LANGUAGE_USERS);

        // Call setUp once with all scenarios
        setUp(
            smokeJudgeUsers.injectOpen(
                rampUsers(1).during(Duration.ofMinutes(1)) 
            ).protocols(httpProtocolInternal),
            smokeCourtClerkUsers.injectOpen(
                rampUsers(1).during(Duration.ofMinutes(1)) 
            ).protocols(httpProtocolInternal),
            smokeCourtManagerUsers.injectOpen(
                rampUsers(1).during(Duration.ofMinutes(1)) 
            ).protocols(httpProtocolInternal),
            smokeTranscriberUsers.injectOpen(
                rampUsers(1).during(Duration.ofMinutes(1))
            ).protocols(httpProtocolExternal),
            smokeLanguageShopUsers.injectOpen(
                rampUsers(1).during(Duration.ofMinutes(1)) 
            ).protocols(httpProtocolExternal)
        );
    }

    private ScenarioBuilder setUpTranscriberUsers(String scenarioName) {
        return scenario(scenarioName)        
            .exec(feed(Feeders.createTranscriberUsers()))
            .exec(DartsPortalExternalLoginScenario.DartsPortalExternalLoginRequest())
            .exec(DartsPortalExternalLogoutScenario.DartsPortalExternalLogoutRequest());
    }

    private ScenarioBuilder setUpCourtClerkUsers(String scenarioName) {
        return scenario(scenarioName)        
            .exec(feed(Feeders.createCourtClerkUsers()))
            .exec(DartsPortalInternalLoginScenario.DartsPortalInternalLoginRequest())
            .exec(DartsPortalInternalLogoutScenario.DartsPortalInternalLogoutRequest());
    }

    private ScenarioBuilder setUpCourtManagerUsers(String scenarioName) {
        return scenario(scenarioName)        
            .exec(feed(Feeders.createCourtManagerUsers()))
            .exec(DartsPortalInternalLoginScenario.DartsPortalInternalLoginRequest())
            .exec(DartsPortalInternalLogoutScenario.DartsPortalInternalLogoutRequest());
    }

    private ScenarioBuilder setUpLanguageShopUsers(String scenarioName) {
        return scenario(scenarioName)        
            .exec(feed(Feeders.createLanguageShopUsers()))
            .exec(DartsPortalExternalLoginScenario.DartsPortalExternalLoginRequest())
            .exec(DartsPortalExternalLogoutScenario.DartsPortalExternalLogoutRequest());
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
