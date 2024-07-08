package simulations.Scripts.DartsSoap;

import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Scenario.DartsSoap.AddDocumentEventTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.RegisterWithTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.RegisterWithUsernameScenario;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class AddDocumentEventTokenSimulation extends Simulation {

    private static final String BASELINE_SCENARIO_NAME = "DARTS - GateWay - Soap - AddDocument:POST - Event Token";


    @Override
    public void before() {
      System.out.println("Simulation is about to start!");
    }

    public AddDocumentEventTokenSimulation() {
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
        ScenarioBuilder baselineScenario = setUpScenario(BASELINE_SCENARIO_NAME);

        // Call setUp once with all scenarios
        setUp(
            baselineScenario.injectOpen(rampUsers(10).during(1))).protocols(httpProtocol);    
    }

    private ScenarioBuilder setUpScenario(String scenarioName) {
        return scenario(scenarioName)
            .group(scenarioName)
            .on(exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_PASSWORD.getUrl()))
            .exec(RegisterWithTokenScenario.RegisterWithToken(EnvironmentURL.DARTS_SOAP_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_PASSWORD.getUrl()))
            .repeat(100)
            .on(exec(AddDocumentEventTokenScenario.AddDocumentEventToken())));
    }
}
