package simulations.Scripts.DartsApi;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import simulations.Scripts.Scenario.DartsApi.GetApiTokenScenario;
import simulations.Scripts.Scenario.DartsApi.PostAudioScenario;
import simulations.Scripts.Scenario.DartsApi.PostEventsScenario;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Utilities.HttpUtil;

import static io.gatling.javaapi.core.CoreDsl.*;


public class PostEventsAndAudioSimulation extends Simulation {
    {
        final HttpProtocolBuilder httpProtocol =
                HttpUtil.getHttpProtocol()
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