package simulations.Scripts.DartsApi.AutomatedTasks;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import simulations.Scripts.Scenario.DartsApi.GetApiTokenScenario;
import simulations.Scripts.Scenario.DartsApi.RunCloseOldCasesTaskScenario;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Utilities.HttpUtil;

import static io.gatling.javaapi.core.CoreDsl.*;


public class RunCloseOldCasesTaskSimulation extends Simulation {
    {
        final HttpProtocolBuilder httpProtocol =
                HttpUtil.getHttpProtocol()
                        .baseUrl(EnvironmentURL.B2B_Login.getUrl())
                        .inferHtmlResources();

        final ScenarioBuilder scn1 = scenario("Run Close Old Cases Task Scenario")
                .exec(GetApiTokenScenario.getApiToken())
                .repeat(1)
                .on(exec(RunCloseOldCasesTaskScenario.RunCloseOldCasesTask()
                ));

        setUp(
                scn1.injectOpen(constantUsersPerSec(1).during(1)).protocols(httpProtocol));
    }
}