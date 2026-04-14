package simulations.Scripts.DartsApi.AutomatedTasks;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import simulations.Scripts.Scenario.DartsApi.GetApiTokenScenario;
import simulations.Scripts.Scenario.DartsApi.RunDailyListHouseKeepingTaskScenario;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Utilities.HttpUtil;

import static io.gatling.javaapi.core.CoreDsl.*;


public class RunDailyListHouseKeepingTaskSimulation extends Simulation {
    {
        final HttpProtocolBuilder httpProtocol =
                HttpUtil.getHttpProtocol()
                        .baseUrl(EnvironmentURL.B2B_Login.getUrl())
                        .inferHtmlResources();

        final ScenarioBuilder scn1 = scenario("Run Daily List HouseKeeping Task Scenario")
                .exec(GetApiTokenScenario.getApiToken())
                .repeat(1)
                .on(exec(RunDailyListHouseKeepingTaskScenario.RunDailyListHouseKeepingTask()
                ));

        setUp(
                scn1.injectOpen(constantUsersPerSec(1).during(1)).protocols(httpProtocol));
    }
}