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
                exec(http("Darts-Portal - User - Refresh-profile")
                    .post(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/user/refresh-profile")
                    .headers(Headers.CommonHeaders)
                )
               .pause(2, 5)
                                
                // Initialize `caseCount` to 0 before starting the search
                .exec(session -> session.set("caseCount", 0))
                .exec(session -> session.set("400Count", 0))

                .exec(session -> {
                    String searchRequestPayload = RequestBodyBuilder.buildSearchCaseRequestBody(session);
                    String email = session.getString("Email");
                    System.out.println("Generated search payload: " + searchRequestPayload + " for user: " + email);
                    return session.set("searchRequestPayload", searchRequestPayload);
                })
                .asLongAs(session -> !session.contains("caseCount") || session.getInt("caseCount") == 0)
                .on(
                    exec(http("Darts-Portal - Api - Cases - Search")
                        .post(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/search")
                        .headers(Headers.searchCaseHeaders(Headers.CommonHeaders))
                        .body(StringBody(session -> session.get("searchRequestPayload"))).asJson()
                        .check(status().in(200, 400).saveAs("status"))  // Allowing 200 and 400 status codes
                        .check(bodyString().saveAs("responseBody"))      // Save the response body
                        .check(jsonPath("$[*].case_id").count().saveAs("caseCount"))
                        .check(jsonPath("$[*].case_id").findRandom().optional().saveAs("getCaseId"))
                        .check(
                            jsonPath("$.type").optional().saveAs("errorType"),    // Extract error type if it exists
                            jsonPath("$.title").optional().saveAs("errorTitle"),  // Extract error title if it exists
                            jsonPath("$.status").optional().saveAs("errorStatus") // Extract error status if it exists
                        )
                    )
                    .exec(session -> {
                        int caseCount = session.getInt("caseCount");
                        String email = session.getString("Email");
                        int statusCode = session.getInt("status");

                        System.out.println("Search completed. caseCount: " + caseCount + " for user: " + email);

                        // Handle the 400 status code
                        if (statusCode == 400) {
                            String responseBody = session.getString("responseBody");
                            System.out.println("400 Bad Request encountered. Response: " + responseBody + " for user: " + email);
                            
                            int currentCount400 = session.getInt("400Count");                            
                            session.set("400Count", currentCount400 + 1);

                            return session.markAsSucceeded();  // Mark as passed for 400
                        }

                        // If no cases are found, retry with a new search payload
                        if (caseCount == 0) {
                            System.out.println("Empty response received. Retrying...");
                            String searchPayload = RequestBodyBuilder.buildSearchCaseRequestBody(session);
                            System.out.println("Retrying with new payload: " + searchPayload + " for user: " + email);
                            return session.set("searchRequestPayload", searchPayload);
                        }

                        System.out.println("Non-empty response received. Proceeding with caseCount: " + caseCount);
                        return session;
                    })
                    .pause(2, 5)
                    .exec(session -> {
                        int statusCode = session.getInt("status");
                        String email = session.getString("Email");
                        if (statusCode == 502 || statusCode == 504) {
                            System.out.println("Received error status code: " + statusCode + ". Marking as failed." + email + " Darts-Portal - Api - Cases - Search");
                            session = session.markAsFailed();  // Mark as failed to trigger logging in UserInfoLogger
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
