package simulations.Scripts.Scenario.DartsPortal;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.UserInfoLogger;
import io.gatling.javaapi.core.*;
import simulations.Scripts.RequestBodyBuilder.RequestBodyBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public final class DartsPortalAdvanceSearchScenario {

    private DartsPortalAdvanceSearchScenario() {}

    public static ChainBuilder DartsPortalAdvanceSearchScenario() {
        return group("Darts Advance Search")
            .on(

            exec(session -> {
                System.out.println("DartsPortalAdvanceSearchScenario is being called");
                return session;
            })            
                .exec(http("Darts-Portal - User - Refresh-profile")
                    .post(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/user/refresh-profile")
                    .headers(Headers.CommonHeaders)
                )
                .pause(5)
                                
                // Initialize `caseCount` to 0 before starting the search
                .exec(session -> session.set("caseCount", 0))
        
                .exec(session -> {
                    String searchRequestPayload = RequestBodyBuilder.buildSearchCaseRequestBody(session);
                    System.out.println("Generated search payload: " + searchRequestPayload);
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
                        .check(jsonPath("$[*].case_id").findRandom().optional().saveAs("getCaseId"))
                    )
                    .exec(session -> {
                        int caseCount = session.getInt("caseCount");
                        String email = session.getString("Email");
                        System.out.println("Search completed. caseCount: " + caseCount + " for user: " + email);

                        // If no cases are found, retry with a new search payload
                        if (caseCount == 0) {
                            System.out.println("Empty response received. Retrying...");
                            String searchPayload = RequestBodyBuilder.buildSearchCaseRequestBody(session);
                            System.out.println("Retrying with new payload: " + searchPayload);
                            return session.set("searchRequestPayload", searchPayload);
                        } else {
                            System.out.println("Non-empty response received. Proceeding with caseCount: " + caseCount);
                            return session;
                        }
                    })
                    .exec(session -> {
                        int statusCode = session.getInt("status");
                        if (statusCode == 400 || statusCode == 502 || statusCode == 504) {
                            System.out.println("Received error status code: " + statusCode + ". Marking as failed.");
                            return session.markAsFailed();  // Mark as failed to trigger logging in UserInfoLogger
                        }
                        return session;
                    })
                )
                .exec(UserInfoLogger.logDetailedErrorMessage("Darts-Portal - Api - Cases - Search"))

                .exec(session -> {
                    // Log non-empty response
                    System.out.println("Non-empty response received.");
                    return session;
                })
                .exec(session -> {
                    Object getCaseId = session.get("getCaseId");
                    if (getCaseId != null) {
                        System.out.println("getCaseId: " + getCaseId.toString());
                    } else {
                        System.out.println("No value saved using saveAs.");
                    }
                    return session;
                })
            );
    }
    
}
