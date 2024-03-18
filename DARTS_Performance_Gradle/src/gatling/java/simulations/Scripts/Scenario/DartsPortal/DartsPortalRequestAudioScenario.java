package simulations.Scripts.Scenario.DartsPortal;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;
import io.gatling.javaapi.core.*;
import scala.util.Random;
import simulations.Scripts.RequestBodyBuilder.RequestBodyBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public final class DartsPortalRequestAudioScenario {

    private static final FeederBuilder<String> feeder = csv(AppConfig.AUDIO_REQUEST_POST_FILE_PATH).random();    
   // private static final FeederBuilder<String> feeder = csv(AppConfig.COURT_HOUSE_AND_COURT_ROOMS_FILE_PATH).random();
    private static final Random randomNumber = new Random();

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
              //.check(jsonPath("$.[*].id").saveAs("getHearingId"))
              .check(jsonPath("$[*]").ofMap().findRandom().saveAs("getHearings")) 
              ).exec(session -> {
                Object getHearings = session.get("getHearings");
                if (getHearings != null) {
                    System.out.println("getHearings: " + getHearings.toString());
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
              // .check(jsonPath("$[*].name").ofMap().findRandom().saveAs("getEventName")) 
              // .check(jsonPath("$[*].text").ofMap().findRandom().saveAs("getEventText")) 
              // .check(jsonPath("$[*].timestamp").ofMap().findRandom().saveAs("getEventTimestamp")) 
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
          .exec(
            http("Darts-Portal - Api - Hearings - Annotations")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/hearings/#{getHearings.id}/annotations")
              .headers(Headers.caseReferer(Headers.CommonHeaders))
          )
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
          .pause(2)
          .exec(session -> {
            String audioxmlPayload = RequestBodyBuilder.buildAudioRequestBody(session);
            session.get("getEvent");
            return session.set("audioxmlPayload", audioxmlPayload);
          })
          .exec(
            http("Darts-Portal - Api - Audio-requests")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio-requests")
              .headers(Headers.searchCaseHeaders(Headers.CommonHeaders))
              .body(StringBody(session -> session.get("audioxmlPayload"))).asJson()
          )          
          .exec(
            http("Darts-Portal - Api - Audio-requests - Not-accessed-count")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio-requests/not-accessed-count")
              .headers(Headers.CommonHeaders)
          )
      );
    }
}