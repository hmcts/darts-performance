package simulations.Scripts.Scenario.DartsApi;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.RequestBodyBuilder.RequestBodyBuilder;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;
import simulations.Scripts.Utilities.SQLQueryProvider;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public final class CreateRetenionsScenario {

    
    private CreateRetenionsScenario() {}
    public static ChainBuilder CreateRetenions() {
        
        String sql = SQLQueryProvider.getCaseDetailsForRetentionQuery();  

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
            }
        )
        .pause(15)
        .exec(http("DARTS - Api - AutomatedTasksRequest:POST")
                .post(AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() + "/admin/automated-tasks/11/run") 
                .headers(Headers.getHeaders(24))
                .check(status().saveAs("statusCode"))
                .check(status().is(202))
        )
        .pause(5)
        .exec(session -> {
            String xmlPayload = RequestBodyBuilder.buildRetentionsPostBody(session);
            System.out.println("Retentions xmlPayload: " + xmlPayload);
            
            System.out.println("Retentions session: " + session); 
            return session.set("xmlPayload", xmlPayload);
        })      
        .exec(http("DARTS - Api - RetentionsRequest:POST")
            .post(AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() + "/retentions?validate_only=false") 
            .headers(Headers.CourthouseHeaders)
            .body(StringBody(session -> session.get("xmlPayload"))).asJson()
            .check(status().saveAs("statusCode"))
            .check(status().is(200))
        )
        .exec(session -> {
            System.out.println("Case id: " + session.getString("cas_id")+ " has had a Retention added");
        return session;
        })
        .pause(120); 
    }       
}