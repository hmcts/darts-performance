package simulations.Scripts.Scenario.DartsApi;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.RequestBodyBuilder.RequestBodyBuilder;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.Feeders;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public final class InterpreterUsedEventScenario {

    private InterpreterUsedEventScenario() {}
    public static ChainBuilder InterpreterUsedEvent() {

        String sql = 
            "WITH random_cases AS (" +
            "    SELECT DISTINCT ON (cth_id) cas_id, cth_id " +
            "    FROM darts.court_case " +
            "    ORDER BY cth_id, RANDOM()" +
            "), " +
            "case_with_courtrooms AS (" +
            "    SELECT rc.cas_id, cc.case_number, rc.cth_id, ch.courthouse_name, " +
            "           (SELECT cr.courtroom_name " +
            "            FROM darts.courtroom cr " +
            "            WHERE cr.cth_id = rc.cth_id " +
            "            ORDER BY RANDOM() LIMIT 1) AS courtroom_name " +
            "    FROM random_cases rc " +
            "    JOIN darts.court_case cc ON rc.cas_id = cc.cas_id " +
            "    JOIN darts.courthouse ch ON rc.cth_id = ch.cth_id " +
            ") " +
            "SELECT cas_id, case_number, cth_id, courthouse_name, courtroom_name " +
            "FROM case_with_courtrooms " +
            "ORDER BY cth_id;";
    
        // Create the JDBC feeder
        FeederBuilder<Object> feeder = Feeders.jdbcFeeder(sql);

        return group("Create Interpreter Used for a Case")
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
            String xmlPayload = RequestBodyBuilder.buildInterpreterUsedEventBody(session);
            return session.set("xmlPayload", xmlPayload);
        })           
            .exec(http("DARTS - Api - EventsRequest:POST")
                .post(AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() + "/events") 
                .headers(Headers.CourthouseHeaders)
                .body(StringBody(session -> session.get("xmlPayload")))
                .check(status().saveAs("statusCode"))
                .check(status().is(201))
            ));
    }       
}