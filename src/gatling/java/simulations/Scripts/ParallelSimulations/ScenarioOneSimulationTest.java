package simulations.Scripts.ParallelSimulations;

import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Scenario.DartsApi.GetAudioRequestScenario;
import simulations.Scripts.Scenario.DartsApi.PostAudioRequestScenario;
import simulations.Scripts.Scenario.DartsSoap.AddCaseUserScenario;
import simulations.Scripts.Scenario.DartsSoap.AddDocumentEventTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.GetCasesTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.GetCasesUserScenario;
import simulations.Scripts.Scenario.DartsSoap.GetCourtlogTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.RegisterWithTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.RegisterWithUsernameScenario;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import java.time.Duration;

public class ScenarioOneSimulationTest extends Simulation {

    private static final String BASELINE_ADD_DOCUMENT_EVENTS = "Baseline - DARTS - GateWay - Soap - AddDocument:POST - Event Token";
    private static final String BASELINE_GET_CASES = "Baseline - DARTS - GateWay - Soap - GetCase - User";
    private static final String BASELINE_ADD_CASES = "Baseline - DARTS - GateWay - Soap - addCase - User";
    private static final String BASELINE_POST_AUDIO_REQUEST = "Baseline - DARTS - Api - POST Audio Request";
    private static final String BASELINE_GET_AUDIO_REQUEST = "Baseline - DARTS - Api - GET Audio Request";

    @Override
    public void before() {
        System.out.println("Simulation is about to start!");
    }

    public ScenarioOneSimulationTest() {
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
        ScenarioBuilder baselineAddDocumentEvent = setUpAddDocumentEvent(BASELINE_ADD_DOCUMENT_EVENTS, AppConfig.SMOKE_PACE_DURATION_MINS, AppConfig.EVENTS_SMOKE_REPEATS);
        ScenarioBuilder baselineGetCases = setUpGetCases(BASELINE_GET_CASES, AppConfig.SMOKE_PACE_DURATION_MINS, AppConfig.GET_CASES_SMOKE_REPEATS);
        ScenarioBuilder baselineAddCase = setUpAddCase(BASELINE_ADD_CASES, AppConfig.SMOKE_PACE_DURATION_MINS, AppConfig.ADD_CASES_SMOKE_REPEATS);
        ScenarioBuilder baselinePostAudioRequest = setUpPostAudioRequest(BASELINE_POST_AUDIO_REQUEST, AppConfig.SMOKE_PACE_DURATION_MINS, AppConfig.POST_AUDIO_REQUEST_SMOKE_REPEATS);
        ScenarioBuilder baselineGetAudioRequest = setUpGetAudioRequest(BASELINE_GET_AUDIO_REQUEST, AppConfig.SMOKE_PACE_DURATION_MINS, AppConfig.GET_AUDIO_REQUEST_SMOKE_REPEATS);

        // Call setUp once with all scenarios
        setUp(
                baselineAddDocumentEvent.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.SMOKE_PACE_DURATION_MINS))).protocols(httpProtocol),
                baselineGetCases.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.SMOKE_PACE_DURATION_MINS))).protocols(httpProtocol),
                baselineAddCase.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.SMOKE_PACE_DURATION_MINS))).protocols(httpProtocol),
                baselinePostAudioRequest.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.SMOKE_PACE_DURATION_MINS))).protocols(httpProtocol),
                baselineGetAudioRequest.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.SMOKE_PACE_DURATION_MINS))).protocols(httpProtocol)
        );
    }

    private ScenarioBuilder setUpAddDocumentEvent(String scenarioName, int paceDurationMillis, int repeats) {
        return scenario(scenarioName)        
        .group(scenarioName)
            .on(exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_PASSWORD.getUrl()))
                .exec(RegisterWithTokenScenario.RegisterWithToken(EnvironmentURL.DARTS_SOAP_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_PASSWORD.getUrl()))
                .repeat(repeats)
                .on(exec(AddDocumentEventTokenScenario.AddDocumentEventToken().pace(Duration.ofMillis(paceDurationMillis)))));
    }

    private ScenarioBuilder setUpGetCases(String scenarioName, int paceDurationMillis, int repeats) {
        return scenario(scenarioName)
        .group(scenarioName)
        .on(exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_EXTERNAL_PASSWORD.getUrl()))
                .exec(RegisterWithTokenScenario.RegisterWithToken(EnvironmentURL.DARTS_SOAP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_EXTERNAL_PASSWORD.getUrl()))
                .repeat(repeats)
                .on(exec(GetCasesUserScenario.GetCaseSOAPUser(EnvironmentURL.DARTS_SOAP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_EXTERNAL_PASSWORD.getUrl()).pace(Duration.ofMillis(paceDurationMillis)))));
    }

    private ScenarioBuilder setUpAddCase(String scenarioName, int paceDurationMillis, int repeats) {
        return scenario(scenarioName)
        .group(scenarioName)
        .on(exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_EXTERNAL_PASSWORD.getUrl()))
                .exec(RegisterWithTokenScenario.RegisterWithToken(EnvironmentURL.DARTS_SOAP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_EXTERNAL_PASSWORD.getUrl()))
                .repeat(repeats)
                .on(exec(AddCaseUserScenario.addCaseUser(EnvironmentURL.DARTS_SOAP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_EXTERNAL_PASSWORD.getUrl()).pace(Duration.ofMillis(paceDurationMillis)))));
    }

    private ScenarioBuilder setUpPostAudioRequest(String scenarioName, int paceDurationMillis, int repeats) {
        return scenario(scenarioName)
        .group(scenarioName)
        .on(exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_EXTERNAL_PASSWORD.getUrl()))
                .exec(RegisterWithTokenScenario.RegisterWithToken(EnvironmentURL.DARTS_SOAP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_EXTERNAL_PASSWORD.getUrl()))
                .repeat(repeats)
                .on(exec(PostAudioRequestScenario.PostaudioRequest().pace(Duration.ofMillis(paceDurationMillis)))));
    }

    private ScenarioBuilder setUpGetAudioRequest(String scenarioName, int paceDurationMillis, int repeats) {
        return scenario(scenarioName)
        .group(scenarioName)
        .on(exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_EXTERNAL_PASSWORD.getUrl()))
                .exec(RegisterWithTokenScenario.RegisterWithToken(EnvironmentURL.DARTS_SOAP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_EXTERNAL_PASSWORD.getUrl()))
                .repeat(repeats)
                .on(exec(GetAudioRequestScenario.GetAudioRequest().pace(Duration.ofMillis(paceDurationMillis)))));
    }

    @Override
    public void after() {
        System.out.println("Simulation is finished!");
    }
}
