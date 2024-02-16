package DartsApi;

import Headers.Headers;
import Utilities.AppConfig;
import Utilities.AppConfig.EnvironmentURL;
import Utilities.RandomStringGenerator;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import java.util.UUID;

public class AudioPost extends Simulation { 

    String boundary = UUID.randomUUID().toString();
    private RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
    String caseName = randomStringGenerator.generateRandomString(10);
    
    FeederBuilder<String> feeder = csv(AppConfig.COURT_HOUSE_AND_COURT_ROOMS_FILE_PATH).random();

    final HttpProtocolBuilder httpProtocol = http
            .proxy(Proxy(AppConfig.PROXY_HOST, AppConfig.PROXY_PORT).httpsPort(AppConfig.PROXY_PORT))
            .baseUrl(EnvironmentURL.B2B_Login.getUrl())
            .contentTypeHeader("multipart/form-data; boundary=" + boundary);

            final ScenarioBuilder scn1 = scenario("AudioPost")
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
            .exec(http("DARTS - Api - Audios:POST")
                .post(EnvironmentURL.DARTS_BASE_URL.getUrl() + "/audios")
                .headers(Headers.AuthorizationHeaders)
                .bodyPart(StringBodyPart("metadata", "{\"started_at\": \"1972-11-25T17:28:59.936Z\", \"ended_at\": \"1972-11-25T18:28:59.936Z\", \"channel\": 1, \"total_channels\": 4, \"format\": \"mp2\", \"filename\": \"sample.mp2\", \"courthouse\": \"#{CourtHouseName}\", \"courtroom\": \"#{CourtRoom}\", \"file_size\": 937.96, \"checksum\": \"TVRMwq16b4mcZwPSlZj/iQ==\", \"cases\": [\"PerfCase_" +caseName + "\"] }")
                    .contentType("application/json")
                    .charset("US-ASCII")
                    .dispositionType("form-data"))
                .bodyPart(RawFileBodyPart("file", "C:\\Users\\a.cooper\\Desktop\\Performance.Testing\\DARTS\\Gatling_Base\\user-files\\Data\\sample.mp2")
                    .fileName("sample.mp2")
                    .contentType("audio/mpeg")
                    .dispositionType("form-data")));
            {
              setUp(scn1.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
            }    
}