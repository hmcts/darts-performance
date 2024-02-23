package scenario;

import Headers.Headers;
import Utilities.AppConfig;
import Utilities.AppConfig.EnvironmentURL;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import RequestBodyBuilder.RequestBodyBuilder;

public final class PostCourthouseScenario {
    private static final String AUDIO_REQUEST_FILE_PATH = AppConfig.AUDIO_REQUEST_POST_FILE_PATH;
    private static final FeederBuilder<String> feeder = csv(AUDIO_REQUEST_FILE_PATH).random();

    private PostCourthouseScenario() {}

    public static ChainBuilder CourthousePost() {
    return group("Courthouse Api Request Group")
            .on(exec(feed(feeder))
                .exec(session -> {
                    String xmlPayload = RequestBodyBuilder.buildCourtHousePostBody(session);

                    System.out.println("Code xmlPayload: " + xmlPayload);
                    System.out.println("Code session: " + session);


                    return session.set("xmlPayload", xmlPayload);
                })
                .exec(http("DARTS - Api - CourtHouse:Post")
                        .post(EnvironmentURL.DARTS_BASE_URL.getUrl() + "/courthouses")
                        .headers(Headers.ApiHeaders)
                        .body(StringBody(session -> session.get("xmlPayload")))
                        .check(status().is(200))
            ));
    }   
}
