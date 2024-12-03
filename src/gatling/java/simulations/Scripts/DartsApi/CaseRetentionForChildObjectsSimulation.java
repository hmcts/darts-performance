package simulations.Scripts.DartsApi;

import simulations.Scripts.Scenario.DartsApi.CaseRetentionForChildObjectScenario;
import simulations.Scripts.Scenario.DartsApi.GetApiTokenScenario;

import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;


public class CaseRetentionForChildObjectsSimulation extends Simulation {   
  {
    final HttpProtocolBuilder httpProtocol = http
        .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
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