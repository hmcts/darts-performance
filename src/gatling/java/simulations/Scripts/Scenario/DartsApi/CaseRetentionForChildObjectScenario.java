package simulations.Scripts.Scenario.DartsApi;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.RequestBodyBuilder.RequestBodyBuilder;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public final class CaseRetentionForChildObjectScenario {

    
    private CaseRetentionForChildObjectScenario() {}
    public static ChainBuilder CaseRetentionForChildObject() {        

        // Create the JDBC feeder
        FeederBuilder<Object> feeder = Feeders.jdbcFeeder2(); 
    
        return group("Close a Case")
        .on(feed(feeder)
        .exec(session -> {
            // Extract the data from the feeder and set it to the session
            String cas_id = session.getString("cas_id");
            String case_number = session.getString("case_number");
            String cth_id = session.getString("cth_id");
            String courthouse_name = session.getString("courthouse_name");
            String courtroom_name = session.getString("courtroom_name");
            String audio_count = session.getString("audio_count");
            String transcription_doc_count = session.getString("transcription_doc_count");
            String annotation_doc_count = session.getString("annotation_doc_count");
            String case_doc_count = session.getString("case_doc_count");

            return session
                .set("cas_id", cas_id)
                .set("case_number", case_number)
                .set("cth_id", cth_id)
                .set("courthouse_name", courthouse_name)
                .set("courtroom_name", courtroom_name)
                .set("audio_count", audio_count)
                .set("transcription_doc_count", transcription_doc_count)
                .set("annotation_doc_count", annotation_doc_count)
                .set("case_doc_count", case_doc_count);
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
        .exec(session -> {
            System.out.println("Case Closed for Id:" + session.get("cas_id"));
            System.out.println("Case Closed with audio count:" + session.get("audio_count"));
            System.out.println("Case Closed with transcription doc count:" + session.get("transcription_doc_count"));
            System.out.println("Case Closed with annotation doc count:" + session.get("annotation_doc_count"));
            System.out.println("Case Closed with case doc count:" + session.get("case_doc_count"));
            return session;
        });
    }       
}