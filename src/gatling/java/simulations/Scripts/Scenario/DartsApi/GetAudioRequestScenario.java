package simulations.Scripts.Scenario.DartsApi;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Utilities.SQLQueryProvider;
import simulations.Scripts.Utilities.UserInfoLogger;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public final class GetAudioRequestScenario {

    public static Object feeder = null;

    private GetAudioRequestScenario() {}


    public static ChainBuilder GetAudioRequest() {
        return group("Audio Request Get")    
            .on(//exec(feed(Feeders.createAudioRequestCSV()))
                exec(http("DARTS - Api - AudioRequest:GET")
                        .get(AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() + "/audio-requests/v2?expired=false")
                        .headers(Headers.addAdditionalHeader(Headers.getHeaders(24), true, false))
                        .check(jsonPath("$.transformed_media_details[?(@.media_request_status=='COMPLETED')].transformed_media_id").saveAs("trm_id"))
                        .check(status().saveAs("statusCode"))
                        .check(status().is(200))
                        .check(bodyString().saveAs("responseBody"))
            )
            .exec(session -> {
                    //String responseBody = session.getString("responseBody");
                    //System.out.println("Response Body: " + responseBody);
                    String transformedMediaId = session.getString("trm_id");
                    int statusCode = session.getInt("statusCode");
    
                    // Log to confirm if 'trm_id' and statusCode are populated
                    System.out.println("Fetched transformedMediaId: " + transformedMediaId + ", StatusCode: " + statusCode);
                    
                    return session;
            })    
        );
    }

    @SuppressWarnings("unchecked")
    public static ChainBuilder GetAudioRequestPlayBack() {

        String sql = SQLQueryProvider.getTransformedMediaIdForPlayBackQuery();
        
        //Selecting which feeder to use based on fixed or Dynami data.
        if (AppConfig.isFixed) {
            feeder = Feeders.createTransformedMediaPlaybackIdCSV();
        } else {
            if (feeder == null) {
                feeder = Feeders.jdbcFeeder(sql); 
            }
        }

        return group("Audio Request Get - PlayBack")
            .on(exec(feed((FeederBuilder<String>) feeder)
                .exec(session -> {
                    String transformedMediaId = session.getString("trm_id");
                    return session.set("trm_id", transformedMediaId);
                })
                .exec(http("DARTS - Api - AudioRequest:GET PlayBack")
                    .get(session -> AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() + "/audio-requests/playback?transformed_media_id=" + session.getString("trm_id"))
                    .headers(Headers.getHeaders(24))
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
        )); 
    }
    @SuppressWarnings("unchecked")
    public static ChainBuilder GetAudioRequestDownload() {

        String sql = SQLQueryProvider.getTransformedMediaIdForDownloadQuery();  
        
       // Selecting which feeder to use based on fixed or dynamic data
       if (AppConfig.isFixed) {
        feeder = Feeders.createTransformedMediaDownloadIdCSV();
    } else {
        if (feeder == null) {
            feeder = Feeders.jdbcFeeder(sql); 
        }
    }

        return group("Audio Request Get - Download")
            .on(exec(feed((FeederBuilder<String>) feeder)
                .exec(session -> {
                    String transformedMediaId = session.getString("trm_id");
                    return session.set("trm_id", transformedMediaId);
                })
                .exec(http("DARTS - Api - AudioRequest:GET Download")
                        .get(session -> AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() + "/audio-requests/download?transformed_media_id=" + session.get("trm_id"))
                        .headers(Headers.getHeaders(24))
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
                    .headers(Headers.getHeaders(24))
                    .body(StringBody(session -> session.getString("xmlPayload"))).asJson()
                        .check(
                            jsonPath("$.type").optional().saveAs("errorType"), // Extract error type if it exists
                            jsonPath("$.title").optional().saveAs("errorTitle"), // Extract error title if it exists
                            jsonPath("$.status").optional().saveAs("errorStatus") // Extract error status if it exists
                        )
                )
                // Log error details if the request failed
                .exec(UserInfoLogger.logDetailedErrorMessage("DARTS - Api - AudioRequest:GET Download"))
            ))
        ;
    } 
}