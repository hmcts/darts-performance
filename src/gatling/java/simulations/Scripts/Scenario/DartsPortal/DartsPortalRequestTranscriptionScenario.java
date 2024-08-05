package simulations.Scripts.Scenario.DartsPortal;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;
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
      return group("Darts Request Audio PlayBack/Download")
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
          .exitHereIfFailed()
          
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
          .exitHereIfFailed()
          //Request Transcription.
          .exec(
            http("request_16")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + randomNumber.nextInt())
              .headers(Headers.CommonHeaders)
          )
          .exec(
            http("request_17")
              .post(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/user/refresh-profile")
              .headers(Headers.CommonHeaders)
          )
          .exec(
            http("request_18")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/transcriptions/types")
              .headers(Headers.CommonHeaders)
          )
          .exec(
            http("request_19")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/#{getCaseId}/hearings")
              .headers(Headers.CommonHeaders)
          )
          .exec(
            http("request_20")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/transcriptions/urgencies")
              .headers(Headers.CommonHeaders)
          )
          .exec(
            http("request_21")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/#{getCaseId}")
              .headers(Headers.CommonHeaders)
          )
          .exec(
            http("request_22")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio/hearings/#{getHearings.id}/audios")
              .headers(Headers.CommonHeaders)
          )
          .exec(
            http("request_23")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/hearings/#{getHearings.id}/events")
              .headers(Headers.CommonHeaders)
          )
          .exec
            (session -> {
              String xmlPayload = RequestBodyBuilder.buildTranscriptionsBody(session);
              return session.set("xmlPayload", xmlPayload);
          })
          .exec(
            http("request_25")
              .post(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/transcriptions")
              .headers(Headers.getHeaders(8))
              .body(StringBody(session -> session.get("xmlPayload"))).asJson()              
          )
          .exec(
            http("request_16")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + randomNumber.nextInt())
              .headers(Headers.CommonHeaders)
          )
          .exec(
            http("request_17")
              .post(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/user/refresh-profile")
              .headers(Headers.CommonHeaders)
          )
          .exec(
            http("Darts-Portal - Api - Cases")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/#{getCaseId}")
              .headers(Headers.caseReferer(Headers.CommonHeaders))
          )
          .exec(
            http("request_19")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/#{getCaseId}/hearings")
              .headers(Headers.CommonHeaders)
          )
          .exec(
            http("request_22")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio/hearings/#{getHearings.id}/audios")
              .headers(Headers.CommonHeaders)
          )
          .exec(
            http("Darts-Portal - Api - Audio-requests - Not-accessed-count")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio-requests/not-accessed-count")
              .headers(Headers.CommonHeaders)
          )
          .exec(
            http("request_23")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/hearings/#{getHearings.id}/events")
              .headers(Headers.CommonHeaders)
          )
          .exec(
            http("Darts-Portal - Api - Hearings - Transcripts")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/hearings/#{getHearings.id}/transcripts")
              .headers(Headers.caseReferer(Headers.CommonHeaders))
              .check(status().in(200, 403))
          )
        );
    }
}