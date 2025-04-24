package simulations.Scripts.Scenario.DartsPortal;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.UserInfoLogger;
import simulations.Scripts.Utilities.NumberGenerator;
import io.gatling.javaapi.core.*;
import simulations.Scripts.RequestBodyBuilder.RequestBodyBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public final class DartsPortalRequestTranscriptionScenario {

    private DartsPortalRequestTranscriptionScenario() {}

    public static ChainBuilder DartsPortalRequestTranscription() {
      return group("Darts Request Transcription")
      .on(exec(session -> {
                Object getCaseId = session.get("getCaseId");
                if (getCaseId != null) {
                //    System.out.println("getCaseId: " + getCaseId.toString());
                } else {
                    System.out.println("No Case Id value saved using saveAs.");
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
                //    System.out.println("getHearings from Cases - Hearings: " + getHearings.toString());
                } else {
                    System.out.println("No Hearing value saved using saveAs.");
                }
                Object getHearingId = session.get("getHearingId");
                if (getHearingId != null) {
                 //   System.out.println("getHearingId from Cases - Hearings: " + getHearingId.toString());
                } else {
                    System.out.println("No Hearing Id value saved using saveAs.");
                }
                return session;
            }
          )
          //Request Transcription.
          .exec(
            http("Darts-Portal - Auth - Is-authenticated")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + NumberGenerator.generateRandom13DigitNumber())
              .headers(Headers.getHeaders(14))
          )
          .exec(
            http("Darts-Portal - User - Refresh - Profile")
              .post(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/user/refresh-profile")
              .headers(Headers.getHeaders(12))
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - User - Refresh - Profile"))

          .exec(
            http("Darts-Portal - Api - Transcriptions - Types")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/transcriptions/types")
              .headers(Headers.getHeaders(12))
              .check(status().is(200))
                 .check(
                  jsonPath("$[?(@.transcription_type_id == 5 || @.transcription_type_id == 9)].transcription_type_id")
                      .findRandom()
                      .saveAs("transcriptionTypeId")              
              )
          )
          .exec(session -> {
              Object typeId = session.get("transcriptionTypeId");
              String email  = session.getString("Email");
          
              if (typeId != null) {
                  System.out.println("Random Transcription Type ID: " + typeId + " for user: " + email);
              } else {
                  System.out.println("No transcription type ID found in response.");
              }
          
              return session;
          })
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Transcriptions - Types"))

          .exec(
            http("Darts-Portal - Api - Cases - Hearings")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/#{getCaseId}/hearings")
              .headers(Headers.getHeaders(12))
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Cases - Hearings"))

          .exec(
            http("Darts-Portal - Api - Transcriptions - Urgencies")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/transcriptions/urgencies")
              .headers(Headers.getHeaders(12))
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Transcriptions - Urgencies"))

          .exec(
            http("Darts-Portal - Api - Cases")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/#{getCaseId}")
              .headers(Headers.getHeaders(12))
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Cases"))

          .exec(
            http("Darts-Portal - Api - Hearings - Audios")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio/hearings/#{getHearingId}/audios")
              .headers(Headers.getHeaders(12))
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Hearings - Audios"))

          .exec(
            http("Darts-Portal - Api - Hearings - Events")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/hearings/#{getHearingId}/events")
              .headers(Headers.getHeaders(12))
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
                  String email = session.getString("Email");
                  Object getCaseId = session.get("getCaseId");
                  Object getHearingId = session.get("getHearingId");

                  System.out.println("Received 409 Conflict. Details:");
                  System.out.println("Status: " + errorStatus + "Type: " + errorType + "Title: " + errorTitle + " for user: " + email +"Case Id: " + getCaseId + "Hearing Id: " + getHearingId);

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
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + NumberGenerator.generateRandom13DigitNumber())
              .headers(Headers.getHeaders(14))
          )
          .exec(
            http("Darts-Portal - User - Refresh - Profile")
              .post(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/user/refresh-profile")
              .headers(Headers.getHeaders(12))
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - User - Refresh-Profile"))

          .exec(
            http("Darts-Portal - Api - Cases")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/#{getCaseId}")
              .headers(Headers.getHeaders(12))
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Cases"))

          .exec(
            http("Darts-Portal - Api - Cases = Hearings")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/#{getCaseId}/hearings")
              .headers(Headers.getHeaders(12))
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Cases = Hearings"))

          .exec(
            http("Darts-Portal - Api - Audio - Hearings - Audios")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio/hearings/#{getHearingId}/audios")
              .headers(Headers.getHeaders(12))
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Audio - Hearings - Audios"))

          .exec(
            http("Darts-Portal - Api - Audio-requests - Not-accessed-count")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio-requests/not-accessed-count")
              .headers(Headers.getHeaders(12))
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Audio-requests - Not-accessed-count"))

          .exec(
            http("Darts-Portal - Api - Hearings - Events")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/hearings/#{getHearingId}/events")
              .headers(Headers.getHeaders(12))
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Hearings - Events"))

          .exec(
            http("Darts-Portal - Api - Hearings - Transcripts")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/hearings/#{getHearingId}/transcripts")
              .headers(Headers.getHeaders(12))
              .check(status().in(200, 403))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Hearings - Transcripts"))

        );
    }
}