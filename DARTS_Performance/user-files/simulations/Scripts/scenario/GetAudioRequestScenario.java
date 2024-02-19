package scenario;

import Headers.Headers;
import Utilities.AppConfig;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import java.util.concurrent.ThreadLocalRandom;

public final class GetAudioRequestScenario {

    private static final FeederBuilder<String> feeder = csv(AppConfig.AUDIO_REQUEST_POST_FILE_PATH).random();
    private static final boolean expired = ThreadLocalRandom.current().nextBoolean();

    private GetAudioRequestScenario() {}
    public static ChainBuilder GetAudioRequest() {
        return group("Audio Request Get")
            .on(exec(feed(feeder))
                .exec(http("DARTS - Api - AudioRequest:GET")
                        .get(AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() + "/audio-requests/v2?expired=" + expired + "")
                        .headers(Headers.addAdditionalHeader(Headers.AuthorizationHeaders, true))
                        .check(status().saveAs("statusCode"))
                        .check(status().is(200))
            ));
    }   

    public static ChainBuilder GetAudioRequestPlayBack() {
        return group("Audio Request Get")
            .on(exec(feed(feeder))
                .exec(http("DARTS - Api - AudioRequest:GET PlayBack")
                        .get(AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() + "/audio-requests/playback?transformed_media_id=#{hea_id}")
                        .headers(Headers.AuthorizationHeaders)
                        .check(status().saveAs("statusCode"))
                        .check(status().is(200))
            ));
    } 

    public static ChainBuilder GetAudioRequestDownload() {
        return group("Audio Request Get")
            .on(exec(feed(feeder))
                .exec(http("DARTS - Api - AudioRequest:GET Download")
                        .get(AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() + "/audio-requests/download?transformed_media_id=#{hea_id}")
                        .headers(Headers.AuthorizationHeaders)
                        .check(status().saveAs("statusCode"))
                        .check(status().is(200))
            ));
    } 
}