package simulations.Scripts.Scenario.DartsPortal;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;
import io.gatling.javaapi.core.*;
import scala.util.Random;
import simulations.Scripts.RequestBodyBuilder.RequestBodyBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public final class DartsPortalRequestAudio {

   // private static final FeederBuilder<String> feeder = csv(AppConfig.AUDIO_REQUEST_POST_FILE_PATH).random();    
    private static final FeederBuilder<String> feeder = csv(AppConfig.DARTS_PORTAL_USERS_FILE_PATH).random();
    private static final Random randomNumber = new Random();

    private DartsPortalRequestAudio() {}

    public static ChainBuilder DartsPortalRequestAudioDownload() {
      return group("Darts Request Audio PLayBack/Download")
      .on(exec(feed(feeder))
      .exec(session -> {
              String xmlPayload = RequestBodyBuilder.buildSearchCaseRequestBody(session);
              return session.set("xmlPayload", xmlPayload);
          })
          .exec(http("Darts-Portal - Api - Cases - Search")
              .post("/api/cases/search")
              .headers(Headers.DartsPortalHeaders1)
              .body(StringBody(session -> session.get("xmlPayload")))
            )
          .pause(1)
          .exec(http("Darts-Portal - Auth - Is-authenticated")
              .get("/auth/is-authenticated?t=" + randomNumber.nextInt())
              .headers(Headers.DartsPortalHeaders1)
          )
          .exec(http("Darts-Portal - Api - Cases")
          .get("/api/cases/29709")
          .headers(Headers.DartsPortalHeaders1)
          )
          .exec(http("Darts-Portal - Api - Cases - Hearings")
          .get("/api/cases/29709")
          .headers(Headers.DartsPortalHeaders1)
          )
          .exec(http("Darts-Portal - Api - Cases - Transcripts")
          .get("/api/cases/29709/transcripts")
          .headers(Headers.DartsPortalHeaders1)
          )
          .pause(1)
          .exec(http("Darts-Portal - Auth - Is-authenticated")
          .get("/auth/is-authenticated?t=" + randomNumber.nextInt())
          .headers(Headers.DartsPortalHeaders1)
          )
          .exec(
            http("Darts-Portal - Api - Cases")
              .get("/api/cases/29709")
              .headers(Headers.DartsPortalHeaders1)
          )
          .exec(
            http("Darts-Portal - Api - Cases - Hearings")
              .get("/api/cases/29709/hearings")
              .headers(Headers.DartsPortalHeaders1)
              .check(status().saveAs("statusCode"))
              .check(jsonPath("$.[*].id").saveAs("getHearingId")) 
          )
          .exec(
            http("Darts-Portal - Api - Hearings - Events")
              .get("/api/hearings/#{getHearingId}/events")
              .headers(Headers.DartsPortalHeaders1)
              .check(jsonPath("$.[*].id").findRandom().saveAs("getEventId")) 
              .check(jsonPath("$.[*].name").findRandom().saveAs("getEventName")) 
              .check(jsonPath("$.[*].text").findRandom().saveAs("getEventText")) 
              .check(jsonPath("$.[*].timestamp").findRandom().saveAs("getEventTimestamp")) 
          )
          .exec(
            http("Darts-Portal - Api - Hearings - Audios")
              .get("/api/audio/hearings/#{getHearingId}/audios")
              .headers(Headers.DartsPortalHeaders1)
          )
          .exec(
            http("Darts-Portal - Api - Hearings - Transcripts")
              .get("/api/hearings/#{getHearingId}/transcripts")
              .headers(Headers.DartsPortalHeaders1)
          )
          .pause(2)
          .exec(
            http("Darts-Portal - Api - Audio-requests - Not-accessed-count")
              .get("/api/audio-requests/not-accessed-count")
              .headers(Headers.DartsPortalHeaders1)
          )
          .exec(
            http("Darts-Portal - Api - Transcriptions - Transcriber-counts")
              .get("/api/transcriptions/transcriber-counts")
              .headers(Headers.DartsPortalHeaders1)
          )
          .exec(
            http("Darts-Portal - Api - Transcriptions - Transcriber-counts")
              .get("/api/transcriptions/transcriber-counts")
              .headers(Headers.DartsPortalHeaders1)
          )
          .pause(2)
          .exec(session -> {
            String xmlPayload = RequestBodyBuilder.buildAudioRequestBody(session);
            return session.set("xmlPayload", xmlPayload);
          })
          .exec(
            http("Darts-Portal - Api - Audio-requests")
              .get("/api/audio-requests")
              .headers(Headers.DartsPortalHeaders1)
              .body(StringBody(session -> session.get("xmlPayload")))
          )          
          .exec(
            http("Darts-Portal - Api - Transcriptions - Transcriber-counts")
              .get("/api/transcriptions/transcriber-counts")
              .headers(Headers.DartsPortalHeaders1)
          )
          .exec(
            http("Darts-Portal - Api - Audio-requests - Not-accessed-count")
              .get("/api/audio-requests/not-accessed-count")
              .headers(Headers.DartsPortalHeaders1)
          )
          .exec(
            http("Darts-Portal - Api - Transcriptions - Transcriber-counts")
              .get("/api/transcriptions/transcriber-counts")
              .headers(Headers.DartsPortalHeaders1)
          )
      );
    }
}