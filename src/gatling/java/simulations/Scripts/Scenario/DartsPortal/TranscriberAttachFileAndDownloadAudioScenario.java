package simulations.Scripts.Scenario.DartsPortal;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Utilities.NumberGenerator;
import simulations.Scripts.Utilities.UserInfoLogger;
import io.gatling.javaapi.core.*;
import scala.util.Random;
import simulations.Scripts.RequestBodyBuilder.RequestBodyBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public final class TranscriberAttachFileAndDownloadAudioScenario {
  
private static final String randomDocumentFile = AppConfig.getRandomDocumentFile();

    private TranscriberAttachFileAndDownloadAudioScenario() {}

    public static ChainBuilder TranscriberAttachFileAndDownloadAudio() {
      return group("Darts Attach File And Downlaod Audio")
      .on(exec(
            http("Darts-Portal - Auth - Is-authenticated")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + NumberGenerator.generateRandom13DigitNumber())
              .headers(Headers.getHeaders(14))
          )
          .exec(
            http("Darts-Portal - Api - Transcriptions - Transcriber-view")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/transcriptions/transcriber-view?assigned=false")
              .headers(Headers.getHeaders(12))
              .check(jsonPath("$[*].transcription_id").findRandom().saveAs("getTranscriptionId"))
              .check(status().is(200))
              .check(status().saveAs("status"))
              ).exec(session -> {
                Object getTranscriptionId = session.get("getTranscriptionId");
                if (getTranscriptionId != null) {
                //    System.out.println("getTranscriptionId: " + getTranscriptionId.toString());
                } else {
                    System.out.println("No Transcription Id value saved using saveAs.");
                }
                return session;
            }) 
            .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Transcriptions - Transcriber-view"))
         
          .exitHereIfFailed()
          .pause(2, 5)
          .exec(
            http("Darts-Portal - Auth - Is-authenticated")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + NumberGenerator.generateRandom13DigitNumber())
              .headers(Headers.getHeaders(14))
          )
          .exec(
            http("Darts-Portal - Api - Transcriptions - Id")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/transcriptions/#{getTranscriptionId}")
              .headers(Headers.getHeaders(12))
              .check(status().is(200))
              .check(status().saveAs("status"))
          )  
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Transcriptions - Id"))
        
          .pause(2, 5)
          .exec(
            http("Darts-Portal - Api - Transcriptions - Id")
              .patch(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/transcriptions/#{getTranscriptionId}")
              .headers(Headers.getHeaders(9))
              .body(StringBody("{\"transcription_status_id\":5}")).asJson()
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Transcriptions - Id"))

          .exec(
            http("Darts-Portal - Auth - Is-authenticated")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + NumberGenerator.generateRandom13DigitNumber())
              .headers(Headers.getHeaders(14))
          )
          .exec(
            http("Darts-Portal - Api - Transcriptions - Transcriber-view")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/transcriptions/transcriber-view?assigned=true")
              .headers(Headers.getHeaders(12))
              .check(status().is(200))
              .check(status().saveAs("status"))
          )  
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Transcriptions - Transcriber-view"))
        
          .pause(2, 10)
          .exec(
            http("Darts-Portal - Api - Audio-requests - Not-accessed-count")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio-requests/not-accessed-count")
              .headers(Headers.getHeaders(12))
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Audio-requests - Not-accessed-count"))

          .exec(
            http("Darts-Portal - Api - Transcriptions - Transcriber-counts")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/transcriptions/transcriber-counts")
              .headers(Headers.getHeaders(12))
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Transcriptions - Transcriber-counts"))

          .exec(
            http("Darts-Portal - Api - Transcriptions - Transcriber-counts")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/transcriptions/transcriber-counts")
              .headers(Headers.getHeaders(12))
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Transcriptions - Transcriber-count"))


          /// Add Transcription Doc

          .exec(
            http("Darts-Portal - Auth - Is-authenticated")
            .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + NumberGenerator.generateRandom13DigitNumber())
            .headers(Headers.getHeaders(14))
            )
          .exec(
            http("Darts-Portal - Api - Transcriptions - Transcriber-view")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/transcriptions/transcriber-view?assigned=true")              
              .headers(Headers.getHeaders(12))
              .check(status().is(200))    
              .check(status().saveAs("status"))    
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Transcriptions - Transcriber-view"))

          .pause(2, 5)
          .exec(
            http("Darts-Portal - Auth - Is-authenticated")
            .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + NumberGenerator.generateRandom13DigitNumber())
            .headers(Headers.getHeaders(14))
            )
          .exec(
            http("Darts-Portal - Api - Transcriptions - Id")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/transcriptions/#{getTranscriptionId}")
              .headers(Headers.getHeaders(12))
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Transcriptions - Id"))

          .pause(2, 5)
          .exec(
            http("Darts-Portal - Api - Transcriptions - Document")
              .post(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/transcriptions/#{getTranscriptionId}/document")
              .headers(Headers.AddDocHeaders)
          .bodyPart(RawFileBodyPart("transcript", randomDocumentFile)
              .fileName(randomDocumentFile)
              .contentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
              .dispositionType("form-data"))
          ) 
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Transcriptions - Document"))

          .exec(
            http("Darts-Portal - Auth - Is-authenticated")
            .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + NumberGenerator.generateRandom13DigitNumber())
            .headers(Headers.getHeaders(14))
            ) 
          .exec(
            http("Darts-Portal - Auth - Is-authenticated")
            .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + NumberGenerator.generateRandom13DigitNumber())
            .headers(Headers.getHeaders(14))
            )
          .exec(
            http("Darts-Portal - Api - Transcriptions - Transcriber-view")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/transcriptions/transcriber-view?assigned=true") 
              .headers(Headers.getHeaders(12))
              .check(status().is(200))      
              .check(status().saveAs("status"))   
          )          
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Transcriptions - Transcriber-view"))
       );
    }
}