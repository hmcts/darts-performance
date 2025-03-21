package simulations.Scripts.DartsApi;

import simulations.Scripts.Scenario.DartsApi.GetApiTokenScenario;
import simulations.Scripts.Scenario.DartsApi.PostAudioScenario;
import simulations.Scripts.Scenario.DartsApi.PostEventsScenario;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Utilities.Feeders;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;


public class PostEventsAndAudioSimulation extends Simulation {   
  {
    final HttpProtocolBuilder httpProtocol = http
        .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
        .baseUrl(EnvironmentURL.B2B_Login.getUrl())
        .inferHtmlResources();

    final ScenarioBuilder scn1 = scenario("POST Events Scenario")
        .exec(feed(Feeders.createCourtHouseAndCourtRooms()))
        .exec(GetApiTokenScenario.getApiToken())
        .repeat(1)    
        .on(exec(PostEventsScenario.PostEventsRequest(),
            exec(PostAudioScenario.PostApiAudio())    

        ));

    setUp(
        scn1.injectOpen(constantUsersPerSec(1).during(1)).protocols(httpProtocol));
    }    
}