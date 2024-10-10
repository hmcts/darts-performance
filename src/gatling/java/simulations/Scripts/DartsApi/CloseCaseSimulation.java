package simulations.Scripts.DartsApi;

import simulations.Scripts.Scenario.DartsApi.CloseCaseScenario;
import simulations.Scripts.Scenario.DartsApi.GetApiTokenScenario;

import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Utilities.Feeders;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;


public class CloseCaseSimulation extends Simulation {   
  {
    final HttpProtocolBuilder httpProtocol = http
        .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
        .baseUrl(EnvironmentURL.B2B_Login.getUrl())
        .inferHtmlResources();

    final ScenarioBuilder scn1 = scenario("Close Case Scenario")
        .exec(feed(Feeders.createCaseHouseRoomsHearingDetails()))
        .exec(GetApiTokenScenario.getApiToken())
        .repeat(1000)    
        .on(exec(CloseCaseScenario.CloseCase()
        ));

    setUp(
        scn1.injectOpen(constantUsersPerSec(1).during(1)).protocols(httpProtocol));
    }    
}