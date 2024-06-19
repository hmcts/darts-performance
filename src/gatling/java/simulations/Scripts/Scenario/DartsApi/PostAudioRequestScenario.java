package simulations.Scripts.Scenario.DartsApi;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Utilities.Feeders;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import simulations.Scripts.RequestBodyBuilder.RequestBodyBuilder;

public final class PostAudioRequestScenario {

    private PostAudioRequestScenario() {}

    public static ChainBuilder PostaudioRequest() {
          return group("Audio-request:Post")
          .on(exec(feed(Feeders.createAudioRequestCSV()))
            .exec(session -> {
                String xmlPayload = RequestBodyBuilder.buildPOSTAudioRequestBody(session);
                return session.set("xmlPayload", xmlPayload);
            })
          .exec(
              http("DARTS - Api - Audio-request:Post")
                  .post(EnvironmentURL.DARTS_BASE_URL.getUrl() + "/audio-requests")
                  .headers(Headers.AuthorizationHeaders)
                  .body(StringBody(session -> session.get("xmlPayload"))).asJson()
                  .check(status().saveAs("statusCode"))
                  .check(status().in(200, 409))
          )
      );
  }
  
}
