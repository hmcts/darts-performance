
import java.time.Duration;
import java.util.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class RecordedSimulation extends Simulation {

  {
    HttpProtocolBuilder httpProtocol = http
      .baseUrl("https://hmctsstgextid.b2clogin.com")
      .inferHtmlResources()
      .acceptHeader("application/json, text/plain, */*")
      .acceptEncodingHeader("gzip, deflate, br")
      .acceptLanguageHeader("en-US,en;q=0.9")
      .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36");
    
    Map<CharSequence, String> headers_0 = new HashMap<>();
    headers_0.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
    headers_0.put("Sec-Fetch-Dest", "document");
    headers_0.put("Sec-Fetch-Mode", "navigate");
    headers_0.put("Sec-Fetch-Site", "same-origin");
    headers_0.put("Sec-Fetch-User", "?1");
    headers_0.put("Upgrade-Insecure-Requests", "1");
    headers_0.put("sec-ch-ua", "Not A(Brand\";v=\"99\", \"Google Chrome\";v=\"121\", \"Chromium\";v=\"121");
    headers_0.put("sec-ch-ua-mobile", "?0");
    headers_0.put("sec-ch-ua-platform", "Windows");
    
    Map<CharSequence, String> headers_1 = new HashMap<>();
    headers_1.put("Accept", "*/*");
    headers_1.put("Origin", "https://hmctsstgextid.b2clogin.com");
    headers_1.put("Sec-Fetch-Dest", "empty");
    headers_1.put("Sec-Fetch-Mode", "cors");
    headers_1.put("Sec-Fetch-Site", "cross-site");
    headers_1.put("sec-ch-ua", "Not A(Brand\";v=\"99\", \"Google Chrome\";v=\"121\", \"Chromium\";v=\"121");
    headers_1.put("sec-ch-ua-mobile", "?0");
    headers_1.put("sec-ch-ua-platform", "Windows");
    
    Map<CharSequence, String> headers_2 = new HashMap<>();
    headers_2.put("Accept", "application/json, text/javascript, */*; q=0.01");
    headers_2.put("Content-Type", "application/json; charset=UTF-8");
    headers_2.put("Origin", "https://hmctsstgextid.b2clogin.com");
    headers_2.put("Sec-Fetch-Dest", "empty");
    headers_2.put("Sec-Fetch-Mode", "cors");
    headers_2.put("Sec-Fetch-Site", "same-origin");
    headers_2.put("X-CSRF-TOKEN", "WUdDNzVYYUNlS0toMzVjcm53RlYyeTVadTFvRHlWNjNEc3BtbkNNOUJDYk5kMHRtdndGVXdNdXJtQmJBa3JGdFJWQUJrUTlKNkE2WGF3bi9WUVh4dnc9PTsyMDI0LTAyLTIxVDExOjMyOjU5LjYyNzU5MjFaOytrSWFaR2h5azZQc2wyZ1g1Y3krTVE9PTt7Ik9yY2hlc3RyYXRpb25TdGVwIjoxfQ==");
    headers_2.put("X-Requested-With", "XMLHttpRequest");
    headers_2.put("sec-ch-ua", "Not A(Brand\";v=\"99\", \"Google Chrome\";v=\"121\", \"Chromium\";v=\"121");
    headers_2.put("sec-ch-ua-mobile", "?0");
    headers_2.put("sec-ch-ua-platform", "Windows");
    
    Map<CharSequence, String> headers_3 = new HashMap<>();
    headers_3.put("Accept", "application/json, text/javascript, */*; q=0.01");
    headers_3.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
    headers_3.put("Origin", "https://hmctsstgextid.b2clogin.com");
    headers_3.put("Sec-Fetch-Dest", "empty");
    headers_3.put("Sec-Fetch-Mode", "cors");
    headers_3.put("Sec-Fetch-Site", "same-origin");
    headers_3.put("X-CSRF-TOKEN", "WUdDNzVYYUNlS0toMzVjcm53RlYyeTVadTFvRHlWNjNEc3BtbkNNOUJDYk5kMHRtdndGVXdNdXJtQmJBa3JGdFJWQUJrUTlKNkE2WGF3bi9WUVh4dnc9PTsyMDI0LTAyLTIxVDExOjMyOjU5LjYyNzU5MjFaOytrSWFaR2h5azZQc2wyZ1g1Y3krTVE9PTt7Ik9yY2hlc3RyYXRpb25TdGVwIjoxfQ==");
    headers_3.put("X-Requested-With", "XMLHttpRequest");
    headers_3.put("sec-ch-ua", "Not A(Brand\";v=\"99\", \"Google Chrome\";v=\"121\", \"Chromium\";v=\"121");
    headers_3.put("sec-ch-ua-mobile", "?0");
    headers_3.put("sec-ch-ua-platform", "Windows");
    
    Map<CharSequence, String> headers_5 = new HashMap<>();
    headers_5.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
    headers_5.put("Cache-Control", "max-age=0");
    headers_5.put("Origin", "https://hmctsstgextid.b2clogin.com");
    headers_5.put("Sec-Fetch-Dest", "document");
    headers_5.put("Sec-Fetch-Mode", "navigate");
    headers_5.put("Sec-Fetch-Site", "cross-site");
    headers_5.put("Upgrade-Insecure-Requests", "1");
    headers_5.put("sec-ch-ua", "Not A(Brand\";v=\"99\", \"Google Chrome\";v=\"121\", \"Chromium\";v=\"121");
    headers_5.put("sec-ch-ua-mobile", "?0");
    headers_5.put("sec-ch-ua-platform", "Windows");
    
    Map<CharSequence, String> headers_6 = new HashMap<>();
    headers_6.put("Sec-Fetch-Dest", "empty");
    headers_6.put("Sec-Fetch-Mode", "cors");
    headers_6.put("Sec-Fetch-Site", "same-origin");
    headers_6.put("sec-ch-ua", "Not A(Brand\";v=\"99\", \"Google Chrome\";v=\"121\", \"Chromium\";v=\"121");
    headers_6.put("sec-ch-ua-mobile", "?0");
    headers_6.put("sec-ch-ua-platform", "Windows");
    headers_6.put("x-dtpc", "1$315192570_228h2vRUPELAAMUTHGMGAVQKEMCVNGFKSNLOCP-0e0");
    
    Map<CharSequence, String> headers_7 = new HashMap<>();
    headers_7.put("Sec-Fetch-Dest", "empty");
    headers_7.put("Sec-Fetch-Mode", "cors");
    headers_7.put("Sec-Fetch-Site", "same-origin");
    headers_7.put("sec-ch-ua", "Not A(Brand\";v=\"99\", \"Google Chrome\";v=\"121\", \"Chromium\";v=\"121");
    headers_7.put("sec-ch-ua-mobile", "?0");
    headers_7.put("sec-ch-ua-platform", "Windows");
    headers_7.put("x-dtpc", "1$315192570_228h4vRUPELAAMUTHGMGAVQKEMCVNGFKSNLOCP-0e0");
    
    Map<CharSequence, String> headers_8 = new HashMap<>();
    headers_8.put("Sec-Fetch-Dest", "empty");
    headers_8.put("Sec-Fetch-Mode", "cors");
    headers_8.put("Sec-Fetch-Site", "same-origin");
    headers_8.put("sec-ch-ua", "Not A(Brand\";v=\"99\", \"Google Chrome\";v=\"121\", \"Chromium\";v=\"121");
    headers_8.put("sec-ch-ua-mobile", "?0");
    headers_8.put("sec-ch-ua-platform", "Windows");
    headers_8.put("x-dtpc", "1$315192570_228h5vRUPELAAMUTHGMGAVQKEMCVNGFKSNLOCP-0e0");
    
    Map<CharSequence, String> headers_9 = new HashMap<>();
    headers_9.put("Sec-Fetch-Dest", "empty");
    headers_9.put("Sec-Fetch-Mode", "cors");
    headers_9.put("Sec-Fetch-Site", "same-origin");
    headers_9.put("sec-ch-ua", "Not A(Brand\";v=\"99\", \"Google Chrome\";v=\"121\", \"Chromium\";v=\"121");
    headers_9.put("sec-ch-ua-mobile", "?0");
    headers_9.put("sec-ch-ua-platform", "Windows");
    headers_9.put("x-dtpc", "1$315192570_228h7vRUPELAAMUTHGMGAVQKEMCVNGFKSNLOCP-0e0");
    
    Map<CharSequence, String> headers_10 = new HashMap<>();
    headers_10.put("Sec-Fetch-Dest", "empty");
    headers_10.put("Sec-Fetch-Mode", "cors");
    headers_10.put("Sec-Fetch-Site", "same-origin");
    headers_10.put("sec-ch-ua", "Not A(Brand\";v=\"99\", \"Google Chrome\";v=\"121\", \"Chromium\";v=\"121");
    headers_10.put("sec-ch-ua-mobile", "?0");
    headers_10.put("sec-ch-ua-platform", "Windows");
    headers_10.put("x-dtpc", "1$315192570_228h6vRUPELAAMUTHGMGAVQKEMCVNGFKSNLOCP-0e0");
    
    Map<CharSequence, String> headers_11 = new HashMap<>();
    headers_11.put("Sec-Fetch-Dest", "empty");
    headers_11.put("Sec-Fetch-Mode", "cors");
    headers_11.put("Sec-Fetch-Site", "same-origin");
    headers_11.put("sec-ch-ua", "Not A(Brand\";v=\"99\", \"Google Chrome\";v=\"121\", \"Chromium\";v=\"121");
    headers_11.put("sec-ch-ua-mobile", "?0");
    headers_11.put("sec-ch-ua-platform", "Windows");
    headers_11.put("x-dtpc", "1$315192570_228h8vRUPELAAMUTHGMGAVQKEMCVNGFKSNLOCP-0e0");
    
    Map<CharSequence, String> headers_12 = new HashMap<>();
    headers_12.put("Sec-Fetch-Dest", "empty");
    headers_12.put("Sec-Fetch-Mode", "cors");
    headers_12.put("Sec-Fetch-Site", "same-origin");
    headers_12.put("sec-ch-ua", "Not A(Brand\";v=\"99\", \"Google Chrome\";v=\"121\", \"Chromium\";v=\"121");
    headers_12.put("sec-ch-ua-mobile", "?0");
    headers_12.put("sec-ch-ua-platform", "Windows");
    headers_12.put("x-dtpc", "1$315192570_228h9vRUPELAAMUTHGMGAVQKEMCVNGFKSNLOCP-0e0");
    
    Map<CharSequence, String> headers_13 = new HashMap<>();
    headers_13.put("Sec-Fetch-Dest", "empty");
    headers_13.put("Sec-Fetch-Mode", "cors");
    headers_13.put("Sec-Fetch-Site", "same-origin");
    headers_13.put("sec-ch-ua", "Not A(Brand\";v=\"99\", \"Google Chrome\";v=\"121\", \"Chromium\";v=\"121");
    headers_13.put("sec-ch-ua-mobile", "?0");
    headers_13.put("sec-ch-ua-platform", "Windows");
    headers_13.put("x-dtpc", "1$315192570_228h10vRUPELAAMUTHGMGAVQKEMCVNGFKSNLOCP-0e0");
    headers_13.put("x-dtreferer", "https://darts.staging.apps.hmcts.net/");
    
    String uri2 = "https://darts.staging.apps.hmcts.net";

    ScenarioBuilder scn = scenario("RecordedSimulation")
      .exec(
        http("request_0")
          .get(uri2 + "/auth/login")
          .headers(headers_0)
          .resources(
            http("request_1")
              .get(uri2 + "/auth/azuread-b2c-login?screenName=loginScreen&ui_locales=en")
              .headers(headers_1),
            http("request_2")
              .post("/hmctsstgextid.onmicrosoft.com/B2C_1_darts_externaluser_signin/client/perftrace?tx=StateProperties=eyJUSUQiOiI0ZmQ1M2ZjZi00YzE1LTQ4OTktYTY0Yy1iMTYxYjlmZTRmZDAifQ&p=B2C_1_darts_externaluser_signin")
              .headers(headers_2)
              .body(RawFileBody("recordedsimulation/0002_request.bin"))
          )
      )
      .pause(8)
      .exec(
        http("request_3")
          .post("/hmctsstgextid.onmicrosoft.com/B2C_1_darts_externaluser_signin/SelfAsserted?tx=StateProperties=eyJUSUQiOiI0ZmQ1M2ZjZi00YzE1LTQ4OTktYTY0Yy1iMTYxYjlmZTRmZDAifQ&p=B2C_1_darts_externaluser_signin")
          .headers(headers_3)
          .formParam("request_type", "RESPONSE")
          .formParam("email", "PerfTranscirber01@hmcts.net")
          .formParam("password", "PerfTester@01")
          .resources(
            http("request_4")
              .get("/hmctsstgextid.onmicrosoft.com/B2C_1_darts_externaluser_signin/api/CombinedSigninAndSignup/confirmed?rememberMe=false&csrf_token=WUdDNzVYYUNlS0toMzVjcm53RlYyeTVadTFvRHlWNjNEc3BtbkNNOUJDYk5kMHRtdndGVXdNdXJtQmJBa3JGdFJWQUJrUTlKNkE2WGF3bi9WUVh4dnc9PTsyMDI0LTAyLTIxVDExOjMyOjU5LjYyNzU5MjFaOytrSWFaR2h5azZQc2wyZ1g1Y3krTVE9PTt7Ik9yY2hlc3RyYXRpb25TdGVwIjoxfQ==&tx=StateProperties=eyJUSUQiOiI0ZmQ1M2ZjZi00YzE1LTQ4OTktYTY0Yy1iMTYxYjlmZTRmZDAifQ&p=B2C_1_darts_externaluser_signin&diags=%7B%22pageViewId%22%3A%223ec520ab-1a56-4387-a71c-8f4357eb169d%22%2C%22pageId%22%3A%22CombinedSigninAndSignup%22%2C%22trace%22%3A%5B%7B%22ac%22%3A%22T005%22%2C%22acST%22%3A1708515180%2C%22acD%22%3A2%7D%2C%7B%22ac%22%3A%22T021%20-%20URL%3Ahttps%3A%2F%2Fdarts.staging.apps.hmcts.net%2Fauth%2Fazuread-b2c-login%3FscreenName%3DloginScreen%26ui_locales%3Den%22%2C%22acST%22%3A1708515180%2C%22acD%22%3A267%7D%2C%7B%22ac%22%3A%22T019%22%2C%22acST%22%3A1708515180%2C%22acD%22%3A3%7D%2C%7B%22ac%22%3A%22T004%22%2C%22acST%22%3A1708515181%2C%22acD%22%3A2%7D%2C%7B%22ac%22%3A%22T003%22%2C%22acST%22%3A1708515181%2C%22acD%22%3A1%7D%2C%7B%22ac%22%3A%22T035%22%2C%22acST%22%3A1708515181%2C%22acD%22%3A0%7D%2C%7B%22ac%22%3A%22T030Online%22%2C%22acST%22%3A1708515181%2C%22acD%22%3A0%7D%2C%7B%22ac%22%3A%22T035%22%2C%22acST%22%3A1708515181%2C%22acD%22%3A0%7D%2C%7B%22ac%22%3A%22T002%22%2C%22acST%22%3A1708515190%2C%22acD%22%3A0%7D%2C%7B%22ac%22%3A%22T018T010%22%2C%22acST%22%3A1708515189%2C%22acD%22%3A608%7D%5D%7D")
              .headers(headers_0),
            http("request_5")
              .post(uri2 + "/auth/callback")
              .headers(headers_5)
              .formParam("code", "eyJraWQiOiJjcGltY29yZV8wOTI1MjAxNSIsInZlciI6IjEuMCIsInppcCI6IkRlZmxhdGUiLCJzZXIiOiIxLjAifQ..LH_eIy4Q7kaoCR_c._5eExBhEYJv7oJChP__qQhlFOw0w2_04yu-62m70ni-M-ANr3HfkPiFidilCpwctbGaIqr2o5OoJJC46q2pCxhRTIMPAUuya6nnm1Z-zZXEeYzuPU5-zn9IBQy93SNmhlabhKxYC-7c76WR6udxGoW2qOtv5nsqZykdB6XZHo6VX9gqQ27PFz792ONgXU18mAhd1GNGNbyr4PeiIvQwcRyIW6C-4qcEZRnF4HjZS1wirLJ0LZZFCz0kGq1F2cC1gSscS7EIQf5rY_aHcmS5vT5U3b4Sbcl-jYwoiSjHRBUqz-2Uz83rSo9YQ-3rJuWvdysLR1aialR3mnsWIzhSOi2AY03qxTwhExTrLixJ_-uxJx0l0BdTAuKUTXCPTgl6KlZTQCSYxiZm3ZX5NCpYhAL9ZjMts_ORy3O1XYuhkHo5FqW6nm_605udX8EyDRLiSZ-1wiPUmOXv7470eYyaRkcaXitITj1JQOayiQHb3sIOrSZJ6PHhqQRUma40o32inykp9HHgVa0kh0Wfg6EZtgFCM7bCDnCQwNzjabrd7F95bQ6xhZvR6uyLrXCUupeGhBuLmsMq52KRX.z7g6tj_LlHdxJjjYunVr8A"),
            http("request_6")
              .get(uri2 + "/app/config")
              .headers(headers_6),
            http("request_7")
              .get(uri2 + "/auth/is-authenticated?t=1708515193065")
              .headers(headers_7),
            http("request_8")
              .get(uri2 + "/auth/is-authenticated?t=1708515193209")
              .headers(headers_8),
            http("request_9")
              .get(uri2 + "/user/profile")
              .headers(headers_9),
            http("request_10")
              .get(uri2 + "/api/audio-requests/not-accessed-count")
              .headers(headers_10),
            http("request_11")
              .get(uri2 + "/api/transcriptions/transcriber-counts")
              .headers(headers_11),
            http("request_12")
              .get(uri2 + "/api/transcriptions/transcriber-counts")
              .headers(headers_12),
            http("request_13")
              .get(uri2 + "/api/courthouses")
              .headers(headers_13)
          )
      );

	  setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
  }
}
