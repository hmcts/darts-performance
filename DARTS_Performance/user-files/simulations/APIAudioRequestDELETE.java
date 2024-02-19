
import java.time.Duration;
import java.util.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class APIAudioRequestDELETE extends Simulation {

  {
    HttpProtocolBuilder httpProtocol = http
      .baseUrl("https://darts-api.staging.platform.hmcts.net")
      .inferHtmlResources()
      .acceptHeader("*/*")
      .acceptEncodingHeader("gzip, deflate, br")
      .acceptLanguageHeader("en-US,en;q=0.9")
      .authorizationHeader("Bearer eyJhbGciOiJSUzI1NiIsImtpZCI6Ilg1ZVhrNHh5b2pORnVtMWtsMll0djhkbE5QNC1jNTdkTzZRR1RWQndhTmsiLCJ0eXAiOiJKV1QifQ.eyJpZHAiOiJMb2NhbEFjY291bnQiLCJvaWQiOiI4ZmIwMDRmMy00NzRiLTQ4MzItYjk1YS03YzRiNjhjZTJjNmQiLCJzdWIiOiI4ZmIwMDRmMy00NzRiLTQ4MzItYjk1YS03YzRiNjhjZTJjNmQiLCJnaXZlbl9uYW1lIjoiRGFydHMiLCJmYW1pbHlfbmFtZSI6IlRyYW5zY3JpYmVyIiwiZW1haWxzIjpbImRhcnRzLnRyYW5zY3JpYmVyQGhtY3RzLm5ldCJdLCJ0ZnAiOiJCMkNfMV9yb3BjX2RhcnRzX3NpZ25pbiIsInNjcCI6IkZ1bmN0aW9uYWwuVGVzdCIsImF6cCI6Ijg1MTE4ZWUyLTY1NDQtNDE4MC1hMTZlLTk5NjE1MzY1ZjIwOSIsInZlciI6IjEuMCIsImlhdCI6MTcwODMzMzA5NSwiYXVkIjoiODUxMThlZTItNjU0NC00MTgwLWExNmUtOTk2MTUzNjVmMjA5IiwiZXhwIjoxNzA4MzM2Njk1LCJpc3MiOiJodHRwczovL2htY3Rzc3RnZXh0aWQuYjJjbG9naW4uY29tLzhiMTg1ZjhiLTY2NWQtNGJiMy1hZjRhLWFiN2VlNjFiOTMzNC92Mi4wLyIsIm5iZiI6MTcwODMzMzA5NX0.opmaDQHieQIjugB9DOm5_-BVMsgZZa-xGf42t0CzhBoaKAftnM3R50_201Uagj4JQxjZeppF0IsU-XSMIrxmnHMNATHxFvDhMV1803X0gHB-uCD8dmUB0iABcT4YUlZleLbebrzE8ZuZRfztTRnQyXTnkTZmM4zOs_XLenq2-yO1J7UBLkkYRZVmhLN5_0PBeFi7CmGqJMU2b_wun0Oz2tZGepTnEtSOT8hnUaVeShhXYd9NbmYEup6Kg8zmf9oHrJKX_mjyA9H_MK_DO7Co7RziegHR4OrTzWElnW3oskStFPUtnlfrblsFJKOLSSwo7Y4cRxu_ds6jG_zjdG4v8A")
      .originHeader("https://darts-api.staging.platform.hmcts.net")
      .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36");
    
    Map<CharSequence, String> headers_0 = new HashMap<>();
    headers_0.put("Sec-Fetch-Dest", "empty");
    headers_0.put("Sec-Fetch-Mode", "cors");
    headers_0.put("Sec-Fetch-Site", "same-origin");
    headers_0.put("sec-ch-ua", "Not A(Brand\";v=\"99\", \"Google Chrome\";v=\"121\", \"Chromium\";v=\"121");
    headers_0.put("sec-ch-ua-mobile", "?0");
    headers_0.put("sec-ch-ua-platform", "Windows");
    headers_0.put("x-dtpc", "4$131465890_612h22vUNWIECAHPTPPPMPQGKHVUROGFMMSFUWV-0e0");


    ScenarioBuilder scn = scenario("APIAudioRequestDELETE")
      .exec(
        http("request_0")
          .delete("/audio-requests/transformed_media/10897")
          .headers(headers_0)
          .check(status().is(401))
      );

	  setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
  }
}
