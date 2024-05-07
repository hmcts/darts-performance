
// import java.time.Duration;
// import java.util.*;

// import io.gatling.javaapi.core.*;
// import io.gatling.javaapi.http.*;
// import io.gatling.javaapi.jdbc.*;

// import static io.gatling.javaapi.core.CoreDsl.*;
// import static io.gatling.javaapi.http.HttpDsl.*;
// import static io.gatling.javaapi.jdbc.JdbcDsl.*;

// public class DartsPortalRequestAudio extends Simulation {

//   {
//     HttpProtocolBuilder httpProtocol = http
//       .baseUrl("https://darts.staging.apps.hmcts.net")
//       .inferHtmlResources()
//       .acceptHeader("application/json, text/plain, */*")
//       .acceptEncodingHeader("gzip, deflate, br")
//       .acceptLanguageHeader("en-US,en;q=0.9")
//       .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36");
    

//     ScenarioBuilder scn = scenario("DartsPortalRequestAudio")
//       .exec(
//         http("request_0")
//           .post("/api/cases/search")
//           .headers(headers_0)
//           .body(RawFileBody("dartsportalrequestaudio/0000_request.json"))
//       )
//       .pause(1)
//       .exec(
//         http("request_1")
//           .get("/auth/is-authenticated?t=1709737442045")
//           .headers(headers_1)
//           .resources(
//             http("request_2")
//               .get("/api/cases/29709")
//               .headers(headers_2),
//             http("request_3")
//               .get("/api/cases/29709/hearings")
//               .headers(headers_3),
//             http("request_4")
//               .get("/api/cases/29709/transcripts")
//               .headers(headers_4)
//           )
//       )
//       .pause(1)
//       .exec(
//         http("request_5")
//           .get("/auth/is-authenticated?t=1709737443976")
//           .headers(headers_5)
//           .resources(
//             http("request_6")
//               .get("/api/cases/29709")
//               .headers(headers_6),
//             http("request_7")
//               .get("/api/cases/29709/hearings")
//               .headers(headers_7),
//             http("request_8")
//               .get("/api/hearings/30569/events")
//               .headers(headers_8),
//             http("request_9")
//               .get("/api/audio/hearings/30569/audios")
//               .headers(headers_9),
//             http("request_10")
//               .get("/api/hearings/30569/transcripts")
//               .headers(headers_10)
//           )
//       )
//       .pause(2)
//       .exec(
//         http("request_11")
//           .get("/api/audio-requests/not-accessed-count")
//           .headers(headers_11)
//           .resources(
//             http("request_12")
//               .get("/api/transcriptions/transcriber-counts")
//               .headers(headers_12),
//             http("request_13")
//               .get("/api/transcriptions/transcriber-counts")
//               .headers(headers_13)
//           )
//       )
//       .pause(3)
//       .exec(
//         http("request_14")
//           .post("/api/audio-requests")
//           .headers(headers_14)
//           .body(RawFileBody("dartsportalrequestaudio/0014_request.json"))
//       )
//       .pause(3)
//       .exec(
//         http("request_15")
//           .get("/api/transcriptions/transcriber-counts")
//           .headers(headers_15)
//           .resources(
//             http("request_16")
//               .get("/api/audio-requests/not-accessed-count")
//               .headers(headers_16),
//             http("request_17")
//               .get("/api/transcriptions/transcriber-counts")
//               .headers(headers_17)
//           )
//       )
//       .pause(6)
//       .exec(
//         http("request_18")
//           .post("/api/audio-requests")
//           .headers(headers_18)
//           .body(RawFileBody("dartsportalrequestaudio/0018_request.json"))
//       );

// 	  setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
//   }
// }
