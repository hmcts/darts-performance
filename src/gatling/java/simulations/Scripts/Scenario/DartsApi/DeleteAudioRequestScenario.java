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
        String sql = 
        "SELECT darts.transformed_media.trm_id, " +
            "darts.transformed_media.mer_id, " +
            "darts.media_request.hea_id, " +
            "darts.media_request.request_status, " +
            "darts.media_request.request_type " +
        "FROM darts.transformed_media " +
        "INNER JOIN " +
            "darts.media_request " +
        "ON " +
            "darts.transformed_media.mer_id = darts.media_request.mer_id " +
        "WHERE darts.media_request.request_type = 'DOWNLOAD' " +
        "AND darts.media_request.request_status != 'DELETED' " +
        "ORDER BY trm_id ASC LIMIT 500;"; 

        // Create the JDBC feeder
        FeederBuilder<Object> feeder = Feeders.jdbcFeeder(sql);

        return group("Audio Request Delete")
        .on(feed(feeder)
            .exec(session -> {
                // Retrieve the transformed media ID from the feeder
                String transformedMediaId = session.getString("trm_id");
                
                // Log the trm_id to verify it's being populated
                System.out.println("Fetched trm_id: " + transformedMediaId);
                
                return session.set("trm_id", transformedMediaId);
            })
            .exec(http("DARTS - Api - AudioRequest:DELETE")
                .delete(session -> AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() 
                        + "/audio-requests/transformed_media/" 
                        + session.getString("trm_id"))
                .headers(Headers.getHeaders(10))
                .check(status().saveAs("statusCode"))
                .check(status().in(200, 302)) // Check if the status code is 200 or 302
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
                System.out.println("Audio Delete, Response Status: " + session.get("statusCode") + ", trm_id deleted: " + transformedMediaId);
            }
            return session;
        })        
        .exec(UserInfoLogger.logDetailedErrorMessage("DARTS - Api - Audio-request:Post"));        
    }
}
