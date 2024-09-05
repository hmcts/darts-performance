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

public final class DartsPortalRequestAudioScenario {

    private static final Random randomNumber = new Random();
    private static final String requestType = Feeders.getRandomRequestType();

    private DartsPortalRequestAudioScenario() {}

    public static ChainBuilder DartsPortalRequestAudioDownload() {
      return group("Darts Request Audio PlayBack/Download")
      .on(

          pause(3)
          .exec(http("Darts-Portal - Auth - Is-authenticated")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + randomNumber.nextInt())
              .headers(Headers.CommonHeaders)
              .check(status().is(200))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Auth - Is-authenticated"))

          .exec(
            http("Darts-Portal - Api - Cases")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/#{getCaseId}")
              .headers(Headers.searchCaseHeaders(Headers.CommonHeaders))
              .check(status().is(200))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Cases"))

          .exec(
            http("Darts-Portal - Api - Cases - Hearings")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/#{getCaseId}")
              .headers(Headers.searchReferer(Headers.CommonHeaders))
              .check(status().is(200))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Cases - Hearings"))

          .exec(
            http("Darts-Portal - Api - Cases - Transcripts")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/#{getCaseId}/transcripts")
              .headers(Headers.searchReferer(Headers.CommonHeaders))
              .check(status().in(200, 403).saveAs("responseStatus"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Cases - Transcripts"))

          .exec(session -> {
            int statusCode = session.getInt("responseStatus");
            if (statusCode == 403) {
                // Log the 403 and clear the failure status
                System.out.println("Received 403, resetting failure status...");
                return session.markAsSucceeded();
            }
            return session;
          })
          .pause(3)
          .exec(
            http("Darts-Portal - Auth - Is-authenticated")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + randomNumber.nextInt())
              .headers(Headers.CommonHeaders)
              .check(status().is(200))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Auth - Is-authenticated"))

          .exec(
            http("Darts-Portal - Api - Cases")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/#{getCaseId}")
              .headers(Headers.caseReferer(Headers.CommonHeaders))
              .check(status().is(200))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Cases"))

          .exec(
            http("Darts-Portal - Api - Cases - Hearings")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/#{getCaseId}/hearings")
              .headers(Headers.caseReferer(Headers.CommonHeaders))
              .check(status().saveAs("statusCode"))
              .check(jsonPath("$[*]").ofMap().findRandom().saveAs("getHearings")) 
              .check(jsonPath("$[*].id").saveAs("getHearingId"))
              ).exec(session -> {
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
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Cases - Hearings"))

          .exitHereIfFailed()
          .exec(
            http("Darts-Portal - Api - Hearings - Events")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/hearings/#{getHearings.id}/events")
              .headers(Headers.caseReferer(Headers.CommonHeaders))
              //.check(jsonPath("$[*]").ofMap().findRandom().saveAs("getEvent"))  
              .check(status().in(200, 502, 504).saveAs("status"))
              )
        //       .exec(session -> {
        //         Object getEvent = session.get("getEvent");

        //         if (getEvent != null) {
        //             System.out.println("getEvent: " + getEvent.toString());

        //         } else {
        //             System.out.println("No value saved using saveAs.");
        //         }
        //         return session;
        //     }                

        //   )
        .exec(session -> {
            int statusCode = session.getInt("status");
            if (statusCode == 502 || statusCode == 504) {
                return session.markAsFailed();  // Mark as failed to trigger logging in UserInfoLogger
            }
            return session;
        })
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Hearings - Events"))

          .exec(
            http("Darts-Portal - Api - Hearings - Audios")
                .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio/hearings/#{getHearingId}/audios")
                .headers(Headers.caseReferer(Headers.CommonHeaders))
                .check(jsonPath("$[0].media_start_timestamp").saveAs("getAudioStartDate"))
                .check(jsonPath("$[0].media_end_timestamp").saveAs("getAudioEndDate"))
                .check(jsonPath("$[0].id").saveAs("extractedId")) 
                .check(status().is(200))
        )
        .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Hearings - Audios"))

        .exec(session -> {
            String getAudioStartDate = session.getString("getAudioStartDate");
            if (getAudioStartDate != null) {
                System.out.println("getAudioStartDate: " + getAudioStartDate);
            } else {
                System.out.println("No value saved for getAudioStartDate using saveAs.");
            }
        
            String getAudioEndDate = session.getString("getAudioEndDate");
            if (getAudioEndDate != null) {
                System.out.println("getAudioEndDate: " + getAudioEndDate);
            } else {
                System.out.println("No value saved for getAudioEndDate using saveAs.");
            }

            String extractedId = session.getString("extractedId");
            if (extractedId != null) {
                System.out.println("Extracted ID: " + extractedId);
            } else {
                System.out.println("No value saved for extractedId using saveAs.");
            }
            return session;
        })        
          // .exec(
          //   http("Darts-Portal - Api - Hearings - Annotations")
          //     .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/hearings/#{getHearings.id}/annotations")
          //     .headers(Headers.caseReferer(Headers.CommonHeaders))
          // )
          .exec(
            http("Darts-Portal - Api - Hearings - Transcripts")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/hearings/#{getHearings.id}/transcripts")
              .headers(Headers.caseReferer(Headers.CommonHeaders))
              .check(status().in(200, 403).saveAs("responseStatus"))
          )
          .exec(session -> {
              int statusCode = session.getInt("responseStatus");
              if (statusCode == 403) {
                  // Log the 403 and clear the failure status
                  System.out.println("Received 403, resetting failure status...");
                  return session.markAsSucceeded();
              }
              return session;
          })          
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Hearings - Transcripts"))

          .pause(3)
          .exec(
            http("Darts-Portal - Api - Audio-requests - Not-accessed-count")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio-requests/not-accessed-count")
              .headers(Headers.CommonHeaders)
              .check(status().is(200))
          )  
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Audio-requests - Not-accessed-count"))

          .exec(session -> {
              // Get the user type from the session
              String userType = session.get("Type").toString();
              System.out.println("userType for Audio Request: " + userType);
  
              String requestType;
  
              // Determine request type based on user type
              if (userType.equalsIgnoreCase("Transcriber")) {
                  // If the user type is Transcriber, select request type randomly between "download" and "playback"
                  requestType = Feeders.getRandomRequestType();
              } else if (userType.equalsIgnoreCase("CourtClerk")) {
                  // If the user type is Clerk, request type is "playback"
                  requestType = "playback";
              } else if (userType.equalsIgnoreCase("LanguageShop")) {
                // If the user type is LanguageShop, request type is "playback"
                requestType = "playback";
              } else if (userType.equalsIgnoreCase("CourtManager")) {
                  // If the user type is Reviewer, request type is "download"
                  requestType = "download";
              } else {
                  // Default request type if user type is not recognized
                  requestType = "playback";
              }
  
              // Set request type in the session
              Session requestTypeSession = session.set("requestType", requestType);
              System.out.println("requestType for Audio Request: " + requestType);
  
              return requestTypeSession;
          })
          .exec(session -> {

            Object getHearingId = session.get("getHearingId");
            System.out.println("getHearingId for Audio Request: " + getHearingId);
            Object getUserId = session.get("getUserId");
            System.out.println("getUserId for Audio Request: " + getUserId);
            Object getAudioStartDate = session.get("getAudioStartDate");
            System.out.println("getAudioStartDate for Audio Request: " + getAudioStartDate);
            Object getAudioEndDate = session.get("getAudioEndDate");
            System.out.println("getAudioEndDate for Audio Request: " + getAudioEndDate);
            Object getCaseId = session.get("getCaseId");
            System.out.println("getCaseId for Audio Request: " + getCaseId);

            // Build audioXmlPayload
            String audioXmlPayload = RequestBodyBuilder.buildAudioRequestBody(
              session, getHearingId, getUserId, getAudioStartDate, getAudioEndDate, session.get("requestType")
          );
          return session.set("AudioXmlPayload", audioXmlPayload);
      })
      .exec(
          http(requestTypeSession -> "Darts-Portal - Api - Audio-requests/" + 
              (requestTypeSession.get("requestType") != null ? 
              requestTypeSession.get("requestType").toString().toLowerCase() : ""))
              .post(requestTypeSession -> AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + 
              "/api/audio-requests/" + 
              (requestTypeSession.get("requestType") != null ? 
              requestTypeSession.get("requestType").toString().toLowerCase() : ""))
              .headers(Headers.StandardHeaders2)
              .body(StringBody(session -> session.get("AudioXmlPayload"))).asJson()
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
      
              // Set the error details in the session
              return session.set("errorStatusCode", String.valueOf(statusCode))
                            .set("errorType", errorType)
                            .set("errorTitle", errorTitle);
          } else {
              // Handle other status codes if necessary
              return session;
          }
      })
      .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Audio-requests"))
      .exec(session -> {
          // Mark the session as succeeded even if it encountered an error
          return session.markAsSucceeded();
      })
          .exec(
            http("Darts-Portal - Api - Audio-requests - Not-accessed-count")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio-requests/not-accessed-count")
              .headers(Headers.CommonHeaders)
              .check(status().is(200))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Audio-requests - Not-accessed-count"))

        );
    }
}