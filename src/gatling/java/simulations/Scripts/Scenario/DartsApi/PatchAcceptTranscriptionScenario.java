package simulations.Scripts.Scenario.DartsApi;

import io.gatling.javaapi.core.ChainBuilder;
import lombok.extern.slf4j.Slf4j;
import simulations.Scripts.Headers.Headers;
import simulations.Scripts.RequestBodyBuilder.RequestBodyBuilder;
import simulations.Scripts.Utilities.AppConfig.EnvironmentURL;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

@Slf4j
public final class PatchAcceptTranscriptionScenario {
    private PatchAcceptTranscriptionScenario() {
    }


    public static ChainBuilder PatchAcceptTranscription() {
        return group("Transcription Request Group")
                .on(exec(session -> {
                    // Dynamically build the request body
                    String xmlPayload = RequestBodyBuilder.buildTranscriptionPatchAcceptRequestBody(session);
                    return session.set("xmlPayload", xmlPayload);
                })
                        .exec(
                                http("DARTS - Api - Transcription:Patch - Accept")
                                        .patch(EnvironmentURL.DARTS_BASE_URL.getUrl() + "/transcriptions/#{tra_id}")
                                        .headers(Headers.getHeaders(24))
                                        .body(StringBody(session ->
                                                RequestBodyBuilder.buildTranscriptionPatchAcceptRequestBody(session)
                                        )).asJson()
                                        .check(status().saveAs("statusCode"))
                                        .check(jsonPath("$.transcription_id").saveAs("transcriptionId"))
                        )
                        .exec(session -> {
                            Object transcriptionId = session.get("transcriptionId");
                            if (transcriptionId != null) {
                                log.info("Created Transcription Id: " + transcriptionId);
                            } else {
                                log.info("No transcription Id value saved.");
                            }
                            return session;
                        }));
    }
}
