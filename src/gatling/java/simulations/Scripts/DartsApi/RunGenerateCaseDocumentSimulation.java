package simulations.Scripts.DartsApi;

import simulations.Scripts.Scenario.DartsApi.GetApiTokenScenario;
import simulations.Scripts.Scenario.DartsApi.RunCloseOldUnfinishedTranscriptionsTaskScenario;
import simulations.Scripts.Scenario.DartsApi.RunGenerateCaseDocumentTaskScenario;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;


public class RunGenerateCaseDocumentSimulation extends Simulation {   
  {
    final HttpProtocolBuilder httpProtocol = http
        .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
        .baseUrl(EnvironmentURL.B2B_Login.getUrl())
        .inferHtmlResources();

    final ScenarioBuilder scn1 = scenario("Run Generate Case Document Task Scenario")
        .exec(GetApiTokenScenario.getApiToken())
        .repeat(1)    
        .on(exec(RunGenerateCaseDocumentTaskScenario.RunGenerateCaseDocumentTask()
        ));

    setUp(
        scn1.injectOpen(constantUsersPerSec(1).during(1)).protocols(httpProtocol));
    }    
}