package simulations.Scripts.DartsSoap;

import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Scenario.DartsSoap.RegisterWithTokenScenario;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class RegisterWithTokenSOAPSimulation extends Simulation {

  FeederBuilder<String> feeder = csv(AppConfig.COURT_HOUSE_AND_COURT_ROOMS_FILE_PATH).random();
  {
    HttpProtocolBuilder httpProtocol = http
      .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
      .baseUrl(EnvironmentURL.GATEWAY_BASE_URL.getUrl())
      .inferHtmlResources()
      .acceptEncodingHeader("gzip,deflate")
      .contentTypeHeader("text/xml;charset=UTF-8")
      .userAgentHeader("Apache-HttpClient/4.5.5 (Java/16.0.2)");

    final ScenarioBuilder scn = scenario("DARTS - GateWay - Soap - RegisterWithToken")
        .feed(feeder)    
        .repeat(1)    
        .on(exec(RegisterWithTokenScenario.RegisterWithToken(EnvironmentURL.DARTS_SOAP_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_USERNAME.getUrl()))    
        );    
  
    setUp(
        scn.injectOpen(constantUsersPerSec(1).during(1)).protocols(httpProtocol));
    }  
}
