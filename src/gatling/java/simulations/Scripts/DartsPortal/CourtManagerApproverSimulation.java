package simulations.Scripts.DartsPortal;

import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalAdvanceSearchSecnario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalApproveAudioScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalInternalLoginScenario;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalInternalLogoutScenario;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;


import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import java.time.Duration;

public class CourtManagerApproverSimulation extends Simulation {   
  {

      HttpProtocolBuilder httpProtocol = http
        .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
        .baseUrl(AppConfig.EnvironmentURL.B2B_Login.getUrl())
        .baseUrl("https://login.microsoftonline.com") 

        .inferHtmlResources()
        .acceptHeader("application/json, text/plain, */*")
        .acceptEncodingHeader("gzip, deflate, br")
        .acceptLanguageHeader("en-US,en;q=0.9")
        .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36");
      

    final ScenarioBuilder scn1 = scenario("Darts Portal Login")
        .exec(feed(Feeders.createCourtManagerUsers()))
        .exec(DartsPortalInternalLoginScenario.DartsPortalInternalLoginRequest())      
        .repeat(1).on(
        exec(DartsPortalAdvanceSearchSecnario.DartsPortalAdvanceSearchSecnario())
        .exec(DartsPortalApproveAudioScenario.DartsPortalApproveAudio()))
        .exec(DartsPortalInternalLogoutScenario.DartsPortalInternalLogoutRequest());
    setUp(
        scn1.injectOpen(rampUsers(100).during(Duration.ofMinutes(30))).protocols(httpProtocol));
    }    
} 
    

