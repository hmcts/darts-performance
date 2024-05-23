package simulations.Scripts.Scenario.DartsApi;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;

import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import java.util.concurrent.ThreadLocalRandom;

public final class GetAudioRequestScenario {

    private static final boolean expired = ThreadLocalRandom.current().nextBoolean();

    private GetAudioRequestScenario() {}
    public static ChainBuilder GetAudioRequest() {
        return group("Audio Request Get")
            .on(exec(feed(Feeders.createAudioRequestCSV()))
                .exec(http("DARTS - Api - AudioRequest:GET")
                        .get(AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() + "/audio-requests/v2?expired=true") //+ expired + "")
                        .headers(Headers.addAdditionalHeader(Headers.AuthorizationHeaders, true, false))
                        .check(Feeders.saveTransformedMediaId())
                        .check(status().saveAs("statusCode"))
                        .check(status().is(200))
            ));
    }   

    public static ChainBuilder GetAudioRequestPlayBack() {
        return group("Audio Request Get")
            .on(exec(feed(Feeders.createAudioRequestCSV()))
                .exec(http("DARTS - Api - AudioRequest:GET PlayBack")
                        .get(AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() + "/audio-requests/playback?transformed_media_id=#{getTransformedMediaId}")
                        .headers(Headers.AuthorizationHeaders)
                        .check(status().saveAs("statusCode"))
                        .check(status().is(200))
            ));
    } 

    public static ChainBuilder GetAudioRequestDownload() {
        return group("Audio Request Get")
            .on(exec(feed(Feeders.createAudioRequestCSV()))
                .exec(http("DARTS - Api - AudioRequest:GET Download")
                        .get(AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() + "/audio-requests/download?transformed_media_id=#{getTransformedMediaId}")
                        .headers(Headers.AuthorizationHeaders)
                        .check(status().saveAs("statusCode"))
                        .check(status().is(200))
            ));
    } 
}