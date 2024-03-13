package simulations.Scripts.Scenario.DartsSoap;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.SoapServiceEndpoint;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import simulations.Scripts.SOAPRequestBuilder.SOAPRequestBuilder;

public final class AddCourtlogSoapUserScenario {

    private static final FeederBuilder<String> feeder = csv(AppConfig.AUDIO_REQUEST_POST_FILE_PATH).random();
    private AddCourtlogSoapUserScenario() {}
    public static ChainBuilder addCourtLogSOAPUser() {
        return group("CourtLog SOAP Request Group")
            .on(exec(feed(feeder))
                .exec(session -> {
                    String xmlPayload = SOAPRequestBuilder.AddCourtLogSOAPRequest(session);
                    return session.set("xmlPayload", xmlPayload);
                })
                .exec(http("DARTS - GateWay - Soap - CourtLog - User")
                        .post(SoapServiceEndpoint.ContextRegistryService.getEndpoint())
                        .headers(Headers.SoapHeaders)
                        .body(StringBody(session -> session.get("xmlPayload")))
                        .check(status().is(200))
                        .check(xpath("//return/code").saveAs("statusCode"))
                        .check(xpath("//return/message").saveAs("message"))
            ));
    } 
}
