package simulations.Scripts.DartsApi.CreateData;

import simulations.Scripts.Scenario.DartsApi.CreateRetenionsScenario;
import simulations.Scripts.Scenario.DartsApi.GetApiTokenScenario;

import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import simulations.Scripts.Utilities.HttpUtil;


public class CaseRetentionSimulation extends Simulation {   
  {
    final HttpProtocolBuilder httpProtocol =
        HttpUtil.getHttpProtocol()
        .baseUrl(EnvironmentURL.B2B_Login.getUrl())
        .inferHtmlResources();

    final ScenarioBuilder scn1 = scenario("CaseRetention Scenario")
        .exec(exec(GetApiTokenScenario.getApiToken())
        .repeat(100)    
        .on(exec(CreateRetenionsScenario.CreateRetenions()
        )));

    setUp(
        scn1.injectOpen(constantUsersPerSec(1).during(1)).protocols(httpProtocol));
    }    
}