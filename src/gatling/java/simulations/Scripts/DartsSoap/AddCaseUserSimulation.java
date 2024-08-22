package simulations.Scripts.DartsSoap;

import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Scenario.DartsSoap.AddCaseUserScenario;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import java.time.Duration;
public class AddCaseUserSimulation extends Simulation {

  
  private static final String BASELINE_SCENARIO_NAME = "DARTS - GateWay - Soap - AddCase:POST";
  private static final String RAMP_UP_SCENARIO_NAME = "Ramp Up Test";
  private static final String SPIKE_SCENARIO_NAME = "Spike Test";

  public AddCaseUserSimulation() {
      HttpProtocolBuilder httpProtocol = http
          .baseUrl(EnvironmentURL.PROXY_BASE_URL.getUrl())
          .inferHtmlResources();

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
      .on(repeat(1)
          .on(exec(AddCaseUserScenario.addCaseUser(EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_PASSWORD.getUrl()).pace(Duration.ofMillis(paceDurationMillis)))));
  }
}
