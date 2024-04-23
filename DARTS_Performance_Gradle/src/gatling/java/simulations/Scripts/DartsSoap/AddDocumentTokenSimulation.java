package simulations.Scripts.DartsSoap;

import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Scenario.DartsSoap.AddDocumentDailyListTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.AddDocumentEventTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.RegisterWithTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.RegisterWithUsernameScenario;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import java.util.UUID;

public class AddDocumentTokenSimulation extends Simulation {

  String boundary = UUID.randomUUID().toString();
  {
    HttpProtocolBuilder httpProtocol = http
      .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
      .baseUrl(EnvironmentURL.GATEWAY_BASE_URL.getUrl())
      .inferHtmlResources()
      .acceptEncodingHeader("gzip,deflate")
      .contentTypeHeader("text/xml;charset=UTF-8")
      .userAgentHeader("Apache-HttpClient/4.5.5 (Java/16.0.2)");

    final ScenarioBuilder scn = scenario("DARTS - GateWay - Soap - AddDocument:POST")
        .feed(Feeders.createCourtHouseAndCourtRooms())   
        .exec(RegisterWithUsernameScenario.RegisterWithUsername(EnvironmentURL.DARTS_SOAP_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_PASSWORD.getUrl()))
        .exec(RegisterWithTokenScenario.RegisterWithToken(EnvironmentURL.DARTS_SOAP_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_PASSWORD.getUrl()))
        .repeat(5)    
        .on(exec(AddDocumentDailyListTokenScenario.AddDocumentDailyListToken())); 
    //    .on(randomSwitchOrElse().on(
    //         percent(60.0).then(AddDocumentDailyListTokenScenario.AddDocumentDailyListToken()),
    //         percent(20.0).then(AddDocumentEventTokenScenario.AddDocumentEventToken())
    // ).orElse(exitHere()));           
       // );    
  
    setUp(
        scn.injectOpen(constantUsersPerSec(1).during(1)).protocols(httpProtocol));
    }  
}
