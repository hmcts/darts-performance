package simulations.Scripts.Scenario.DartsApi;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public final class GetAudioPreviewScenario {

    private static final FeederBuilder<String> feeder = csv(AppConfig.AUDIO_REQUEST_POST_FILE_PATH).random();

    private GetAudioPreviewScenario() {}
    public static ChainBuilder GetAudioPreview() {
        return group("Audio Get Preview")
            .on(exec(feed(feeder))
                .exec(http("DARTS - Api - Audio:GET Preview")
                        .get(AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() + "/audio/preview/20145")
                        .headers(Headers.addAdditionalHeader(Headers.AuthorizationHeaders, false, true))
                       // .check(Feeders.saveTransformedMediaId())
                        .check(status().saveAs("statusCode"))
                        .check(status().is(200))
            ));
    }      
}