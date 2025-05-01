package simulations.Scripts.Scenario.DartsPortal;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Utilities.UserInfoLogger;
import simulations.Scripts.Utilities.NumberGenerator;
import io.gatling.javaapi.core.*;
import simulations.Scripts.RequestBodyBuilder.RequestBodyBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public final class DartsPortalRequestAudioScenario {

    private DartsPortalRequestAudioScenario() {}

    public static ChainBuilder DartsPortalRequestAudioDownload() {
      return group("Darts Request Audio PlayBack/Download")
      .on(
          pause(2, 5)
          .exec(http("Darts-Portal - Auth - Is-authenticated")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + NumberGenerator.generateRandom13DigitNumber())
              .headers(Headers.getHeaders(14))
          )
          .exec(
            http("Darts‑Portal - Api - Cases")
                .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl()
                     + "/api/cases/#{getCaseId}")
                .headers(Headers.getHeaders(9))
                .check(status().is(200))
                .check(status().saveAs("status"))
        )
        .exec(session -> {
            String email      = session.getString("Email"); 
            String caseId     = session.getString("getCaseId");  
            Integer status    = session.getInt("status");        
        
            System.out.printf("Get Cases Id: %s | Status: %d | User: %s%n",
                              caseId, status, email);        
            return session;
        })
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Cases"))
         
          .exec(
            http("Darts-Portal - Api - Cases - Hearings")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/#{getCaseId}/hearings")
              .headers(Headers.getHeaders(12))
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Cases - Hearings"))
          .exec(
            http("Darts-Portal - Api - Cases - Transcripts")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/#{getCaseId}/transcripts")
              .headers(Headers.getHeaders(12))
              .check(status().in(200, 403).saveAs("responseStatus"))
              .check(status().saveAs("status"))
          )
                   
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Cases - #{getCaseId} - Transcripts"))

          .exec(session -> {
            int statusCode = session.getInt("responseStatus");
            if (statusCode == 403) {
                // Log the 403 and clear the failure status
                System.out.println("Received 403, resetting failure status...");
                return session.markAsSucceeded();
            }
            return session;
          })
          .pause(2, 5)
          .exec(
            http("Darts-Portal - Auth - Is-authenticated")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + NumberGenerator.generateRandom13DigitNumber())
              .headers(Headers.getHeaders(14))
          )
          .exec(
            http("Darts-Portal - Api - Cases")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/#{getCaseId}")
              .headers(Headers.getHeaders(12))
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Cases"))

          .exec(
            http("Darts-Portal - Api - Cases - Hearings")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/#{getCaseId}/hearings")
              .headers(Headers.getHeaders(12))
              .check(status().saveAs("statusCode"))
              .check(jsonPath("$[*]").ofMap().findRandom().saveAs("getHearings")) 
              .check(jsonPath("$[*].id").saveAs("extractedHearingId")) 
              .check(jsonPath("$[?(@.id == #{getHearingId})].date").find().saveAs("getHearingdate"))
              )
              .exec(session -> {
                  System.out.println("DEBUG getHearingId = " + session.getString("getHearingId"));
                  return session;
              })
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Cases - Hearings"))
          
          .exec(session -> {
            String email = session.getString("Email");
            Object getHearingId = session.get("getHearingId");
            if (getHearingId != null) {
                System.out.println("getHearingId from Cases - Hearings: " + getHearingId.toString() + " for user: " + email);
            } else {
                System.out.println("No Hearing Id value saved using saveAs.");
            }
            return session;
            }
          )
          .exitHereIfFailed()
          .exec(
            http("Darts-Portal - Api - Hearings - Events")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/hearings/#{getHearingId}/events")
              .headers(Headers.getHeaders(12))
              //.check(jsonPath("$[*]").ofMap().findRandom().saveAs("getEvent"))  
              .check(status().in(200, 502, 504).saveAs("status"))
              .check(status().saveAs("status"))
          )
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
                .headers(Headers.getHeaders(12))
        
                // Save the full response for logging
                .check(bodyString().saveAs("fullResponseBody"))
        
                // Optional checks for values
                .check(jsonPath("$[0].media_start_timestamp").optional().saveAs("getAudioStartDate"))
                .check(jsonPath("$[0].media_end_timestamp").optional().saveAs("getAudioEndDate"))
                .check(jsonPath("$[0].id").optional().saveAs("extractedId"))
        
                // Ensure status is 200
                .check(status().is(200))
                .check(status().saveAs("status"))
        )        

        .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Hearings - Audios"))   

          // .exec(
          //   http("Darts-Portal - Api - Hearings - Annotations")
          //     .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/hearings/#{getHearingId.id}/annotations")
          //     .headers(Headers.caseReferer(Headers.CommonHeaders))
          // )
          .exec(
            http("Darts-Portal - Api - Hearings - Transcripts")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/hearings/#{getHearingId}/transcripts")
              .headers(Headers.getHeaders(12))
              .check(status().in(200, 403).saveAs("responseStatus"))
              .check(status().saveAs("status"))
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

          .pause(2, 5)
          .exec(
            http("Darts-Portal - Api - Audio-requests - Not-accessed-count")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio-requests/not-accessed-count")
              .headers(Headers.getHeaders(12))
              .check(status().is(200))
              .check(status().saveAs("status"))
          )  
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Audio-requests - Not-accessed-count"))

          .exec(session -> {
              // Get the user type from the session
              String userType = session.get("Type").toString();
             // System.out.println("userType for Audio Request: " + userType);
  
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
             // System.out.println("requestType for Audio Request: " + requestType);
  
              return requestTypeSession;
          })
          .exec(session -> {

            Object getHearingId = session.get("getHearingId");
           // System.out.println("getHearingId for Audio Request: " + getHearingId);
            Object getUserId = session.get("getUserId");
           // System.out.println("getUserId for Audio Request: " + getUserId);
            Object getAudioStartDate = session.get("getAudioStartDate");
           // System.out.println("getAudioStartDate for Audio Request: " + getAudioStartDate);
            Object getAudioEndDate = session.get("getAudioEndDate");
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
              .headers(Headers.getHeaders(22))
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
              String audioXmlPayload = session.getString("AudioXmlPayload");
              String email = session.getString("Email");

              System.out.println("Audio Request payload: " + audioXmlPayload + " for user: " + email);
    
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
              .headers(Headers.getHeaders(12))
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Audio-requests - Not-accessed-count"))

        );
    }
}