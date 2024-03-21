package simulations.Scripts.DartsSoap;

import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Scenario.DartsApi.GetApiTokenScenario;
import simulations.Scripts.Scenario.DartsApi.GetAudioRequestScenario;
import simulations.Scripts.Scenario.DartsSoap.AddDcoumentDailyListTokenScenario;
import simulations.Scripts.Scenario.DartsSoap.AddDcoumentEventTokenScenario;

import simulations.Scripts.Scenario.DartsSoap.RegisterWithUsernameSoapScenario;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class AddDocumentSOAPTokenSimulation extends Simulation {

  FeederBuilder<String> feeder = csv(AppConfig.COURT_HOUSE_AND_COURT_ROOMS_FILE_PATH).random();
  
    HttpProtocolBuilder httpProtocol = http
      .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT).httpsPort(AppConfig.PROXY_PORT))
      .baseUrl(EnvironmentURL.GATEWAY_BASE_URL.getUrl())
      .inferHtmlResources()
      .acceptEncodingHeader("gzip,deflate")
      .contentTypeHeader("text/xml;charset=UTF-8")
      .userAgentHeader("Apache-HttpClient/4.5.5 (Java/16.0.2)");

      protected ScenarioBuilder getScenario() {      
        return scenario("DARTS - GateWay - Soap - AddDocument:POST")
                .feed(feeder)
                .exec(RegisterWithUsernameSoapScenario.RegisterWithUsernameSoap())
                .randomSwitchOrElse().on(
                  percent(60.0).then(AddDcoumentDailyListTokenScenario.AddDcoumentDailyListToken()),
                  percent(20.0).then(AddDcoumentEventTokenScenario.AddDcoumentEventToken())
                ).orElse(exitHere()
              ); 
    }  
}
