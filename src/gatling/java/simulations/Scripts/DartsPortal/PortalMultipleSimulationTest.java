package simulations.Scripts.DartsPortal;

import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalLoginScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalLogoutScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalRequestAudioScenario;
import simulations.Scripts.Scenario.DartsPortal.TranscriberAttachfileAndDownlaodAudioScenario;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class PortalMultipleSimulationTest extends Simulation {   

    private static final String BASELINE_SCENARIO_NAME = "Baseline - DARTS - Portal - Transcriber Attachfile And Downlaod Audio";
    private static final String RAMP_UP_SCENARIO_NAME = "Ramp Up - DARTS - Portal - Transcriber Attachfile And Downlaod Audio";
    private static final String SPIKE_SCENARIO_NAME = "Spike - DARTS - Portal - Transcriber Attachfile And Downlaod Audio";
    private static final String BASELINE_SCENARIO_NAME2 = "Baseline - DARTS - Portal - Court Clerk Requesting Audio";
    private static final String RAMP_UP_SCENARIO_NAME2 = "Ramp Up - DARTS - Portal - Court Clerk Requesting Audio";
    private static final String SPIKE_SCENARIO_NAME2 = "Spike - DARTS - Portal - Court Clerk Requesting Audio";

    @Override
    public void before() {
        System.out.println("Simulation is about to start!");
    }

    public PortalMultipleSimulationTest() {
        HttpProtocolBuilder httpProtocol = http
      //      .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
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
        ScenarioBuilder baselineScenario = setUpScenario(BASELINE_SCENARIO_NAME, AppConfig.SOAP_BASELINE_PACE_DURATION_MILLIS, AppConfig.SOAP_BASELINE_REPEATS);
        ScenarioBuilder rampUpScenario = setUpScenario(RAMP_UP_SCENARIO_NAME, AppConfig.SOAP_RAMPUP_PACE_DURATION_MILLIS, AppConfig.SOAP_RAMPUP_REPEATS);
        ScenarioBuilder spikeScenario = setUpScenario(SPIKE_SCENARIO_NAME, AppConfig.SOAP_SPIKE_PACE_DURATION_MILLIS, AppConfig.SOAP_SPIKE_REPEATS);
        ScenarioBuilder baselineScenario2 = setUpScenario2(BASELINE_SCENARIO_NAME2, AppConfig.SOAP_BASELINE_PACE_DURATION_MILLIS, AppConfig.SOAP_BASELINE_REPEATS);
        ScenarioBuilder rampUpScenario2 = setUpScenario2(RAMP_UP_SCENARIO_NAME2, AppConfig.SOAP_RAMPUP_PACE_DURATION_MILLIS, AppConfig.SOAP_RAMPUP_REPEATS);
        ScenarioBuilder spikeScenario2 = setUpScenario2(SPIKE_SCENARIO_NAME2, AppConfig.SOAP_SPIKE_PACE_DURATION_MILLIS, AppConfig.SOAP_SPIKE_REPEATS);

        // Call setUp once with all scenarios
        setUp(
                baselineScenario.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.BASELINE_TEST_DURATION_MINUTES))).protocols(httpProtocol)
                        .andThen(rampUpScenario.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.RAMP_TEST_DURATION_MINUTES))).protocols(httpProtocol))
                        .andThen(spikeScenario.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.SPIKE_TEST_DURATION_MINUTES))).protocols(httpProtocol)),
                baselineScenario2.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.BASELINE_TEST_DURATION_MINUTES))).protocols(httpProtocol)
                        .andThen(rampUpScenario2.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.RAMP_TEST_DURATION_MINUTES))).protocols(httpProtocol))
                        .andThen(spikeScenario2.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.SPIKE_TEST_DURATION_MINUTES))).protocols(httpProtocol))
        );
    }

    private ScenarioBuilder setUpScenario(String scenarioName, int paceDurationMillis, int repeats) {
        return scenario(scenarioName)        
        .group(scenarioName)
            .on(exec(feed(Feeders.createTranscriberUsers()))
                .exec(feed(Feeders.createJudgesFeeder()))
                .exec(DartsPortalLoginScenario.DartsPortalLoginRequest())
                .exec(DartsPortalRequestAudioScenario.DartsPortalRequestAudioDownload())
                .exec(TranscriberAttachfileAndDownlaodAudioScenario.TranscriberAttachfileAndDownlaodAudio())
                .exec(DartsPortalLogoutScenario.DartsPortalLogoutRequest()));
    }

    private ScenarioBuilder setUpScenario2(String scenarioName, int paceDurationMillis, int repeats) {
        return scenario(scenarioName)        
        .group(scenarioName)
            .on(exec(feed(Feeders.createCourtClerkUsers()))
            .exec(feed(Feeders.JudgesCSV))
            .exec(DartsPortalLoginScenario.DartsPortalLoginRequest())
            .exec(DartsPortalRequestAudioScenario.DartsPortalRequestAudioDownload())
            .exec(DartsPortalLogoutScenario.DartsPortalLogoutRequest()));
    }

    @Override
    public void after() {
        System.out.println("Simulation is finished!");
    }
}
