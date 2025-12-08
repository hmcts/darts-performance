package simulations.Scripts.DartsApi.CreateData;

import simulations.Scripts.Scenario.DartsApi.GetApiTokenScenario;
import simulations.Scripts.Scenario.DartsApi.InterpreterUsedEventScenario;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import simulations.Scripts.Utilities.HttpUtil;


public class InterpreterUsedEventSimulation extends Simulation {  
        
  {

    
    final HttpProtocolBuilder httpProtocol =
        HttpUtil.getHttpProtocol()
        .baseUrl(EnvironmentURL.B2B_Login.getUrl())
        .inferHtmlResources();        

    final ScenarioBuilder scn1 = scenario("POST Events Scenario")
        .exec(GetApiTokenScenario.getApiToken())
        .repeat(141)    
        .on(exec(InterpreterUsedEventScenario.InterpreterUsedEvent()
        ));

    setUp(
        scn1.injectOpen(constantUsersPerSec(1).during(1)).protocols(httpProtocol));
    }    
}