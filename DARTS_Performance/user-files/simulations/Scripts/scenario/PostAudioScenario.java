package scenario;

import Headers.Headers;
import Utilities.AppConfig;
import Utilities.AppConfig.EnvironmentURL;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import RequestBodyBuilder.RequestBodyBuilder;

public final class PostAudioScenario {

    private static final FeederBuilder<String> feeder = csv(AppConfig.COURT_HOUSE_AND_COURT_ROOMS_FILE_PATH).random();
    private static final String randomAudioFile = AppConfig.getRandomAudioFile();

    private PostAudioScenario() {}

    public static ChainBuilder PostApiAudio() {
        return group("Audio Request Group")
                .on(exec(feed(feeder))
                .exec(session -> {
                    String xmlPayload = RequestBodyBuilder.buildPostAudioApiRequest(session);
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
