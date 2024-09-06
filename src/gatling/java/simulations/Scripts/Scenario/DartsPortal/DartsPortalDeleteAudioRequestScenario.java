package simulations.Scripts.Scenario.DartsPortal;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Utilities.UserInfoLogger;
import io.gatling.javaapi.core.*;
import io.netty.util.internal.ThreadLocalRandom;
import scala.util.Random;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import java.util.Map;

public final class DartsPortalDeleteAudioRequestScenario {

    private static final Random randomNumber = new Random();
    private static final String requestType = Feeders.getRandomRequestType();

    private DartsPortalDeleteAudioRequestScenario() {}

    public static ChainBuilder DartsPortalDeleteAudioRequestScenario() {
      return group("Darts Request Transcription")
      .on(exec(
          //Delete Audio Request.          
            http("Darts-Portal - Auth - Is-authenticated")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + randomNumber.nextInt())
              .headers(Headers.CommonHeaders)
          )
          .exec(
            http("Darts-Portal - User - Refresh - Profile")
              .post(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/user/refresh-profile")
              .headers(Headers.getHeaders(11))
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - User - Refresh - Profile"))
         
          .exec(
            http("Darts-Portal - Api - Audio-Requests - V2")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio-requests/v2?expired=true")
              .headers(Headers.getHeaders(8))
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Audio-Requests - V2"))
          
          .exec(
            http("Darts-Portal - Api - Audio-Requests - V2")
            .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio-requests/v2?expired=false")
            .headers(Headers.getHeaders(8))
            .check(jsonPath("$.transformed_media_details[*].transformed_media_id").findAll().saveAs("allTransformedMediaIds"))
            .check(jsonPath("$.transformed_media_details[*].case_id").findAll().saveAs("allCaseIds"))
            .check(jsonPath("$.transformed_media_details[*].hearing_id").findAll().saveAs("allHearingIds"))

            .check(status().is(200))
            .check(status().saveAs("status"))
          )
          .exec(session -> {
            // Get the saved lists of IDs
            java.util.List<String> transformedMediaIds = session.getList("allTransformedMediaIds");
            java.util.List<String> caseIds = session.getList("allCaseIds");
            java.util.List<String> hearingIds = session.getList("allHearingIds");

            // Check if lists are not empty to avoid IndexOutOfBoundsException
            if (transformedMediaIds != null && !transformedMediaIds.isEmpty() && caseIds != null && !caseIds.isEmpty()) {
                // Get a random index from the array
                int randomIndex = ThreadLocalRandom.current().nextInt(transformedMediaIds.size());
        
                // Store the selected IDs back in the session
                session = session.set("getTransformedMediaId", transformedMediaIds.get(randomIndex));
                session = session.set("getCaseId", caseIds.get(randomIndex));
                session = session.set("getHearingId", hearingIds.get(randomIndex));

              }
        
                return session;
            }
          )
          // Log detailed error message
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Audio-Requests - V2"))
          
          .pause(5)
          .exec(
            http("Darts-Portal - Auth - Is-authenticated")
            .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + randomNumber.nextInt())
            .headers(Headers.CommonHeaders)
          )
          .exec(
            http("Darts-Portal - User - Refresh - Profile")
              .post(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/user/refresh-profile")
              .headers(Headers.CommonHeaders)
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - User - Refresh - Profile"))
          
          .exec(
            http("Darts-Portal - Api - Audio-Requests - Transformed_Media")
              .patch(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio-requests/transformed_media/#{getTransformedMediaId}")
              .headers(Headers.getHeaders(9))
              .body(StringBody("{}"))
              .check(status().is(204))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Audio-Requests - Transformed_Media"))
          
          .exec(
            http("Darts-Portal - Api - Cases")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/#{getCaseId}")
              .headers(Headers.getHeaders(12))
              .body(StringBody("{}"))
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Cases"))
          
          .exec(
            http("Darts-Portal - Api - Hearings - Events")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/hearings/#{getHearingId}/events")
              .headers(Headers.getHeaders(12))
              .body(StringBody("{}"))
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Hearings - Events"))
          
          .exec(
            http("Darts-Portal - Api - Audio-Requests - Playback")
              .head(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio-requests/playback?transformed_media_id=#{getTransformedMediaId}")
              .headers(Headers.getHeaders(12))
              .body(StringBody("{}"))
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Cases"))
          
          .pause(5)
          .exec(
            http("Darts-Portal - Api - Audio-requests - Not-accessed-count")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio-requests/not-accessed-count")
              .headers(Headers.getHeaders(8))
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Audio-requests - Not-accessed-count"))

          .exec(
            http("Darts-Portal - Api - Transcriptions - Transcriber-Counts")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/transcriptions/transcriber-counts")
              .headers(Headers.getHeaders(12))
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Transcriptions - Transcriber-Counts"))
          
          .pause(10)
          .exec(
            http("Darts-Portal - Api - Audio-Requests - Transformed_Media")
              .delete(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio-requests/transformed_media/#{getTransformedMediaId}")
              .headers(Headers.getHeaders(11))
              .check(status().is(204))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Audio-Requests - Transformed_Media"))
          
          .exec(
            http("Darts-Portal - Auth - Is-authenticated")
            .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + randomNumber.nextInt())
            .headers(Headers.CommonHeaders)
          )
          .exec(
            http("Darts-Portal - User - Refresh - Profile")
              .post(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/user/refresh-profile")
              .headers(Headers.CommonHeaders)
              .check(status().is(200))
              .check(status().saveAs("status"))
          )

          .exec(
            http("Darts-Portal - Api - Audio-Requests - V2")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio-requests/v2?expired=true")
              .headers(Headers.getHeaders(8))
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Audio-Requests - V2"))
          
          .exec(
            http("Darts-Portal - Api - Audio-Requests - V2")
            .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio-requests/v2?expired=false")
            .headers(Headers.getHeaders(8))
            .check(jsonPath("$.transformed_media_details[*].transformed_media_id").findAll().saveAs("allTransformedMediaIds"))
            .check(jsonPath("$.transformed_media_details[*].case_id").findAll().saveAs("allCaseIds"))
            .check(jsonPath("$.transformed_media_details[*].hearing_id").findAll().saveAs("allHearingIds"))

            .check(status().is(200))
            .check(status().saveAs("status"))
          )
          .exec(session -> {
            // Get the saved lists of IDs
            java.util.List<String> transformedMediaIds = session.getList("allTransformedMediaIds");
            java.util.List<String> caseIds = session.getList("allCaseIds");
            java.util.List<String> hearingIds = session.getList("allHearingIds");

            // Check if lists are not empty to avoid IndexOutOfBoundsException
            if (transformedMediaIds != null && !transformedMediaIds.isEmpty() && caseIds != null && !caseIds.isEmpty()) {
                // Get a random index from the array
                int randomIndex = ThreadLocalRandom.current().nextInt(transformedMediaIds.size());
        
                // Store the selected IDs back in the session
                session = session.set("getTransformedMediaId", transformedMediaIds.get(randomIndex));
                session = session.set("getCaseId", caseIds.get(randomIndex));
                session = session.set("getHearingId", hearingIds.get(randomIndex));

              }
        
                return session;
            }
          )                 
          // Log detailed error message
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Audio-Requests - V2"))
          
          .exec(
            http("Darts-Portal - Api - Transcriptions - Transcriber-Counts")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/transcriptions/transcriber-counts")
              .headers(Headers.getHeaders(12))
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Transcriptions - Transcriber-Counts"))
          
          .exec(
            http("Darts-Portal - Api - Audio-requests - Not-accessed-count")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio-requests/not-accessed-count")
              .headers(Headers.getHeaders(8))
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Audio-requests - Not-accessed-count"))       
        
      );
    }
}

