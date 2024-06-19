package simulations.Scripts.Scenario.DartsApi;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.RequestBodyBuilder.RequestBodyBuilder;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;

import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public final class PostRetentionsScenario {

    private PostRetentionsScenario() {}
    public static ChainBuilder PostRetentionsRequest() {
        return group("Retentions Request POST")
        .on(//exec(feed(Feeders.createCaseHouseRoomsHearingDetails())).
            exec(session -> {
                    String xmlPayload = RequestBodyBuilder.buildRetentionsPostBody(session);
                    System.out.println("Retentions xmlPayload: " + xmlPayload);
                    System.out.println("Retentions session: " + session); 
                    return session.set("xmlPayload", xmlPayload);
            })            
            .exec(http("DARTS - Api - RetentionsRequest:POST")
                .post(AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() + "/retentions?validate_only=false") 
                .headers(Headers.CourthouseHeaders)
                .body(StringBody(session -> session.get("xmlPayload"))).asJson()
                .check(status().saveAs("statusCode"))
                .check(status().is(200))
            ));
    }       
}