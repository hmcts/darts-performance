package simulations.Scripts.DartsApi;

import simulations.Scripts.Scenario.DartsApi.GetApiTokenScenario;
import simulations.Scripts.Scenario.DartsApi.PostAudioRequestScenario;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import java.time.Duration;

public class AudioRequestRAMPUPTest1 extends Simulation {  

    private static final String RAMP_UP_SCENARIO_NAME = "Ramp Up Test - DARTS - API - Audio-request:POST";
  
    public AudioRequestRAMPUPTest1() {

        HttpProtocolBuilder httpProtocol = http
        .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
        .baseUrl(EnvironmentURL.B2B_Login.getUrl())
        .inferHtmlResources();
  
        setUpScenarios(httpProtocol);
    }

    private void setUpScenarios(HttpProtocolBuilder httpProtocol) {
        // Set up scenarios with configurable parameters
        ScenarioBuilder rampUpScenario = setUpRampUpScenario(RAMP_UP_SCENARIO_NAME, 95, Duration.ofHours(1)); // New Ramp Up Scenario with 95 requests
  
        // Call setUp once with all scenarios
        setUp(
            rampUpScenario.injectOpen(atOnceUsers(1)).protocols(httpProtocol)) // Injecting 1 user            
        ;
    }


    private ScenarioBuilder setUpRampUpScenario(String scenarioName, int totalRequests, Duration duration) {
        double rate = totalRequests / (double) duration.toSeconds();
        return scenario(scenarioName)
        .group(scenarioName)
        .on(exec(GetApiTokenScenario.getApiToken())
            .repeat((int) (rate * duration.toSeconds()))
            .on(exec(PostAudioRequestScenario.PostaudioRequest().pace(Duration.ofSeconds(1).dividedBy((long) rate)))));
    }
}
