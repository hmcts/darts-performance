package simulations.Scripts.Scenario.DartsApi;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Utilities.UserInfoLogger;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public final class DeleteAudioRequestScenario {

    private DeleteAudioRequestScenario() {}

    public static ChainBuilder DeleteAudioRequest() {
        return group("Audio Request Delete")
            .on(exec(feed(Feeders.createTransformedMediaDeleteIdsCSV()))
            .exec(session -> {
                // Retrieve and set the transformed media ID from the session
                String transformedMediaId = session.getString("trm_id");
                return session.set("trm_id", transformedMediaId);
            })
            .exec(http("DARTS - Api - AudioRequest:DELETE")
                .delete(session -> AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() 
                        + "/audio-requests/transformed_media/" 
                        + session.getString("trm_id"))
                .headers(Headers.getHeaders(10))
                .check(status().saveAs("statusCode"))
                .check(status().in(200, 302)) // Check if the status code is 200 or 302
            )
        );
    }
}
