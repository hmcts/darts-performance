package simulations.Scripts.DartsSoap;

import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Scenario.DartsSoap.AddAudioUserScenario;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import java.util.UUID;

public class AddAudioUserSimulation extends Simulation {

  String boundary = UUID.randomUUID().toString(); 
  private static final String BASELINE_SCENARIO_NAME = "DARTS - GateWay - Soap - AddAudio:POST";
  private static final String RAMP_UP_SCENARIO_NAME = "Ramp Up Test";
  private static final String SPIKE_SCENARIO_NAME = "Spike Test";

  public AddAudioUserSimulation() {
      HttpProtocolBuilder httpProtocol = http
      .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
      .baseUrl(EnvironmentURL.PROXY_BASE_URL.getUrl())
      .inferHtmlResources()
      .acceptEncodingHeader("gzip,deflate")
      .contentTypeHeader("multipart/related; type=\"application/xop+xml\"; start=\"<rootpart@soapui.org>\"; start-info=\"text/xml\"; boundary=" + boundary)
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
        .on(repeat(1)
          .on(exec(AddAudioUserScenario.addAudioUser(EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_VIQ_EXTERNAL_PASSWORD.getUrl()).pace(Duration.ofMillis(paceDurationMillis))
          // exec(AddAudioUserScenario.addAudioUserBinary(EnvironmentURL.DARTS_SOAP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_EXTERNAL_PASSWORD.getUrl()).pace(Duration.ofMillis(paceDurationMillis)))
        )));
  }
}
