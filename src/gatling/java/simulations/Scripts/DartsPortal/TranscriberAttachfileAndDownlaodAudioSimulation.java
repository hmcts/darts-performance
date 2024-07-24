package simulations.Scripts.DartsPortal;

import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalExternalLoginScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalRequestAudioScenario;
import simulations.Scripts.Scenario.DartsPortal.TranscriberAttachFileAndDownloadAudioScenario;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;


import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class TranscriberAttachfileAndDownlaodAudioSimulation extends Simulation {   
  {
      HttpProtocolBuilder httpProtocol = http
        .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
        .baseUrl(AppConfig.EnvironmentURL.B2B_Login.getUrl())
        .inferHtmlResources()
        .acceptHeader("application/json, text/plain, */*")
        .acceptEncodingHeader("gzip, deflate, br")
        .acceptLanguageHeader("en-US,en;q=0.9")
        .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36");   

    final ScenarioBuilder scn1 = scenario("Darts Portal Login")
        .exec(feed(Feeders.createTranscriberUsers()))
        .exec(DartsPortalExternalLoginScenario.DartsPortalExternalLoginRequest())
        .repeat(1)
        .on(exec(DartsPortalRequestAudioScenario.DartsPortalRequestAudioDownload()))
        .exec(TranscriberAttachFileAndDownloadAudioScenario.TranscriberAttachfileAndDownlaodAudio())
        .exec(DartsPortalExternalLoginScenario.DartsPortalExternalLoginRequest());

    setUp(
        scn1.injectOpen(constantUsersPerSec(1).during(1)).protocols(httpProtocol));
    }    
} 
    

