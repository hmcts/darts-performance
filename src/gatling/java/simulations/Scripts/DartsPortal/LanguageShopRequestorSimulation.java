package simulations.Scripts.DartsPortal;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import simulations.Scripts.Scenario.DartsPortal.*;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Utilities.HttpUtil;

import static io.gatling.javaapi.core.CoreDsl.*;

public class LanguageShopRequestorSimulation extends Simulation {
    {
        HttpProtocolBuilder httpProtocol =
                HttpUtil.getHttpProtocol()
                        .baseUrl(AppConfig.EnvironmentURL.B2B_Login.getUrl())
                        .inferHtmlResources()
                        .acceptHeader("application/json, text/plain, */*")
                        .acceptEncodingHeader("gzip, deflate, br")
                        .acceptLanguageHeader("en-US,en;q=0.9")
                        .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36");

        final ScenarioBuilder scn1 = scenario("Darts Portal Login")
                .exec(feed(Feeders.createLanguageShopUsers()))
                .exec(DartsPortalExternalLoginScenario.DartsPortalExternalLoginRequest())
                .exec(DartsPortalAdvanceSearchScenario.DartsPortalAdvanceSearch())
                .exec(DartsPortalRequestAudioScenario.DartsPortalRequestAudioDownload())
                .exec(DartsPortalPreviewAudioScenario.DartsPortalPreviewAudio())
                .exec(DartsPortalExternalLogoutScenario.DartsPortalExternalLogoutRequest());

        setUp(
                scn1.injectOpen(constantUsersPerSec(1).during(1)).protocols(httpProtocol));
    }
} 
    

