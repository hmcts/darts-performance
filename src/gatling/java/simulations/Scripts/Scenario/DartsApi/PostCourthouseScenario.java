package simulations.Scripts.Scenario.DartsApi;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import simulations.Scripts.RequestBodyBuilder.RequestBodyBuilder;

public final class PostCourthouseScenario {

    private PostCourthouseScenario() {}

    public static ChainBuilder CourthousePost() {
    return group("Courthouse Api Request Group")
            .on(exec(session -> {
                    String xmlPayload = RequestBodyBuilder.buildCourtHousePostBody(session);

                    System.out.println("Code xmlPayload: " + xmlPayload);
                    System.out.println("Code session: " + session);


                    return session.set("xmlPayload", xmlPayload);
                })
                .exec(http("DARTS - Api - CourtHouse:Post")
                        .post(EnvironmentURL.DARTS_BASE_URL.getUrl() + "/admin/courthouses")
                        .headers(Headers.CourthouseHeaders)
                        .body(StringBody(session -> session.get("xmlPayload")))
                        .check(status().is(200))
            ));
    }   
}
