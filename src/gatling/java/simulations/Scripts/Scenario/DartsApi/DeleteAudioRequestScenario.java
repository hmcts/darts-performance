package simulations.Scripts.Scenario.DartsApi;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public final class DeleteAudioRequestScenario {

    private DeleteAudioRequestScenario() {}
    public static ChainBuilder DeleteAudioRequest() {
        return group("Audio Request Delete")
            .on(exec(feed(Feeders.createTransformedMediaDeleteIdsCSV()))
                .exec(http("DARTS - Api - AudioRequest:DELETE")
                        .delete(AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() + "/audio-requests/transformed_media/#{getTransformedMediaId}")
                        .headers(Headers.AuthorizationHeaders)
                        .check(Feeders.saveTransformedMediaId())
                        .check(status().saveAs("statusCode"))
                        .check(status().is(200))
            ));
    }      
}