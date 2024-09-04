package simulations.Scripts.DartsPortal;

import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalInternalLoginScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalExternalLoginScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalInternalLogoutScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalRequestAudioScenario;
import simulations.Scripts.Scenario.DartsPortal.TranscriberAttachFileAndDownloadAudioScenario;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class PortalMultipleSimulationTest extends Simulation {   

    private static final String BASELINE_SCENARIO_NAME = "Baseline - DARTS - Portal - Transcriber Attachfile And Downlaod Audio";
    private static final String BASELINE_SCENARIO_NAME2 = "Baseline - DARTS - Portal - Court Clerk Requesting Audio";

    @Override
    public void before() {
        System.out.println("Simulation is about to start!");
    }

    public PortalMultipleSimulationTest() {

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
        ScenarioBuilder baselineScenario = setUpScenario(BASELINE_SCENARIO_NAME);
        ScenarioBuilder baselineScenario2 = setUpScenario2(BASELINE_SCENARIO_NAME2);

        // Call setUp once with all scenarios
        setUp(
            baselineScenario.injectOpen(
                rampUsers(10).during(Duration.ofMinutes(10)) // Ramp up 93 users over 30 minutes
            ).protocols(httpProtocol)
            // baselineScenario2.injectOpen(
            //     rampUsers(93).during(Duration.ofMinutes(30)) // Ramp up 93 users over 30 minutes
            // ).protocols(httpProtocol)
        );
    }

    private ScenarioBuilder setUpScenario(String scenarioName) {
        return scenario(scenarioName)        
            .exec(feed(Feeders.createTranscriberUsers()))
            .exec(DartsPortalExternalLoginScenario.DartsPortalExternalLoginRequest())
            .exec(DartsPortalRequestAudioScenario.DartsPortalRequestAudioDownload())
            .exec(TranscriberAttachFileAndDownloadAudioScenario.TranscriberAttachFileAndDownloadAudio())
            .exec(DartsPortalExternalLoginScenario.DartsPortalExternalLoginRequest());
    }

    private ScenarioBuilder setUpScenario2(String scenarioName) {
        return scenario(scenarioName)        
            .exec(feed(Feeders.createCourtClerkUsers()))
            .exec(feed(Feeders.createJudgesFeeder()))
            .exec(DartsPortalExternalLoginScenario.DartsPortalExternalLoginRequest())
            .exec(DartsPortalRequestAudioScenario.DartsPortalRequestAudioDownload())
            .exec(DartsPortalExternalLoginScenario.DartsPortalExternalLoginRequest());
    }

    @Override
    public void after() {
        System.out.println("Simulation is finished!");
    }
}
