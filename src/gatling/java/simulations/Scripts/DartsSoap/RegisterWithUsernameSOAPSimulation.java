package simulations.Scripts.DartsSoap;

import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Scenario.DartsSoap.RegisterWithTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.RegisterWithUsernameScenario;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class RegisterWithUsernameSOAPSimulation extends Simulation {
  {
    HttpProtocolBuilder httpProtocol = http
      .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
      .baseUrl(EnvironmentURL.PROXY_BASE_URL.getUrl())
      .inferHtmlResources()
      .acceptEncodingHeader("gzip,deflate")
      .contentTypeHeader("text/xml;charset=UTF-8")
      .userAgentHeader("Apache-HttpClient/4.5.5 (Java/16.0.2)");

    final ScenarioBuilder scn = scenario("DARTS - GateWay - Soap - RegisterWithUsername")           
        .repeat(1)    
        .on(exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_PASSWORD.getUrl()))
        .exec(RegisterWithTokenScenario.RegisterWithToken(EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_XHIBIT_EXTERNAL_PASSWORD.getUrl()))
        );    
  
    setUp(
        scn.injectOpen(constantUsersPerSec(150).during(1)).protocols(httpProtocol));  
    }  
}
