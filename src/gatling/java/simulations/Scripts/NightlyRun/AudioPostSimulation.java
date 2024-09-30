package simulations.Scripts.NightlyRun;

import simulations.Scripts.Scenario.DartsApi.GetApiTokenScenario;
import simulations.Scripts.Scenario.DartsApi.PostAudioScenario;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;


public class AudioPostSimulation extends Simulation {   
  {

    final HttpProtocolBuilder httpProtocol = http
        .baseUrl(EnvironmentURL.B2B_Login.getUrl())
        .inferHtmlResources();

    final ScenarioBuilder scn1 = scenario("Audio:POST")
        .exec(GetApiTokenScenario.getApiToken())
        .repeat(AppConfig.NIGHTLY_RUN_REPEATS)    
        .on(exec(PostAudioScenario.PostApiAudio())    
        );

    setUp(
        scn1.injectOpen(constantUsersPerSec(AppConfig.NIGHTLY_RUN_USERS).during(1)).protocols(httpProtocol));
    }    
}