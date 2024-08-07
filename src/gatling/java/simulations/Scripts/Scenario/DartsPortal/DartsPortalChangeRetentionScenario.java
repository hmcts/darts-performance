package simulations.Scripts.Scenario.DartsPortal;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;
import io.gatling.javaapi.core.*;
import scala.util.Random;
import simulations.Scripts.RequestBodyBuilder.RequestBodyBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public final class DartsPortalChangeRetentionScenario {

    private static final Random randomNumber = new Random();
    private static final String requestType = Feeders.getRandomRequestType();

    private DartsPortalChangeRetentionScenario() {}

    public static ChainBuilder DartsPortalChangeRetention() {
      return group("Darts Change Cases Retention")
      .on(exec
            (session -> {
              String xmlPayload = RequestBodyBuilder.buildSearchCaseRequestBody(session);
              return session.set("xmlPayload", xmlPayload);
          })
          .pause(3)
          .exec(http("Darts-Portal - Api - Cases - Search")
              .post(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/search")
              .headers(Headers.searchCaseHeaders(Headers.CommonHeaders))              
              .body(StringBody(session -> session.get("xmlPayload"))).asJson()
              .check(status().is(200))
             .check(jsonPath("$[*]..case_id").count().gt(0))
              .check(Feeders.saveCaseId())
             .check(jsonPath("$[*].case_id").findRandom().saveAs("getCaseId"))
              .check(jsonPath("$.title").optional().saveAs("errorTitle"))              
              )
              .exec(session -> {
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
          .pause(3)
          .exec(
            http("Darts-Portal - Auth - Is-authenticated")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + randomNumber.nextInt())
              .headers(Headers.CommonHeaders)
          )
          .exec(
            http("Darts-Portal - Api - Cases")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/#{getCaseId}")
              .headers(Headers.searchCaseHeaders(Headers.CommonHeaders))
          )
          .exec(
            http("Darts-Portal - Api - Cases - Hearings")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/#{getCaseId}")
              .headers(Headers.searchReferer(Headers.CommonHeaders))
          )
          .exec(
            http("Darts-Portal - Api - Cases - Transcripts")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/#{getCaseId}/transcripts")
              .headers(Headers.searchReferer(Headers.CommonHeaders))
              .check(status().in(200, 403))
          )
          .pause(3)
          .exec(
            http("Darts-Portal - Auth - Is-authenticated")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + randomNumber.nextInt())
              .headers(Headers.CommonHeaders)
          )
          .exec(
            http("Darts-Portal - Api - Cases")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/#{getCaseId}")
              .headers(Headers.caseReferer(Headers.CommonHeaders))
          )
          
          .exec(
            http("Darts-Portal - Auth - Is-authenticated")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + randomNumber.nextInt())
              .headers(Headers.CommonHeaders)
          )
          .exec(
            http("Darts-Portal - User - Refresh-profile")
            .post(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/user/refresh-profile")
            .headers(Headers.CommonHeaders)
          )
          .exec(
            http("Darts-Portal - Api - Cases")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/#{getCaseId}")
              .headers(Headers.getHeaders(8))
            )            
          .exec(
            http("Darts-Portal - Api - Retentions - Case_id")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/retentions?case_id=#{getCaseId}")
              .headers(Headers.getHeaders(8))
          ) 
          .pause(19)
          .exec
            (session -> {
              String xmlPayload = RequestBodyBuilder.buildChangeRetentionsBody(session);
              return session.set("xmlPayload", xmlPayload);
          })
          .exec(
            http("Darts-Portal - Api - Retentions - Validate_only")
            .post(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/retentions?validate_only=true")
            .headers(Headers.getHeaders(9))
            .body(StringBody(session -> session.get("xmlPayload"))).asJson()
          )
          .pause(8)
          .exec(
            http("Darts-Portal - Api - Retentions")
              .post(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/retentions")
              .headers(Headers.getHeaders(9))
              .body(StringBody(session -> session.get("xmlPayload"))).asJson()
            )
          .exec(
            http("Darts-Portal - Api - Cases")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/#{getCaseId}")
              .headers(Headers.getHeaders(8))
          )
          .exec(
            http("Darts-Portal - Api - Audio-requests - Not-accessed-count")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio-requests/not-accessed-count")
              .headers(Headers.getHeaders(8))
          )
          .exec(
            http("Darts-Portal - Api - Retentions - Case_id")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/retentions?case_id=#{getCaseId}")
              .headers(Headers.getHeaders(8))
            )
        );
        
    }
}