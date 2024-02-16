package DartsApi;

import Headers.Headers;
import Utilities.RandomStringGenerator;
import Utilities.AppConfig;
import Utilities.AppConfig.EnvironmentURL;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class TranscriptionPOST extends Simulation {

  private RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
  String randomComment = randomStringGenerator.generateRandomString(10);
  FeederBuilder feeder = csv(AppConfig.TRANSCRIPTION_POST_FILE_PATH).random();
  
  private HttpProtocolBuilder httpProtocol = http
    .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT).httpsPort(AppConfig.PROXY_PORT))
    .baseUrl(EnvironmentURL.B2B_Login.getUrl())
    .inferHtmlResources()
    .acceptHeader("application/json")
    .acceptEncodingHeader("gzip, deflate, br")
    .contentTypeHeader("application/json")
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36");
  
  private ScenarioBuilder scn = scenario("APIToken")
  .exec(
    http("DARTS - Api - Token:GET")
      .get(EnvironmentURL.B2B_Token.getUrl())
      .headers(Headers.ApiHeaders)     
      .formParam("grant_type", EnvironmentURL.GRANT_TYPE.getUrl())
      .formParam("client_id", EnvironmentURL.CLINET_ID.getUrl())
      .formParam("client_secret", EnvironmentURL.CLIENT_SECRET.getUrl())
      .formParam("scope", EnvironmentURL.SCOPE.getUrl())
      .formParam("username", EnvironmentURL.DARTS_API_GLOBAL_USERNAME.getUrl())      
      .formParam("password", EnvironmentURL.DARTS_API_PASSWORD.getUrl())
      .check(jsonPath("$.access_token").saveAs("bearerToken"))                    
  ).exec(session -> {
      Object bearerToken = session.get("bearerToken");
      if (bearerToken != null) {
          System.out.println("bearerToken: " + bearerToken.toString());
      } else {
          System.out.println("No value saved using saveAs.");
      }
      return session;
    })      
    .pause(1)
    .feed(feeder)
    .exec(
      http("DARTS - Api - Transcription:POST")
        .post(EnvironmentURL.DARTS_BASE_URL.getUrl() + "/transcriptions")
        .headers(Headers.AuthorizationHeaders)
        .body(StringBody("{\"hearing_id\": \"#{hearing_id}\",\"case_id\": \"#{case_id}\",\"transcription_urgency_id\": \"#{transcription_urgency_id}\",\"transcription_type_id\": \"#{transcription_type_id}\",\"comment\": \"" + randomComment + "\"}")).asJson()
        .check(status().is(200))
        .check(jsonPath("$.transcription_id").saveAs("transcriptionId")) 
    )
    .exec(session -> {
        Object transcriptionId = session.get("transcriptionId");
        if (transcriptionId != null) {
            System.out.println("Created Transcription Id: " + transcriptionId.toString());
        } else {
            System.out.println("No value saved using saveAs.");
        }
        return session;
    });
  {
	  setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
  }
}
