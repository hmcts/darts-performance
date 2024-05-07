package simulations.Scripts.Scenario.DartsApi;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import simulations.Scripts.Utilities.RandomStringGenerator;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import simulations.Scripts.RequestBodyBuilder.RequestBodyBuilder;

public final class PostTranscriptionScenario {
    private static final FeederBuilder<String> feeder = csv(AppConfig.TRANSCRIPTION_POST_FILE_PATH).random();
    private PostTranscriptionScenario() {}

   

    public static ChainBuilder PostTranscription() {
        return group("Transcription Request Group")
            .on(exec(feed(feeder))
                .exec(session -> {
                    RandomStringGenerator randomStringGenerator = new RandomStringGenerator();
                    String randomComment = randomStringGenerator.generateRandomString(10);
                    return session.set("comment", randomComment);
                })
                .exec(
                    http("DARTS - Api - Transcription:POST")
                        .post(EnvironmentURL.DARTS_BASE_URL.getUrl() + "/transcriptions")
                        .headers(Headers.AuthorizationHeaders)
                        .body(StringBody(session ->
                            RequestBodyBuilder.buildTranscriptionRequestBody(
                                session.get("hearing_id"),
                                session.get("case_id"),
                                session.get("transcription_urgency_id"),
                                session.get("transcription_type_id"),
                                session.get("comment")
                            )
                        )).asJson()
                        .check(status().saveAs("statusCode"))
                        .check(jsonPath("$.transcription_id").saveAs("transcriptionId")) 
                )
                .exec(session -> {
                    Object transcriptionId = session.get("transcriptionId");
                    if (transcriptionId != null) {
                        System.out.println("Created Transcription Id: " + transcriptionId.toString());
                    } else {
                        System.out.println("No value saved using saveAs.");
                    }
                    return session;
                }));
    }  
}
