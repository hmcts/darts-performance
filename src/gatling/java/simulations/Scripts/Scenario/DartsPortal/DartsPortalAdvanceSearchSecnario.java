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
                    String xmlSearchPayload = RequestBodyBuilder.buildSearchCaseRequestBody(session);
                    return session.set("xmlSearchPayload", xmlSearchPayload);
                })
                .asLongAs(session -> !session.contains("caseCount") || session.getInt("caseCount") == 0)
                .on(
                    exec(http("Darts-Portal - Api - Cases - Search")
                        .post(AppConfig.EnvironmentURL.DARTS_PORTAL_BASE_URL.getUrl() + "/api/cases/search")
                        .headers(Headers.searchCaseHeaders(Headers.CommonHeaders))
                        .body(StringBody(session -> session.get("xmlSearchPayload"))).asJson()
                        .check(status().saveAs("status"))
                        .checkIf(session -> session.getInt("status") == 400).then(
                            jsonPath("$.type").saveAs("errorType"),
                            jsonPath("$.title").saveAs("errorTitle"),
                            jsonPath("$.status").saveAs("errorStatus")
                        )
                        .check(jsonPath("$[*].case_id").count().saveAs("caseCount"))
                        .check(jsonPath("$[*].case_id").findRandom().saveAs("getCaseId"))
                    )
                    .exec(session -> {
                        int statusCode = session.getInt("status");
                        if (statusCode == 400) {
                            String errorType = session.getString("errorType");
                            String errorTitle = session.getString("errorTitle");
                            int errorStatus = session.getInt("errorStatus");
                            System.out.println("Received 400 Bad Request. Details:");
                            System.out.println("Type: " + errorType);
                            System.out.println("Title: " + errorTitle);
                            System.out.println("Status: " + errorStatus);
                            // Handle 400 Bad Request as a non-error, continue execution - This is because you may get "Too many results have been returned. Please change search criteria"
                            String newXmlPayload = RequestBodyBuilder.buildSearchCaseRequestBody(session);
                            return session.set("xmlSearchPayload", newXmlPayload).markAsSucceeded();
                        } else if (session.getInt("caseCount") == 0) {
                            System.out.println("Empty response received. Retrying...");
                            String xmlPayload = RequestBodyBuilder.buildSearchCaseRequestBody(session);
                            return session.set("xmlSearchPayload", xmlPayload);
                        } else {
                            return session;
                        }
                    })
                )
                .exec(session -> {
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

                    Object errorTitle = session.get("errorTitle");
                    if (errorTitle != null) {
                        String errorMessage = "Request failed with error: " + errorTitle.toString();
                        System.out.println(errorMessage);
                        throw new RuntimeException(errorMessage);
                    }
                    return session;
                })
            );
    }
}
