package simulations.Scripts.Scenario.DartsApi;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.RequestBodyBuilder.RequestBodyBuilder;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;

import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public final class CreateRetenionsScenario {

    
    private CreateRetenionsScenario() {}
    public static ChainBuilder CreateRetenionsScenario() {

        String sql = "WITH random_case AS (SELECT cas_id FROM darts.court_case ORDER BY RANDOM() LIMIT 1), " +
        "random_courtroom AS (SELECT cr.courtroom_name FROM darts.courtroom AS cr INNER JOIN darts.court_case AS cc ON cr.cth_id = cc.cth_id " +
        "WHERE cc.cas_id = (SELECT cas_id FROM random_case) ORDER BY RANDOM() LIMIT 1) " +
        "SELECT cc.cas_id, cc.case_number, cc.cth_id, ch.courthouse_name, rc.courtroom_name FROM darts.court_case AS cc " +
        "INNER JOIN darts.courthouse AS ch ON cc.cth_id = ch.cth_id " +
        "INNER JOIN random_courtroom AS rc ON rc.courtroom_name IS NOT NULL WHERE cc.cas_id = (SELECT cas_id FROM random_case);";
    
        // Create the JDBC feeder
        FeederBuilder<Object> feeder = Feeders.jdbcFeeder(sql);

        return group("Create Retenions for a Closed Case")
        .on(feed(feeder)
        .exec(session -> {
            // Extract the data from the feeder and set it to the session
            String cas_id = session.getString("cas_id");
            String case_number = session.getString("case_number");
            String cth_id = session.getString("cth_id");
            String courthouse_name = session.getString("courthouse_name");
            String courtroom_name = session.getString("courtroom_name");
            
            return session
                .set("cas_id", cas_id)
                .set("case_number", case_number)
                .set("cth_id", cth_id)
                .set("courthouse_name", courthouse_name)
                .set("courtroom_name", courtroom_name);
        })
        .exec(session -> {
            String xmlPayload = RequestBodyBuilder.buildEventsRetentionsPostBody(session);
            return session.set("xmlPayload", xmlPayload);
        })           
            .exec(http("DARTS - Api - EventsRequest:POST")
                .post(AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() + "/events") 
                .headers(Headers.CourthouseHeaders)
                .body(StringBody(session -> session.get("xmlPayload")))
                .check(status().saveAs("statusCode"))
                .check(status().is(201))
            ))
            .pause(5)          
                // Step 3: Change date from Database
                .exec(session -> {
                    String cas_id = session.getString("cas_id");

                    //String query = "SELECT * FROM darts.hearing_media_ae ORDER BY hea_id DESC, med_id DESC LIMIT 100";
                    String query = "UPDATE darts.case_retention SET created_ts = CURRENT_DATE - INTERVAL '8 days' WHERE cas_id = " + cas_id;
                    Feeders.executeUpdate(query);
                    return session.set("Retenions_cas_id", cas_id);
                })
            .pause(5)
        .exec(http("DARTS - Api - AutomatedTasksRequest:POST")
                .post(AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() + "/admin/automated-tasks/11/run") 
                .headers(Headers.AuthorizationHeaders)
                .check(status().saveAs("statusCode"))
                .check(status().is(202))
        ); 
        // .exec(session -> {
        //     String xmlPayload = RequestBodyBuilder.buildRetentionsPostBody(session);
        //     System.out.println("Retentions xmlPayload: " + xmlPayload);
            
        //     System.out.println("Retentions session: " + session); 
        //     return session.set("xmlPayload", xmlPayload);
        // })      
        // .exec(http("DARTS - Api - RetentionsRequest:POST")
        //     .post(AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() + "/retentions?validate_only=false") 
        //     .headers(Headers.CourthouseHeaders)
        //     .body(StringBody(session -> session.get("xmlPayload"))).asJson()
        //     .check(status().saveAs("statusCode"))
        //     .check(status().is(200))
        // );
    }       
}