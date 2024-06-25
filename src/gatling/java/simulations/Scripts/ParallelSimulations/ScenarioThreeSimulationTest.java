package simulations.Scripts.ParallelSimulations;

import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Scenario.DartsApi.*;
import simulations.Scripts.Scenario.DartsSoap.*;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import java.time.Duration;
import java.util.Arrays;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class ScenarioThreeSimulationTest extends Simulation {

    private static final String[] BASELINE_SCENARIO_NAMES = {
        "Baseline - DARTS - GateWay - Soap - AddDocument:POST - Event Token",
        "Baseline - DARTS - GateWay - Soap - GetCase - User",
        "Baseline - DARTS - GateWay - Soap - addCase - User",
        "Baseline - DARTS - GateWay - Soap - getCourtLog - Token",
        "Baseline - DARTS - API - Post - AudioRequest",
        "Baseline - DARTS - API - Get - AudioRequest",
        "Baseline - DARTS - API - Delete - AudioRequest",
        "Baseline - DARTS - API - Post - Audio"
    };

    private static final String[] RAMP_UP_SCENARIO_NAMES = {
        "Ramp Up - DARTS - GateWay - Soap - AddDocument:POST - Event Token",
        "Ramp Up - DARTS - GateWay - Soap - GetCase - User",
        "Ramp Up - DARTS - GateWay - Soap - addCase - User",
        "Ramp Up - DARTS - GateWay - Soap - getCourtLog - Token",
        "Ramp Up - DARTS - API - Post - AudioRequest",
        "Ramp Up - DARTS - API - Get - AudioRequest",
        "Ramp Up - DARTS - API - Delete - AudioRequest",
        "Ramp Up - DARTS - API - Post - Audio"
    };

    private static final String[] SPIKE_SCENARIO_NAMES = {
        "Spike - DARTS - GateWay - Soap - AddDocument:POST - Event Token",
        "Spike - DARTS - GateWay - Soap - GetCase - User",
        "Spike - DARTS - GateWay - Soap - addCase - User",
        "Spike - DARTS - GateWay - Soap - getCourtLog - Token",
        "Spike - DARTS - API - Post - AudioRequest",
        "Spike - DARTS - API - Get - AudioRequest",
        "Spike - DARTS - API - Delete - AudioRequest",
        "Spike - DARTS - API - Post - Audio"
    };

    private static final int BASELINE_DURATION_MILLIS = AppConfig.SMOKE_PACE_DURATION_MINS;
    private static final int RAMPUP_DURATION_MILLIS = AppConfig.SMOKE_PACE_DURATION_MINS;
    private static final int SPIKE_DURATION_MILLIS = AppConfig.SMOKE_PACE_DURATION_MINS;
    private static final int REPEATS = AppConfig.SOAP_BASELINE_REPEATS;
    private static final int USERS_PER_SECOND = AppConfig.USERS_PER_SECOND;
    private static final long BASELINE_TEST_DURATION_MINUTES = AppConfig.SMOKE_PACE_DURATION_MINS;
    private static final long RAMP_TEST_DURATION_MINUTES = AppConfig.SMOKE_PACE_DURATION_MINS;
    private static final long SPIKE_TEST_DURATION_MINUTES = AppConfig.SMOKE_PACE_DURATION_MINS;

    @Override
    public void before() {
        System.out.println("Simulation is about to start!");
    }

    public ScenarioThreeSimulationTest() {
        HttpProtocolBuilder httpProtocol = createHttpProtocol(EnvironmentURL.GATEWAY_BASE_URL.getUrl());
        setUpScenarios(httpProtocol, BASELINE_SCENARIO_NAMES, RAMP_UP_SCENARIO_NAMES, SPIKE_SCENARIO_NAMES);

        HttpProtocolBuilder httpProtocolApi = createHttpProtocol(EnvironmentURL.B2B_Login.getUrl());
        setUpScenariosApi(httpProtocolApi, BASELINE_SCENARIO_NAMES, RAMP_UP_SCENARIO_NAMES, SPIKE_SCENARIO_NAMES);
    }

    private HttpProtocolBuilder createHttpProtocol(String baseUrl) {
        return http
            .baseUrl(baseUrl)
            .inferHtmlResources()
            .acceptEncodingHeader("gzip,deflate")
            .contentTypeHeader("text/xml;charset=UTF-8")
            .userAgentHeader("Apache-HttpClient/4.5.5 (Java/16.0.2)");
    }

    private void setUpScenarios(HttpProtocolBuilder httpProtocol, String[] baselineNames, String[] rampUpNames, String[] spikeNames) {
        ScenarioBuilder[] baselineScenarios = createScenarios(baselineNames, AppConfig.SMOKE_PACE_DURATION_MINS, REPEATS);
        ScenarioBuilder[] rampUpScenarios = createScenarios(rampUpNames, RAMPUP_DURATION_MILLIS, REPEATS);
        ScenarioBuilder[] spikeScenarios = createScenarios(spikeNames, SPIKE_DURATION_MILLIS, REPEATS);

        setUp(
            createCompositeScenario(baselineScenarios, rampUpScenarios, spikeScenarios, httpProtocol)
        );
    }

    private void setUpScenariosApi(HttpProtocolBuilder httpProtocolApi, String[] baselineNames, String[] rampUpNames, String[] spikeNames) {
        ScenarioBuilder[] baselineScenarios = createScenarios(baselineNames, AppConfig.SMOKE_PACE_DURATION_MINS, REPEATS);
        ScenarioBuilder[] rampUpScenarios = createScenarios(rampUpNames, RAMPUP_DURATION_MILLIS, REPEATS);
        ScenarioBuilder[] spikeScenarios = createScenarios(spikeNames, SPIKE_DURATION_MILLIS, REPEATS);

        setUp(
            createCompositeScenario(baselineScenarios, rampUpScenarios, spikeScenarios, httpProtocolApi)
        );
    }

    private ScenarioBuilder[] createScenarios(String[] scenarioNames, int durationMillis, int repeats) {
        return Arrays.stream(scenarioNames)
            .map(name -> scenario(name).group(name)
                .on(exec(setUpScenarioStep(name, durationMillis, repeats))))
            .toArray(ScenarioBuilder[]::new);
    }

    private ChainBuilder setUpScenarioStep(String scenarioName, int paceDurationMillis, int repeats) {
        return exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_PASSWORD.getUrl()))
            .exec(RegisterWithTokenScenario.RegisterWithToken(EnvironmentURL.DARTS_SOAP_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_PASSWORD.getUrl()))
            .repeat(repeats)
            .on(exec(GetCasesUserScenario.GetCaseSOAPUser(EnvironmentURL.DARTS_SOAP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_EXTERNAL_PASSWORD.getUrl()).pace(Duration.ofMillis(paceDurationMillis))));
    }

    private PopulationBuilder[] createCompositeScenario(ScenarioBuilder[] baselineScenarios, ScenarioBuilder[] rampUpScenarios, ScenarioBuilder[] spikeScenarios, HttpProtocolBuilder httpProtocol) {
        PopulationBuilder[] allScenarios = new PopulationBuilder[baselineScenarios.length * 3];
        for (int i = 0; i < baselineScenarios.length; i++) {
            allScenarios[i * 3] = baselineScenarios[i].injectOpen(rampUsers(USERS_PER_SECOND).during(Duration.ofMinutes(BASELINE_TEST_DURATION_MINUTES))).protocols(httpProtocol);
            allScenarios[i * 3 + 1] = rampUpScenarios[i].injectOpen(rampUsers(USERS_PER_SECOND).during(Duration.ofMinutes(RAMP_TEST_DURATION_MINUTES))).protocols(httpProtocol);
            allScenarios[i * 3 + 2] = spikeScenarios[i].injectOpen(rampUsers(USERS_PER_SECOND).during(Duration.ofMinutes(SPIKE_TEST_DURATION_MINUTES))).protocols(httpProtocol);
        }
        return allScenarios;
    }

    @Override
    public void after() {
        System.out.println("Simulation is finished!");
    }
}
