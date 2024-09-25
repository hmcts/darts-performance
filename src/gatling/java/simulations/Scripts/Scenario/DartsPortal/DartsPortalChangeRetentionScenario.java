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

public final class DartsPortalChangeRetentionScenario {

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
          .pause(2, 5)
          
          // Initialize `caseCount` to 0 before starting the search
          .exec(session -> session.set("caseCount", 0))

          .exec(session -> {
            String searchRequestPayload = RequestBodyBuilder.buildSearchCaseRequestBody(session);
            return session.set("searchRequestPayload", searchRequestPayload);
          })
          .asLongAs(session -> !session.contains("caseCount") || session.getInt("caseCount") == 0)
          .on(
              exec(http("Darts-Portal - Api - Cases - Search")
                  .post(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/search")
                  .headers(Headers.searchCaseHeaders(Headers.CommonHeaders))
                  .body(StringBody(session -> session.get("searchRequestPayload"))).asJson()
                  // Check if the status is 200 to consider it as passed
                  .check(status().is(200))
                  .check(status().saveAs("status"))
                  .check(jsonPath("$[*].case_id").count().saveAs("caseCount"))
              )
              .exec(session -> {
                  int caseCount = session.getInt("caseCount");
                  String email = session.getString("Email");

                  if (caseCount == 0) {
                      // Handle empty response
                      System.out.println("Empty response received. Marking as passed and retrying... User:" + email);
                      String searchPayload = RequestBodyBuilder.buildSearchCaseRequestBody(session);
                      return session.set("searchRequestPayload", searchPayload).markAsSucceeded();
                  } else {
                      return session;
                  }
              })
              .exec(session -> {
                  int statusCode = session.getInt("status");
                  if (statusCode == 400 || statusCode == 502 || statusCode == 504) {
                      return session.markAsFailed();  // Mark as failed to trigger logging in UserInfoLogger
                  }
                  return session;
              })
          )

          .exec(session -> {
              // Log non-empty response
              System.out.println("Response received.");
              return session;
          })          
          
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Cases - Search"))
         
          .exitHereIfFailed()
          .pause(2, 5)
          .exec(
            http("Darts-Portal - Auth - Is-authenticated")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + NumberGenerator.generateRandom13DigitNumber())
              .headers(Headers.CommonHeaders)
          )
          .exec(session -> {
            Object getCaseId = session.get("getCaseId");
            if (getCaseId != null) {
            //    System.out.println("getCaseId: " + getCaseId.toString());
            } else {
                System.out.println("No Case Id value saved using saveAs.");
            }
            return session;
            }
          ) 
          .exec(
            http("Darts-Portal - Api - Cases")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/#{getCaseId}")
              .headers(Headers.searchCaseHeaders(Headers.CommonHeaders))
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Cases"))

          .exec(
            http("Darts-Portal - Api - Cases - Hearings")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/#{getCaseId}")
              .headers(Headers.searchReferer(Headers.CommonHeaders))
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Cases - Hearings"))

          .exec(
            http("Darts-Portal - Api - Cases - Transcripts")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/#{getCaseId}/transcripts")
              .headers(Headers.searchReferer(Headers.CommonHeaders))
              .check(status().in(200, 403))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Cases - #{getCaseId} - Transcripts"))

          .pause(2, 5)
          .exec(
            http("Darts-Portal - Auth - Is-authenticated")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + NumberGenerator.generateRandom13DigitNumber())
              .headers(Headers.CommonHeaders)
          )

          .exec(
            http("Darts-Portal - Api - Cases")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/#{getCaseId}")
              .headers(Headers.caseReferer(Headers.CommonHeaders))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Cases"))

          .exec(
            http("Darts-Portal - Auth - Is-authenticated")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/auth/is-authenticated?t=" + NumberGenerator.generateRandom13DigitNumber())
              .headers(Headers.CommonHeaders)
              )
          .exec(
            http("Darts-Portal - User - Refresh-profile")
            .post(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/user/refresh-profile")
            .headers(Headers.CommonHeaders)
            .check(status().is(200))
            .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - User - Refresh-profile"))

          .exec(
            http("Darts-Portal - Api - Cases")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/#{getCaseId}")
              .headers(Headers.getHeaders(8))
              .check(status().is(200))
              .check(status().saveAs("status"))
            )   
            .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Cases"))
         
          .exec(
            http("Darts-Portal - Api - Retentions - Case_id")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/retentions?case_id=#{getCaseId}")
              .headers(Headers.getHeaders(8))
              .check(status().is(200))
              .check(status().saveAs("status"))
          ) 
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Retentions - Case_id"))

          .pause(5, 10)
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
            .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Retentions - Validate_only"))

          .pause(2, 5)
          .exec(
            http("Darts-Portal - Api - Retentions")
              .post(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/retentions")
              .headers(Headers.getHeaders(9))
              .body(StringBody(session -> session.get("xmlPayload"))).asJson()
              .check(status().is(200))
              .check(status().saveAs("status"))
            )
            .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Retentions"))

          .exec(
            http("Darts-Portal - Api - Cases")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/#{getCaseId}")
              .headers(Headers.getHeaders(8))
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Cases"))

          .exec(
            http("Darts-Portal - Api - Audio-requests - Not-accessed-count")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/audio-requests/not-accessed-count")
              .headers(Headers.getHeaders(8))
              .check(status().is(200))
              .check(status().saveAs("status"))
          )
          .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Audio-requests - Not-accessed-count"))

          .exec(
            http("Darts-Portal - Api - Retentions - Case_id")
              .get(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/retentions?case_id=#{getCaseId}")
              .headers(Headers.getHeaders(8))
              .check(status().is(200))
              .check(status().saveAs("status"))
            )
            .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Retentions - Case_id"))

        );
        
    }
}