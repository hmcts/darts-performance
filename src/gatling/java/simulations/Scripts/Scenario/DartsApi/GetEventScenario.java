package simulations.Scripts.Scenario.DartsApi;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.RequestBodyBuilder.RequestBodyBuilder;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Utilities.Feeders;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public final class GetEventScenario {

    private GetEventScenario() {}
    public static ChainBuilder GetEvent() {
       

        return group("Create Interpreter Used for a Case")
        .on(
            feed(Feeders.createEventsToUpdate())
            .exec(session -> {
                System.out.println("EVE_ID in session: " + session.getString("eve_id"));
                return session;
            })
            .exec(
                http("DARTS - Api - EventsRequest:GET")
                    .get(AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() + "/admin/events/#{eve_id}")
                    .headers(Headers.CourthouseHeaders)
                    .check(jsonPath("$.cases[0].case_number").saveAs("case_number"))
                    .check(jsonPath("$.courthouse.display_name").saveAs("courthouse_display_name"))
                    .check(jsonPath("$.courtroom.name").saveAs("courtroom_name"))
                    .check(jsonPath("$.hearings[0].hearing_date").saveAs("hearing_date"))

                    .check(status().saveAs("statusCode"))
                    .check(status().is(201))
            )
            .exec(session -> {
                System.out.println("case_number in session: " + session.getString("case_number"));
                System.out.println("hearing_date in session: " + session.getString("hearing_date"));

                return session;
            })
        )   
        // .exec(session -> {
        //     String xmlPayload = RequestBodyBuilder.buildInterpreterUsedForUpdatedEventBody(session);
        //     return session.set("xmlPayload", xmlPayload);
        // })           
        // .exec(http("DARTS - Api - EventsRequest:POST")
        //     .post(AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() + "/events") 
        //     .headers(Headers.CourthouseHeaders)
        //     .body(StringBody(session -> session.get("xmlPayload")))
        //     .check(status().saveAs("statusCode"))
        //     .check(status().is(201))
        // )

        .exec(session -> {
            String randomAudioFile = Feeders.getRandomAudioFile();
            String xmlPayload = RequestBodyBuilder.buildPostAudioApiRequest(session, randomAudioFile);
            System.out.println("Selected file: " + randomAudioFile);
            return session.set("randomAudioFile", randomAudioFile)
                          .set("xmlPayload", xmlPayload);
            }
        )
        .exec(http(session -> "DARTS - Api - Audios:POST: File: " + session.get("randomAudioFile"))
            .post(EnvironmentURL.DARTS_BASE_URL.getUrl() + "/audios/metadata")
            .headers(Headers.getHeaders(24))
            .bodyPart(StringBodyPart("metadata", session -> session.get("xmlPayload"))
           //     .contentType("application/json")
           //     .charset("US-ASCII")
           //     .dispositionType("form-data"))
          //  .bodyPart(RawFileBodyPart("file", session -> session.get("randomAudioFile"))
          //      .fileName(session -> session.get("randomAudioFile"))
         //       .contentType("audio/mpeg")
         //       .dispositionType("form-data")
            )
                .check(status().saveAs("statusCode"))
                .check(status().is(200))
                .check(bodyString().saveAs("responseBody"))

        )
        .exitHereIfFailed()
        .exec(session -> {
            if (!"200".equals(session.getString("statusCode"))) {
                String responseBody = session.getString("responseBody");
                System.err.println("Error: Non-200 status code: " + session.get("statusCode" + responseBody));
            } else {
                System.out.println("Audio Created for" + session.get("randomAudioFile") + ", Response Status: " + session.get("statusCode"));
            }  
            return session;
            }
        );

    }       
}