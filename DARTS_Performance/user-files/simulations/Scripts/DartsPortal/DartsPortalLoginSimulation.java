package DartsPortal;

import java.time.Duration;
import java.util.*;

import RequestBodyBuilder.RequestBodyBuilder;
import Utilities.AppConfig;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;
import scenario.DartsPortalLoginScenario;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class DartsPortalLoginSimulation extends Simulation {   
  {
    //final FeederBuilder<String> feeder = csv(AppConfig.AUDIO_REQUEST_POST_FILE_PATH).random();


      HttpProtocolBuilder httpProtocol = http
      .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT).httpsPort(AppConfig.PROXY_PORT))
        .baseUrl(AppConfig.EnvironmentURL.B2B_Login.getUrl())
        .inferHtmlResources()
        .acceptHeader("application/json, text/plain, */*")
        .acceptEncodingHeader("gzip, deflate, br")
        .acceptLanguageHeader("en-US,en;q=0.9")
        .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36");
      

    final ScenarioBuilder scn1 = scenario("Darts Portal Login")
        .exec(DartsPortalLoginScenario.DartsPortalLoginRequest());

    setUp(
        scn1.injectOpen(constantUsersPerSec(1).during(1)).protocols(httpProtocol));
    }    
} 
    

