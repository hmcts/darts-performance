package simulations.Scripts.DartsSoap;

import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Scenario.DartsSoap.AddDocumentEventTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.RegisterWithTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.RegisterWithUsernameScenario;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;


public class AddDocumentTokenSimulation extends Simulation {

    private static final String BASELINE_SCENARIO_NAME = "DARTS - GateWay - Soap - AddDocument:POST - Event Token";
    private static final String RAMP_UP_SCENARIO_NAME = "Ramp Up Test";
    private static final String SPIKE_SCENARIO_NAME = "Spike Test";

    @Override
    public void before() {
      System.out.println("Simulation is about to start!");
    }

    public AddDocumentTokenSimulation() {
        HttpProtocolBuilder httpProtocol = http
            .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
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
    
    // Call setUp once with all scenarios
    setUp(
        baselineScenario.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.BASELINE_TEST_DURATION_MINUTES))).protocols(httpProtocol)
        .andThen(rampUpScenario.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.RAMP_TEST_DURATION_MINUTES))).protocols(httpProtocol))
        .andThen(spikeScenario.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.SPIKE_TEST_DURATION_MINUTES))).protocols(httpProtocol))
        );    
    }

    private ScenarioBuilder setUpScenario(String scenarioName, int paceDurationMillis, int repeats) {
    return scenario(scenarioName)
        .exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_EXTERNAL_PASSWORD.getUrl()))
        .exec(RegisterWithTokenScenario.RegisterWithToken(EnvironmentURL.DARTS_SOAP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_EXTERNAL_PASSWORD.getUrl()))
        .repeat(repeats)
        .on(            
            doIf(session -> session.get("messageId").equals("E_UNKNOWN_TOKEN"))
                .then(
                    exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_EXTERNAL_PASSWORD.getUrl()))
                        .exec(RegisterWithTokenScenario.RegisterWithToken(EnvironmentURL.DARTS_SOAP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_EXTERNAL_PASSWORD.getUrl()))
                )
        
        .exec(AddDocumentEventTokenScenario.AddDocumentEventToken().pace(Duration.ofMillis(paceDurationMillis))));
    }
    @Override
    public void after() {
    System.out.println("Simulation is finished!");
    }
}
