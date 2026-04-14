package simulations.Scripts.DartsPortal;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import simulations.Scripts.Scenario.DartsPortal.DartsPortalInternalLoginScenario;
import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Utilities.HttpUtil;

import static io.gatling.javaapi.core.CoreDsl.*;

public class InternalLogin extends Simulation {

    {
        HttpProtocolBuilder httpProtocol =
                HttpUtil.getHttpProtocol()
                        .baseUrl("https://login.microsoftonline.com")
                        .inferHtmlResources()
                        .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
                        .acceptEncodingHeader("gzip, deflate, br, zstd")
                        .acceptLanguageHeader("en-US,en;q=0.9")
                        .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36");

        final ScenarioBuilder scn1 = scenario("Darts Portal Login")
                .exec(feed(Feeders.createCourtClerkUsers()))
                .exec(DartsPortalInternalLoginScenario.DartsPortalInternalLoginRequest());
        setUp(
                scn1.injectOpen(constantUsersPerSec(1).during(1)).protocols(httpProtocol));
    }
} 