package scenario;

import Headers.Headers;
import Utilities.AppConfig;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public final class GetAudioRequestScenario {

    private static final FeederBuilder<String> feeder = csv(AppConfig.AUDIO_REQUEST_POST_FILE_PATH).random();
    private GetAudioRequestScenario() {}
    public static ChainBuilder GetAudioRequest() {
        return group("Audio Request Get")
            .on(exec(feed(feeder))
                .exec(http("DARTS - Api - AudioRequest:GET")
                        .get(AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() + "/audio-requests/v2?expired=true")
                        .headers(Headers.addAdditionalHeader(Headers.AuthorizationHeaders, true))
                        .check(status().saveAs("statusCode"))
                        .check(status().is(200))
            ));
    }   
}