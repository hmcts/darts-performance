package simulations.Scripts.Scenario.DartsPortal;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Utilities.UserInfoLogger;
import simulations.Scripts.Utilities.NumberGenerator;
import io.gatling.javaapi.core.*;
import scala.util.Random;
import simulations.Scripts.RequestBodyBuilder.RequestBodyBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public final class DartsPortalApproveAudioScenario {

    private DartsPortalApproveAudioScenario() {}    

    public static ChainBuilder DartsPortalApproveAudio() {    

      return group("Darts Approve / Reject Transcription")
      .on(exec(
              http("Darts-Portal - Auth - Is-authenticated")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + NumberGenerator.generateRandom13DigitNumber())
              .headers(Headers.CommonHeaders)
          )
          .exec(
            http("Darts-Portal - Api - Audio-requests - Not-accessed-count")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio-requests/not-accessed-count")
              .headers(Headers.CommonHeaders)
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Audio-requests - Not-accessed-count"))

          .exec(
            http("Darts-Portal - Api - Transcriptions")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/transcriptions")
              .headers(Headers.CommonHeaders)
              .check(jsonPath("$.approver_transcriptions[*].transcription_id").findRandom().saveAs("getTranscriptionId"))
              .check(status().is(200))
              ).exec(session -> {
                Object getTranscriptionId = session.get("getTranscriptionId");
                if (getTranscriptionId != null) {
                //    System.out.println("getTranscriptionId: " + getTranscriptionId.toString());
                } else {
                    System.out.println("No Transcription Id value saved using saveAs.");
                }
                return session;
            })
            .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Transcriptions"))

          .exitHereIfFailed()
          .exec(
            http("Darts-Portal - Api - Transcriptions - Urgencies")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/transcriptions/urgencies")
              .headers(Headers.CommonHeaders)
              .check(status().is(200))
              .check(status().saveAs("status"))
          )    
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Transcriptions - Urgencies"))
      
          .pause(2, 5)
          .exec(            
            http("Darts-Portal - Auth - Is-authenticated")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + NumberGenerator.generateRandom13DigitNumber())
              .headers(Headers.CommonHeaders)
          )     
     
          .exec(
            http("Darts-Portal - Api - Transcriptions - Id")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/transcriptions/#{getTranscriptionId}")
              .headers(Headers.CommonHeaders)
              .check(status().is(200))
              .check(status().saveAs("status"))
          )   
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Transcriptions - Id"))
       
          .pause(2, 5)
          .exec(session -> {
            String xmlPayload = RequestBodyBuilder.buildTranscriptionApprovalRequestBody(session);
            return session.set("xmlPayload", xmlPayload);
        })
          .exec(
            http("Darts-Portal - Api - Transcriptions - Id")
              .patch(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/transcriptions/#{getTranscriptionId}")
              .headers(Headers.searchCaseHeaders(Headers.CommonHeaders))
              .body(StringBody(session -> session.get("xmlPayload"))).asJson()
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Transcriptions - Id"))

          .exitHereIfFailed()
          .exec(
            http("Darts-Portal - Auth - Is-authenticated")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + NumberGenerator.generateRandom13DigitNumber())
              .headers(Headers.CommonHeaders)
          ) 
          .exec(
            http("Darts-Portal - Api - Audio-requests - Not-accessed-count")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio-requests/not-accessed-count")
              .headers(Headers.CommonHeaders)   
              .check(status().is(200))   
              .check(status().saveAs("status"))    
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Audio-requests - Not-accessed-count"))

          .exec(
            http("Darts-Portal - Api - Transcriptions")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/transcriptions")
              .headers(Headers.CommonHeaders)
              .check(status().is(200))
              .check(status().saveAs("status"))
              )
              .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Transcriptions"))

          ); 
        }
}
