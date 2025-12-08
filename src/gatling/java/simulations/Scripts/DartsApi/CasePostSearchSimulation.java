package simulations.Scripts.DartsApi;

import simulations.Scripts.Scenario.DartsApi.GetApiTokenScenario;
import simulations.Scripts.Scenario.DartsApi.PostCaseSearchScenario;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import simulations.Scripts.Utilities.HttpUtil;


public class CasePostSearchSimulation extends Simulation {   
  {

    final HttpProtocolBuilder httpProtocol =
        HttpUtil.getHttpProtocol()
      // .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT).httpsPort(AppConfig.PROXY_PORT))
        .baseUrl(EnvironmentURL.B2B_Login.getUrl())
        .inferHtmlResources();

    final ScenarioBuilder scn1 = scenario("Case Requests:POST Search")
        .exec(GetApiTokenScenario.getApiToken())
        .repeat(1)    
        .on(exec(PostCaseSearchScenario.PostCaseSearch())    
        );

    setUp(        
        scn1.injectOpen(constantUsersPerSec(1).during(1)).protocols(httpProtocol));
    }    
}