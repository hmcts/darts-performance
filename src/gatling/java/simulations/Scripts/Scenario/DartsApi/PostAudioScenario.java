package simulations.Scripts.Scenario.DartsApi;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.*;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import simulations.Scripts.RequestBodyBuilder.RequestBodyBuilder;

public final class PostAudioScenario {

    private PostAudioScenario() {}

    public static ChainBuilder PostApiAudio() {
        return group("Audio Request Group")
                .on(exec(feed(Feeders.createCourtHouseAndCourtRooms()))
                //.pause(120)
                .exec(session -> {
                    String randomAudioFile = Feeders.getRandomAudioFile();
                    String xmlPayload = RequestBodyBuilder.buildPostAudioApiRequest(session, randomAudioFile);
                  //  log.info("Code xmlPayload: " + xmlPayload);
                   // log.info("Code session: " + session);
                    log.info("Selected file: " + randomAudioFile);
                    return session.set("randomAudioFile", randomAudioFile)
                                  .set("xmlPayload", xmlPayload);
                    }
                )
                .exec(http(session -> "DARTS - Api - Audios:POST: File - " + session.get("randomAudioFile"))
                    .post(EnvironmentURL.DARTS_BASE_URL.getUrl() + "/audios/metadata")
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
                        log.info("Audio Created for" + session.get("randomAudioFile") + ", Response Status: " + session.get("statusCode"));
                    }  
                    return session;
                    }
                )
                .exec(UserInfoLogger.logDetailedErrorMessage("DARTS - Api - Audio-request:Post"))
            );
        }
    }