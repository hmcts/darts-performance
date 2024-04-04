package simulations.Scripts.Scenario.DartsPortal;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Utilities.RandomStringGenerator;
import io.gatling.javaapi.core.*;
import scala.util.Random;
import simulations.Scripts.RequestBodyBuilder.RequestBodyBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public final class DartsPortalRequestAudioScenario {

    private static final FeederBuilder<String> feeder = csv(AppConfig.AUDIO_REQUEST_POST_FILE_PATH).random();    
    private static final Random randomNumber = new Random();
    private static final String requestType = Feeders.getRandomRequestType();

    private DartsPortalRequestAudioScenario() {}

    public static ChainBuilder DartsPortalRequestAudioDownload() {
      return group("Darts Request Audio PLayBack/Download")
      .on(exec(feed(feeder))
      .exec(session -> {
              String xmlPayload = RequestBodyBuilder.buildSearchCaseRequestBody(session);
              return session.set("xmlPayload", xmlPayload);
          })
          .exec(http("Darts-Portal - Api - Cases - Search")
              .post(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/search")
              .headers(Headers.searchCaseHeaders(Headers.CommonHeaders))
              .body(StringBody(session -> session.get("xmlPayload"))).asJson()
              .check(jsonPath("$[*].case_id").count().gt(1))
              //.check(Feeders.saveCaseId())
              .check(jsonPath("$[*]").ofMap().findRandom().saveAs("getCaseId"))
              ).exec(session -> {
                Object getCaseId = session.get("getCaseId");
                if (getCaseId != null) {
                    System.out.println("getCaseId: " + getCaseId.toString());
                } else {
                    System.out.println("No value saved using saveAs.");
                }
                return session;
            }
          )          
          .pause(1)
          .exec(http("Darts-Portal - Auth - Is-authenticated")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + randomNumber.nextInt())
              .headers(Headers.CommonHeaders)
          )
          .exec(http("Darts-Portal - Api - Cases")
          .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/#{getCaseId.case_id}")
          .headers(Headers.searchCaseHeaders(Headers.CommonHeaders))
          )
          .exec(http("Darts-Portal - Api - Cases - Hearings")
          .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/#{getCaseId.case_id}")
          .headers(Headers.searchReferer(Headers.CommonHeaders))
          )
          .exec(http("Darts-Portal - Api - Cases - Transcripts")
          .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/#{getCaseId.case_id}/transcripts")
          .headers(Headers.searchReferer(Headers.CommonHeaders))
          )
          .pause(1)
          .exec(http("Darts-Portal - Auth - Is-authenticated")
          .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + randomNumber.nextInt())
          .headers(Headers.CommonHeaders)
          )
          .exec(
            http("Darts-Portal - Api - Cases")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/#{getCaseId.case_id}")
              .headers(Headers.caseReferer(Headers.CommonHeaders))
          )
          .exec(
            http("Darts-Portal - Api - Cases - Hearings")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/#{getCaseId.case_id}/hearings")
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
          .exec(
            http("Darts-Portal - Api - Hearings - Events")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/hearings/#{getHearings.id}/events")
              .headers(Headers.caseReferer(Headers.CommonHeaders))
              .check(jsonPath("$[*]").ofMap().findRandom().saveAs("getEvent"))  
              ).exec(session -> {
                Object getEvent = session.get("getEvent");

                if (getEvent != null) {
                    System.out.println("getEvent: " + getEvent.toString());

                } else {
                    System.out.println("No value saved using saveAs.");
                }
                return session;
            }
          )
          .exec(
            http("Darts-Portal - Api - Hearings - Audios")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio/hearings/#{getHearings.id}/audios")
              .headers(Headers.caseReferer(Headers.CommonHeaders))
          )               
          // .exec(
          //   http("Darts-Portal - Api - Hearings - Annotations")
          //     .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/hearings/#{getHearings.id}/annotations")
          //     .headers(Headers.caseReferer(Headers.CommonHeaders))
          // )
          .exec(
            http("Darts-Portal - Api - Hearings - Transcripts")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/hearings/#{getHearings.id}/transcripts")
              .headers(Headers.caseReferer(Headers.CommonHeaders))
          )
          .pause(2)
          .exec(
            http("Darts-Portal - Api - Audio-requests - Not-accessed-count")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio-requests/not-accessed-count")
              .headers(Headers.CommonHeaders)
          )
          .exec(session -> {

            Object getHearingId = session.get("getHearingId");
            System.out.println("getHearingId for Audio Request: " + getHearingId);
            Object getUserId = session.get("getUserId");
            System.out.println("getUserId for Audio Request: " + getUserId);

            // Build audioXmlPayload
            String audioXmlPayload = RequestBodyBuilder.buildAudioRequestBody(session, getHearingId, getUserId, requestType);
            return session.set("AudioXmlPayload", audioXmlPayload);
          })
          .exec(
              http("Darts-Portal - Api - Audio-requests")
                  .post(session -> AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio-requests/" + requestType.toLowerCase())
                  .headers(Headers.StandardHeaders2)
                  .body(StringBody(session -> session.get("AudioXmlPayload"))).asJson()
          )       
          .exec(
            http("Darts-Portal - Api - Audio-requests - Not-accessed-count")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio-requests/not-accessed-count")
              .headers(Headers.CommonHeaders)
          )
        );
    }
}