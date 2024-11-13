package simulations.Scripts.Scenario.DartsApi;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Utilities.SQLQueryProvider;
import io.gatling.javaapi.core.*;
import simulations.Scripts.Utilities.UserInfoLogger;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import simulations.Scripts.RequestBodyBuilder.RequestBodyBuilder;

public final class PostAudioRequestScenario {

    private PostAudioRequestScenario() {}
    public static Object feeder = null;

    @SuppressWarnings("unchecked")
    public static ChainBuilder PostaudioRequest() {

        String sql = SQLQueryProvider.getHearingQuery();    
     
        //Selecting which feeder to use based on fixed or Dynami data.
        if (AppConfig.isFixed) {
            feeder = (Object) Feeders.createAudioRequestCSV();
        } else {
            if (feeder == null) {
                feeder = (Object) Feeders.jdbcFeeder(sql); 
            }
        }

        return group("Audio-request:Post")
            .on(exec(feed((FeederBuilder<String>) feeder)           
                .exec(session -> {
                    String xmlPayload = RequestBodyBuilder.buildPOSTAudioRequestBody(session);
                    System.out.println("Body request:" + xmlPayload);
                    return session.set("xmlPayload", xmlPayload);
                })
                .exec(
                    http("DARTS - Api - Audio-request:Post")
                        .post(EnvironmentURL.DARTS_BASE_URL.getUrl() + "/audio-requests")
                        .headers(Headers.getHeaders(24))
                        .body(StringBody(session -> session.getString("xmlPayload"))).asJson()
                        .check(status().saveAs("statusCode"))
                        .check(status().in(200, 409, 401))  // Add 401 to the allowed status codes
                        .check(jsonPath("$.request_id").optional().saveAs("getRequestId"))
                        .check(bodyString().saveAs("responseBody"))
                )
                .exec(session -> {
                    String responseBody = session.getString("responseBody");
                    System.out.println("Response Body: " + responseBody);
                    int statusCode = session.getInt("statusCode");
                    String requestId = session.getString("getRequestId");
                    
                    System.out.println("Get Request Id: " + requestId + ", Response Status: " + statusCode);
                    return session;
                })  
                // Handle non-200 or non-409 status codes, including 401
                .exec(session -> {
                    int statusCode = session.getInt("statusCode");
                    if (statusCode == 401) {
                        String responseBody = session.getString("responseBody");
                        System.err.println("Error 401: Unauthorized - " + responseBody);
                        return session.set("error", true);
                    } else if (statusCode != 200 && statusCode != 409) {
                        String responseBody = session.getString("responseBody");
                        System.err.println("Error: Non-200 status code: " + statusCode + " - " + responseBody);
                        return session.set("error", true);
                    } else {
                        System.out.println("Audio Requested for hearing Id: " + session.get("hea_id") + ", Response Status: " + statusCode);
                        return session.set("error", false);
                    }
                })
                .doIf(session -> session.getBoolean("error")).then(
                    exec(
                        http("DARTS - Api - Audio-request:Post Error Handling")
                            .post(EnvironmentURL.DARTS_BASE_URL.getUrl() + "/audio-requests")
                            .headers(Headers.getHeaders(24))
                            .body(StringBody(session -> session.getString("xmlPayload"))).asJson()
                            .check(
                                jsonPath("$.type").optional().saveAs("errorType"), 
                                jsonPath("$.title").optional().saveAs("errorTitle"), 
                                jsonPath("$.status").optional().saveAs("errorStatus") 
                            )
                    )
                    .exec(UserInfoLogger.logDetailedErrorMessage("DARTS - Api - Audio-request:Post"))
                )
            )
        );    
    }
}
