
import java.time.Duration;
import java.util.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class RequestAudio extends Simulation {

  {
    HttpProtocolBuilder httpProtocol = http
      .baseUrl("https://darts.test.apps.hmcts.net")
      .inferHtmlResources()
      .acceptHeader("application/json, text/plain, */*")
      .acceptEncodingHeader("gzip, deflate, br")
      .acceptLanguageHeader("en-US,en;q=0.9")
      .contentTypeHeader("application/json")
      .originHeader("https://darts.test.apps.hmcts.net")
      .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36");
    
    Map<CharSequence, String> headers_0 = new HashMap<>();
    headers_0.put("Sec-Fetch-Dest", "empty");
    headers_0.put("Sec-Fetch-Mode", "cors");
    headers_0.put("Sec-Fetch-Site", "same-origin");
    headers_0.put("sec-ch-ua", "Chromium\";v=\"122\", \"Not(A:Brand\";v=\"24\", \"Google Chrome\";v=\"122");
    headers_0.put("sec-ch-ua-mobile", "?0");
    headers_0.put("sec-ch-ua-platform", "Windows");


    ScenarioBuilder scn = scenario("RequestAudio")
      .exec(
        http("request_0")
          .post("/api/audio-requests")
          .headers(headers_0)
          .body(RawFileBody("requestaudio/0000_request.json"))
      );

	  setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
  }
}
