package simulations.Scripts.ParallelSimulations;

import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Scenario.DartsApi.DeleteAudioRequestScenario;
import simulations.Scripts.Scenario.DartsApi.GetApiTokenScenario;
import simulations.Scripts.Scenario.DartsApi.GetAudioRequestScenario;
import simulations.Scripts.Scenario.DartsApi.PostAudioRequestScenario;
import simulations.Scripts.Scenario.DartsApi.PostAudioScenario;
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

public class ScenarioTwoSimulationTest extends Simulation {

    private static final String BASELINE_SCENARIO_NAME = "Baseline - DARTS - GateWay - Soap - AddDocument:POST - Event Token";
    private static final String RAMP_UP_SCENARIO_NAME = "Ramp Up - DARTS - GateWay - Soap - AddDocument:POST - Event Token";
    private static final String SPIKE_SCENARIO_NAME = "Spike - DARTS - GateWay - Soap - AddDocument:POST - Event Token";
    private static final String BASELINE_SCENARIO_NAME2 = "Baseline - DARTS - GateWay - Soap - GetCase - User";
    private static final String RAMP_UP_SCENARIO_NAME2 = "Ramp Up - DARTS - GateWay - Soap - GetCase - User";
    private static final String SPIKE_SCENARIO_NAME2 = "Spike - DARTS - GateWay - Soap - GetCase - User";
    private static final String BASELINE_SCENARIO_NAME3 = "Baseline - DARTS - GateWay - Soap - addCase - User";
    private static final String RAMP_UP_SCENARIO_NAME3 = "Ramp Up - DARTS - GateWay - Soap - addCase - User";
    private static final String SPIKE_SCENARIO_NAME3 = "Spike - DARTS - GateWay - Soap - addCase - User";
    private static final String BASELINE_SCENARIO_NAME4 = "Baseline - DARTS - GateWay - Soap - getCourtLog - Token";
    private static final String RAMP_UP_SCENARIO_NAME4 = "Ramp Up - DARTS - GateWay - Soap - getCourtLog - Token";
    private static final String SPIKE_SCENARIO_NAME4 = "Spike - DARTS - GateWay - Soap - getCourtLog - Token";
    private static final String BASELINE_SCENARIO_NAME5 = "Baseline - DARTS - API - Post - AudioRequest"; 
    private static final String RAMP_UP_SCENARIO_NAME5 = "Ramp Up - DARTS - API - Post - AudioRequest";
    private static final String SPIKE_SCENARIO_NAME5 = "Spike - DARTS - API - Post - AudioRequest";
    private static final String BASELINE_SCENARIO_NAME6 = "Baseline - DARTS - API - Get - AudioRequest";
    private static final String RAMP_UP_SCENARIO_NAME6 = "Ramp Up - DARTS - API - Get - AudioRequest";
    private static final String SPIKE_SCENARIO_NAME6 = "Spike - DARTS - API - Get - AudioRequest";
    private static final String BASELINE_SCENARIO_NAME7 = "Baseline - DARTS - API - Delete - AudioRequest";
    private static final String RAMP_UP_SCENARIO_NAME7 = "Ramp Up - DARTS - API - Delete - AudioRequest";
    private static final String SPIKE_SCENARIO_NAME7 = "Spike - DARTS - API - Delete - AudioRequest";
    private static final String BASELINE_SCENARIO_NAME8 = "Baseline - DARTS - API - Post - Audio";
    private static final String RAMP_UP_SCENARIO_NAME8 = "Ramp Up - DARTS - API - Post - Audio";
    private static final String SPIKE_SCENARIO_NAME8 = "Spike - DARTS - API - Post - Audio";

    @Override
    public void before() {
        System.out.println("Simulation is about to start!");
    }

    public ScenarioTwoSimulationTest() {
        HttpProtocolBuilder httpProtocol = http
           //     .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
                .baseUrl(EnvironmentURL.GATEWAY_BASE_URL.getUrl())
                .inferHtmlResources()
                .acceptEncodingHeader("gzip,deflate")
                .contentTypeHeader("text/xml;charset=UTF-8")
                .userAgentHeader("Apache-HttpClient/4.5.5 (Java/16.0.2)");
        setUpScenarios(httpProtocol);

        HttpProtocolBuilder httpProtocolApi = http
        //     .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
               .baseUrl(EnvironmentURL.B2B_Login.getUrl())
               .inferHtmlResources();
     setUpScenariosApi(httpProtocolApi);
    }

    private void setUpScenarios(HttpProtocolBuilder httpProtocol) {
        // Set up scenarios with configurable parameters
        ScenarioBuilder baselineAddDocument = setUpAddDocumentEvent(BASELINE_SCENARIO_NAME, AppConfig.SMOKE_PACE_DURATION_MINS, AppConfig.SOAP_BASELINE_REPEATS);
        ScenarioBuilder rampUpAddDocument = setUpAddDocumentEvent(RAMP_UP_SCENARIO_NAME, AppConfig.SMOKE_PACE_DURATION_MINS, AppConfig.SOAP_RAMPUP_REPEATS);
        ScenarioBuilder spikeAddDocument = setUpAddDocumentEvent(SPIKE_SCENARIO_NAME, AppConfig.SMOKE_PACE_DURATION_MINS, AppConfig.SOAP_SPIKE_REPEATS);
        ScenarioBuilder baselineGetCase = setUpGetCase(BASELINE_SCENARIO_NAME2, AppConfig.SMOKE_PACE_DURATION_MINS, AppConfig.SOAP_BASELINE_REPEATS);
        ScenarioBuilder rampUpGetCase = setUpGetCase(RAMP_UP_SCENARIO_NAME2, AppConfig.SMOKE_PACE_DURATION_MINS, AppConfig.SOAP_RAMPUP_REPEATS);
        ScenarioBuilder spikeGetCase = setUpGetCase(SPIKE_SCENARIO_NAME2, AppConfig.SMOKE_PACE_DURATION_MINS, AppConfig.SOAP_SPIKE_REPEATS);
        ScenarioBuilder baselineAddCase = setUpAddCase(BASELINE_SCENARIO_NAME3, AppConfig.SMOKE_PACE_DURATION_MINS, AppConfig.SOAP_BASELINE_REPEATS);
        ScenarioBuilder rampUpAddCase = setUpAddCase(RAMP_UP_SCENARIO_NAME3, AppConfig.SMOKE_PACE_DURATION_MINS, AppConfig.SOAP_RAMPUP_REPEATS);
        ScenarioBuilder spikeAddCase = setUpAddCase(SPIKE_SCENARIO_NAME3, AppConfig.SMOKE_PACE_DURATION_MINS, AppConfig.SOAP_SPIKE_REPEATS);
        ScenarioBuilder baselineGetCourtLog = setUpGetCourtLog(BASELINE_SCENARIO_NAME4, AppConfig.SMOKE_PACE_DURATION_MINS, AppConfig.SOAP_BASELINE_REPEATS);
        ScenarioBuilder rampUpGetCourtLog = setUpGetCourtLog(RAMP_UP_SCENARIO_NAME4, AppConfig.SMOKE_PACE_DURATION_MINS, AppConfig.SOAP_RAMPUP_REPEATS);
        ScenarioBuilder spikeGetCourtLog = setUpGetCourtLog(SPIKE_SCENARIO_NAME4, AppConfig.SMOKE_PACE_DURATION_MINS, AppConfig.SOAP_SPIKE_REPEATS);

        // Call setUp once with all scenarios
        setUp(
            baselineAddDocument.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.SMOKE_PACE_DURATION_MINS))).protocols(httpProtocol)
                        .andThen(rampUpAddDocument.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.SMOKE_PACE_DURATION_MINS))).protocols(httpProtocol))
                        .andThen(spikeAddDocument.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.SMOKE_PACE_DURATION_MINS))).protocols(httpProtocol)),
                        baselineGetCase.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.SMOKE_PACE_DURATION_MINS))).protocols(httpProtocol)
                        .andThen(rampUpGetCase.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.SMOKE_PACE_DURATION_MINS))).protocols(httpProtocol))
                        .andThen(spikeGetCase.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.SMOKE_PACE_DURATION_MINS))).protocols(httpProtocol)),
                        baselineAddCase.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.SMOKE_PACE_DURATION_MINS))).protocols(httpProtocol)
                        .andThen(rampUpAddCase.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.SMOKE_PACE_DURATION_MINS))).protocols(httpProtocol))
                        .andThen(spikeAddCase.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.SMOKE_PACE_DURATION_MINS))).protocols(httpProtocol)),
                        baselineGetCourtLog.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.SMOKE_PACE_DURATION_MINS))).protocols(httpProtocol)
                        .andThen(rampUpGetCourtLog.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.SMOKE_PACE_DURATION_MINS))).protocols(httpProtocol))
                        .andThen(spikeGetCourtLog.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.SMOKE_PACE_DURATION_MINS))).protocols(httpProtocol))
        );
    }

    private void setUpScenariosApi(HttpProtocolBuilder httpProtocolApi) {
        // Set up scenarios with configurable parameters
        ScenarioBuilder baselinePostAudioRequest = setUpPostAudioRequest(BASELINE_SCENARIO_NAME5, AppConfig.SMOKE_PACE_DURATION_MINS, AppConfig.SOAP_BASELINE_REPEATS);
        ScenarioBuilder rampUpPostAudioRequest = setUpPostAudioRequest(RAMP_UP_SCENARIO_NAME5, AppConfig.SMOKE_PACE_DURATION_MINS, AppConfig.SOAP_RAMPUP_REPEATS);
        ScenarioBuilder spikePostAudioRequest = setUpPostAudioRequest(SPIKE_SCENARIO_NAME5, AppConfig.SMOKE_PACE_DURATION_MINS, AppConfig.SOAP_SPIKE_REPEATS);
        ScenarioBuilder baselineGetAudioRequest = setUpGetAudioRequest(BASELINE_SCENARIO_NAME6, AppConfig.SMOKE_PACE_DURATION_MINS, AppConfig.SOAP_BASELINE_REPEATS);
        ScenarioBuilder rampUpGetAudioRequest = setUpGetAudioRequest(RAMP_UP_SCENARIO_NAME6, AppConfig.SMOKE_PACE_DURATION_MINS, AppConfig.SOAP_RAMPUP_REPEATS);
        ScenarioBuilder spikeGetAudioRequest = setUpGetAudioRequest(SPIKE_SCENARIO_NAME6, AppConfig.SMOKE_PACE_DURATION_MINS, AppConfig.SOAP_SPIKE_REPEATS);
        ScenarioBuilder baselineDeleteAudioRequest = setUpDeleteAudioRequest(BASELINE_SCENARIO_NAME7, AppConfig.SMOKE_PACE_DURATION_MINS, AppConfig.SOAP_BASELINE_REPEATS);
        ScenarioBuilder rampUpDeleteAudioRequest = setUpDeleteAudioRequest(RAMP_UP_SCENARIO_NAME7, AppConfig.SMOKE_PACE_DURATION_MINS, AppConfig.SOAP_RAMPUP_REPEATS);
        ScenarioBuilder spikeDeleteAudioRequest = setUpDeleteAudioRequest(SPIKE_SCENARIO_NAME7, AppConfig.SMOKE_PACE_DURATION_MINS, AppConfig.SOAP_SPIKE_REPEATS);
        ScenarioBuilder baselinePostAudio = setUpPostAudio(BASELINE_SCENARIO_NAME8, AppConfig.SMOKE_PACE_DURATION_MINS, AppConfig.SOAP_BASELINE_REPEATS);
        ScenarioBuilder rampUpPostAudio = setUpPostAudio(RAMP_UP_SCENARIO_NAME8, AppConfig.SMOKE_PACE_DURATION_MINS, AppConfig.SOAP_RAMPUP_REPEATS);
        ScenarioBuilder spikePostAudio = setUpPostAudio(SPIKE_SCENARIO_NAME8, AppConfig.SMOKE_PACE_DURATION_MINS, AppConfig.SOAP_SPIKE_REPEATS);

        // Call setUp once with all scenarios
         setUp(
            baselinePostAudioRequest.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.SMOKE_PACE_DURATION_MINS))).protocols(httpProtocolApi)
                    .andThen(rampUpPostAudioRequest.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.SMOKE_PACE_DURATION_MINS))).protocols(httpProtocolApi))
                    .andThen(spikePostAudioRequest.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.SMOKE_PACE_DURATION_MINS))).protocols(httpProtocolApi)),
                    baselineGetAudioRequest.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.SMOKE_PACE_DURATION_MINS))).protocols(httpProtocolApi)
                    .andThen(rampUpGetAudioRequest.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.SMOKE_PACE_DURATION_MINS))).protocols(httpProtocolApi))
                    .andThen(spikeGetAudioRequest.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.SMOKE_PACE_DURATION_MINS))).protocols(httpProtocolApi)),
                    baselineDeleteAudioRequest.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.SMOKE_PACE_DURATION_MINS))).protocols(httpProtocolApi)
                    .andThen(rampUpDeleteAudioRequest.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.SMOKE_PACE_DURATION_MINS))).protocols(httpProtocolApi))
                    .andThen(spikeDeleteAudioRequest.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.SMOKE_PACE_DURATION_MINS))).protocols(httpProtocolApi)),
                    baselinePostAudio.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.SMOKE_PACE_DURATION_MINS))).protocols(httpProtocolApi)
                    .andThen(rampUpPostAudio.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.SMOKE_PACE_DURATION_MINS))).protocols(httpProtocolApi))
                    .andThen(spikePostAudio.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.SMOKE_PACE_DURATION_MINS))).protocols(httpProtocolApi))
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

    private ScenarioBuilder setUpGetCase(String scenarioName, int paceDurationMillis, int repeats) {
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

    private ScenarioBuilder setUpGetCourtLog(String scenarioName, int paceDurationMillis, int repeats) {
        return scenario(scenarioName)
        .group(scenarioName)
        .on(exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_EXTERNAL_PASSWORD.getUrl()))
                .exec(RegisterWithTokenScenario.RegisterWithToken(EnvironmentURL.DARTS_SOAP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_EXTERNAL_PASSWORD.getUrl()))
                .repeat(repeats)
                .on(exec(GetCourtlogTokenScenario.getCourtLogToken().pace(Duration.ofMillis(paceDurationMillis)))));
    }

    private ScenarioBuilder setUpPostAudioRequest(String scenarioName, int paceDurationMillis, int repeats) {
        return scenario(scenarioName)        
        .group(scenarioName)
            .on(exec(GetApiTokenScenario.getApiToken()))
                .repeat(repeats)
                .on(exec(PostAudioRequestScenario.PostaudioRequest().pace(Duration.ofMillis(paceDurationMillis))));
    }

    private ScenarioBuilder setUpGetAudioRequest(String scenarioName, int paceDurationMillis, int repeats) {
        return scenario(scenarioName)        
        .group(scenarioName)
            .on(exec(GetApiTokenScenario.getApiToken()))
                .repeat(repeats)
                .on(exec(GetAudioRequestScenario.GetAudioRequest().pace(Duration.ofMillis(paceDurationMillis))));
    }

    private ScenarioBuilder setUpDeleteAudioRequest(String scenarioName, int paceDurationMillis, int repeats) {
        return scenario(scenarioName)        
        .group(scenarioName)
            .on(exec(GetApiTokenScenario.getApiToken()))
                .repeat(repeats)
                .on(exec(DeleteAudioRequestScenario.DeleteAudioRequest().pace(Duration.ofMillis(paceDurationMillis))));
    }

    private ScenarioBuilder setUpPostAudio(String scenarioName, int paceDurationMillis, int repeats) {
        return scenario(scenarioName)        
        .group(scenarioName)
            .on(exec(GetApiTokenScenario.getApiToken()))
                .repeat(repeats)
                .on(exec(PostAudioScenario.PostApiAudio().pace(Duration.ofMillis(paceDurationMillis))));
    }

    @Override
    public void after() {
        System.out.println("Simulation is finished!");
    }
}
