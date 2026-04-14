package simulations.Scripts.Scenario.DartsApi;

import io.gatling.javaapi.core.ChainBuilder;
import simulations.Scripts.Headers.Headers;
import simulations.Scripts.RequestBodyBuilder.RequestBodyBuilder;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public final class PostCaseSearchScenario {


    private PostCaseSearchScenario() {
    }

    public static ChainBuilder PostCaseSearch() {
        return group("Audio POST Preview")
                .on(exec(feed(Feeders.createAudioRequestCSV()))
                        .exec(session -> {
                            String xmlPayload = RequestBodyBuilder.buildPostCaseSearchApiRequest(session);
                            return session.set("xmlPayload", xmlPayload);
                        })
                        .exec(http("DARTS - Api - Case:POST Search")
                                .post(AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() + "/cases/search")
                                .headers(Headers.getHeaders(24))
                                // .check(Feeders.saveTransformedMediaId())
                                .check(status().saveAs("statusCode"))
                                .check(status().is(200))
                        ));
    }
}