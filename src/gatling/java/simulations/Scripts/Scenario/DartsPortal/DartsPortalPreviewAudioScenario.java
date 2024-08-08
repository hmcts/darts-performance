package simulations.Scripts.Scenario.DartsPortal;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;
import io.gatling.javaapi.core.*;
import scala.util.Random;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public final class DartsPortalPreviewAudioScenario {

    private static final Random randomNumber = new Random();

    private DartsPortalPreviewAudioScenario() {}

    public static ChainBuilder DartsPortalPreviewAudio() {
      return exec(session -> session.set("status", 202)) // Initialize the status to 202
          .asLongAsDuring(session -> session.getInt("status") != 200, java.time.Duration.ofMinutes(5))
          .on(
              exec(
                  http("Darts-Portal - Api - Audio - Preview")
                      .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio/preview/#{extractedId}")
                      .check(status().saveAs("status"))  // Save the status code to session
              )
              .pause(5) // Pause for 5 seconds between retries
          )
          .exitHereIfFailed() // Exit the chain if the request fails
          .pause(3)
          .exec(http("Darts-Portal - Auth - Is-authenticated")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + randomNumber.nextInt())
              .headers(Headers.CommonHeaders)
          );
    }   
}