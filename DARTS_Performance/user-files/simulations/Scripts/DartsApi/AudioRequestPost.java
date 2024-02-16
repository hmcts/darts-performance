package DartsApi;

import Headers.Headers;
import Utilities.AppConfig;
import Utilities.Feeders;
import Utilities.AppConfig.EnvironmentURL;
import Utilities.TimestampGenerator;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AudioRequestPost extends Simulation {
   
  {
    LocalDateTime startTimeParsed = LocalDateTime.now();

    // Generate random hour difference between 1 and 5
    LocalDateTime endTimeParsed = TimestampGenerator.getRandomTime(startTimeParsed, 1, 5); // Get end time as LocalDateTime
    FeederBuilder<String> feeder = csv(AppConfig.AUDIO_REQUEST_POST_FILE_PATH).random();

    // Format start and end times in the desired format
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
    String startTimeFormatted = startTimeParsed.format(formatter); // Format start time
    String endTimeFormatted = endTimeParsed.format(formatter); // Format end time

    final HttpProtocolBuilder httpProtocol = http
    .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT).httpsPort(AppConfig.PROXY_PORT))
    .baseUrl(EnvironmentURL.B2B_Login.getUrl())
    .inferHtmlResources()
    .acceptHeader("application/json")
    .acceptEncodingHeader("gzip, deflate, br")
    .contentTypeHeader("application/json")
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36");
  
    final ScenarioBuilder scn = scenario("APIToken")
  .exec(
    http("DARTS - Api - Token:GET")
      .get(EnvironmentURL.B2B_Token.getUrl())
      .headers(Headers.ApiHeaders)     
      .formParam("grant_type", EnvironmentURL.GRANT_TYPE.getUrl())
      .formParam("client_id", EnvironmentURL.CLINET_ID.getUrl())
      .formParam("client_secret", EnvironmentURL.CLIENT_SECRET.getUrl())
      .formParam("scope", EnvironmentURL.SCOPE.getUrl())
      .formParam("username", EnvironmentURL.DARTS_API_USERNAME.getUrl())      
      .formParam("password", EnvironmentURL.DARTS_API_PASSWORD.getUrl())
      .check(Feeders.saveBearerToken())                    
      ).exec(session -> {
        Object bearerToken = session.get("bearerToken");
        if (bearerToken != null) {
            System.out.println("bearerToken: " + bearerToken.toString());
        } else {
            System.out.println("No value saved using saveAs.");
        }
        return session;
    })
    .pause(5)

    //  final ScenarioBuilder scn2 = scenario("AudioRequest:Post")   
        .feed(feeder)
        .exec(
            http("DARTS - Api - AudioRequest:Post")
            .post(EnvironmentURL.DARTS_BASE_URL.getUrl() + "/audio-requests")
            .headers(Headers.AuthorizationHeaders)
            .header("authorization", "Bearer #{bearerToken}") // Use the extracted bearer token
            .body(StringBody("{\"hearing_id\": #{hea_id}, \"requestor\": #{usr_id}, \"start_time\": \"" + startTimeFormatted + "\", \"end_time\": \"" + endTimeFormatted + "\", \"request_type\": \"DOWNLOAD\"}")).asJson()
            .check(status().is(200))
        );
        setUp(
          scn.injectOpen(constantUsersPerSec(1).during(1)).protocols(httpProtocol)
            //  .andThen(
            //    scn2.injectOpen(constantUsersPerSec(1).during(1)).protocols(httpProtocol))
            );
      //);
  }
}
