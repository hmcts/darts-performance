package simulations.Scripts.Scenario.DartsApi;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.RequestBodyBuilder.RequestBodyBuilder;
import simulations.Scripts.Utilities.AppConfig;

import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public final class PostEventsScenario {

    private PostEventsScenario() {}
    public static ChainBuilder PostEventsRequest() {
        return group("Events Request POST")
        .on(exec(session -> {
            String xmlPayload = RequestBodyBuilder.buildEventsPostBody(session);
            return session.set("xmlPayload", xmlPayload);
        })           
            .exec(http("DARTS - Api - EventsRequest:POST")
                .post(AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() + "/events") 
                .headers(Headers.CourthouseHeaders)
                .body(StringBody(session -> session.get("xmlPayload")))
                .check(status().saveAs("statusCode"))
                .check(status().is(201))
            )); 
    }       
}