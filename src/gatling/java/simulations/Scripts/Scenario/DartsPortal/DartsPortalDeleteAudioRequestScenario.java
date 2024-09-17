package simulations.Scripts.Scenario.DartsPortal;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Utilities.UserInfoLogger;
import io.gatling.javaapi.core.*;
import io.netty.util.internal.ThreadLocalRandom;
import scala.util.Random;
import simulations.Scripts.Utilities.NumberGenerator;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import java.util.Map;

public final class DartsPortalDeleteAudioRequestScenario {

    private static final String requestType = Feeders.getRandomRequestType();

    private DartsPortalDeleteAudioRequestScenario() {}

    public static ChainBuilder DartsPortalDeleteAudioRequestScenario() {
      return group("Darts Delete Audio Request")
      .on(exec(
          //Delete Audio Request.          
            http("Darts-Portal - Auth - Is-authenticated")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + NumberGenerator.generateRandom13DigitNumber())
              .headers(Headers.getHeaders(14))
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
            http("Darts-Portal - Api - Audio-Requests - V2 - Expired - True")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio-requests/v2?expired=true")
              .headers(Headers.getHeaders(8))
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Audio-Requests - V2 - Expired - True"))
          
          .exec(session -> {
            // Clear previously stored IDs from the session
            session = session.remove("getTransformedMediaId");
            session = session.remove("getCaseId");
            session = session.remove("getHearingId");
            return session;
        })        
          .exec(
            http("Darts-Portal - Api - Audio-Requests - V2 - Expired - False")
            .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio-requests/v2?expired=false")
            .headers(Headers.getHeaders(8))
            .check(jsonPath("$.transformed_media_details[*].transformed_media_id").findAll().saveAs("allTransformedMediaIds"))
            .check(jsonPath("$.transformed_media_details[*].case_id").findAll().saveAs("allCaseIds"))
            .check(jsonPath("$.transformed_media_details[*].hearing_id").findAll().saveAs("allHearingIds"))
            .check(jsonPath("$.transformed_media_details[*].request_type").findAll().saveAs("allRequestType"))

            .check(status().is(200))
            .check(status().saveAs("status"))
          )
          .exec(session -> {
            // Get the saved lists of IDs
            java.util.List<String> transformedMediaIds = session.getList("allTransformedMediaIds");
            java.util.List<String> caseIds = session.getList("allCaseIds");
            java.util.List<String> hearingIds = session.getList("allHearingIds");
            java.util.List<String> requestType = session.getList("allRequestType");

            // Check if lists are not empty to avoid IndexOutOfBoundsException
            if (transformedMediaIds != null && !transformedMediaIds.isEmpty() && caseIds != null && !caseIds.isEmpty()) {
                // Get a random index from the list
                int randomIndex = ThreadLocalRandom.current().nextInt(transformedMediaIds.size());
        
                // Store the selected IDs back in the session
                String selectedTransformedMediaId = transformedMediaIds.get(randomIndex);
                String selectedCaseId = caseIds.get(randomIndex);
                String selectedHearingId = hearingIds.get(randomIndex);
                String selectedRequestType = requestType.get(randomIndex);

                session = session.set("getTransformedMediaId", selectedTransformedMediaId);
                session = session.set("getCaseId", selectedCaseId);
                session = session.set("getHearingId", selectedHearingId);
                session = session.set("getRequestType", selectedRequestType);

                // Print the IDs
                System.out.println("getTransformedMediaId: " + selectedTransformedMediaId);
                System.out.println("getCaseId: " + selectedCaseId);
                System.out.println("getHearingId: " + selectedHearingId);
                System.out.println("getRequestType: " + selectedRequestType);

            } else {
                System.out.println("No IDs found in the response.");
            }
        
            return session;
            }
          )
          // Log detailed error message
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Audio-Requests - V2 - Expired - False"))
          
          .pause(5)
          .exec(
            http("Darts-Portal - Auth - Is-authenticated")
            .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + NumberGenerator.generateRandom13DigitNumber())
            .headers(Headers.getHeaders(14))
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
            http("Darts-Portal - Api - Audio-Requests - Transformed_Media - Patch")
              .patch(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio-requests/transformed_media/#{getTransformedMediaId}")
              .headers(Headers.getHeaders(9))
              .body(StringBody("{}"))
              .check(status().is(204))
              .check(status().saveAs("status"))
              .checkIf(session -> session.getInt("status") == 403).then(
                jsonPath("$.type").saveAs("errorType"),
                jsonPath("$.title").saveAs("errorTitle"),
                jsonPath("$.status").saveAs("errorStatus")
            )
          )
          .exec(session -> {
              int statusCode = session.getInt("status");
              if (statusCode == 403) {
                  String errorType = session.getString("errorType");
                  String errorTitle = session.getString("errorTitle");
                  int errorStatus = session.getInt("errorStatus");

                  System.out.println("Received 403 Conflict. Details:");
                  System.out.println("Type: " + errorType);
                  System.out.println("Title: " + errorTitle);
                  System.out.println("Status: " + errorStatus);

                  // Mark the session as succeeded to prevent this from counting as a failure. 409 response is "A transcription already exists with these properties"
                  return session.markAsSucceeded();
              } else {
                  // Handle other status codes if necessary
                  return session;
              }
          })           
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Audio-Requests - Transformed_Media"))
          
          .exec(
            http("Darts-Portal - Api - Cases")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/#{getCaseId}")
              .headers(Headers.getHeaders(12))
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Cases"))
          
          .exec(
            http("Darts-Portal - Api - Hearings - Events")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/hearings/#{getHearingId}/events")
              .headers(Headers.getHeaders(12))
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Hearings - Events"))

          .doIfEquals("#{getRequestType}", "PLAYBACK").then(
              exec(
                  http("Darts-Portal - Api - Audio-Requests - Playback - Transformed_media_id - HEAD")
                      .head(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio-requests/playback?transformed_media_id=#{getTransformedMediaId}")
                      .headers(Headers.getHeaders(12))
                      .check(status().is(200))
                      .check(status().saveAs("status"))
              )
              .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Audio-Requests - Playback - Transformed_media_id - HEAD"))
          
              .exec(
                  http("Darts-Portal - Api - Audio-Requests - Playback - Transformed_media_id - GET")
                      .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio-requests/playback?transformed_media_id=#{getTransformedMediaId}")
                      .headers(Headers.getHeaders(13))
                      .check(status().is(206))
                      .check(status().saveAs("status"))
              )
              .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Audio-Requests - Playback - Transformed_media_id - GET"))
          
              .exec(
                  http("Darts-Portal - Api - Audio-Requests - Playback - Transformed_media_id - GET - Download")
                      .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio-requests/playback?transformed_media_id=#{getTransformedMediaId}")
                      .headers(Headers.getHeaders(14))
                      .check(status().is(200))
                      .check(status().saveAs("status"))
              )
              .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Audio-Requests - Playback - Transformed_media_id - GET - Download"))    
          ) 
          .doIf(session -> session.getString("getRequestType").equals("DOWNLOAD")).then(
              exec(
                http("Darts-Portal - Api - Audio-Requests - Download - Transformed_media_id - GET - Download")
                .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio-requests/download?transformed_media_id=#{getTransformedMediaId}")
                .headers(Headers.getHeaders(14))
                .check(status().is(200))
                .check(status().saveAs("status"))
              )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Audio-Requests - Download - Transformed_media_id - GET - Download"))    
          )       
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
            http("Darts-Portal - Api - Audio-Requests - Transformed_Media - Delete")
              .delete(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio-requests/transformed_media/#{getTransformedMediaId}")
              .headers(Headers.getHeaders(11))
              .check(status().is(204))
              .check(status().saveAs("status"))
              .checkIf(session -> session.getInt("status") == 403).then(
                jsonPath("$.type").saveAs("errorType"),
                jsonPath("$.title").saveAs("errorTitle"),
                jsonPath("$.status").saveAs("errorStatus")
            )
          )
          .exec(session -> {
              int statusCode = session.getInt("status");
              if (statusCode == 403) {
                  String errorType = session.getString("errorType");
                  String errorTitle = session.getString("errorTitle");
                  int errorStatus = session.getInt("errorStatus");

                  System.out.println("Received 403 Conflict. Details:");
                  System.out.println("Type: " + errorType);
                  System.out.println("Title: " + errorTitle);
                  System.out.println("Status: " + errorStatus);

                  // Mark the session as succeeded to prevent this from counting as a failure. 409 response is "A transcription already exists with these properties"
                  return session.markAsSucceeded();
              } else {
                  // Handle other status codes if necessary
                  return session;
              }
          })          
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Audio-Requests - Transformed_Media - Delete"))
          
          .exec(
            http("Darts-Portal - Auth - Is-authenticated")
            .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + NumberGenerator.generateRandom13DigitNumber())
            .headers(Headers.getHeaders(14))
          )
          .exec(
            http("Darts-Portal - User - Refresh - Profile")
              .post(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/user/refresh-profile")
              .headers(Headers.CommonHeaders)
              .check(status().is(200))
              .check(status().saveAs("status"))
          )

          .exec(
            http("Darts-Portal - Api - Audio-Requests - V2 - Expired - True")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio-requests/v2?expired=true")
              .headers(Headers.getHeaders(8))
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Audio-Requests - V2 - Expired - True"))
          
          .exec(
            http("Darts-Portal - Api - Audio-Requests - V2 - Expired - False")
            .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio-requests/v2?expired=false")
            .headers(Headers.getHeaders(8))
            .check(status().is(200))
            .check(status().saveAs("status"))
          )                 
          // Log detailed error message
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Audio-Requests - V2 - Expired - False"))
          
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

