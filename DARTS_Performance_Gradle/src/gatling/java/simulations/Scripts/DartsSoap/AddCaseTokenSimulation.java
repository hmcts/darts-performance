package simulations.Scripts.DartsSoap;

import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Scenario.DartsSoap.AddCaseTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.RegisterWithTokenSoapScenario;
import simulations.Scripts.Scenario.DartsSoap.RegisterWithUsernameSoapScenario;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import java.util.UUID;
public class AddCaseTokenSimulation extends Simulation {

  FeederBuilder<String> feeder = csv(AppConfig.COURT_HOUSE_AND_COURT_ROOMS_FILE_PATH).random();
  String boundary = UUID.randomUUID().toString();

  {
    HttpProtocolBuilder httpProtocol = http
      .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT).httpsPort(AppConfig.PROXY_PORT))
      .baseUrl(EnvironmentURL.GATEWAY_BASE_URL.getUrl())
      .inferHtmlResources()
      .acceptEncodingHeader("gzip,deflate")
      .contentTypeHeader("multipart/related; type=\"text/xml\"; start=\"<rootpart@soapui.org>\"; boundary=" + boundary)
      .userAgentHeader("Apache-HttpClient/4.5.5 (Java/16.0.2)");

    final ScenarioBuilder scn = scenario("DARTS - GateWay - Soap - AddCase:POST")
        .feed(feeder)    
        .repeat(1)    
        .on(exec(RegisterWithUsernameSoapScenario.RegisterWithUsernameSoap().feed(feeder))  
        .exec(RegisterWithTokenSoapScenario.RegisterWithTokenSoap()  
        .exec(AddCaseTokenScenario.addCaseToken().feed(feeder))    
        ));    
     
    setUp(
        scn.injectOpen(constantUsersPerSec(1).during(1)).protocols(httpProtocol));
    }  
}
