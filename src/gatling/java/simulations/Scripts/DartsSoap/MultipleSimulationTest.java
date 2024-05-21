package simulations.Scripts.DartsSoap;

import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Scenario.DartsSoap.AddDocumentEventTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.GetCasesTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.RegisterWithTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.RegisterWithUsernameScenario;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import java.time.Duration;

public class MultipleSimulationTest extends Simulation {

    private static final String BASELINE_SCENARIO_NAME = "Baseline - DARTS - GateWay - Soap - AddDocument:POST - Event Token";
    private static final String RAMP_UP_SCENARIO_NAME = "Ramp Up - DARTS - GateWay - Soap - AddDocument:POST - Event Token";
    private static final String SPIKE_SCENARIO_NAME = "Spike - DARTS - GateWay - Soap - AddDocument:POST - Event Token";
    private static final String BASELINE_SCENARIO_NAME2 = "Baseline - DARTS - GateWay - Soap - GetCase - Token";
    private static final String RAMP_UP_SCENARIO_NAME2 = "Ramp Up - DARTS - GateWay - Soap - GetCase - Token";
    private static final String SPIKE_SCENARIO_NAME2 = "Spike - DARTS - GateWay - Soap - GetCase - Token";

    @Override
    public void before() {
        System.out.println("Simulation is about to start!");
    }

    public MultipleSimulationTest() {
        HttpProtocolBuilder httpProtocol = http
           //     .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
                .baseUrl(EnvironmentURL.GATEWAY_BASE_URL.getUrl())
                .inferHtmlResources()
                .acceptEncodingHeader("gzip,deflate")
                .contentTypeHeader("text/xml;charset=UTF-8")
                .userAgentHeader("Apache-HttpClient/4.5.5 (Java/16.0.2)");

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
            .on(exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_PASSWORD.getUrl()))
                .exec(RegisterWithTokenScenario.RegisterWithToken(EnvironmentURL.DARTS_SOAP_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_PASSWORD.getUrl()))
                .repeat(repeats)
                .on(exec(AddDocumentEventTokenScenario.AddDocumentEventToken().pace(Duration.ofMillis(paceDurationMillis)))));
    }

    private ScenarioBuilder setUpScenario2(String scenarioName, int paceDurationMillis, int repeats) {
        return scenario(scenarioName)
        .group(scenarioName)
        .on(exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_EXTERNAL_PASSWORD.getUrl()))
                .exec(RegisterWithTokenScenario.RegisterWithToken(EnvironmentURL.DARTS_SOAP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_EXTERNAL_PASSWORD.getUrl()))
                .repeat(repeats)
                .on(exec(GetCasesTokenScenario.GetCaseToken().pace(Duration.ofMillis(paceDurationMillis)))));
    }

    @Override
    public void after() {
        System.out.println("Simulation is finished!");
    }
}
