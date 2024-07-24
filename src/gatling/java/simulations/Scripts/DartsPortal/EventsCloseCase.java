package simulations.Scripts.DartsPortal;


import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;

import java.util.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class EventsCloseCase extends Simulation {

    {
        HttpProtocolBuilder httpProtocol = http
            .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT))
            .baseUrl("https://darts-api.test.platform.hmcts.net")
            .inferHtmlResources()
            .acceptHeader("application/json")
            .acceptEncodingHeader("gzip, deflate, br")
            .acceptLanguageHeader("en-US,en;q=0.9")
            .authorizationHeader("Bearer eyJhbGciOiJSUzI1NiIsImtpZCI6Ilg1ZVhrNHh5b2pORnVtMWtsMll0djhkbE5QNC1jNTdkTzZRR1RWQndhTmsiLCJ0eXAiOiJKV1QifQ.eyJhdWQiOiI4NTExOGVlMi02NTQ0LTQxODAtYTE2ZS05OTYxNTM2NWYyMDkiLCJpc3MiOiJodHRwczovL2htY3Rzc3RnZXh0aWQuYjJjbG9naW4uY29tLzhiMTg1ZjhiLTY2NWQtNGJiMy1hZjRhLWFiN2VlNjFiOTMzNC92Mi4wLyIsImV4cCI6MTcyMTQyMzM2NSwibmJmIjoxNzIxMzgwMTY1LCJpZHAiOiJMb2NhbEFjY291bnQiLCJvaWQiOiI5Nzk3YTRmYy04ZTYyLTQwOTUtYmRhNi0wNGU3NzQ3YjA1ZTUiLCJzdWIiOiI5Nzk3YTRmYy04ZTYyLTQwOTUtYmRhNi0wNGU3NzQ3YjA1ZTUiLCJnaXZlbl9uYW1lIjoiRGFydHMgR2xvYmFsIiwiZmFtaWx5X25hbWUiOiJUZXN0IFVzZXIiLCJ0ZnAiOiJCMkNfMV9yb3BjX2RhcnRzX3NpZ25pbiIsInNjcCI6IkZ1bmN0aW9uYWwuVGVzdCIsImF6cCI6Ijg1MTE4ZWUyLTY1NDQtNDE4MC1hMTZlLTk5NjE1MzY1ZjIwOSIsInZlciI6IjEuMCIsImlhdCI6MTcyMTM4MDE2NX0.j-ekK0K_mIRWEZZLJCGskjyVPqtt4adAGlCDT9MdWU-7ggOnyFhx0xJjzYXM4oxsCjkqkNntN61vGOKkxa_cHHLx3HAGAMa3Q9V5q8MHfwCnoYCakrs9niQTaHIk2J2BQLZKifAuJ_Z--UPVstuJtS6UclMhlA5ur-GvWdybzbQFAI_JXhfIVzbNw9g8Ce-V1RL8hsdSndOs4UbtKUku9WjDJnLRceHK9eVUD3cVs6FPUmgVZHVVZLuu1fDLW3WtYbUA34dAWpMoA8lsN8QvfHcP6sbfgLxjbONCoTsd3S-lVAFvoEt1969rS8xpeTkybDk2tyKy2385PxIfzkAJHg")
            .contentTypeHeader("application/json")
            .originHeader("https://darts-api.test.platform.hmcts.net")
            .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36");

        Map<CharSequence, String> headers_0 = new HashMap<>();
        headers_0.put("Sec-Fetch-Dest", "empty");
        headers_0.put("Sec-Fetch-Mode", "cors");
        headers_0.put("Sec-Fetch-Site", "same-origin");
        headers_0.put("sec-ch-ua", "\"Not/A)Brand\";v=\"8\", \"Chromium\";v=\"126\", \"Google Chrome\";v=\"126\"");
        headers_0.put("sec-ch-ua-mobile", "?0");
        headers_0.put("sec-ch-ua-platform", "Windows");

        String sql = "WITH random_case AS (SELECT cas_id FROM darts.court_case ORDER BY RANDOM() LIMIT 1), " +
                     "random_courtroom AS (SELECT cr.courtroom_name FROM darts.courtroom AS cr INNER JOIN darts.court_case AS cc ON cr.cth_id = cc.cth_id " +
                     "WHERE cc.cas_id = (SELECT cas_id FROM random_case) ORDER BY RANDOM() LIMIT 1) " +
                     "SELECT cc.cas_id, cc.case_number, cc.cth_id, ch.courthouse_name, rc.courtroom_name FROM darts.court_case AS cc " +
                     "INNER JOIN darts.courthouse AS ch ON cc.cth_id = ch.cth_id " +
                     "INNER JOIN random_courtroom AS rc ON rc.courtroom_name IS NOT NULL WHERE cc.cas_id = (SELECT cas_id FROM random_case);";

        // Create the JDBC feeder
        FeederBuilder<Object> feeder = Feeders.jdbcFeeder(sql);

        ScenarioBuilder scn = scenario("EventsCloseCase")
            .feed(feeder)
            .exec(session -> {
                // Extract the data from the feeder and set it to the session
                String cas_id = session.getString("cas_id");
                String case_number = session.getString("case_number");
                String cth_id = session.getString("cth_id");
                String courthouse_name = session.getString("courthouse_name");
                String courtroom_name = session.getString("courtroom_name");
                
                return session
                    .set("cas_id", cas_id)
                    .set("case_number", case_number)
                    .set("cth_id", cth_id)
                    .set("courthouse_name", courthouse_name)
                    .set("courtroom_name", courtroom_name);
            })
            .exec(
                http("request_0")
                    .post("/events")
                    .headers(headers_0)
                    .body(StringBody(session -> {
                        // Build the JSON body using session variables
                        return "{" +
                            "\"cas_id\": \"" + session.getString("cas_id") + "\"," +
                            "\"case_number\": \"" + session.getString("case_number") + "\"," +
                            "\"cth_id\": \"" + session.getString("cth_id") + "\"," +
                            "\"courthouse_name\": \"" + session.getString("courthouse_name") + "\"," +
                            "\"courtroom_name\": \"" + session.getString("courtroom_name") + "\"" +
                        "}";
                    }))
                    .check(status().is(200))
            );

        setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
    }
}
