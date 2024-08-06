package simulations.Scripts.DartsSoap;

import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Scenario.DartsSoap.AddAudioTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.RegisterWithTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.RegisterWithUsernameScenario;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class AddAudioTokenSimulation extends Simulation {


  private static final String BASELINE_SCENARIO_NAME = "BaseLine - DARTS - GateWay - Soap - AddAudio:POST";
  private static final String RAMP_UP_SCENARIO_NAME = "Ramp Up - DARTS - GateWay - Soap - AddAudio:POST";
  private static final String SPIKE_SCENARIO_NAME = "Spike - DARTS - GateWay - Soap - AddAudio:POST";

  public AddAudioTokenSimulation() {
      HttpProtocolBuilder httpProtocol = http
      .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
      .baseUrl(EnvironmentURL.PROXY_BASE_URL.getUrl())
      .inferHtmlResources();

      setUpScenarios(httpProtocol);
  }

  private void setUpScenarios(HttpProtocolBuilder httpProtocol) {
      // Set up scenarios with configurable parameters
      ScenarioBuilder baselineScenario = setUpScenario(BASELINE_SCENARIO_NAME, AppConfig.SMOKE_PACE_DURATION_MINS, 1);
      ScenarioBuilder rampUpScenario = setUpScenario(RAMP_UP_SCENARIO_NAME, AppConfig.BASELINE_NORMAL_PACE_DURATION_MINS, 1);
      ScenarioBuilder spikeScenario = setUpScenario(SPIKE_SCENARIO_NAME, AppConfig.PEAK_PACE_DURATION_MINS, 1);

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
          .exec(RegisterWithTokenScenario.RegisterWithToken(EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_PASSWORD.getUrl()))
          .repeat(repeats)
          .on(exec(AddAudioTokenScenario.addAudioToken().pace(Duration.ofMillis(paceDurationMillis)))));
  }
}
