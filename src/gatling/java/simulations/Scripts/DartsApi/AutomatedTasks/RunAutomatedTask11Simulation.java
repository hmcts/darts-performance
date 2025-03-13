package simulations.Scripts.DartsApi;

import simulations.Scripts.Scenario.DartsApi.GetApiTokenScenario;
import simulations.Scripts.Scenario.DartsApi.RunAutomatedTask11Scenario;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;


public class RunAutomatedTask11Simulation extends Simulation {   
  {
    final HttpProtocolBuilder httpProtocol = http
        .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
        .baseUrl(EnvironmentURL.B2B_Login.getUrl())
        .inferHtmlResources();

    final ScenarioBuilder scn1 = scenario("CaseRetention Scenario")
        .exec(GetApiTokenScenario.getApiToken())
        .repeat(1)    
        .on(exec(RunAutomatedTask11Scenario.RunAutomatedTask11Scenario()
        ));

    setUp(
        scn1.injectOpen(constantUsersPerSec(1).during(1)).protocols(httpProtocol));
    }    
}