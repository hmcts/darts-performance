package simulations.Scripts.Scenario.DartsApi;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.RequestBodyBuilder.RequestBodyBuilder;
import simulations.Scripts.Utilities.*;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public final class LinkingAudioToCaseScenario {


    private LinkingAudioToCaseScenario() {}

    static RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
        static String caseName = randomStringGenerator.generateRandomString(10);
        static String caseName2 = randomStringGenerator.generateRandomString(10);


    public static ChainBuilder LinkingAudioToCase() {
        return group("Link Audio")
            .on(exec(feed(Feeders.createCourtHouseAndCourtRooms()))
            .exec(session -> {
                String xmlPayload = RequestBodyBuilder.buildPostCaseForLinkingAudioApiRequest(session, caseName);
                return session.set("xmlPayload", xmlPayload);
            })           
            .exec(http("DARTS - Api - Case:POST")
                .post(AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() + "/cases") 
                .headers(Headers.CourthouseHeaders)
                .body(StringBody(session -> session.get("xmlPayload")))
                .check(status().saveAs("statusCode"))
                .check(status().is(201))
            ))

            // Create Audio using POST /audio

            .exec(session -> {
                String randomAudioFile = Feeders.getRandomAudioFile();
                String xmlPayload = RequestBodyBuilder.buildPostAudioLinkingForCaseApiRequest(session, randomAudioFile, caseName);
              //  System.out.println("Code xmlPayload: " + xmlPayload);
               // System.out.println("Code session: " + session);
                System.out.println("Selected file: " + randomAudioFile);
                return session.set("randomAudioFile", randomAudioFile)
                              .set("xmlPayload", xmlPayload);
                }
            )
            .exec(http(session -> "DARTS - Api - Audios:POST: File - " + session.get("randomAudioFile"))
                .post(EnvironmentURL.DARTS_BASE_URL.getUrl() + "/audios")
                .headers(Headers.getHeaders(24))
                .bodyPart(StringBodyPart("metadata", session -> session.get("xmlPayload"))
                    .contentType("application/json")
                    .charset("US-ASCII")
                    .dispositionType("form-data"))
                .bodyPart(RawFileBodyPart("file", session -> session.get("randomAudioFile"))
                    .fileName(session -> session.get("randomAudioFile"))
                    .contentType("audio/mpeg")
                    .dispositionType("form-data")
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
            )
            .exec(UserInfoLogger.logDetailedErrorMessage("DARTS - Api - Audio-request:Post"))
            
            // Create Event using POST /events
            .exec(session -> {
                String xmlPayload = RequestBodyBuilder.buildEventsForLinkingCasePostBody(session, caseName);
                return session.set("xmlPayload", xmlPayload);
            })                
            .exec(http("DARTS - Api - EventsRequest:POST")
                .post(AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() + "/events") 
                .headers(Headers.CourthouseHeaders)
                .body(StringBody(session -> session.get("xmlPayload")))
                .check(status().saveAs("statusCode"))
                .check(status().is(201))
            )            

            // Create 1 Audio using POST /audio with a different case name (case number) but with the same start and end times align with the event `date_time` created above.

            .exec(session -> {
                String randomAudioFile = Feeders.getRandomAudioFile();
                String xmlPayload = RequestBodyBuilder.buildPostAudioLinkingForCaseApiRequest(session, randomAudioFile, caseName2);
              //  System.out.println("Code xmlPayload: " + xmlPayload);
               // System.out.println("Code session: " + session);
                System.out.println("Selected file: " + randomAudioFile);
                return session.set("randomAudioFile", randomAudioFile)
                              .set("xmlPayload", xmlPayload);
                }
            )
            .exec(http(session -> "DARTS - Api - Audios:POST: File - " + session.get("randomAudioFile"))
                .post(EnvironmentURL.DARTS_BASE_URL.getUrl() + "/audios")
                .headers(Headers.getHeaders(24))
                .bodyPart(StringBodyPart("metadata", session -> session.get("xmlPayload"))
                    .contentType("application/json")
                    .charset("US-ASCII")
                    .dispositionType("form-data"))
                .bodyPart(RawFileBodyPart("file", session -> session.get("randomAudioFile"))
                    .fileName(session -> session.get("randomAudioFile"))
                    .contentType("audio/mpeg")
                    .dispositionType("form-data")
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
            )
            .exec(UserInfoLogger.logDetailedErrorMessage("DARTS - Api - Audio-request:Post"))


            // Create Event using POST /events with same case name as first created but with the same start and end time as the Audio created 1 before this.
            .exec(session -> {
                String xmlPayload = RequestBodyBuilder.buildEventsForLinkingCasePostBody(session, caseName);
                return session.set("xmlPayload", xmlPayload);
            })                
            .exec(http("DARTS - Api - EventsRequest:POST")
                .post(AppConfig.EnvironmentURL.DARTS_BASE_URL.getUrl() + "/events") 
                .headers(Headers.CourthouseHeaders)
                .body(StringBody(session -> session.get("xmlPayload")))
                .check(status().saveAs("statusCode"))
                .check(status().is(201))
            );
    }      
}