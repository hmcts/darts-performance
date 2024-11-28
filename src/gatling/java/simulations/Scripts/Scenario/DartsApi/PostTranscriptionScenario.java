package simulations.Scripts.Scenario.DartsApi;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import simulations.Scripts.RequestBodyBuilder.RequestBodyBuilder;

public final class PostTranscriptionScenario {
    private PostTranscriptionScenario() {}   

    public static ChainBuilder PostTranscription() {
        return group("Transcription Request Group")
                .on(exec(session -> {
                    // Dynamically build the request body
                    String xmlPayload = RequestBodyBuilder.buildTranscriptionRequestBody(session);
                    return session.set("xmlPayload", xmlPayload);
                })   
                .exec(
                    http("DARTS - Api - Transcription:POST")
                        .post(EnvironmentURL.DARTS_BASE_URL.getUrl() + "/transcriptions")
                        .headers(Headers.getHeaders(24))
                        .body(StringBody(session ->
                            RequestBodyBuilder.buildTranscriptionRequestBody(session)
                        )).asJson()
                        .check(status().saveAs("statusCode"))
                        .check(jsonPath("$.transcription_id").saveAs("transcriptionId")) 
                )
                .exec(session -> {
                    Object transcriptionId = session.get("transcriptionId");
                    if (transcriptionId != null) {
                        System.out.println("Created Transcription Id: " + transcriptionId.toString());
                    } else {
                        System.out.println("No transcription Id value saved.");
                    }
                    return session;
                }));
    }  
}
