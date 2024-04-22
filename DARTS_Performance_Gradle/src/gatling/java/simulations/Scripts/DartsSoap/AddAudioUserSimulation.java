package simulations.Scripts.DartsSoap;

import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Scenario.DartsSoap.AddAudioUserScenario;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;


import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import java.util.UUID;

public class AddAudioUserSimulation extends Simulation {

  String boundary = UUID.randomUUID().toString();
  {
    HttpProtocolBuilder httpProtocol = http
      .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
      .baseUrl(EnvironmentURL.GATEWAY_BASE_URL.getUrl())
      .inferHtmlResources()
      .acceptEncodingHeader("gzip,deflate")
      .contentTypeHeader("multipart/related; type=\"application/xop+xml\"; start=\"<rootpart@soapui.org>\"; start-info=\"text/xml\"; boundary=" + boundary)
      .userAgentHeader("Apache-HttpClient/4.5.5 (Java/16.0.2)");
    final ScenarioBuilder scn = scenario("DARTS - GateWay - Soap - AddAudio:POST")
        .feed(Feeders.createCourtHouseAndCourtRooms())    
        .repeat(1)    
        .on(exec(AddAudioUserScenario.addAudioUser(EnvironmentURL.DARTS_SOAP_EXTERNAL_USERNAME.getUrl(), EnvironmentURL.DARTS_SOAP_EXTERNAL_PASSWORD.getUrl()))    
        );    
  
    setUp(
        scn.injectOpen(constantUsersPerSec(1).during(1)).protocols(httpProtocol));
    }  
}
