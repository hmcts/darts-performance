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
    public static Object feeder = null;
    public static Boolean isFixed = false;

    public static ChainBuilder PostaudioRequest() {

        String sql =
        "SELECT " +
        "   subquery.hea_id, " +
        "   subquery.start_ts, " +
        "   subquery.end_ts, " +
        "   subquery.usr_id " +
        "FROM ( " +
        "   SELECT " +
        "       h.hea_id, " +
        "       TO_CHAR(m.start_ts AT TIME ZONE 'UTC', 'YYYY-MM-DD\"T\"HH24:MI:SS.MS\"z\"') AS start_ts, " +
        "       TO_CHAR(m.end_ts AT TIME ZONE 'UTC', 'YYYY-MM-DD\"T\"HH24:MI:SS.MS\"z\"') AS end_ts, " +
        "       sguae.usr_id " +
        "   FROM " +
        "       darts.hearing h " +
        "   INNER JOIN " +
        "       darts.courtroom cr ON h.ctr_id = cr.ctr_id " +
        "   INNER JOIN " +
        "       darts.courthouse ch ON cr.cth_id = ch.cth_id " +
        "   INNER JOIN " +
        "       darts.court_case cc ON cc.cas_id = h.cas_id " +
        "   INNER JOIN " +
        "       darts.hearing_media_ae hma ON hma.hea_id = h.hea_id " +
        "   INNER JOIN " +
        "       darts.media m ON m.med_id = hma.med_id " +
        "   INNER JOIN " +
        "       darts.security_group_courthouse_ae sgcae ON ch.cth_id = sgcae.cth_id " +
        "   INNER JOIN " +
        "       darts.security_group_user_account_ae sguae ON sgcae.grp_id = sguae.grp_id " +
        "   WHERE " +
        "       h.hearing_date BETWEEN '2023-02-26' AND '2024-05-27' " +
        "   AND " +
        "       sguae.usr_id NOT IN (-100, -99, -69, -68, -67, -48, -44, -4, -3, -2, -1, 0, 1, -101, 221, 241, 1141) " +
        "   ORDER BY " +
        "       RANDOM() " +
        "   LIMIT 1000 " +
        ") subquery;";
    
     
        //Selecting which feeder to use based on fixed or Dynami data.
        if (isFixed) {
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
                        .headers(Headers.AuthorizationHeaders)
                        .body(StringBody(session -> session.getString("xmlPayload"))).asJson()
                        .check(status().saveAs("statusCode"))
                        .check(status().in(200, 409))
                        .check(jsonPath("$.request_id").saveAs("getRequestId"))
                        .check(bodyString().saveAs("responseBody"))
                )
                .exec(session -> {
                    String responseBody = session.getString("responseBody");
                    System.out.println("Response Body: " + responseBody);
                    int statusCode = session.getInt("statusCode");
                    String requestId = session.get("getRequestId");
    
                    System.out.println("Get Request Id:" + requestId + ", Response Status: " + session.get("statusCode"));
                    return session.set("getRequestId", requestId);
                })  
                // Only perform error handling if the status is not 200 or 409
                .exec(session -> {
                    int statusCode = session.getInt("statusCode");
                    if (statusCode != 200 && statusCode != 409) {
                        String responseBody = session.getString("responseBody");
                        System.err.println("Error: Non-200 status code: " + session.get("statusCode" + responseBody));
                        return session.set("error", true);
                    } else {
                        System.out.println("Audio Requested for hearing Id:" + session.get("hea_id") + ", Response Status: " + session.get("statusCode"));
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
            ));
    }
}
