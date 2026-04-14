package simulations.Scripts.DartsApi.CreateData;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import simulations.Scripts.Scenario.DartsApi.GetApiTokenScenario;
import simulations.Scripts.Scenario.DartsApi.GetEventScenario;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Utilities.HttpUtil;

import static io.gatling.javaapi.core.CoreDsl.*;


public class GetEventUpdateInterpereterUsed extends Simulation {

    {

        final HttpProtocolBuilder httpProtocol =
                HttpUtil.getHttpProtocol()
                        .baseUrl(EnvironmentURL.B2B_Login.getUrl())
                        .inferHtmlResources();

        final ScenarioBuilder scn1 = scenario("POST Events Scenario")
                .exec(GetApiTokenScenario.getApiToken())
                .repeat(1)
                .on(exec(GetEventScenario.GetEvent()));

        setUp(
                scn1.injectOpen(constantUsersPerSec(1).during(1)).protocols(httpProtocol));
    }
}