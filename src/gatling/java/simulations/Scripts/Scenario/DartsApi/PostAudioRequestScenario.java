package simulations.Scripts.Scenario.DartsApi;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Utilities.Feeders;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import java.time.LocalDateTime;
import simulations.Scripts.RequestBodyBuilder.RequestBodyBuilder;

public final class PostAudioRequestScenario {

    private PostAudioRequestScenario() {}

    public static ChainBuilder PostaudioRequest() {
          return group("Audio Request Group")
          .on(exec(feed(Feeders.createAudioRequestCSV()))
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
                      RequestBodyBuilder.buildPOSTAudioRequestBody(
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
