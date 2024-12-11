package simulations.Scripts.DartsSoap;

import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Scenario.DartsSoap.AddCaseTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.RegisterWithTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.RegisterWithUsernameScenario;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class AddCaseTokenSimulation extends Simulation {

  private static final String BASELINE_SCENARIO_NAME = "DARTS - GateWay - Soap - AddCase:POST";
  private static final String RAMP_UP_SCENARIO_NAME = "Ramp Up Test";
  private static final String SPIKE_SCENARIO_NAME = "Spike Test";

  public AddCaseTokenSimulation() {
      HttpProtocolBuilder httpProtocol = http        
          .baseUrl(EnvironmentURL.PROXY_BASE_URL.getUrl())
          .inferHtmlResources()
          .acceptEncodingHeader("gzip,deflate")
          .contentTypeHeader("text/xml;charset=UTF-8")
          .userAgentHeader("Apache-HttpClient/4.5.5 (Java/16.0.2)");

      setUpScenarios(httpProtocol);
  }

  private void setUpScenarios(HttpProtocolBuilder httpProtocol) {
    // Set up scenarios with configurable parameters
    ScenarioBuilder baselineScenario = setUpScenario(BASELINE_SCENARIO_NAME, AppConfig.SMOKE_PACE_DURATION_MINS, AppConfig.SOAP_SMOKE_REPEATS);
    ScenarioBuilder rampUpScenario = setUpScenario(RAMP_UP_SCENARIO_NAME, AppConfig.BASELINE_NORMAL_PACE_DURATION_MINS, AppConfig.SOAP_BASELINE_NORMAL_REPEATS);
    ScenarioBuilder spikeScenario = setUpScenario(SPIKE_SCENARIO_NAME, AppConfig.PEAK_PACE_DURATION_MINS, AppConfig.SOAP_BASELINE_PEAK_REPEATS);

    // Call setUp once with all scenarios
    setUp(
        baselineScenario.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.SMOKE_PACE_DURATION_MINS))).protocols(httpProtocol)
        .andThen(rampUpScenario.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.BASELINE_NORMAL_PACE_DURATION_MINS))).protocols(httpProtocol))
        .andThen(spikeScenario.injectOpen(rampUsers(AppConfig.USERS_PER_SECOND).during(Duration.ofMinutes(AppConfig.PEAK_PACE_DURATION_MINS))).protocols(httpProtocol))
    );
}

  private ScenarioBuilder setUpScenario(String scenarioName, int paceDurationMillis, int repeats) {
      return scenario(scenarioName)
      .group(scenarioName)
      .on(exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_PASSWORD.getUrl()))
          .exec(RegisterWithTokenScenario.registerWithToken(EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_PASSWORD.getUrl()))
          .repeat(1)
          .on(exec(AddCaseTokenScenario.addCaseToken().pace(Duration.ofMillis(paceDurationMillis)))));
  }
}
