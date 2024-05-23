package simulations.Scripts.Scenario.DartsApi;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.*;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import simulations.Scripts.RequestBodyBuilder.RequestBodyBuilder;

public final class PostAudioScenario {

    private static final String randomAudioFile = AppConfig.getRandomAudioFile();

    private PostAudioScenario() {}

    public static ChainBuilder PostApiAudio() {
        return group("Audio Request Group")
                .on(exec(feed(Feeders.CourtHouseAndCourtRooms))
                .exec(session -> {
                    String xmlPayload = RequestBodyBuilder.buildPostAudioApiRequest(session);
                    System.out.println("Code xmlPayload: " + xmlPayload);
                    System.out.println("Code session: " + session);
                    return session.set("xmlPayload", xmlPayload);
                })
                .exec(http("DARTS - Api - Audios:POST")
                        .post(EnvironmentURL.DARTS_BASE_URL.getUrl() + "/audios")
                        .headers(Headers.AuthorizationHeaders)                    
                        .bodyPart(StringBodyPart("metadata", session -> session.get("xmlPayload"))
                            .contentType("application/json")
                            .charset("US-ASCII")
                            .dispositionType("form-data"))
                        .bodyPart(RawFileBodyPart("file", AppConfig.CSV_FILE_COMMON_PATH + randomAudioFile)
                            .fileName(randomAudioFile)
                            .contentType("audio/mpeg")
                            .dispositionType("form-data")
                        )                
                .check(status().saveAs("statusCode"))
                .check(status().is(200))
            ));
    }
}
