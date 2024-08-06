package simulations.Scripts.ParallelSimulations;

import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Scenario.DartsApi.GetAudioRequestScenario;
import simulations.Scripts.Scenario.DartsApi.PostAudioRequestScenario;
import simulations.Scripts.Scenario.DartsSoap.AddCaseUserScenario;
import simulations.Scripts.Scenario.DartsSoap.AddDocumentEventTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.GetCasesUserScenario;
import simulations.Scripts.Scenario.DartsSoap.RegisterWithTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.RegisterWithUsernameScenario;
import simulations.Scripts.Scenario.DartsApi.GetApiTokenScenario;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import java.time.Duration;

public class OneSmokeSimulationTest extends Simulation {

    private static final String SMOKE_ADD_DOCUMENT_EVENTS = "Smoke - DARTS - GateWay - Soap - AddDocument:POST - Event Token";
    private static final String SMOKE_GET_CASES = "Smoke - DARTS - GateWay - Soap - GetCase - User";
    private static final String SMOKE_ADD_CASES = "Smoke - DARTS - GateWay - Soap - addCase - User";
    private static final String SMOKE_POST_AUDIO_REQUEST = "Smoke - DARTS - Api - POST Audio Request";
    private static final String SMOKE_GET_AUDIO_REQUEST = "Smoke - DARTS - Api - GET Audio Request";

    private static final int TOTAL_TEST_DURATION_MINUTES = 60;
    private static final int TOTAL_TEST_DURATION_MILLIS = TOTAL_TEST_DURATION_MINUTES * 60 * 1000;

    @Override
    public void before() {
        System.out.println("Simulation is about to start!");
    }

    public OneSmokeSimulationTest() {
        HttpProtocolBuilder httpProtocolSoap = http
                .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
                .inferHtmlResources()
                .acceptEncodingHeader("gzip,deflate")
                .contentTypeHeader("text/xml;charset=UTF-8")
                .userAgentHeader("Apache-HttpClient/4.5.5 (Java/16.0.2)")
                .baseUrl(EnvironmentURL.GATEWAY_BASE_URL.getUrl());

        HttpProtocolBuilder httpProtocolApi = http
                .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
                .inferHtmlResources()
                .baseUrl(EnvironmentURL.B2B_Login.getUrl());

        setUpScenarios(httpProtocolSoap, httpProtocolApi);
    }

    private void setUpScenarios(HttpProtocolBuilder httpProtocolSoap, HttpProtocolBuilder httpProtocolApi) {
        // Set up SOAP scenarios with configurable parameters
        ScenarioBuilder baselineAddDocumentEvent = setUpAddDocumentEvent(SMOKE_ADD_DOCUMENT_EVENTS, calculatePaceDuration(AppConfig.EVENTS_SMOKE_REPEATS), AppConfig.EVENTS_SMOKE_REPEATS);
        ScenarioBuilder baselineGetCases = setUpGetCases(SMOKE_GET_CASES, calculatePaceDuration(AppConfig.GET_CASES_SMOKE_REPEATS), AppConfig.GET_CASES_SMOKE_REPEATS);
        ScenarioBuilder baselineAddCase = setUpAddCase(SMOKE_ADD_CASES, calculatePaceDuration(AppConfig.ADD_CASES_SMOKE_REPEATS), AppConfig.ADD_CASES_SMOKE_REPEATS);

        // Set up API scenarios with configurable parameters
        ScenarioBuilder baselinePostAudioRequest = setUpPostAudioRequest(SMOKE_POST_AUDIO_REQUEST, calculatePaceDuration(AppConfig.POST_AUDIO_REQUEST_SMOKE_REPEATS), AppConfig.POST_AUDIO_REQUEST_SMOKE_REPEATS);
       // ScenarioBuilder baselineGetAudioRequest = setUpGetAudioRequest(SMOKE_GET_AUDIO_REQUEST, calculatePaceDuration(AppConfig.GET_AUDIO_REQUEST_SMOKE_REPEATS), AppConfig.GET_AUDIO_REQUEST_SMOKE_REPEATS);

        // Call setUp once with all scenarios
        setUp(
                baselineAddDocumentEvent.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(TOTAL_TEST_DURATION_MINUTES))).protocols(httpProtocolSoap),
                baselineGetCases.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(TOTAL_TEST_DURATION_MINUTES))).protocols(httpProtocolSoap),
                baselineAddCase.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(TOTAL_TEST_DURATION_MINUTES))).protocols(httpProtocolSoap),
                baselinePostAudioRequest.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(TOTAL_TEST_DURATION_MINUTES))).protocols(httpProtocolApi)
        //        baselineGetAudioRequest.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(TOTAL_TEST_DURATION_MINUTES))).protocols(httpProtocolApi)
        );
    }

    private int calculatePaceDuration(int repeats) {
        return TOTAL_TEST_DURATION_MILLIS / repeats;
    }

    private ScenarioBuilder setUpAddDocumentEvent(String scenarioName, int paceDurationMillis, int repeats) {
        return scenario(scenarioName)
                .group(scenarioName)
                .on(exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_PASSWORD.getUrl()))
                        .exec(RegisterWithTokenScenario.RegisterWithToken(EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_PASSWORD.getUrl()))
                        .repeat(repeats)
                        .on(exec(AddDocumentEventTokenScenario.AddDocumentEventToken()).pace(Duration.ofMillis(paceDurationMillis))));
    }

    private ScenarioBuilder setUpGetCases(String scenarioName, int paceDurationMillis, int repeats) {
        return scenario(scenarioName)
                .group(scenarioName)
                .on(exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_EXTERNAL_PASSWORD.getUrl()))
                        .exec(RegisterWithTokenScenario.RegisterWithToken(EnvironmentURL.DARTS_SOAP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_EXTERNAL_PASSWORD.getUrl()))
                        .repeat(repeats)
                        .on(exec(GetCasesUserScenario.GetCaseSOAPUser(EnvironmentURL.DARTS_SOAP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_EXTERNAL_PASSWORD.getUrl())).pace(Duration.ofMillis(paceDurationMillis))));
    }

    private ScenarioBuilder setUpAddCase(String scenarioName, int paceDurationMillis, int repeats) {
        return scenario(scenarioName)
                .group(scenarioName)
                .on(exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_EXTERNAL_PASSWORD.getUrl()))
                        .exec(RegisterWithTokenScenario.RegisterWithToken(EnvironmentURL.DARTS_SOAP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_EXTERNAL_PASSWORD.getUrl()))
                        .repeat(repeats)
                        .on(exec(AddCaseUserScenario.addCaseUser(EnvironmentURL.DARTS_SOAP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_EXTERNAL_PASSWORD.getUrl())).pace(Duration.ofMillis(paceDurationMillis))));
    }

    private ScenarioBuilder setUpPostAudioRequest(String scenarioName, int paceDurationMillis, int repeats) {
        return scenario(scenarioName)
                .group(scenarioName)
                .on(exec(GetApiTokenScenario.getApiToken())
                        .repeat(repeats)
                        .on(exec(PostAudioRequestScenario.PostaudioRequest()).pace(Duration.ofMillis(paceDurationMillis))));
    }

//     private ScenarioBuilder setUpGetAudioRequest(String scenarioName, int paceDurationMillis, int repeats) {
//         return scenario(scenarioName)
//                 .group(scenarioName)
//                 .on(exec(GetApiTokenScenario.getApiToken())
//                         .repeat(repeats).on(
//                                 uniformRandomSwitch().on(
//                                         exec(GetAudioRequestScenario.GetAudioRequestDownload()),
//                                         exec(GetAudioRequestScenario.GetAudioRequestPlayBack())
//                                 )
//                         ).pace(Duration.ofMillis(paceDurationMillis))
//                 );
//     }

    @Override
    public void after() {
        System.out.println("Simulation is finished!");
    }
}
