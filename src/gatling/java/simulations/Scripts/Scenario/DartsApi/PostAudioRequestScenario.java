package simulations.Scripts.Scenario.DartsApi;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Utilities.Feeders;
import io.gatling.javaapi.core.*;
import simulations.Scripts.Utilities.UserInfoLogger;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import simulations.Scripts.RequestBodyBuilder.RequestBodyBuilder;

public final class PostAudioRequestScenario {

    private PostAudioRequestScenario() {}

    public static ChainBuilder PostaudioRequest() {
        return group("Audio-request:Post")
            .on(exec(feed(Feeders.createAudioRequestCSV()))
                .exec(session -> {
                    String xmlPayload = RequestBodyBuilder.buildPOSTAudioRequestBody(session);
                    return session.set("xmlPayload", xmlPayload);
                })
                .exec(
                    http("DARTS - Api - Audio-request:Post")
                        .post(EnvironmentURL.DARTS_BASE_URL.getUrl() + "/audio-requests")
                        .headers(Headers.AuthorizationHeaders)
                        .body(StringBody(session -> session.getString("xmlPayload"))).asJson()
                        .check(status().saveAs("statusCode"))
                        .check(status().in(200, 409)) // Only proceed if status is 200 or 409
                )
                // Only perform error handling if the status is not 200 or 409
                .exec(session -> {
                    int statusCode = session.getInt("statusCode");
                    if (statusCode != 200 && statusCode != 409) {
                        return session.set("error", true);
                    } else {
                        return session.set("error", false);
                    }
                })
                .doIf(session -> session.getBoolean("error")).then(
                    exec(
                        http("DARTS - Api - Audio-request:Post Error Handling")
                            .post(EnvironmentURL.DARTS_BASE_URL.getUrl() + "/audio-requests")
                            .headers(Headers.AuthorizationHeaders)
                            .body(StringBody(session -> session.getString("xmlPayload"))).asJson()
                            .check(
                                jsonPath("$.type").optional().saveAs("errorType"), // Extract error type if it exists
                                jsonPath("$.title").optional().saveAs("errorTitle"), // Extract error title if it exists
                                jsonPath("$.status").optional().saveAs("errorStatus") // Extract error status if it exists
                            )
                    )
                    // Log error details if the request failed
                    .exec(UserInfoLogger.logDetailedErrorMessage("DARTS - Api - Audio-request:Post"))
                )
            );
    }
}
