package simulations.Scripts.DartsApi;

import simulations.Scripts.Scenario.DartsApi.PostCourthouseScenario;
import simulations.Scripts.Scenario.DartsApi.GetApiTokenScenario;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class CourthousePostSimulation extends Simulation {   
  {
    final HttpProtocolBuilder httpProtocol = http
    //    .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
        .baseUrl(EnvironmentURL.B2B_Login.getUrl())
        .inferHtmlResources();

    final ScenarioBuilder scn1 = scenario("Courthouse:POST")
        .exec(GetApiTokenScenario.getApiToken()
        .repeat(134)    
        .on(exec(PostCourthouseScenario.CourthousePost())    
        ));

    setUp(
        scn1.injectOpen(constantUsersPerSec(1).during(1)).protocols(httpProtocol));
    }    
}