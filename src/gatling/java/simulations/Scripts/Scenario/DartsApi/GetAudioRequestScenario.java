package simulations.Scripts.Scenario.DartsApi;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Utilities.UserInfoLogger;
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
        String sql = 
        "SELECT darts.transformed_media.trm_id, " + 
        "darts.transformed_media.mer_id, " + 
        "darts.media_request.hea_id, " + 
        "darts.media_request.request_status, " + 
        "darts.media_request.request_type " + 
        "FROM darts.transformed_media " + 
        "INNER JOIN " + 
        "    darts.media_request " +  
        "    ON " + 
        "    darts.transformed_media.mer_id = darts.media_request.mer_id " + 
        "WHERE darts.media_request.request_type = 'PLAYBACK' " + 
        "AND darts.media_request.request_status = 'COMPLETED' " + 
        "ORDER BY trm_id DESC LIMIT 1000;"; 
        
        // Create the JDBC feeder
        FeederBuilder<Object> feeder = Feeders.jdbcFeeder(sql);

        return group("Audio Request Get - PlayBack")
            .on(feed(feeder)
                .exec(session -> {
                    String transformedMediaId = session.getString("trm_id");
                    return session.set("trm_id", transformedMediaId);
                })
                .exec(http("DARTS - Api - AudioRequest:GET PlayBack")
                    .get(session -> AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() + "/audio-requests/playback?transformed_media_id=" + session.getString("trm_id"))
                    .headers(Headers.AuthorizationHeaders)
                    .check(status().saveAs("statusCode"))
                    .check(status().is(200))
                    .check(bodyString().saveAs("responseBody"))
                )
            ) 
            .exitHereIfFailed()
            .exec(session -> {
                if (!"200".equals(session.getString("statusCode"))) {
                    String responseBody = session.getString("responseBody");
                    System.err.println("Error: Non-200 status code: " + session.get("statusCode") + " " + responseBody);
                } else {
                    // Log the trm_id used in the DELETE request
                    String transformedMediaId = session.getString("trm_id");
                    System.out.println("Audio Request, Response Status: " + session.get("statusCode") + ", PlayBack Id: " + transformedMediaId);
                }
                return session;
            })        
            .exec(UserInfoLogger.logDetailedErrorMessage("Audio Request Get - PlayBack")
        ); 
    }

    public static ChainBuilder GetAudioRequestDownload() {
        String sql = 
        "SELECT darts.transformed_media.trm_id, " + 
        "darts.transformed_media.mer_id, " + 
        "darts.media_request.hea_id, " + 
        "darts.media_request.request_status, " + 
        "darts.media_request.request_type " + 
        "FROM darts.transformed_media " + 
        "INNER JOIN " + 
        "    darts.media_request " +  
        "    ON " + 
        "    darts.transformed_media.mer_id = darts.media_request.mer_id " + 
        "WHERE darts.media_request.request_type = 'DOWNLOAD' " + 
        "AND darts.media_request.request_status = 'COMPLETED' " + 
        "ORDER BY trm_id DESC LIMIT 1000;"; 
        
        // Create the JDBC feeder
        FeederBuilder<Object> feeder = Feeders.jdbcFeeder(sql);

        return group("Audio Request Get - Download")
            .on(feed(feeder)
                .exec(session -> {
                    String transformedMediaId = session.getString("trm_id");
                    return session.set("trm_id", transformedMediaId);
                })
                .exec(http("DARTS - Api - AudioRequest:GET Download")
                        .get(session -> AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() + "/audio-requests/download?transformed_media_id=" + session.get("trm_id"))
                        .headers(Headers.AuthorizationHeaders)
                        .check(status().saveAs("statusCode"))
                        .check(status().is(200))
                        .check(bodyString().saveAs("responseBody"))
            ))

             // Only perform error handling if the status is not 200 or 409
             .exec(session -> {
                int statusCode = session.getInt("statusCode");
                String transformedMediaId = session.get("trm_id");

                System.out.println("Audio Request, Response Status: " + session.get("statusCode") + ", Download Id: " + transformedMediaId);

                if (statusCode != 200 && statusCode != 409) {
                    return session.set("error", true);
                } else {
                    return session.set("error", false);
                }
            })
            .doIf(session -> session.getBoolean("error")).then(
                exec(
                    http("DARTS - Api - Audio-request:GET Download Error Handling")
                    .get(session -> AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() + "/audio-requests/download?transformed_media_id=" + session.get("trm_id"))
                    .headers(Headers.AuthorizationHeaders)
                        .body(StringBody(session -> session.getString("xmlPayload"))).asJson()
                        .check(
                            jsonPath("$.type").optional().saveAs("errorType"), // Extract error type if it exists
                            jsonPath("$.title").optional().saveAs("errorTitle"), // Extract error title if it exists
                            jsonPath("$.status").optional().saveAs("errorStatus") // Extract error status if it exists
                        )
                )
                // Log error details if the request failed
                .exec(UserInfoLogger.logDetailedErrorMessage("DARTS - Api - AudioRequest:GET Download"))
            )
        ;
    } 
}