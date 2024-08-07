package simulations.Scripts.DartsApi;

import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Scenario.DartsApi.GetApiTokenScenario;
import simulations.Scripts.Scenario.DartsApi.PostAudioScenario;
import simulations.Scripts.Scenario.DartsSoap.GetCasesTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.RegisterWithTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.RegisterWithUsernameScenario;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import java.time.Duration;

public class ScenarioMultipleSimulationTest extends Simulation {

    private static final String BASELINE_SCENARIO_NAME = "Baseline - DARTS - Api - Audio:POST";
    private static final String RAMP_UP_SCENARIO_NAME = "Ramp Up - DARTS - Api - Audio:POST";
    private static final String SPIKE_SCENARIO_NAME = "Spike - DARTS - GateWay - Soap - AddDocument:POST - Event Token";
    private static final String BASELINE_SCENARIO_NAME2 = "Baseline - DARTS - GateWay - Soap - GetCase - Token";
    private static final String RAMP_UP_SCENARIO_NAME2 = "Ramp Up - DARTS - GateWay - Soap - GetCase - Token";
    private static final String SPIKE_SCENARIO_NAME2 = "Spike - DARTS - GateWay - Soap - GetCase - Token";

    @Override
    public void before() {
        System.out.println("Simulation is about to start!");
    }

    public ScenarioMultipleSimulationTest() {
        HttpProtocolBuilder httpProtocol = http
           //     .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
                  .baseUrl(EnvironmentURL.B2B_Login.getUrl())
                  .inferHtmlResources();
        setUpScenarios(httpProtocol);
        
    }


    private void setUpScenarios(HttpProtocolBuilder httpProtocol) {
        // Set up scenarios with configurable parameters
        ScenarioBuilder baselineScenario = setUpScenario(BASELINE_SCENARIO_NAME, AppConfig.SMOKE_PACE_DURATION_MINS, AppConfig.SOAP_SMOKE_REPEATS);
        ScenarioBuilder rampUpScenario = setUpScenario(RAMP_UP_SCENARIO_NAME, AppConfig.SMOKE_PACE_DURATION_MINS, AppConfig.SOAP_BASELINE_NORMAL_REPEATS);
        ScenarioBuilder spikeScenario = setUpScenario(SPIKE_SCENARIO_NAME, AppConfig.SMOKE_PACE_DURATION_MINS, AppConfig.SOAP_BASELINE_PEAK_REPEATS);
        ScenarioBuilder baselineScenario2 = setUpScenario2(BASELINE_SCENARIO_NAME2, AppConfig.SMOKE_PACE_DURATION_MINS, AppConfig.SOAP_SMOKE_REPEATS);
        ScenarioBuilder rampUpScenario2 = setUpScenario2(RAMP_UP_SCENARIO_NAME2, AppConfig.SMOKE_PACE_DURATION_MINS, AppConfig.SOAP_BASELINE_NORMAL_REPEATS);
        ScenarioBuilder spikeScenario2 = setUpScenario2(SPIKE_SCENARIO_NAME2, AppConfig.SMOKE_PACE_DURATION_MINS, AppConfig.SOAP_BASELINE_PEAK_REPEATS);

        // Call setUp once with all scenarios
        setUp(
                baselineScenario.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.SMOKE_PACE_DURATION_MINS))).protocols(httpProtocol)
                        .andThen(rampUpScenario.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.SMOKE_PACE_DURATION_MINS))).protocols(httpProtocol))
                        .andThen(spikeScenario.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.SMOKE_PACE_DURATION_MINS))).protocols(httpProtocol)),
                baselineScenario2.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.SMOKE_PACE_DURATION_MINS))).protocols(httpProtocol)
                        .andThen(rampUpScenario2.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.SMOKE_PACE_DURATION_MINS))).protocols(httpProtocol))
                        .andThen(spikeScenario2.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.SMOKE_PACE_DURATION_MINS))).protocols(httpProtocol))
        );
    }

    private ScenarioBuilder setUpScenario(String scenarioName, int paceDurationMillis, int repeats) {
        return scenario(scenarioName)        
        .group(scenarioName)
            .on(exec(GetApiTokenScenario.getApiToken()))
                .repeat(repeats)
                .on(exec(PostAudioScenario.PostApiAudio().pace(Duration.ofMillis(paceDurationMillis))));
    }

    private ScenarioBuilder setUpScenario2(String scenarioName, int paceDurationMillis, int repeats) {
        return scenario(scenarioName)
        .group(scenarioName)
        .on(exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_PASSWORD.getUrl()))
                .exec(RegisterWithTokenScenario.RegisterWithToken(EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_PASSWORD.getUrl()))
                .repeat(repeats)
                .on(exec(GetCasesTokenScenario.GetCaseToken().pace(Duration.ofMillis(paceDurationMillis)))));
    }


    @Override
    public void after() {
        System.out.println("Simulation is finished!");
    }
}
