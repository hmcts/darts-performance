package simulations.Scripts.Scenario.DartsPortal;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Utilities.UserInfoLogger;
import io.gatling.javaapi.core.*;
import scala.util.Random;
import simulations.Scripts.RequestBodyBuilder.RequestBodyBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public final class DartsPortalRequestTranscriptionScenario {

    private static final Random randomNumber = new Random();
    private static final String requestType = Feeders.getRandomRequestType();

    private DartsPortalRequestTranscriptionScenario() {}

    public static ChainBuilder DartsPortalRequestTranscription() {
      return group("Darts Request Transcription")
      .on(exec(session -> {
                Object getCaseId = session.get("getCaseId");
                if (getCaseId != null) {
                    System.out.println("getCaseId: " + getCaseId.toString());
                } else {
                    System.out.println("No value saved using saveAs.");
                }
                
                Object errorTitle = session.get("errorTitle");
                if (errorTitle != null) {
                    String errorMessage = "Request failed with error: " + errorTitle.toString();
                    System.out.println(errorMessage);
                    throw new RuntimeException(errorMessage); // Fail the test by throwing an exception
                }
                return session;
            }
          )
          .exec(session -> {
                Object getHearings = session.get("getHearings");
                if (getHearings != null) {
                    System.out.println("getHearings from Cases - Hearings: " + getHearings.toString());
                } else {
                    System.out.println("No value saved using saveAs.");
                }
                Object getHearingId = session.get("getHearingId");
                if (getHearingId != null) {
                    System.out.println("getHearingId from Cases - Hearings: " + getHearingId.toString());
                } else {
                    System.out.println("No value saved using saveAs.");
                }
                return session;
            }
          )
          //Request Transcription.
          .exec(
            http("Darts-Portal - Auth - Is-authenticated")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + randomNumber.nextInt())
              .headers(Headers.CommonHeaders)
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Auth - Is-authenticated"))

          .exec(
            http("Darts-Portal - User - Refresh - Profile")
              .post(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/user/refresh-profile")
              .headers(Headers.CommonHeaders)
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - User - Refresh - Profile"))

          .exec(
            http("Darts-Portal - Api - Transcriptions - Types")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/transcriptions/types")
              .headers(Headers.CommonHeaders)
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Transcriptions - Types"))

          .exec(
            http("Darts-Portal - Api - Cases - Hearings")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/#{getCaseId}/hearings")
              .headers(Headers.CommonHeaders)
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Cases - Hearings"))

          .exec(
            http("Darts-Portal - Api - Transcriptions - Urgencies")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/transcriptions/urgencies")
              .headers(Headers.CommonHeaders)
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Transcriptions - Urgencies"))

          .exec(
            http("Darts-Portal - Api - Cases")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/#{getCaseId}")
              .headers(Headers.CommonHeaders)
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Cases"))

          .exec(
            http("Darts-Portal - Api - Hearings - Audios")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio/hearings/#{getHearings.id}/audios")
              .headers(Headers.CommonHeaders)
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Hearings - Audios"))

          .exec(
            http("Darts-Portal - Api - Hearings - Events")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/hearings/#{getHearings.id}/events")
              .headers(Headers.CommonHeaders)
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Hearings - Events"))

          .exec(session -> {
            String xmlPayload = RequestBodyBuilder.buildTranscriptionsBody(session);
            return session.set("xmlPayload", xmlPayload);
          })
          // Request Transcriptions
          .exec(
              http("Darts-Portal - Api - Transcriptions")
                  .post(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/transcriptions")
                  .headers(Headers.getHeaders(8))
                  .body(StringBody(session -> session.get("xmlPayload"))).asJson()
                  .check(status().saveAs("status"))
                  .checkIf(session -> session.getInt("status") == 409).then(
                      jsonPath("$.type").saveAs("errorType"),
                      jsonPath("$.title").saveAs("errorTitle"),
                      jsonPath("$.status").saveAs("errorStatus")
                  )
          )
          .exec(session -> {
              int statusCode = session.getInt("status");
              if (statusCode == 409) {
                  String errorType = session.getString("errorType");
                  String errorTitle = session.getString("errorTitle");
                  int errorStatus = session.getInt("errorStatus");

                  System.out.println("Received 409 Conflict. Details:");
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
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Transcriptions"))

          // Return to hearing
          .exec(
            http("Darts-Portal - Auth - Is-authenticated")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + randomNumber.nextInt())
              .headers(Headers.CommonHeaders)
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Auth - Is-authenticated"))

          .exec(
            http("Darts-Portal - User - Refresh-Profile")
              .post(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/user/refresh-profile")
              .headers(Headers.CommonHeaders)
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - User - Refresh-Profile"))

          .exec(
            http("Darts-Portal - Api - Cases")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/#{getCaseId}")
              .headers(Headers.caseReferer(Headers.CommonHeaders))
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Cases"))

          .exec(
            http("Darts-Portal - Api - Cases = Hearings")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/#{getCaseId}/hearings")
              .headers(Headers.CommonHeaders)
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Cases = Hearings"))

          .exec(
            http("Darts-Portal - Api - Audio - Hearings - Audios")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio/hearings/#{getHearings.id}/audios")
              .headers(Headers.CommonHeaders)
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Audio - Hearings - Audios"))

          .exec(
            http("Darts-Portal - Api - Audio-requests - Not-accessed-count")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio-requests/not-accessed-count")
              .headers(Headers.CommonHeaders)
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Audio-requests - Not-accessed-count"))

          .exec(
            http("Darts-Portal - Api - Hearings - Events")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/hearings/#{getHearings.id}/events")
              .headers(Headers.CommonHeaders)
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Hearings - Events"))

          .exec(
            http("Darts-Portal - Api - Hearings - Transcripts")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/hearings/#{getHearings.id}/transcripts")
              .headers(Headers.caseReferer(Headers.CommonHeaders))
              .check(status().in(200, 403))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Hearings - Transcripts"))

        );
    }
}