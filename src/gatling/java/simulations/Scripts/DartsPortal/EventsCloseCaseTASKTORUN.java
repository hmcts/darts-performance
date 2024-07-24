package simulations.Scripts.DartsPortal;

import java.time.Duration;
import java.util.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class EventsCloseCaseTASKTORUN extends Simulation {

  {
    HttpProtocolBuilder httpProtocol = http
      .baseUrl("https://darts-api.test.platform.hmcts.net")
      .inferHtmlResources()
      .acceptHeader("*/*")
      .acceptEncodingHeader("gzip, deflate, br")
      .acceptLanguageHeader("en-US,en;q=0.9")
      .authorizationHeader("Bearer eyJhbGciOiJSUzI1NiIsImtpZCI6Ilg1ZVhrNHh5b2pORnVtMWtsMll0djhkbE5QNC1jNTdkTzZRR1RWQndhTmsiLCJ0eXAiOiJKV1QifQ.eyJhdWQiOiI4NTExOGVlMi02NTQ0LTQxODAtYTE2ZS05OTYxNTM2NWYyMDkiLCJpc3MiOiJodHRwczovL2htY3Rzc3RnZXh0aWQuYjJjbG9naW4uY29tLzhiMTg1ZjhiLTY2NWQtNGJiMy1hZjRhLWFiN2VlNjFiOTMzNC92Mi4wLyIsImV4cCI6MTcyMTQyMzM2NSwibmJmIjoxNzIxMzgwMTY1LCJpZHAiOiJMb2NhbEFjY291bnQiLCJvaWQiOiI5Nzk3YTRmYy04ZTYyLTQwOTUtYmRhNi0wNGU3NzQ3YjA1ZTUiLCJzdWIiOiI5Nzk3YTRmYy04ZTYyLTQwOTUtYmRhNi0wNGU3NzQ3YjA1ZTUiLCJnaXZlbl9uYW1lIjoiRGFydHMgR2xvYmFsIiwiZmFtaWx5X25hbWUiOiJUZXN0IFVzZXIiLCJ0ZnAiOiJCMkNfMV9yb3BjX2RhcnRzX3NpZ25pbiIsInNjcCI6IkZ1bmN0aW9uYWwuVGVzdCIsImF6cCI6Ijg1MTE4ZWUyLTY1NDQtNDE4MC1hMTZlLTk5NjE1MzY1ZjIwOSIsInZlciI6IjEuMCIsImlhdCI6MTcyMTM4MDE2NX0.j-ekK0K_mIRWEZZLJCGskjyVPqtt4adAGlCDT9MdWU-7ggOnyFhx0xJjzYXM4oxsCjkqkNntN61vGOKkxa_cHHLx3HAGAMa3Q9V5q8MHfwCnoYCakrs9niQTaHIk2J2BQLZKifAuJ_Z--UPVstuJtS6UclMhlA5ur-GvWdybzbQFAI_JXhfIVzbNw9g8Ce-V1RL8hsdSndOs4UbtKUku9WjDJnLRceHK9eVUD3cVs6FPUmgVZHVVZLuu1fDLW3WtYbUA34dAWpMoA8lsN8QvfHcP6sbfgLxjbONCoTsd3S-lVAFvoEt1969rS8xpeTkybDk2tyKy2385PxIfzkAJHg")
      .originHeader("https://darts-api.test.platform.hmcts.net")
      .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36");
    
    Map<CharSequence, String> headers_0 = new HashMap<>();
    headers_0.put("Sec-Fetch-Dest", "empty");
    headers_0.put("Sec-Fetch-Mode", "cors");
    headers_0.put("Sec-Fetch-Site", "same-origin");
    headers_0.put("sec-ch-ua", "Not/A)Brand\";v=\"8\", \"Chromium\";v=\"126\", \"Google Chrome\";v=\"126");
    headers_0.put("sec-ch-ua-mobile", "?0");
    headers_0.put("sec-ch-ua-platform", "Windows");


    ScenarioBuilder scn = scenario("EventsCloseCaseTASKTORUN")
      .exec(
        http("request_0")
          .post("/admin/automated-tasks/11/run")
          .headers(headers_0)
      );

	  setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
  }
}
