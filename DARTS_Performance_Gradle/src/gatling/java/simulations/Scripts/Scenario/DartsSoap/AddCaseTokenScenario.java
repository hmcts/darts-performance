package simulations.Scripts.Scenario.DartsSoap;

import simulations.Scripts.Headers.Headers;
import simulations.Scripts.Utilities.AppConfig;
import simulations.Scripts.Utilities.AppConfig.SoapServiceEndpoint;
import io.gatling.javaapi.core.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import simulations.Scripts.SOAPRequestBuilder.SOAPRequestBuilder;
import java.util.UUID;

public final class AddCaseTokenScenario {

    private static final FeederBuilder<String> feeder = csv(AppConfig.COURT_HOUSE_AND_COURT_ROOMS_FILE_PATH).random();
    private static final String boundary = UUID.randomUUID().toString();

    private AddCaseTokenScenario() {}

    public static ChainBuilder addCaseToken() {
        return group("AddCase SOAP Request Group")
            .on(exec(feed(feeder))
            .exec(session -> {
                String xmlPayload = SOAPRequestBuilder.AddCaseTokenRequest(session);
                return session.set("xmlPayload", xmlPayload);
            })
            .exec(http("DARTS - GateWay - Soap - AddCase - Token")
                .post(SoapServiceEndpoint.StandardService.getEndpoint())
                //.headers(Headers.SoapHeaders)
                .header("SOAPAction", "\"\"")
                .header("Client-Type", "SOAPUI Gateway Suite") 
                .body(StringBody(session -> session.get("xmlPayload")))
                .check(status().is(200))
                .check(xpath("//return/code").saveAs("statusCode"))
                .check(xpath("//return/message").saveAs("message"))
        ));
    }
}