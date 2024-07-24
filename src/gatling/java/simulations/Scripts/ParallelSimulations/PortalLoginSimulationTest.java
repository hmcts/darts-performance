package simulations.Scripts.ParallelSimulations;

import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalInternalLoginScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalExternalLoginScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalLogoutScenario;
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

        HttpProtocolBuilder httpProtocol = http
        .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
            .baseUrl(AppConfig.EnvironmentURL.B2B_Login.getUrl())
            .inferHtmlResources()
            .acceptHeader("application/json, text/plain, */*")
            .acceptEncodingHeader("gzip, deflate, br")
            .acceptLanguageHeader("en-US,en;q=0.9")
            .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36");   


        setUpScenarios(httpProtocol);
    }

    private void setUpScenarios(HttpProtocolBuilder httpProtocol) {
        // Set up scenarios with configurable parameters
        ScenarioBuilder smokeJudgeUsers = setUpJudgeUsers(SMOKE_LOGIN_JUDGE_USERS);
        ScenarioBuilder smokeCourtClerkUsers = setUpCourtClerkUsers(SMOKE_LOGIN_COURT_CLERK_USERS);
        ScenarioBuilder smokeCourtManagerUsers = setUpCourtManagerUsers(SMOKE_LOGIN_COURT_MANAGER_USERS);
        ScenarioBuilder smokeTranscriberUsers = setUpTranscriberUsers(SMOKE_LOGIN_TRANSCRIBER_USERS);
        ScenarioBuilder smokeLanguageShopUsers = setUpLanguageShopUsers(SMOKE_LOGIN_LANGUAGE_USERS);

        // Call setUp once with all scenarios
        setUp(
            smokeJudgeUsers.injectOpen(
                rampUsers(2).during(Duration.ofMinutes(30)) 
            ).protocols(httpProtocol),
            smokeCourtClerkUsers.injectOpen(
                rampUsers(19).during(Duration.ofMinutes(30)) 
            ).protocols(httpProtocol),
            smokeCourtManagerUsers.injectOpen(
                rampUsers(9).during(Duration.ofMinutes(30)) 
            ).protocols(httpProtocol),
            smokeTranscriberUsers.injectOpen(
                rampUsers(4).during(Duration.ofMinutes(30))
            ).protocols(httpProtocol),
            smokeLanguageShopUsers.injectOpen(
                rampUsers(1).during(Duration.ofMinutes(30)) 
            ).protocols(httpProtocol)
        );
    }

    private ScenarioBuilder setUpTranscriberUsers(String scenarioName) {
        return scenario(scenarioName)        
            .exec(feed(Feeders.createTranscriberUsers()))
            .exec(DartsPortalExternalLoginScenario.DartsPortalExternalLoginRequest())
            .exec(DartsPortalLogoutScenario.DartsPortalLogoutRequest());
    }

    private ScenarioBuilder setUpCourtClerkUsers(String scenarioName) {
        return scenario(scenarioName)        
            .exec(feed(Feeders.createCourtClerkUsers()))
            .exec(DartsPortalExternalLoginScenario.DartsPortalExternalLoginRequest())
            .exec(DartsPortalLogoutScenario.DartsPortalLogoutRequest());
    }

    private ScenarioBuilder setUpCourtManagerUsers(String scenarioName) {
        return scenario(scenarioName)        
            .exec(feed(Feeders.createCourtManagerUsers()))
            .exec(DartsPortalExternalLoginScenario.DartsPortalExternalLoginRequest())
            .exec(DartsPortalLogoutScenario.DartsPortalLogoutRequest());
    }

    private ScenarioBuilder setUpLanguageShopUsers(String scenarioName) {
        return scenario(scenarioName)        
            .exec(feed(Feeders.createLanguageShopUsers()))
            .exec(DartsPortalExternalLoginScenario.DartsPortalExternalLoginRequest())
            .exec(DartsPortalLogoutScenario.DartsPortalLogoutRequest());
    }

    private ScenarioBuilder setUpJudgeUsers(String scenarioName) {
        return scenario(scenarioName)        
            .exec(feed(Feeders.createJudgeUsers()))
            .exec(DartsPortalExternalLoginScenario.DartsPortalExternalLoginRequest())
            .exec(DartsPortalLogoutScenario.DartsPortalLogoutRequest());
    }
    @Override
    public void after() {
        System.out.println("Simulation is finished!");
    }
}
