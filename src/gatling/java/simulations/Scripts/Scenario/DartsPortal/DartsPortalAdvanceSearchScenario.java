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
                .pause(5)
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
                    // Check for 200 status
                    .check(status().is(200))
                    // Save the status code if it's not 200 to handle error codes later
                    .check(status().saveAs("responseStatus"))
                )
                .exec(session -> {
                    // Get the status from the session
                    int status = session.getInt("responseStatus");

                    // If the status is 400, 502, or 504, mark the session as failed to trigger error logging
                    if (status == 400 || status == 502 || status == 504) {
                        return session.markAsFailed();
                    }
                    return session;
                })
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
            ));
    }
    
}
