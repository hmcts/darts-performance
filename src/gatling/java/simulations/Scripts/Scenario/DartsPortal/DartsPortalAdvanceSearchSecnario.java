package simulations.Scripts.Scenario.DartsPortal;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;
import io.gatling.javaapi.core.*;
import scala.util.Random;
import simulations.Scripts.RequestBodyBuilder.RequestBodyBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public final class DartsPortalAdvanceSearchSecnario {

    private static final Random randomNumber = new Random();
    private static final String requestType = Feeders.getRandomRequestType();

    private DartsPortalAdvanceSearchSecnario() {}

    public static ChainBuilder DartsPortalAdvanceSearchSecnario() {
      return group("Darts Advance Search")
      .on(
        pause(5)
        .exec(session -> {
            String xmlSearchPayload =  RequestBodyBuilder.buildSearchCaseRequestBody(session);
            return session.set("xmlSearchPayload", xmlSearchPayload);
        })
        .exec(http("Darts-Portal - Api - Cases - Search")
        .post(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/search")
        .headers(Headers.searchCaseHeaders(Headers.CommonHeaders))
        .body(StringBody(session -> session.get("xmlSearchPayload"))).asJson()
        .check(status().saveAs("status"))
        .check(jsonPath("$[*].case_id").count().saveAs("caseCount")) // Save the count of case_id
        .check(jsonPath("$[*].case_id").findRandom().saveAs("getCaseId")) // Save a random case_id if available
        )
        .doIf(session -> session.contains("caseCount") && session.getInt("caseCount") == 0) // Check if caseCount is 0
        .then(
            exec(session -> {
                System.out.println("Empty response received. Retrying with new dates...");
                String xmlPayload = RequestBodyBuilder.buildSearchCaseRequestBody(session);
                return session.set("xmlPayload", xmlPayload);
            })
            .exec(http("Darts-Portal - Api - Cases - Search - Retry")
                .post(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/search")
                .headers(Headers.searchCaseHeaders(Headers.CommonHeaders))
                .body(StringBody(session -> session.get("xmlPayload"))).asJson()
                .check(status().is(200))
                .check(jsonPath("$[*].case_id").count().gt(0)) // Ensure response is not empty on retry
                .check(jsonPath("$[*].case_id").findRandom().saveAs("getCaseId"))
            )
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
        })         
        );
    }
}