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

public final class DartsPortalChangeRetentionScenario {

    private static final Random randomNumber = new Random();
    private static final String requestType = Feeders.getRandomRequestType();

    private DartsPortalChangeRetentionScenario() {}

    public static ChainBuilder DartsPortalChangeRetention() {
      return group("Darts Change Cases Retention")
      .on(exec(
            http("Darts-Portal - User - Refresh-profile")
            .post(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/user/refresh-profile")
            .headers(Headers.CommonHeaders)
          )
          .exec
            (session -> {
              String xmlPayload = RequestBodyBuilder.buildSearchCaseRequestBody(session);
              return session.set("xmlPayload", xmlPayload);
          })
          .pause(3)
          
          // Initialize `caseCount` to 0 before starting the search
          .exec(session -> session.set("caseCount", 0))

          .exec(http("Darts-Portal - Api - Cases - Search")
              .post(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/search")
              .headers(Headers.searchCaseHeaders(Headers.CommonHeaders))              
              .body(StringBody(session -> session.get("xmlPayload"))).asJson()
              .check(status().is(200))
              .check(jsonPath("$.title").optional().saveAs("errorTitle"))              
              )
              .exec(session -> {                
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
              .check(status().in(200, 403))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Cases - Transcripts"))

          .pause(3)
          .exec(
            http("Darts-Portal - Auth - Is-authenticated")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + randomNumber.nextInt())
              .headers(Headers.CommonHeaders)
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Auth - Is-authenticated"))

          .exec(
            http("Darts-Portal - Api - Cases")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/#{getCaseId}")
              .headers(Headers.caseReferer(Headers.CommonHeaders))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Cases"))

          .exec(
            http("Darts-Portal - Auth - Is-authenticated")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + randomNumber.nextInt())
              .headers(Headers.CommonHeaders)
              )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Auth - Is-authenticated"))

          .exec(
            http("Darts-Portal - User - Refresh-profile")
            .post(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/user/refresh-profile")
            .headers(Headers.CommonHeaders)
            .check(status().is(200))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - User - Refresh-profile"))

          .exec(
            http("Darts-Portal - Api - Cases")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/#{getCaseId}")
              .headers(Headers.getHeaders(8))
              .check(status().is(200))
            )   
            .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Cases"))
         
          .exec(
            http("Darts-Portal - Api - Retentions - Case_id")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/retentions?case_id=#{getCaseId}")
              .headers(Headers.getHeaders(8))
              .check(status().is(200))
          ) 
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Retentions - Case_id"))

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
            .check(status().is(200))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Retentions - Validate_only"))

          .pause(8)
          .exec(
            http("Darts-Portal - Api - Retentions")
              .post(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/retentions")
              .headers(Headers.getHeaders(9))
              .body(StringBody(session -> session.get("xmlPayload"))).asJson()
              .check(status().is(200))
            )
            .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Retentions"))

          .exec(
            http("Darts-Portal - Api - Cases")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/#{getCaseId}")
              .headers(Headers.getHeaders(8))
              .check(status().is(200))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Cases"))

          .exec(
            http("Darts-Portal - Api - Audio-requests - Not-accessed-count")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio-requests/not-accessed-count")
              .headers(Headers.getHeaders(8))
              .check(status().is(200))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Audio-requests - Not-accessed-count"))

          .exec(
            http("Darts-Portal - Api - Retentions - Case_id")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/retentions?case_id=#{getCaseId}")
              .headers(Headers.getHeaders(8))
              .check(status().is(200))
            )
            .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Retentions - Case_id"))

        );
        
    }
}