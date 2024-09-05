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

public final class TranscriberAttachFileAndDownloadAudioScenario {

    private static final Random randomNumber = new Random();
    private static final String randomDocumentFile = AppConfig.getRandomDocumentFile();

    private TranscriberAttachFileAndDownloadAudioScenario() {}

    public static ChainBuilder TranscriberAttachFileAndDownloadAudio() {
      return group("Darts Attach File And Downlaod Audio")
      .on(exec(feed(Feeders.createAudioRequestCSV()))
      .exec(session -> {
              String xmlPayload = RequestBodyBuilder.buildSearchCaseRequestBody(session);
              return session.set("xmlPayload", xmlPayload);
          })
          .exec(
            http("Darts-Portal - Auth - Is-authenticated")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + randomNumber.nextInt())
              .headers(Headers.CommonHeaders)
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Auth - Is-authenticated"))

          .exec(
            http("Darts-Portal - Api - Transcriptions - Transcriber-view")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/transcriptions/transcriber-view?assigned=false")
              .headers(Headers.CommonHeaders)
              .check(jsonPath("$[*].transcription_id").findRandom().saveAs("getTranscriptionId"))
              ).exec(session -> {
                Object getTranscriptionId = session.get("getTranscriptionId");
                if (getTranscriptionId != null) {
                    System.out.println("getTranscriptionId: " + getTranscriptionId.toString());
                } else {
                    System.out.println("No value saved using saveAs.");
                }
                return session;
            }) 
            .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Transcriptions - Transcriber-view"))
         
          )
          .exitHereIfFailed()
          .pause(3)
          .exec(
            http("Darts-Portal - Auth - Is-authenticated")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + randomNumber.nextInt())
              .headers(Headers.CommonHeaders)
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Auth - Is-authenticated"))

          .exec(
            http("Darts-Portal - Api - Transcriptions - Id")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/transcriptions/#{getTranscriptionId}")
              .headers(Headers.CommonHeaders)
              .check(status().is(200))
              .check(status().saveAs("status"))
          )  
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Transcriptions - Id"))
        
          .pause(3)
          .exec(
            http("Darts-Portal - Api - Transcriptions - Id")
              .patch(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/transcriptions/#{getTranscriptionId}")
              .headers(Headers.searchCaseHeaders(Headers.CommonHeaders))
              .body(StringBody("{\"transcription_status_id\":5}")).asJson()
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Transcriptions - Id"))

          .exec(
            http("Darts-Portal - Auth - Is-authenticated")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + randomNumber.nextInt())
              .headers(Headers.CommonHeaders)
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Auth - Is-authenticated"))

          .exec(
            http("Darts-Portal - Api - Transcriptions - Transcriber-view")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/transcriptions/transcriber-view?assigned=true")
              .headers(Headers.CommonHeaders)
              .check(status().is(200))
              .check(status().saveAs("status"))
          )  
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Transcriptions - Transcriber-view"))
        
          .pause(12)
          .exec(
            http("Darts-Portal - Api - Audio-requests - Not-accessed-count")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio-requests/not-accessed-count")
              .headers(Headers.CommonHeaders)
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Audio-requests - Not-accessed-count"))

          .exec(
            http("Darts-Portal - Api - Transcriptions - Transcriber-counts")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/transcriptions/transcriber-counts")
              .headers(Headers.CommonHeaders)
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Transcriptions - Transcriber-counts"))

          .exec(
            http("Darts-Portal - Api - Transcriptions - Transcriber-counts")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/transcriptions/transcriber-counts")
              .headers(Headers.CommonHeaders)
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Transcriptions - Transcriber-count"))


          /// Add Transcription Doc

          .exec(
            http("Darts-Portal - Auth - Is-authenticated")
            .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + randomNumber.nextInt())
            .headers(Headers.CommonHeaders)
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Auth - Is-authenticated"))

          .exec(
            http("Darts-Portal - Api - Transcriptions - Transcriber-view")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/transcriptions/transcriber-view?assigned=true")              
              .headers(Headers.CommonHeaders) 
              .check(status().is(200))    
              .check(status().saveAs("status"))    
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Transcriptions - Transcriber-view"))

          .pause(3)
          .exec(
            http("Darts-Portal - Auth - Is-authenticated")
            .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + randomNumber.nextInt())
              .headers(Headers.CommonHeaders)
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Auth - Is-authenticated"))

          .exec(
            http("Darts-Portal - Api - Transcriptions - Id")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/transcriptions/#{getTranscriptionId}")
              .headers(Headers.CommonHeaders)
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Transcriptions - Id"))

          .pause(3)
          .exec(
            http("Darts-Portal - Api - Transcriptions - Document")
              .post(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/transcriptions/#{getTranscriptionId}/document")
              .headers(Headers.AddDocHeaders)
          .bodyPart(RawFileBodyPart("transcript", AppConfig.CSV_FILE_COMMON_PATH + randomDocumentFile)
              .fileName(randomDocumentFile)
              .contentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
              .dispositionType("form-data"))
          ) 
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Transcriptions - Document"))

          .exec(
            http("Darts-Portal - Auth - Is-authenticated")
            .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + randomNumber.nextInt())
              .headers(Headers.CommonHeaders)
          ) 
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Auth - Is-authenticated"))

          .exec(
            http("Darts-Portal - Auth - Is-authenticated")
            .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + randomNumber.nextInt())
              .headers(Headers.CommonHeaders)
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Auth - Is-authenticated"))

          .exec(
            http("Darts-Portal - Api - Transcriptions - Transcriber-view")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/transcriptions/transcriber-view?assigned=true") 
              .headers(Headers.CommonHeaders) 
              .check(status().is(200))      
              .check(status().saveAs("status"))   
          )          
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Transcriptions - Transcriber-view"))
          ;
    }
}