package simulations.Scripts.DartsSoap;

import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Scenario.DartsSoap.RegisterWithTokenSoapScenario;
import simulations.Scripts.Scenario.DartsSoap.RegisterWithUsernameSoapScenario;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class RegisterWithUsernameSOAPSimulation extends Simulation {

  FeederBuilder<String> feeder = csv(AppConfig.COURT_HOUSE_AND_COURT_ROOMS_FILE_PATH).random();

  //FeederBuilder<Object> users = Feeders.RANDOM_USER_FEEDER;

  {
    HttpProtocolBuilder httpProtocol = http
   //   .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT).httpsPort(AppConfig.PROXY_PORT))
      .baseUrl(EnvironmentURL.GATEWAY_BASE_URL.getUrl())
      .inferHtmlResources()
      .acceptEncodingHeader("gzip,deflate")
      .contentTypeHeader("text/xml;charset=UTF-8")
      .userAgentHeader("Apache-HttpClient/4.5.5 (Java/16.0.2)");

    final ScenarioBuilder scn = scenario("DARTS - GateWay - Soap - RegisterWithUsername")
        .feed(feeder)    
        .repeat(1)    
        .on(exec(RegisterWithUsernameSoapScenario.RegisterWithUsernameSoap().feed(feeder))  
        .exec(RegisterWithTokenSoapScenario.RegisterWithTokenSoap()  
        ));    
  
    setUp(
        scn.injectOpen(constantUsersPerSec(1).during(1)).protocols(httpProtocol));
    }  
}
