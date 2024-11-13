package simulations.Scripts.Scenario.DartsApi;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Utilities.SQLQueryProvider;
import simulations.Scripts.Utilities.UserInfoLogger;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public final class DeleteAudioRequestScenario {

    private DeleteAudioRequestScenario() {}
    public static Object feeder = null;
    
    @SuppressWarnings("unchecked")
    public static ChainBuilder DeleteAudioRequest() {
        

        String sql = SQLQueryProvider.getTransformedMediaToDeleteQuery();  

        //Selecting which feeder to use based on fixed or Dynami data.
        if (AppConfig.isFixed) {
            feeder = (Object) Feeders.createTransformedMediaDeleteIdsCSV();
        } else {
            if (feeder == null) {
                feeder = (Object) Feeders.jdbcFeeder(sql); 
            }
        }

        return group("Audio Request Delete")
        .on(exec(feed((FeederBuilder<String>) feeder))
        //.on(feed(feeder)
            .exec(session -> {
                // Retrieve the transformed media ID from the GET request session
                String transformedMediaId = session.getString("trm_id");
                
                // Log to verify it's populated
                System.out.println("Fetched trm_id for DELETE: " + transformedMediaId);
                
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
