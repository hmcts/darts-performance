package simulations.Scripts.Scenario.DartsSoap;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.SoapServiceEndpoint;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import simulations.Scripts.SOAPRequestBuilder.SOAPRequestBuilder;

public final class AddDocumentEventTokenScenario {

    private static final FeederBuilder<String> feeder = csv(AppConfig.AUDIO_REQUEST_POST_FILE_PATH).random();
    private AddDocumentEventTokenScenario() {}
    public static ChainBuilder AddDocumentEventToken() {
        return group("AddDocument SOAP Request Group")
            .on(exec(feed(feeder))
                .exec(session -> {
                    String xmlPayload = SOAPRequestBuilder.AddDocumentEventTokenRequest(session);
                    return session.set("xmlPayload", xmlPayload);
                })
                .exec(http("DARTS - GateWay - Soap - AddDocument - Event - Token")
                        .post(SoapServiceEndpoint.StandardService.getEndpoint())
                        .headers(Headers.SoapHeaders)
                        .body(StringBody(session -> session.get("xmlPayload")))
                        .check(status().is(200))
                        .check(xpath("//return/code").saveAs("statusCode"))
                        .check(xpath("//return/message").saveAs("message"))
            ));
    } 
}