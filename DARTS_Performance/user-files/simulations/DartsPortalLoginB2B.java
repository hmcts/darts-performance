
import java.time.Duration;
import java.util.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class DartsPortalLoginB2B extends Simulation {

  {
    HttpProtocolBuilder httpProtocol = http
      .baseUrl("https://hmctsstgextid.b2clogin.com")
      .inferHtmlResources()
      .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
      .acceptEncodingHeader("gzip, deflate, br")
      .acceptLanguageHeader("en-US,en;q=0.9")
      .upgradeInsecureRequestsHeader("1")
      .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36");
    
    Map<CharSequence, String> headers_0 = new HashMap<>();
    headers_0.put("Sec-Fetch-Dest", "document");
    headers_0.put("Sec-Fetch-Mode", "navigate");
    headers_0.put("Sec-Fetch-Site", "cross-site");
    headers_0.put("Sec-Fetch-User", "?1");
    headers_0.put("sec-ch-ua", "Not A(Brand\";v=\"99\", \"Google Chrome\";v=\"121\", \"Chromium\";v=\"121");
    headers_0.put("sec-ch-ua-mobile", "?0");
    headers_0.put("sec-ch-ua-platform", "Windows");


    ScenarioBuilder scn = scenario("DartsPortalLoginB2B")
      .exec(
        http("request_0")
          .get("/hmctsstgextid.onmicrosoft.com/B2C_1_darts_externaluser_signin/oauth2/v2.0/authorize?client_id=363c11cb-48b9-44bf-9d06-9a3973f6f413&redirect_uri=https%3A%2F%2Fdarts.staging.apps.hmcts.net%2Fauth%2Fcallback&scope=openid&prompt=login&response_mode=form_post&response_type=code")
          .headers(headers_0)
      );

	  setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
  }
}
