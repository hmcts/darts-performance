package simulations.Scripts.DartsApi;

import simulations.Scripts.Scenario.DartsApi.GetApiTokenScenario;
import simulations.Scripts.Scenario.DartsApi.GetAudioRequestScenario;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;


public class AudioRequestGetSimulation extends Simulation {   
  {

    final HttpProtocolBuilder httpProtocol = http
        .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
        .baseUrl(EnvironmentURL.B2B_Login.getUrl())
        .inferHtmlResources();

        final ScenarioBuilder scn1 = scenario("Audio Requests:GET")
        .exec(GetApiTokenScenario.getApiToken())
        .repeat(10)
        .on(
            uniformRandomSwitch().on(
                exec(GetAudioRequestScenario.GetAudioRequestDownload()),
                exec(GetAudioRequestScenario.GetAudioRequestPlayBack())
            )
        );
    setUp(
             scn1.injectOpen(atOnceUsers(1)).protocols(httpProtocol)
        );
    }    
}