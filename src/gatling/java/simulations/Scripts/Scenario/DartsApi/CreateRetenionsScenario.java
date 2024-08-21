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

        String sql = "WITH random_cases AS (" + 
                   " SELECT cas_id, cth_id " + 
                   " FROM darts.court_case " + 
                   " ORDER BY RANDOM() LIMIT 50" + 
                "), " + 
                "case_with_courtrooms AS (" + 
                "    SELECT rc.cas_id, cc.case_number, rc.cth_id, ch.courthouse_name, cr.courtroom_name" + 
                "    FROM random_cases rc JOIN darts.court_case cc ON rc.cas_id = cc.cas_id" + 
                "    JOIN darts.courthouse ch ON rc.cth_id = ch.cth_id JOIN darts.courtroom cr ON rc.cth_id = cr.cth_id" + 
                "    ORDER BY RANDOM()" + 
                ")" + 
                "SELECT cas_id, case_number, cth_id, courthouse_name, courtroom_name" + 
                "FROM case_with_courtrooms ORDER BY cas_id LIMIT 50;";
        
    
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
            .pause(10)
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