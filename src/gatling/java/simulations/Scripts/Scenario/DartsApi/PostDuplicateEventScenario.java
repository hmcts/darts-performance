package simulations.Scripts.Scenario.DartsApi;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.RequestBodyBuilder.RequestBodyBuilder;
import simulations.Scripts.Utilities.AppConfig;

import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import java.util.concurrent.atomic.AtomicInteger;

public final class PostDuplicateEventScenario {

    private static AtomicInteger pairCounter = new AtomicInteger(0);

    private PostDuplicateEventScenario() {}

    public static ChainBuilder PostDuplicateEvent() {
        return group("Events Request POST")
            .on(exec(session -> {
                // Compute event_id index (switch every 2 requests)
                int eventIdIndex = pairCounter.getAndIncrement() / 2;

                // Generate or retrieve event_id, for example Event-0000, Event-0001, etc.
                String eventId = String.format("106%04d", eventIdIndex);

                // Generate the payload with the dynamic event_id
                String xmlPayload = RequestBodyBuilder.buildDuplicateEventsPostBody(session, eventId);
                return session.set("xmlPayload", xmlPayload);
            }))
            .exec(http("DARTS - Api - EventsRequest:POST")
                .post(AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() + "/events") 
                .headers(Headers.CourthouseHeaders)
                .body(StringBody(session -> session.get("xmlPayload")))
                .check(status().saveAs("statusCode"))
                .check(status().is(201))
            );
    }
}
