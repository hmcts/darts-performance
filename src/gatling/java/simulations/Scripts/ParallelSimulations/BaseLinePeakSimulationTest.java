package simulations.Scripts.ParallelSimulations;

import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Scenario.DartsApi.GetAudioRequestScenario;
import simulations.Scripts.Scenario.DartsApi.PostAudioRequestScenario;
import simulations.Scripts.Scenario.DartsApi.PostAudioScenario;
import simulations.Scripts.Scenario.DartsSoap.AddAudioTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.AddCaseUserScenario;
import simulations.Scripts.Scenario.DartsSoap.AddCourtlogUserScenario;
import simulations.Scripts.Scenario.DartsSoap.AddDocumentEventTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.GetCasesTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.GetCasesUserScenario;
import simulations.Scripts.Scenario.DartsSoap.GetCourtlogTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.RegisterWithTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.RegisterWithUsernameScenario;
import simulations.Scripts.DartsSoap.AddAudioTokenSimulation;
import simulations.Scripts.DartsSoap.AddCourtLogSOAPUserSimulation;
import simulations.Scripts.Scenario.DartsApi.DeleteAudioRequestScenario;
import simulations.Scripts.Scenario.DartsApi.GetApiTokenScenario;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import java.time.Duration;

public class BaseLinePeakSimulationTest extends Simulation {

    private static final String PEAK_ADD_DOCUMENT_EVENTS = "Baseline - Peak - DARTS - GateWay - Soap - AddDocument:POST - Event Token";
    private static final String PEAK_GET_CASES = "Baseline - Peak - DARTS - GateWay - Soap - GetCase - User";
    private static final String PEAK_ADD_CASES = "Baseline - Peak - DARTS - GateWay - Soap - addCase - User";
    private static final String PEAK_ADD_LOG_ENTRY = "Baseline - Peak - DARTS - GateWay - Soap - addLogEntry - User";
    private static final String PEAK_GET_LOG_ENTRY = "Baseline - Peak - DARTS - GateWay - Soap - Get Court Log - User";
    private static final String PEAK_ADD_AUDIO = "Baseline - Peak - DARTS - GateWay - Soap - AddAudio - Token";

    private static final String PEAK_POST_AUDIO_REQUEST = "Baseline - Peak - DARTS - Api - POST Audio Request";
    private static final String PEAK_GET_AUDIO_REQUEST = "Baseline - Peak - DARTS - Api - GET Audio Request";
    private static final String PEAK_DELETE_AUDIO_REQUEST = "Baseline - Peak - DARTS - Api - DELETE Audio Request";
    //private static final String PEAK_POST_AUDIO = "Baseline - Peak - DARTS - Api - POST Audio - User";

    @Override
    public void before() {
        System.out.println("Simulation is about to start!");
    }

    public BaseLinePeakSimulationTest() {
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
        ScenarioBuilder baselineAddDocumentEvent = setUpAddDocumentEvent(PEAK_ADD_DOCUMENT_EVENTS, AppConfig.PEAK_PACE_DURATION_MINS, AppConfig.EVENTS_PEAK_REPEATS);
        ScenarioBuilder baselineGetCases = setUpGetCases(PEAK_GET_CASES, AppConfig.PEAK_PACE_DURATION_MINS, AppConfig.GET_CASES_PEAK_REPEATS);
        ScenarioBuilder baselineAddCase = setUpAddCase(PEAK_ADD_CASES, AppConfig.PEAK_PACE_DURATION_MINS, AppConfig.ADD_CASES_PEAK_REPEATS);
        ScenarioBuilder baselineAddLogEntry = setUpAddLogEntry(PEAK_ADD_LOG_ENTRY, AppConfig.PEAK_PACE_DURATION_MINS, AppConfig.ADD_LOG_ENTRY_PEAK_REPEATS);
        ScenarioBuilder baselineGetLogEntry = setUpGetLogEntry(PEAK_GET_LOG_ENTRY, AppConfig.PEAK_PACE_DURATION_MINS, AppConfig.GET_LOG_ENTRY_PEAK_REPEATS);
        ScenarioBuilder baselineAddAudio = setUpAddAudio(PEAK_ADD_AUDIO, AppConfig.PEAK_PACE_DURATION_MINS, AppConfig.ADD_AUDIO_PEAK_REPEATS);

        // Set up API scenarios with configurable parameters
        ScenarioBuilder baselinePostAudioRequest = setUpPostAudioRequest(PEAK_POST_AUDIO_REQUEST, AppConfig.PEAK_PACE_DURATION_MINS, AppConfig.POST_AUDIO_REQUEST_PEAK_REPEATS);
        ScenarioBuilder baselineGetAudioRequest = setUpGetAudioRequest(PEAK_GET_AUDIO_REQUEST, AppConfig.PEAK_PACE_DURATION_MINS, AppConfig.GET_AUDIO_REQUEST_PEAK_REPEATS);
        ScenarioBuilder baselineDeleteAudioRequest = setUpDeleteAudioRequest(PEAK_DELETE_AUDIO_REQUEST, AppConfig.PEAK_PACE_DURATION_MINS, AppConfig.DELETE_AUDIO_REQUEST_PEAK_REPEATS);
        //ScenarioBuilder baselinePostAudio = setUpPostAudio(PEAK_POST_AUDIO, AppConfig.PEAK_PACE_DURATION_MINS, AppConfig.POST_AUDIO_PEAK_REPEATS);

        // Call setUp once with all scenarios
        setUp(
                baselineAddDocumentEvent.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.PEAK_PACE_DURATION_MINS))).protocols(httpProtocolSoap),
                baselineGetCases.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.PEAK_PACE_DURATION_MINS))).protocols(httpProtocolSoap),
                baselineAddCase.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.PEAK_PACE_DURATION_MINS))).protocols(httpProtocolSoap),
                baselinePostAudioRequest.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.PEAK_PACE_DURATION_MINS))).protocols(httpProtocolApi),
                baselineGetAudioRequest.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.PEAK_PACE_DURATION_MINS))).protocols(httpProtocolApi),
                baselineDeleteAudioRequest.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.PEAK_PACE_DURATION_MINS))).protocols(httpProtocolApi),
                baselineAddLogEntry.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.PEAK_PACE_DURATION_MINS))).protocols(httpProtocolSoap),
                baselineGetLogEntry.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.PEAK_PACE_DURATION_MINS))).protocols(httpProtocolSoap),
                baselineAddAudio.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.PEAK_PACE_DURATION_MINS))).protocols(httpProtocolSoap)
                //baselinePostAudio.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.PEAK_PACE_DURATION_MINS))).protocols(httpProtocolApi)
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

    private ScenarioBuilder setUpAddLogEntry(String scenarioName, int paceDurationMillis, int repeats) {
        return scenario(scenarioName)
        .group(scenarioName)
        .on(exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_EXTERNAL_PASSWORD.getUrl()))
                .exec(RegisterWithTokenScenario.RegisterWithToken(EnvironmentURL.DARTS_SOAP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_EXTERNAL_PASSWORD.getUrl()))
                .repeat(repeats)
                .on(exec(AddCourtlogUserScenario.addCourtLogUser(EnvironmentURL.DARTS_SOAP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_EXTERNAL_PASSWORD.getUrl()).pace(Duration.ofMillis(paceDurationMillis)))));
    }

    private ScenarioBuilder setUpGetLogEntry(String scenarioName, int paceDurationMillis, int repeats) {
        return scenario(scenarioName)
        .group(scenarioName)
        .on(exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_EXTERNAL_PASSWORD.getUrl()))
                .exec(RegisterWithTokenScenario.RegisterWithToken(EnvironmentURL.DARTS_SOAP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_EXTERNAL_PASSWORD.getUrl()))
                .repeat(repeats)
                .on(exec(GetCourtlogTokenScenario.getCourtLogToken().pace(Duration.ofMillis(paceDurationMillis)))));
    }

     private ScenarioBuilder setUpAddAudio(String scenarioName, int paceDurationMillis, int repeats) {
        return scenario(scenarioName)
        .group(scenarioName)
        .on(exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_EXTERNAL_PASSWORD.getUrl()))
                .exec(RegisterWithTokenScenario.RegisterWithToken(EnvironmentURL.DARTS_SOAP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_EXTERNAL_PASSWORD.getUrl()))
                .repeat(repeats)
                .on(exec(AddAudioTokenScenario.addAudioToken().pace(Duration.ofMillis(paceDurationMillis)))));
    }

    private ScenarioBuilder setUpPostAudioRequest(String scenarioName, int paceDurationMillis, int repeats) {
        return scenario(scenarioName)
        .group(scenarioName)
            .on(exec(GetApiTokenScenario.getApiToken())
                .repeat(repeats)
                .on(exec(PostAudioRequestScenario.PostaudioRequest().pace(Duration.ofMillis(paceDurationMillis)))));
    }

    private ScenarioBuilder setUpGetAudioRequest(String scenarioName, int paceDurationMillis, int repeats) {
        return scenario(scenarioName)
        .group(scenarioName)
            .on(exec(GetApiTokenScenario.getApiToken())
                .repeat(repeats).on(
                    uniformRandomSwitch().on(
                        exec(GetAudioRequestScenario.GetAudioRequestDownload()),
                        exec(GetAudioRequestScenario.GetAudioRequestPlayBack())
                    )
                ).pace(Duration.ofMillis(paceDurationMillis))
            );
    }

    private ScenarioBuilder setUpDeleteAudioRequest(String scenarioName, int paceDurationMillis, int repeats) {
        return scenario(scenarioName)
        .group(scenarioName)
            .on(exec(GetApiTokenScenario.getApiToken())
                .repeat(repeats)
                .on(exec(DeleteAudioRequestScenario.DeleteAudioRequest().pace(Duration.ofMillis(paceDurationMillis)))));
    }

    // private ScenarioBuilder setUpPostAudio(String scenarioName, int paceDurationMillis, int repeats) {
    //     return scenario(scenarioName)
    //     .group(scenarioName)
    //         .on(exec(GetApiTokenScenario.getApiToken())
    //             .repeat(repeats)
    //             .on(exec(PostAudioScenario.PostApiAudio().pace(Duration.ofMillis(paceDurationMillis)))));
    // }

    @Override
    public void after() {
        System.out.println("Simulation is finished!");
    }
}
