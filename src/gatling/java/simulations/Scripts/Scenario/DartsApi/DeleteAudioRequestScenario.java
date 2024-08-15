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
                String transformedMediaId = session.getString("trm_id");
                return session.set("trm_id", transformedMediaId);
            })
            .exec(http("DARTS - Api - AudioRequest:DELETE")
                .delete(session -> AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() + "/audio-requests/transformed_media/" + session.getString("trm_id"))
                .headers(Headers.AuthorizationHeaders)
                .check(status().saveAs("statusCode"))
                .check(status().is(204)) // Check if the status code is 204
                .checkIf(session -> session.getInt("statusCode") != 204) // Proceed with error checks only if status is not 204
                .then(
                    status().saveAs("errorStatusCode"),
                    jsonPath("$.type").optional().saveAs("errorType"), // Extract error type if it exists
                    jsonPath("$.title").optional().saveAs("errorTitle"), // Extract error title if it exists
                    jsonPath("$.status").optional().saveAs("errorStatus") // Extract error status if it exists
                )
            )
            // Log error details if the request failed
            .exec(session -> {
                String trmId = session.getString("trm_id");
                UserInfoLogger.logDetailedErrorMessage("DARTS - Api - AudioRequest:DELETE", trmId);
                return session; 
            })
        );
    }
}
