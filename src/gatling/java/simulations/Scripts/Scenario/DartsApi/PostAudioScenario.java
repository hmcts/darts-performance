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
                .on(exec(feed(Feeders.CourtHouseAndCourtRooms))
                //.pause(120)
                .exec(session -> {
                    String randomAudioFile = Feeders.getRandomAudioFile();
                    String xmlPayload = RequestBodyBuilder.buildPostAudioApiRequest(session, randomAudioFile);
                    System.out.println("Code xmlPayload: " + xmlPayload);
                    System.out.println("Code session: " + session);
                    System.out.println("Selected file: " + randomAudioFile);
                    return session.set("randomAudioFile", randomAudioFile)
                                  .set("xmlPayload", xmlPayload);

                })
                .exec(http(session -> "DARTS - Api - Audios:POST: File - " + session.get("randomAudioFile"))
                        .post(EnvironmentURL.DARTS_BASE_URL.getUrl() + "/audios")
                        .headers(Headers.AuthorizationHeaders)
                        .bodyPart(StringBodyPart("metadata", session -> session.get("xmlPayload"))
                            .contentType("application/json")
                            .charset("US-ASCII")
                            .dispositionType("form-data"))
                        .bodyPart(RawFileBodyPart("file", session -> AppConfig.CSV_FILE_COMMON_PATH + session.get("randomAudioFile"))
                            .fileName(session -> session.get("randomAudioFile"))
                            .contentType("audio/mpeg")
                            .dispositionType("form-data")
                        )                
                .check(status().saveAs("statusCode"))
                .check(status().is(200))
            ));
    }
}
