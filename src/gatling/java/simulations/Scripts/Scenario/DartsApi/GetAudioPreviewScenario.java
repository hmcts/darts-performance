package simulations.Scripts.Scenario.DartsApi;

import io.gatling.javaapi.core.ChainBuilder;
import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public final class GetAudioPreviewScenario {


    private GetAudioPreviewScenario() {
    }

    public static ChainBuilder GetAudioPreview() {
        return group("Audio Get Preview")
                .on(exec(feed(Feeders.createAudioRequestCSV()))
                        .exec(http("DARTS - Api - Audio:GET Preview")
                                .get(AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() + "/audio/preview/20145")
                                .headers(Headers.addAdditionalHeader(Headers.getHeaders(24), false, true))
                                // .check(Feeders.saveTransformedMediaId())
                                .check(status().saveAs("statusCode"))
                                .check(status().is(200))
                        ));
    }
}                
