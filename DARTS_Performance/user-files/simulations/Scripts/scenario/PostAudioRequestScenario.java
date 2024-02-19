package scenario;

import Headers.Headers;
import Utilities.AppConfig;
import Utilities.AppConfig.EnvironmentURL;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import java.time.LocalDateTime;
import RequestBodyBuilder.RequestBodyBuilder;

public final class PostAudioRequestScenario {
    private static final String AUDIO_REQUEST_FILE_PATH = AppConfig.AUDIO_REQUEST_POST_FILE_PATH;
    private static final FeederBuilder<String> feeder = csv(AUDIO_REQUEST_FILE_PATH).random();
    private PostAudioRequestScenario() {}

    public static ChainBuilder PostaudioRequest() {
          return group("Audio Request Group")
          .on(exec(feed(feeder))
          .exec(session -> {
              LocalDateTime startTime = LocalDateTime.now();
              LocalDateTime endTime = startTime.plusHours(3);
              return session.set("startTime", startTime).set("endTime", endTime);
          })
          .exec(
              http("DARTS - Api - AudioRequest:Post")
                  .post(EnvironmentURL.DARTS_BASE_URL.getUrl() + "/audio-requests")
                  .headers(Headers.AuthorizationHeaders)
                  .body(StringBody(session -> 
                      RequestBodyBuilder.buildRequestBody(
                          session.get("hea_id"),
                          session.get("usr_id"),
                          session.get("startTime"),
                          session.get("endTime")
                      )
                  )).asJson()
                  .check(status().saveAs("statusCode"))
                  .check(status().in(200, 409))
          )
      );
  }
  
}
