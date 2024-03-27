
import java.time.Duration;
import java.util.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class ApproveTrans extends Simulation {

  {
    HttpProtocolBuilder httpProtocol = http
      .baseUrl("https://darts.test.apps.hmcts.net")
      .inferHtmlResources()
      .acceptHeader("application/json, text/plain, */*")
      .acceptEncodingHeader("gzip, deflate, br")
      .acceptLanguageHeader("en-US,en;q=0.9")
      .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36");
    
    Map<CharSequence, String> headers_0 = new HashMap<>();
    headers_0.put("Request-Context", "appId=cid-v1:237f3073-c217-4339-ad15-f6e9539b8128");
    headers_0.put("Request-Id", "|0e200c9852ec42a2a3b214d8d822e8e7.257d17e891cf469b");
    headers_0.put("Sec-Fetch-Dest", "empty");
    headers_0.put("Sec-Fetch-Mode", "cors");
    headers_0.put("Sec-Fetch-Site", "same-origin");
    headers_0.put("sec-ch-ua", "Google Chrome\";v=\"123\", \"Not:A-Brand\";v=\"8\", \"Chromium\";v=\"123");
    headers_0.put("sec-ch-ua-mobile", "?0");
    headers_0.put("sec-ch-ua-platform", "Windows");
    headers_0.put("traceparent", "00-0e200c9852ec42a2a3b214d8d822e8e7-257d17e891cf469b-01");
    
    Map<CharSequence, String> headers_1 = new HashMap<>();
    headers_1.put("Request-Context", "appId=cid-v1:237f3073-c217-4339-ad15-f6e9539b8128");
    headers_1.put("Request-Id", "|866966d4d5694d3990f9f153a509e711.38cf36070f10444d");
    headers_1.put("Sec-Fetch-Dest", "empty");
    headers_1.put("Sec-Fetch-Mode", "cors");
    headers_1.put("Sec-Fetch-Site", "same-origin");
    headers_1.put("sec-ch-ua", "Google Chrome\";v=\"123\", \"Not:A-Brand\";v=\"8\", \"Chromium\";v=\"123");
    headers_1.put("sec-ch-ua-mobile", "?0");
    headers_1.put("sec-ch-ua-platform", "Windows");
    headers_1.put("traceparent", "00-866966d4d5694d3990f9f153a509e711-38cf36070f10444d-01");
    
    Map<CharSequence, String> headers_2 = new HashMap<>();
    headers_2.put("Request-Context", "appId=cid-v1:237f3073-c217-4339-ad15-f6e9539b8128");
    headers_2.put("Request-Id", "|866966d4d5694d3990f9f153a509e711.e4b697219b984ede");
    headers_2.put("Sec-Fetch-Dest", "empty");
    headers_2.put("Sec-Fetch-Mode", "cors");
    headers_2.put("Sec-Fetch-Site", "same-origin");
    headers_2.put("sec-ch-ua", "Google Chrome\";v=\"123\", \"Not:A-Brand\";v=\"8\", \"Chromium\";v=\"123");
    headers_2.put("sec-ch-ua-mobile", "?0");
    headers_2.put("sec-ch-ua-platform", "Windows");
    headers_2.put("traceparent", "00-866966d4d5694d3990f9f153a509e711-e4b697219b984ede-01");
    
    Map<CharSequence, String> headers_3 = new HashMap<>();
    headers_3.put("Request-Context", "appId=cid-v1:237f3073-c217-4339-ad15-f6e9539b8128");
    headers_3.put("Request-Id", "|866966d4d5694d3990f9f153a509e711.4156d8c73ef940fd");
    headers_3.put("Sec-Fetch-Dest", "empty");
    headers_3.put("Sec-Fetch-Mode", "cors");
    headers_3.put("Sec-Fetch-Site", "same-origin");
    headers_3.put("sec-ch-ua", "Google Chrome\";v=\"123\", \"Not:A-Brand\";v=\"8\", \"Chromium\";v=\"123");
    headers_3.put("sec-ch-ua-mobile", "?0");
    headers_3.put("sec-ch-ua-platform", "Windows");
    headers_3.put("traceparent", "00-866966d4d5694d3990f9f153a509e711-4156d8c73ef940fd-01");
    
    Map<CharSequence, String> headers_4 = new HashMap<>();
    headers_4.put("Request-Context", "appId=cid-v1:237f3073-c217-4339-ad15-f6e9539b8128");
    headers_4.put("Request-Id", "|866966d4d5694d3990f9f153a509e711.f7c1c316bc034f97");
    headers_4.put("Sec-Fetch-Dest", "empty");
    headers_4.put("Sec-Fetch-Mode", "cors");
    headers_4.put("Sec-Fetch-Site", "same-origin");
    headers_4.put("sec-ch-ua", "Google Chrome\";v=\"123\", \"Not:A-Brand\";v=\"8\", \"Chromium\";v=\"123");
    headers_4.put("sec-ch-ua-mobile", "?0");
    headers_4.put("sec-ch-ua-platform", "Windows");
    headers_4.put("traceparent", "00-866966d4d5694d3990f9f153a509e711-f7c1c316bc034f97-01");
    
    Map<CharSequence, String> headers_5 = new HashMap<>();
    headers_5.put("Request-Context", "appId=cid-v1:237f3073-c217-4339-ad15-f6e9539b8128");
    headers_5.put("Request-Id", "|597bfa8201c241449ff1bff600c82581.e89cbc0acae04a2a");
    headers_5.put("Sec-Fetch-Dest", "empty");
    headers_5.put("Sec-Fetch-Mode", "cors");
    headers_5.put("Sec-Fetch-Site", "same-origin");
    headers_5.put("sec-ch-ua", "Google Chrome\";v=\"123\", \"Not:A-Brand\";v=\"8\", \"Chromium\";v=\"123");
    headers_5.put("sec-ch-ua-mobile", "?0");
    headers_5.put("sec-ch-ua-platform", "Windows");
    headers_5.put("traceparent", "00-597bfa8201c241449ff1bff600c82581-e89cbc0acae04a2a-01");
    
    Map<CharSequence, String> headers_6 = new HashMap<>();
    headers_6.put("Content-Type", "application/json");
    headers_6.put("Origin", "https://darts.test.apps.hmcts.net");
    headers_6.put("Request-Context", "appId=cid-v1:237f3073-c217-4339-ad15-f6e9539b8128");
    headers_6.put("Request-Id", "|597bfa8201c241449ff1bff600c82581.c6401b1f193f49e5");
    headers_6.put("Sec-Fetch-Dest", "empty");
    headers_6.put("Sec-Fetch-Mode", "cors");
    headers_6.put("Sec-Fetch-Site", "same-origin");
    headers_6.put("sec-ch-ua", "Google Chrome\";v=\"123\", \"Not:A-Brand\";v=\"8\", \"Chromium\";v=\"123");
    headers_6.put("sec-ch-ua-mobile", "?0");
    headers_6.put("sec-ch-ua-platform", "Windows");
    headers_6.put("traceparent", "00-597bfa8201c241449ff1bff600c82581-c6401b1f193f49e5-01");
    
    Map<CharSequence, String> headers_7 = new HashMap<>();
    headers_7.put("Request-Context", "appId=cid-v1:237f3073-c217-4339-ad15-f6e9539b8128");
    headers_7.put("Request-Id", "|597bfa8201c241449ff1bff600c82581.fdc6c7afba3b4b8d");
    headers_7.put("Sec-Fetch-Dest", "empty");
    headers_7.put("Sec-Fetch-Mode", "cors");
    headers_7.put("Sec-Fetch-Site", "same-origin");
    headers_7.put("sec-ch-ua", "Google Chrome\";v=\"123\", \"Not:A-Brand\";v=\"8\", \"Chromium\";v=\"123");
    headers_7.put("sec-ch-ua-mobile", "?0");
    headers_7.put("sec-ch-ua-platform", "Windows");
    headers_7.put("traceparent", "00-597bfa8201c241449ff1bff600c82581-fdc6c7afba3b4b8d-01");
    
    Map<CharSequence, String> headers_8 = new HashMap<>();
    headers_8.put("Request-Context", "appId=cid-v1:237f3073-c217-4339-ad15-f6e9539b8128");
    headers_8.put("Request-Id", "|28a93d801f3c40e98b76b2009ac300ef.d724053064b1461b");
    headers_8.put("Sec-Fetch-Dest", "empty");
    headers_8.put("Sec-Fetch-Mode", "cors");
    headers_8.put("Sec-Fetch-Site", "same-origin");
    headers_8.put("sec-ch-ua", "Google Chrome\";v=\"123\", \"Not:A-Brand\";v=\"8\", \"Chromium\";v=\"123");
    headers_8.put("sec-ch-ua-mobile", "?0");
    headers_8.put("sec-ch-ua-platform", "Windows");
    headers_8.put("traceparent", "00-28a93d801f3c40e98b76b2009ac300ef-d724053064b1461b-01");
    
    Map<CharSequence, String> headers_9 = new HashMap<>();
    headers_9.put("Request-Context", "appId=cid-v1:237f3073-c217-4339-ad15-f6e9539b8128");
    headers_9.put("Request-Id", "|28a93d801f3c40e98b76b2009ac300ef.b564f34ee9a54324");
    headers_9.put("Sec-Fetch-Dest", "empty");
    headers_9.put("Sec-Fetch-Mode", "cors");
    headers_9.put("Sec-Fetch-Site", "same-origin");
    headers_9.put("sec-ch-ua", "Google Chrome\";v=\"123\", \"Not:A-Brand\";v=\"8\", \"Chromium\";v=\"123");
    headers_9.put("sec-ch-ua-mobile", "?0");
    headers_9.put("sec-ch-ua-platform", "Windows");
    headers_9.put("traceparent", "00-28a93d801f3c40e98b76b2009ac300ef-b564f34ee9a54324-01");


    ScenarioBuilder scn = scenario("ApproveTrans")
      .exec(
        http("request_0")
          .get("/auth/is-authenticated?t=1711460258427")
          .headers(headers_0)
      )
      .pause(3)
      .exec(
        http("request_1")
          .get("/api/audio-requests/not-accessed-count")
          .headers(headers_1)
          .resources(
            http("request_2")
              .get("/api/transcriptions")
              .headers(headers_2),
            http("request_3")
              .get("/api/transcriptions/urgencies")
              .headers(headers_3)
          )
      )
      .pause(10)
      .exec(
        http("request_4")
          .get("/auth/is-authenticated?t=1711460273817")
          .headers(headers_4)
          .resources(
            http("request_5")
              .get("/api/transcriptions/305326")
              .headers(headers_5)
          )
      )
      .pause(4)
      .exec(
        http("request_6")
          .patch("/api/transcriptions/305326")
          .headers(headers_6)
          .body(RawFileBody("approvetrans/0006_request.json"))
          .resources(
            http("request_7")
              .get("/auth/is-authenticated?t=1711460279280")
              .headers(headers_7),
            http("request_8")
              .get("/api/audio-requests/not-accessed-count")
              .headers(headers_8),
            http("request_9")
              .get("/api/transcriptions")
              .headers(headers_9)
          )
      );

	  setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
  }
}
