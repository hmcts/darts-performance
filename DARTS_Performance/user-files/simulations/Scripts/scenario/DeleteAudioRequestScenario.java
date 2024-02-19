package scenario;

import Headers.Headers;
import Utilities.AppConfig;
import Utilities.Feeders;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public final class DeleteAudioRequestScenario {

    private static final FeederBuilder<String> feeder = csv(AppConfig.AUDIO_REQUEST_POST_FILE_PATH).random();

    private DeleteAudioRequestScenario() {}
    public static ChainBuilder DeleteAudioRequest() {
        return group("Audio Request Get")
            .on(exec(feed(feeder))
                .exec(http("DARTS - Api - AudioRequest:DELETE")
                        .delete(AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() + "/audio-requests/transformed_media/#{getTransformedMediaId}")
                        .headers(Headers.AuthorizationHeaders)
                        .check(Feeders.saveTransformedMediaId())
                        .check(status().saveAs("statusCode"))
                        .check(status().is(200))
            ));
    }      
}