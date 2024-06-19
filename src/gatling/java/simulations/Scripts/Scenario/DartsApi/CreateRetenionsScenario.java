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
        return group("Create Retenions for a Closed Case")
        .on(exec(session -> {
            String xmlPayload = RequestBodyBuilder.buildEventsPostBody(session);
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
                    String query = "UPDATE darts.case_retention SET created_ts = CURRENT_DATE + INTERVAL '8 days' WHERE cas_id = " + cas_id;
                    Feeders.executeUpdate(query);
                    return session.set("Retenions_cas_id", cas_id);
                })
        .exec(http("DARTS - Api - AutomatedTasksRequest:POST")
                .post(AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() + "/admin/automated-tasks/11/run") 
                .headers(Headers.AuthorizationHeaders)
                .check(status().saveAs("statusCode"))
                .check(status().is(202))
        ) 
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
        );
    }       
}