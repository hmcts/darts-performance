package simulations.Scripts.DartsApi;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import simulations.Scripts.Scenario.DartsApi.CaseRetentionForChildObjectScenario;
import simulations.Scripts.Scenario.DartsApi.GetApiTokenScenario;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Utilities.HttpUtil;

import static io.gatling.javaapi.core.CoreDsl.*;


public class CaseRetentionForChildObjectsSimulation extends Simulation {
    {
        final HttpProtocolBuilder httpProtocol =
                HttpUtil.getHttpProtocol()
                        .baseUrl(EnvironmentURL.B2B_Login.getUrl())
                        .inferHtmlResources();

        final ScenarioBuilder scn1 = scenario("Close Case Scenario")
                // .exec(feed(Feeders.createCaseHouseRoomsHearingDetails()))
                .exec(GetApiTokenScenario.getApiToken())
                .repeat(10)
                .on(exec(CaseRetentionForChildObjectScenario.CaseRetentionForChildObject()
                ));

        setUp(
                scn1.injectOpen(constantUsersPerSec(10).during(1)).protocols(httpProtocol));
    }
}